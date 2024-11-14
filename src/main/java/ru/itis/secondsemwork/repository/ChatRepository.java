package ru.itis.secondsemwork.repository;

import ru.itis.secondsemwork.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {

}
