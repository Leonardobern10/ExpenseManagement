package com.example.ExpenseManagement.repository;

import com.example.ExpenseManagement.model.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ExpenseRepositoryCustomImpl implements ExpenseRepositoryCustom{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Expense> findExpensesByYear(String userId, int year) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("date")
                        .gte(LocalDateTime.of(year, 1, 1, 0, 0, 0, 0))
                        .lt(LocalDateTime.of(year + 1, 1, 1, 0, 0, 0, 0))
                        .and("userId").is(userId)),
                Aggregation.project("description", "category", "amount", "date")
        );

        AggregationResults<Expense> results = mongoTemplate.aggregate(aggregation, Expense.class, Expense.class);
        return results.getMappedResults();
    }

    @Override
    public List<Expense> findExpensesByMonth (String userId, int year, int month) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("date")
                        .gte(LocalDateTime.of(year, month, 1, 0, 0))
                        .lte(LocalDateTime.of(year, month, 31, 0, 0))
                        .and("userId").is(userId)),
                Aggregation.project("description", "amount", "category", "status", "date")
        );

        AggregationResults<Expense> results = mongoTemplate.aggregate(aggregation, Expense.class, Expense.class);
        return results.getMappedResults();
    }

    @Override
    public List<Expense> findExpensesByDay (String userId, int year, int month, int day) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("date")
                        .gte(LocalDateTime.of(year, month, day, 0, 0))
                        .lte(LocalDateTime.of(year, month, day, 23, 59))
                        .and("userId").is(userId)),
                Aggregation.project("description", "amount", "category", "status", "date")
                );

        AggregationResults<Expense> results = mongoTemplate.aggregate(aggregation, Expense.class, Expense.class);
        return results.getMappedResults();
    }

    @Override
    public List<Expense> findExpenseByCategory(String userId, String category) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("userId").is(userId)
                        .and("category").is(category)),
                Aggregation.project("userId","description", "amount", "category", "status", "date")
                );
        AggregationResults<Expense> results = mongoTemplate.aggregate(aggregation, Expense.class, Expense.class);
        return results.getMappedResults();
    }
}

