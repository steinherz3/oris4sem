package ru.itis.secondsemwork.repository;

import ru.itis.secondsemwork.model.Game;
import ru.itis.secondsemwork.model.enums.GameState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("select g from Game g where g.state = :state")
    List<Game> findByState(GameState state);
}
