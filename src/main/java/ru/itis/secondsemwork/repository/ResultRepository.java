package ru.itis.secondsemwork.repository;

import ru.itis.secondsemwork.model.Results;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResultRepository extends JpaRepository<Results, Long> {

    @Query ("select r from Results r where exists (select h from Hand  h where count (h.cards) = 2)")
    List<Results> findAllBlackjacks();

}
