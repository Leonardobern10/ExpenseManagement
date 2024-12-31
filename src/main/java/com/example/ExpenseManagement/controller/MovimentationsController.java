package com.example.ExpenseManagement.controller;

import com.example.ExpenseManagement.dto.UpdateMovimentationDTO;
import com.example.ExpenseManagement.model.Month;
import com.example.ExpenseManagement.model.movimentations.Movimentations;
import com.example.ExpenseManagement.service.MovimentationsService;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST responsável por gerenciar as movimentações financeiras.
 * Oferece endpoints para consulta, atualização, exclusão e filtragem de movimentações.
 */
@RestController
@RequestMapping("/api/movimentations")
public class MovimentationsController {

    private final MovimentationsService movimentationsService;

    /**
     * Construtor da classe {@code MovimentationsController}.
     * @param movimentationsService Serviço responsável pelas operações relacionadas às movimentações financeiras.
     */
    public MovimentationsController (MovimentationsService movimentationsService) {
        this.movimentationsService = movimentationsService;
    }

    /**
     * Obtém as movimentações financeiras paginadas para o usuário.
     * @param page Página solicitada (valor padrão é 0).
     * @param size Quantidade de itens por página (valor padrão é 10).
     * @return Um modelo paginado de movimentações financeiras.
     */
    @GetMapping
    public ResponseEntity<PagedModel<Movimentations>> getExpenses (
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        PagedModel<Movimentations> expensesPage = movimentationsService.getMovimentationsByUser(page, size);
        return ResponseEntity.ok(expensesPage);
    }

    /**
     * Obtém uma movimentação financeira específica com base no seu ID.
     * @param expenseId ID da movimentação financeira.
     * @return A movimentação correspondente ao ID fornecido.
     */
    @GetMapping("/{expenseId}")
    public Movimentations getOneExpense (@PathVariable String expenseId) {
        return movimentationsService.getOneMovimentation(expenseId);
    }

    /**
     * Exclui uma movimentação financeira com base no seu ID.
     * @param id ID da movimentação a ser excluída.
     * @return Um status HTTP indicando que a operação foi bem-sucedida.
     */
    @DeleteMapping("/{id}") // TUDO OK
    public ResponseEntity<Void> deleteMovimentation (@PathVariable String id) {
        movimentationsService.deleteMovimentation(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Atualiza uma movimentação financeira com base no seu ID.
     * @param idExpense ID da movimentação a ser atualizada.
     * @param movimentationDTO Dados atualizados para a movimentação.
     * @return Um status HTTP indicando que a operação foi bem-sucedida.
     */
    @PutMapping("/{idExpense}")
    public ResponseEntity<Void> updateMovimentation (
            @PathVariable String idExpense,
            @RequestBody UpdateMovimentationDTO movimentationDTO) {
        movimentationsService.updateMovimentation(idExpense, movimentationDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * Obtém uma lista de movimentações financeiras filtradas por categoria.
     * @param category Nome da categoria.
     * @return Uma lista de movimentações que pertencem à categoria especificada.
     */
    @GetMapping("/category")
    public ResponseEntity<List<Movimentations>> getMovimentationsByCategory (
            @RequestParam String category) {
        return ResponseEntity.ok(movimentationsService.getMovimentationsByCategory(category));
    }

    /**
     * Obtém uma lista de movimentações financeiras filtradas por ano.
     *
     * @param year Ano das movimentações.
     * @return Uma lista de movimentações ocorridas no ano especificado.
     */
    @GetMapping("/year/{year}")
    public ResponseEntity<List<Movimentations>> getMovimentationsByYear (
            @PathVariable Integer year) {
        return ResponseEntity.ok(movimentationsService.getMovimentationsByYear(year));
    }

    /**
     * Obtém uma lista de movimentações financeiras filtradas por mês de um determinado ano.
     * @param year Ano das movimentações.
     * @param month Mês das movimentações.
     * @return Uma lista de movimentações ocorridas no mês e ano especificados.
     */
    @GetMapping("/year/{year}/month")
    public ResponseEntity<List<Movimentations>> getMovimentationsByMonth (
            @PathVariable Integer year, @RequestParam Month month) {
        return ResponseEntity.ok(movimentationsService.getMovimentationsByMonth(year, month));
    }

    /**
     * Obtém uma lista de movimentações financeiras filtradas por dia de um determinado mês e ano.
     * @param year Ano das movimentações.
     * @param month Mês das movimentações.
     * @param day Dia das movimentações.
     * @return Uma lista de movimentações ocorridas no dia, mês e ano especificados.
     */
    @GetMapping("/year/{year}/month/{month}/day")
    public ResponseEntity<List<Movimentations>> getMovimentationsByDay (
            @PathVariable Integer year, @PathVariable Month month,
            @RequestParam Integer day) {
        return ResponseEntity.ok(movimentationsService.getMovimentationsByDay(year, month, day));
    }
}

