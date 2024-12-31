package com.example.ExpenseManagement.service;

import com.example.ExpenseManagement.dto.AlertLimitUltrapassedEmailDTO;
import com.example.ExpenseManagement.model.Category;
import com.example.ExpenseManagement.model.Debt;
import com.example.ExpenseManagement.model.user.User;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

/**
 * Serviço responsável por gerenciar as categorias de despesas dos usuários.
 * Este serviço lida com a verificação de limites de categoria e a criação ou atualização de categorias associadas a dívidas.
 */
@Service
public class CategoryService {

    private final EmailService emailService;

    /**
     * Construtor para injeção de dependências no serviço de categorias.
     *
     * @param emailService Serviço para envio de e-mails, utilizado para notificar sobre limites ultrapassados.
     */
    CategoryService (EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * Verifica se o valor total de uma categoria ultrapassou o limite e envia um e-mail de alerta para o usuário.
     *
     * @param user O usuário cujas categorias serão verificadas.
     * @param category A categoria a ser verificada quanto ao limite de valor.
     */
    public void amountBiggerLimit (User user, Category category) {
        System.out.println("amountBiggerLimit() foi chamada ");
        try {
            if ( category.getLimit() != 0 && category.getLimit() <= category.getTotalValue() ) {
                emailService
                        .sendSimpleEmail(new AlertLimitUltrapassedEmailDTO(category, user));
            }
        } catch ( RuntimeException e ) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Cria uma nova categoria de dívida ou atualiza uma categoria existente associada a uma dívida.
     *
     * @param user O usuário para o qual a categoria de dívida será criada ou atualizada.
     * @param debt A dívida que será associada a uma categoria.
     * @return True se uma nova categoria foi criada, false se uma categoria existente foi atualizada.
     */
    public boolean createCategory (User user, Debt debt) {
        // Procura uma categoria dentro de uma li
        Category category = searchCategory(user.getCategories(), debt.getCategoryName());
        if (category != null) {
            category.setTotalValue(category.getTotalValue() + debt.getAmount());
            return false; // Categoria atualizada - false
        }
        user.getCategories().add(new Category(debt.getCategoryName(), debt.getAmount()));
        return true; // Categoria criada - true
    }

    /**
     * Pesquisa uma categoria dentro do conjunto de categorias de um usuário com base no nome da categoria.
     *
     * @param categories Conjunto de categorias do usuário.
     * @param categoryName Nome da categoria a ser pesquisada.
     * @return A categoria encontrada ou null se a categoria não existir.
     */
    public Category searchCategory (Set<Category> categories, String categoryName) {
        if ( categories.isEmpty() )
            return null;

        for ( Category category : categories ) {
            if ( Objects.equals(category.getName(), categoryName) ) {
                return category;
            }
        }
        return null;
    }
}
