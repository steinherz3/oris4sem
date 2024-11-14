package ru.itis.secondsemwork.repository;

import org.springframework.stereotype.Repository;
import ru.itis.secondsemwork.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itis.secondsemwork.repository.criteria.MessageRepositoryCustom;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>, MessageRepositoryCustom {

    List<Message> findByChatId(Long id);
}
