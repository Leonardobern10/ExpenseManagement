package com.example.ExpenseManagement.repository;

import com.example.ExpenseManagement.model.movimentations.Movimentations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class MovimentationsRepositoryCustomImpl implements MovimentationsRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Movimentations> findMovimentationsByYear (String userId, int year) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("date")
                        .gte(LocalDateTime.of(year, 1, 1, 0, 0))
                        .lte(LocalDateTime.of(year, 12, 31, 23, 59))
                        .and("userId").is(userId)),
                Aggregation.project("userId", "description", "amount", "categoryName", "date"));

        AggregationResults<Movimentations> results = mongoTemplate.aggregate(
                aggregation, Movimentations.class, Movimentations.class);
        return results.getMappedResults();
    }

    @Override
    public List<Movimentations> findMovimentationsByMonth (String userId, int year,
                                                           int month) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("date")
                        .gte(LocalDateTime.of(year, month, 1, 0, 0))
                        .lte(LocalDateTime.of(year, month, 31, 23, 59))
                        .and("userId").is(userId)),
                Aggregation.project("userId", "description", "amount", "categoryName", "date"));

        AggregationResults<Movimentations> results = mongoTemplate.aggregate(
                aggregation, Movimentations.class, Movimentations.class);
        return results.getMappedResults();
    }

    @Override
    public List<Movimentations> findMovimentationsByDay (String userId, int year,
                                                         int month, int day) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("date")
                        .gte(LocalDateTime.of(year, month, day, 0, 0))
                        .lte(LocalDateTime.of(year, month, day, 23, 59))
                        .and("userId").is(userId)),
                Aggregation.project("userId", "description", "amount", "categoryName", "date")
        );
        AggregationResults<Movimentations> results = mongoTemplate.aggregate(aggregation, Movimentations.class, Movimentations.class);
        return results.getMappedResults();

    }

    @Override
    public List<Movimentations> findMovimentationsByCategory (String userId,
                                                              String category) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("categoryName").is(category)
                        .and("userId").is(userId)),
                Aggregation.project("userId", "description", "amount", "categoryName", "date")
        );
        AggregationResults<Movimentations> results = mongoTemplate.aggregate(aggregation, Movimentations.class, Movimentations.class);
        return results.getMappedResults();
    }
}
