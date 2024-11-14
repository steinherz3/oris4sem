package ru.itis.secondsemwork.repository.criteria;

import ru.itis.secondsemwork.model.Message;

import java.util.List;

public interface MessageRepositoryCustom {
    List<Message> findByChatEmpty();
}