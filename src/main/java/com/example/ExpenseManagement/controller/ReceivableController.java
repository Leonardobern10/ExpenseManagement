package com.example.ExpenseManagement.controller;

import com.example.ExpenseManagement.dto.InsertReceivableDTO;
import com.example.ExpenseManagement.model.movimentations.Movimentations;
import com.example.ExpenseManagement.model.Receivable;
import com.example.ExpenseManagement.service.ReceivableServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST responsável pela gestão de recebíveis no sistema.
 * Oferece endpoints para criação, consulta e atualização de status de pagamento dos recebíveis.
 */
@RestController
@RequestMapping("/api/movimentations/receivable")
public class ReceivableController {

    private final ReceivableServices receivableServices;

    /**
     * Construtor da classe {@code ReceivableController}.
     *
     * @param receivableServices Serviço responsável pelas operações relacionadas aos recebíveis.
     */
    public ReceivableController (ReceivableServices receivableServices) {
        this.receivableServices = receivableServices;
    }

    /**
     * Cria um novo recebível com base nos dados fornecidos.
     *
     * @param receivable DTO contendo as informações do recebível a ser criado.
     * @return A movimentação criada representando o novo recebível.
     */
    @PostMapping() // ? - TUDO OK!
    public ResponseEntity<Movimentations> createReceivable (
            @RequestBody InsertReceivableDTO receivable) {
        return ResponseEntity.ok(receivableServices.createReceivable(receivable));
    }

    /**
     * Obtém todos os recebíveis cadastrados no sistema.
     *
     * @return Uma lista contendo todos os recebíveis.
     */
    @GetMapping // ? - TUDO OK!
    public ResponseEntity<List<Receivable>> getAllDebts () {
        return ResponseEntity.ok(receivableServices.getAllReceivables());
    }

    /**
     * Obtém um recebível específico com base no seu ID.
     *
     * @param idDebt ID do recebível a ser consultado.
     * @return O recebível correspondente ao ID fornecido.
     */
    @GetMapping("/{idDebt}") // ? - TUDO OK!
    public ResponseEntity<Receivable> getOneDebt (
            @PathVariable String idDebt) {
        return ResponseEntity.ok(receivableServices.getOneReceivables(idDebt));
    }

    /**
     * Atualiza o status de pagamento de um recebível com base no valor pago.
     *
     * @param idDebt ID do recebível a ser atualizado.
     * @param value Valor do pagamento recebido.
     * @return Um status HTTP indicando que a operação foi bem-sucedida.
     */
    @GetMapping("/{idDebt}/payment/value") // ? - TUDO OK!
    public ResponseEntity<Void> updateStatusPayment(
            @PathVariable String idDebt,
            @RequestParam Double value) {
        receivableServices.updateStatusReceivement(idDebt, value);
        return ResponseEntity.ok().build();
    }
}
