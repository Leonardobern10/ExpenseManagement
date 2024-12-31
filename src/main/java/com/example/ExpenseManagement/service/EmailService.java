package com.example.ExpenseManagement.service;

import com.example.ExpenseManagement.dto.AlertLimitUltrapassedEmailDTO;
import com.example.ExpenseManagement.model.email.BuilderEmailDirector;
import com.example.ExpenseManagement.model.email.BuilderEmailImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Service responsável por enviar e-mails relacionados ao sistema de gerenciamento de dívidas.
 * Em particular, este serviço lida com o envio de e-mails de alerta quando o limite de categoria é ultrapassado.
 */
@Service
public class EmailService {

    @Autowired
    private final JavaMailSender javaMailSender;

    @Value("${author}")
    private String author;

    /**
     * Construtor da classe {@link EmailService}.
     *
     * @param javaMailSender o serviço responsável pelo envio de e-mails.
     */
    public EmailService (JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * Envia um e-mail simples informando o usuário de que o limite de dívida da categoria foi ultrapassado.
     * O corpo do e-mail contém detalhes sobre o limite, o valor atual e o excedente.
     *
     * @param alert o objeto que contém as informações para o e-mail, incluindo o usuário e a categoria.
     * @throws RuntimeException se ocorrer um erro durante o envio do e-mail.
     */
    public void sendSimpleEmail (AlertLimitUltrapassedEmailDTO alert) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message = BuilderEmailDirector.construct(new BuilderEmailImp(message), alert.user().getEmail(),
                    alert.category().getName(), createBody(alert),  getAuthor());
            javaMailSender.send(message);
        } catch ( RuntimeException e ) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * Cria o corpo do e-mail com informações detalhadas sobre o limite ultrapassado.
     * O corpo inclui o nome do usuário, o nome da categoria, o limite definido, o total atual e o excedente.
     *
     * @param alert o objeto que contém os dados necessários para a construção do corpo do e-mail.
     * @return o corpo do e-mail formatado como uma String.
     */
    private String createBody (AlertLimitUltrapassedEmailDTO alert) {
        return String.format(
                """
                        Prezado(a) %s,
                        
                        Esperamos que este e-mail o(a) encontre bem.
                        
                        Gostaríamos de informar que o limite de dívida estabelecido para a categoria %s foi ultrapassado. Seguem os detalhes:
                        
                            Limite definido: %.2f
                            Total atual: %.2f
                            Excedente: %.2f
                        
                        Recomendamos que você avalie os gastos relacionados a esta categoria e, se necessário, ajuste seu planejamento financeiro para evitar maiores impactos.
                        
                        Ações sugeridas:
                        
                            Revise os gastos recentes na categoria %s no sistema.
                            Considere redefinir os limites ou realocar recursos para se adequar ao seu planejamento.
                            Entre em contato com nossa equipe de suporte caso precise de assistência.
                        
                        Estamos à disposição para ajudá-lo(a) a manter suas finanças organizadas e sob controle.
                        
                        Atenciosamente,
                        Expense Management
                        Equipe do Sistema de Controle de Gastos
                        expense_management_help@expmanaghelp.com | (99) 9 9999-9999
                        """, alert.user().getUsername().toUpperCase(), alert.category().getName().toUpperCase(),
                alert.category().getLimit(), alert.category().getTotalValue(),
                (alert.category().getTotalValue() - alert.category().getLimit()),
                alert.category().getName().toUpperCase()

        );
    }

    /**
     * Retorna o nome do autor configurado na aplicação.
     *
     * @return o nome do autor.
     */
    public String getAuthor () {
        return author;
    }
}
