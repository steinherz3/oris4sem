package ru.itis.secondsemwork.repository;

import ru.itis.secondsemwork.model.Hand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HandRepository extends JpaRepository<Hand, Long> {
}
