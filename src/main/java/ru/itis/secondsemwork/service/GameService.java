package ru.itis.secondsemwork.service;

import ru.itis.secondsemwork.model.Chat;
import ru.itis.secondsemwork.model.Game;
import ru.itis.secondsemwork.model.dto.request.GameRequest;
import ru.itis.secondsemwork.model.dto.response.GameResponse;
import ru.itis.secondsemwork.model.dto.response.MessageResponse;

import java.util.List;

public interface GameService {


    GameResponse createGame(GameRequest request, Long userId);

    GameResponse startGame(Long gameId, Long currentUser);

    GameResponse joinToGame(Long gameId, Long currentUser);

    void leaveGame(Long gameId, Long currentUser);

    GameResponse drawCard(Long gameId, Long currentUser);

    GameResponse pass(Long gameId, Long currentUser);

    Game findEntityById(Long gameId);

    Chat getGameChat(Long gameId);

    List<MessageResponse> getMessagesFromGame(Long gameId);

    GameResponse getGameResponse(Long gameId);

    List<GameResponse> findAllPreparing();

    void update(Long gameId, GameRequest request, Long id);

    void delete(Long gameId, Long id);
}
