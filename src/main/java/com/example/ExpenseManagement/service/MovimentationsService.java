package com.example.ExpenseManagement.service;

import com.example.ExpenseManagement.dto.UpdateMovimentationDTO;
import com.example.ExpenseManagement.model.*;
import com.example.ExpenseManagement.model.movimentations.MovimentationUpdatedImpl;
import com.example.ExpenseManagement.model.movimentations.Movimentations;
import com.example.ExpenseManagement.model.movimentations.MovimentationsDirectorUpdate;
import com.example.ExpenseManagement.repository.*;
import io.jsonwebtoken.JwtException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovimentationsService {

    private final MovimentationsRepository movimentationsRepository;
    private final MovimentationsRepositoryCustomImpl movimentationsRepositoryCustomImpl;
    private final UserSearchService search;
    private final MovimentationUpdatedImpl movimentationUpdated;

    public MovimentationsService(MovimentationsRepository movimentationsRepository,
                                 MovimentationsRepositoryCustomImpl movimentationsRepositoryCustomImpl,
                                 MovimentationUpdatedImpl movimentationUpdated,
                                 UserSearchService search) {
        this.movimentationsRepository = movimentationsRepository;
        this.movimentationsRepositoryCustomImpl = movimentationsRepositoryCustomImpl;
        this.search = search;
        this.movimentationUpdated = null;
    }

    public PagedModel<Movimentations> getMovimentationsByUser (Integer page, Integer size) {
        try {
            String userId = search.searchUsername().getId();
            Pageable pageable = PageRequest.of(page, size);
            return new PagedModel<>(movimentationsRepository.findByUserId(userId, pageable));
        } catch ( JwtException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public Movimentations getOneMovimentation (String idMovimentation) {
        try {
            search.searchUsername();
            return movimentationsRepository.findById(idMovimentation).orElseThrow(
                    () -> new IllegalArgumentException("This expense don't exists!"));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void deleteMovimentation (String movimentationId) {
        try {
            search.searchUsername();
            movimentationsRepository.deleteById(movimentationId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void updateMovimentation (String idMovimentation, UpdateMovimentationDTO movimentation) {
        try {
            search.searchUsername();
            Movimentations oldMovimentation = getOneMovimentation(idMovimentation);
            oldMovimentation = MovimentationsDirectorUpdate
                    .construct(new MovimentationUpdatedImpl(oldMovimentation),
                    movimentation.description(), movimentation.amount(),
                    movimentation.category(), LocalDateTime.now());
            movimentationsRepository.save(oldMovimentation);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public List<Movimentations> getMovimentationsByCategory (String category) {
        try {
            String userId = search.searchUsername().getId();
            System.out.println(userId);
            return movimentationsRepositoryCustomImpl
                    .findMovimentationsByCategory(userId, category);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public List<Movimentations> getMovimentationsByYear (int year) {
        try {
            String userId = search.searchUsername().getId();
            return movimentationsRepositoryCustomImpl
                    .findMovimentationsByYear(userId, year);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public List<Movimentations> getMovimentationsByMonth (int year, Month month) {
        try {
            String userId = search.searchUsername().getId();
            return movimentationsRepositoryCustomImpl
                    .findMovimentationsByMonth(userId, year, month.getDescription());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public List<Movimentations> getMovimentationsByDay (int year, Month month, int day) {
        try {
            String userId = search.searchUsername().getId();
            return movimentationsRepositoryCustomImpl
                    .findMovimentationsByDay(userId, year, month.getDescription(), day);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

}
