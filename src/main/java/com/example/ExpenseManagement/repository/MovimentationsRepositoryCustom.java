package com.example.ExpenseManagement.repository;

import com.example.ExpenseManagement.model.movimentations.Movimentations;

import java.util.List;

/**
 * Interface personalizada para operações de consulta de movimentações financeiras.
 * Define métodos de pesquisa específicos para movimentações de um usuário, com base em ano, mês, dia ou categoria.
 */
public interface MovimentationsRepositoryCustom {

    /**
     * Recupera uma lista de movimentações financeiras de um usuário para um determinado ano.
     *
     * @param userId O identificador do usuário.
     * @param year O ano das movimentações financeiras a serem recuperadas.
     * @return Uma lista de objetos {@link Movimentations} que representam as movimentações do usuário no ano especificado.
     */
    List<Movimentations> findMovimentationsByYear (String userId, int year);

    /**
     * Recupera uma lista de movimentações financeiras de um usuário para um determinado mês de um ano.
     *
     * @param userId O identificador do usuário.
     * @param year O ano das movimentações financeiras a serem recuperadas.
     * @param month O mês das movimentações financeiras a serem recuperadas.
     * @return Uma lista de objetos {@link Movimentations} que representam as movimentações do usuário no mês especificado.
     */
    List<Movimentations> findMovimentationsByMonth (String userId, int year,
                                                    int month);

    /**
     * Recupera uma lista de movimentações financeiras de um usuário para um determinado dia de um mês e ano.
     *
     * @param userId O identificador do usuário.
     * @param year O ano das movimentações financeiras a serem recuperadas.
     * @param month O mês das movimentações financeiras a serem recuperadas.
     * @param day O dia das movimentações financeiras a serem recuperadas.
     * @return Uma lista de objetos {@link Movimentations} que representam as movimentações do usuário no dia especificado.
     */
    List<Movimentations> findMovimentationsByDay (String userId, int year,
                                                  int month, int day);

    /**
     * Recupera uma lista de movimentações financeiras de um usuário filtradas por categoria.
     *
     * @param userId O identificador do usuário.
     * @param category A categoria das movimentações financeiras a serem recuperadas.
     * @return Uma lista de objetos {@link Movimentations} que representam as movimentações do usuário para a categoria especificada.
     */
    List<Movimentations> findMovimentationsByCategory(String userId, String category);
}
