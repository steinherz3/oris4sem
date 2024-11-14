package ru.itis.secondsemwork.service;

public interface ResultService {
    void win(Long gameId, Long currentUser);

    void lose(Long gameId, Long currentUser);

    void dealer(Long gameId, Long currentUser);
}
