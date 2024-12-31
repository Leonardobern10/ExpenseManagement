package com.example.ExpenseManagement.service;

import com.example.ExpenseManagement.dto.InsertDebtDTO;
import com.example.ExpenseManagement.model.*;
import com.example.ExpenseManagement.model.Category;
import com.example.ExpenseManagement.model.movimentations.Movimentations;
import com.example.ExpenseManagement.model.user.User;
import com.example.ExpenseManagement.repository.MovimentationsRepository;
import com.example.ExpenseManagement.repository.MovimentationsRepositoryCustomImpl;
import com.example.ExpenseManagement.repository.UserRepository;
import com.example.ExpenseManagement.validations.DateTimeValidation;
import com.example.ExpenseManagement.validations.PaymentValidation;
import com.example.ExpenseManagement.validations.StatusPaymentValidation;
import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Serviço responsável pela gestão das dívidas dos usuários, incluindo a criação, atualização, e verificação de limites.
 * Este serviço também lida com a validação das dívidas e o envio de alertas por e-mail caso os limites de categoria sejam ultrapassados.
 */
@Service
public class DebtsService {

    private final MovimentationsRepository movimentationsRepository;
    private final UserSearchService search;
    private final UserRepository userRepository;
    private final CategoryService categoryService;

    /**
     * Construtor do serviço de dívidas.
     *
     * @param movimentationsRepository Repositório para interagir com as movimentações de dívidas.
     * @param search Serviço para buscar o usuário.
     * @param movimentationsRepositoryCustom Repositório personalizado para movimentações.
     * @param userRepository Repositório de usuários.
     * @param categoryService Serviço para gerenciar as categorias de dívidas.
     */
    public DebtsService (MovimentationsRepository movimentationsRepository,
                         UserSearchService search,
                         MovimentationsRepositoryCustomImpl movimentationsRepositoryCustom,
                         UserRepository userRepository,
                         CategoryService categoryService) {
        this.movimentationsRepository = movimentationsRepository;
        this.search = search;
        this.userRepository = userRepository;
        this.categoryService = categoryService;
    }

    /**
     * Recupera todas as dívidas.
     *
     * @return Lista de todas as dívidas.
     * @throws RuntimeException Caso ocorra algum erro ao recuperar as dívidas.
     */
    public List<Debt> getAllDebts () {     // ? - Tudo ok!
        try {
            List<Movimentations> movimentations = movimentationsRepository.findAll();
            List<Debt> debts = new ArrayList<>(List.of());
            addAllDebts(movimentations, debts);
            return debts;
        } catch ( JwtException | IllegalArgumentException e ) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Recupera uma dívida específica pelo ID.
     *
     * @param idDebt ID da dívida a ser recuperada.
     * @return A dívida correspondente ao ID.
     * @throws IllegalArgumentException Caso o ID da dívida seja inválido.
     */
    public Debt getOneDebt (String idDebt) {    // ? - Tudo ok!
        Movimentations movimentation = movimentationsRepository.findById(idDebt)
                .orElseThrow(() -> new IllegalArgumentException("Debt register is invalid!"));

        if (! (movimentation.getClass() == Debt.class) )
            throw new IllegalArgumentException("This debt is invalid!");

        return (Debt) movimentation;
    }

    /**
     * Cria uma nova dívida para um usuário, validando e configurando as informações necessárias.
     *
     * @param debt Dados necessários para criar a dívida.
     * @return A dívida criada.
     * @throws RuntimeException Caso ocorra algum erro ao criar a dívida.
     */
    public Debt createDebt(InsertDebtDTO debt) {
        try {
           User user = search.searchUsername();
           Debt newDebt = new Debt(user.getId(), debt.description(),
                    debt.amount(), debt.category(), debt.dateTime(),
                    debt.statusDebt(), debt.person());
           categoryService.createCategory(user, newDebt);
           DateTimeValidation.validate(newDebt);
           StatusPaymentValidation.validateNull(newDebt);
           sendEmailIfLimitIsUltrapassed(user, newDebt);
           userRepository.save(user);
           return movimentationsRepository.save(newDebt);
        } catch ( RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Atualiza o status de pagamento de uma dívida.
     *
     * @param idDebt ID da dívida a ser atualizada.
     * @param value Valor pago que será subtraído da dívida.
     * @throws RuntimeException Caso ocorra algum erro ao atualizar o status de pagamento.
     */
    public void updateStatusPayment (String idDebt, Double value) {
        User user = search.searchUsername();
        Debt debt = getOneDebt(idDebt);
        StatusPaymentValidation.validate(value, debt.getAmount());
        debt.setAmount(debt.getAmount() - value);

        Category category = categoryService.searchCategory(user.getCategories(), debt.getCategoryName());
        if (category != null) {
            category.setTotalValue(category.getTotalValue() - value);
        }

        PaymentValidation.validate(debt);
        ValuePaidService.insertValuePaid(debt, value);
        movimentationsRepository.save(debt);
        userRepository.save(user);
    }

    /**
     * Define o limite para uma categoria de um usuário.
     *
     * @param category Nome da categoria a ter seu limite alterado.
     * @param limit Novo limite da categoria.
     * @throws RuntimeException Caso ocorra algum erro ao definir o limite.
     */
    public void limitPerCategory (String category, Double limit) {
        try {
            User user = search.searchUsername(); // Busca pelo usuario
            Set<Category> categoryList = user.getCategories();// Lista com as categorias de um usuario
            setLimitIfCategoryExist(categoryList, category, limit);// Verifica se a categoria esta contida na lista
            userRepository.save(user);
        } catch ( RuntimeException e ) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Adiciona todas as dívidas encontradas em movimentações à lista de dívidas.
     *
     * @param movimentations Lista de movimentações para iterar.
     * @param debts Lista de dívidas a ser preenchida.
     */
    private void addAllDebts (List<Movimentations> movimentations,
                              List<Debt> debts) {
        // * - O algoritmo precisa iterar sobre toda a lista pois precisa verificar cada elemento
        for (Movimentations mov : movimentations) {
            if (mov.getClass() == Debt.class) {
                debts.add((Debt) mov);
            }
        }
    }

    /**
     * Envia um e-mail se o limite de uma categoria for ultrapassado.
     *
     * @param user O usuário que possui a categoria.
     * @param debt A dívida associada à categoria.
     */
    private void sendEmailIfLimitIsUltrapassed (User user, Debt debt) {
        Category category = categoryService.searchCategory(user.getCategories(), debt.getCategoryName());
        if (category != null && category.getLimit() != null) {
            categoryService.amountBiggerLimit(user, category);
        }
    }

    /**
     * Define o limite de uma categoria se a categoria existir na lista.
     *
     * @param categoryList Lista de categorias do usuário.
     * @param categoryName Nome da categoria a ser modificada.
     * @param limit Novo limite da categoria.
     */
    private void setLimitIfCategoryExist(Set<Category> categoryList,
                                         String categoryName, Double limit) {
        Category category = categoryService.searchCategory(categoryList, categoryName);
        if (category != null) {
            category.setLimit(limit);
        }
    }


}
