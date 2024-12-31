package com.example.ExpenseManagement.controller;

import com.example.ExpenseManagement.dto.InsertDebtDTO;
import com.example.ExpenseManagement.model.Debt;
import com.example.ExpenseManagement.service.DebtsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Controlador REST responsável por gerenciar as operações relacionadas a dívidas (Debts).
 * Disponibiliza endpoints para criar, listar, consultar e atualizar informações de dívidas.
 */
@RestController
@RequestMapping("/api/movimentations/debt")
public class DebtController {

    private final DebtsService debtsService;

    /**
     * Construtor da classe DebtController.
     * @param debtsService Serviço que fornece a lógica de negócio para operações com dívidas.
     */
    public DebtController (DebtsService debtsService) {
        this.debtsService = debtsService;
    }

    /**
     * Endpoint para criação de uma nova dívida (Debt).
     *
     * @param debtDTO Objeto que contém os dados necessários para criação de uma dívida.
     * @return O objeto Debt criado.
     */
    @PostMapping
    public ResponseEntity<Debt> createDebt (@RequestBody InsertDebtDTO debtDTO) {
        return ResponseEntity.ok(debtsService.createDebt(debtDTO));
    }

    /**
     * Endpoint para obter todos os registros de dívidas (Debts).
     * @return Uma lista contendo todas as dívidas registradas.
     */
    @GetMapping // ? - TUDO OK!
    public ResponseEntity<List<Debt>> getAllDebts () {
        return ResponseEntity.ok(debtsService.getAllDebts());
    }

    /**
     * Endpoint para obter um registro de dívida específico com base no ID.
     * @param idDebt Identificador único da dívida.
     * @return O objeto Debt correspondente ao ID informado.
     */
    @GetMapping("/{idDebt}") // ? - TUDO OK!
    public ResponseEntity<Debt> getOneDebt (@PathVariable String idDebt) {
        return ResponseEntity.ok(debtsService.getOneDebt(idDebt));
    }

    /**
     * Endpoint para atualizar o status de pagamento de uma dívida com base em um valor informado.
     * @param idDebt Identificador único da dívida.
     * @param value  Valor utilizado para atualizar o status de pagamento.
     * @return Resposta sem conteúdo indicando sucesso na operação.
     */
    @GetMapping("/{idDebt}/payment/value") // ? - TUDO OK!
    public ResponseEntity<Void> updateStatusPayment(@PathVariable String idDebt,
                                                    @RequestParam Double value) {
        debtsService.updateStatusPayment(idDebt, value);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint para criar um limite de valor para uma categoria específica de dívidas.
     * @param category Nome da categoria a qual o limite será aplicado.
     * @param limit    Valor do limite a ser definido para a categoria.
     * @return Resposta sem conteúdo indicando sucesso na operação.
     */
    @GetMapping("/{category}/limit")
    public ResponseEntity<Void> createLimitToCategory (
            @PathVariable String category, @RequestParam Double limit
    ) {
        debtsService.limitPerCategory(category, limit);
        return ResponseEntity.ok().build();
    }
}
