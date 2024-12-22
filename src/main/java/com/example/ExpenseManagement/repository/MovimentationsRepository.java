package com.example.ExpenseManagement.repository;

import com.example.ExpenseManagement.model.Movimentations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimentationsRepository extends
        MongoRepository<Movimentations, String> {
    Page<Movimentations> findByUserId(String userId, Pageable pageable);
}
