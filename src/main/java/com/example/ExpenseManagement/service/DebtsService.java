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

@Service
public class DebtsService {

    private final MovimentationsRepository movimentationsRepository;
    private final UserSearchService search;
    private final UserRepository userRepository;
    private final CategoryService categoryService;

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

    // ? - Tudo ok!
    public List<Debt> getAllDebts () {
        try {
            List<Movimentations> movimentations = movimentationsRepository.findAll();
            List<Debt> debts = new ArrayList<>(List.of());
            addAllDebts(movimentations, debts);
            return debts;
        } catch ( JwtException | IllegalArgumentException e ) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // ? - Tudo ok!
    public Debt getOneDebt (String idDebt) {
        Movimentations movimentation = movimentationsRepository.findById(idDebt)
                .orElseThrow(() -> new IllegalArgumentException("Debt register is invalid!"));

        if (! (movimentation.getClass() == Debt.class) )
            throw new IllegalArgumentException("This debt is invalid!");

        return (Debt) movimentation;
    }

    // ? - Tudo ok!
    public Debt createDebt(InsertDebtDTO debt) {
        try {
            // Localização do usuário proprietario da sessao
            User user = search.searchUsername();

            // Criação do objeto Debt
            Debt newDebt = new Debt(user.getId(), debt.description(),
                    debt.amount(), debt.category(), debt.dateTime(),
                    debt.statusDebt(), debt.person());

            // ? - Criação da categoria caso não exista
            // Função removida de User e passada para CategoryService por
            // questões de responsabilidade
            categoryService.createCategory(user, newDebt);

            // Se dataTime não for passada a hora atual é definido
            DateTimeValidation.validate(newDebt);

            // Se statusDebt não for informada o padrão é Not_paid_off
            StatusPaymentValidation.validateNull(newDebt);

            // Se a categoria esta na lista e seu limite é ultrapassado
            // um email é disparado para o usuario para alertá-lo
            sendEmailIfLimitIsUltrapassed(user, newDebt);

            // Retorna o objeto Debt criado
            userRepository.save(user);
            return movimentationsRepository.save(newDebt);
        } catch ( RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // ? - Tudo ok!
    public void updateStatusPayment (String idDebt, Double value) {
        User user = search.searchUsername();
        Debt debt = getOneDebt(idDebt);
        StatusPaymentValidation.validate(value, debt.getAmount());
        debt.setAmount(debt.getAmount() - value);

        // ? - ATUALIZADO
        // Procura uma categoria na lista de categorias do usuario
        Category category = categoryService.searchCategory(user.getCategories(), debt.getCategoryName());
        if (category != null) {
            category.setTotalValue(category.getTotalValue() - value);
        }
        PaymentValidation.validate(debt);
        ValuePaidService.insertValuePaid(debt, value);
        movimentationsRepository.save(debt);
        userRepository.save(user);
    }

    // ? - Tudo ok!
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

    // ? - Tudo ok!
    private void addAllDebts (List<Movimentations> movimentations,
                              List<Debt> debts) {
        // * - O algoritmo precisa iterar sobre toda a lista pois precisa verificar cada elemento
        for (Movimentations mov : movimentations) {
            if (mov.getClass() == Debt.class) {
                debts.add((Debt) mov);
            }
        }
    }

    // Procura uma categoria na lista de categorias do usuario
    private void sendEmailIfLimitIsUltrapassed (User user, Debt debt) {
        Category category = categoryService.searchCategory(user.getCategories(), debt.getCategoryName());
        if (category != null && category.getLimit() != null) {
            categoryService.amountBiggerLimit(user, category);
        }
    }

    private void setLimitIfCategoryExist(Set<Category> categoryList,
                                         String categoryName, Double limit) {
        Category category = categoryService.searchCategory(categoryList, categoryName);
        if (category != null) {
            category.setLimit(limit);
        }
    }


}
