package com.example.ExpenseManagement.model;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class ExpenseTest {

    public static Expense expenseWithArguments;
    Expense expenseDefault = new Expense();

    @BeforeAll
    public static void init() {
        expenseWithArguments = new Expense();
        expenseWithArguments.setId("1");
        expenseWithArguments.setUserId("1");
        expenseWithArguments.setDescription("description");
        expenseWithArguments.setAmount(0.0);
        expenseWithArguments.setCategory("category");
        expenseWithArguments.setDate(LocalDateTime.now());
    }

    @Test
    @DisplayName("Check the initialization of all objects expenses")
    void checkInitialization () {
        Assertions.assertAll(
                () -> Assertions.assertNotNull(expenseWithArguments),
                () -> Assertions.assertNotNull(expenseDefault)
        );
    }

    @Test
    @DisplayName("Check the instance of object with arguments")
    void checkDeclarationArguments() {
        Assertions.assertAll(
                () -> Assertions.assertEquals("1", expenseWithArguments.getId()),
                () -> Assertions.assertEquals("1", expenseWithArguments.getUserId()),
                () -> Assertions.assertEquals("description", expenseWithArguments.getDescription()),
                () -> Assertions.assertEquals(0.0, expenseWithArguments.getAmount()),
                () -> Assertions.assertEquals("category", expenseWithArguments.getCategory()),
                () -> Assertions.assertInstanceOf(LocalDateTime.class, expenseWithArguments.getDate())
        );
    }

}
