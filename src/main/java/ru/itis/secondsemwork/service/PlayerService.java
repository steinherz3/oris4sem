package ru.itis.secondsemwork.service;

import ru.itis.secondsemwork.model.Hand;
import ru.itis.secondsemwork.model.Player;
import ru.itis.secondsemwork.model.dto.request.PlayerRequest;
import ru.itis.secondsemwork.model.enums.PlayerState;

public interface PlayerService {
    Player findEntityById(Long userId);

    void createPlayer(PlayerRequest request);

    void clearHand(Long currentUser);

    void setPlayerState(Long currentUser, PlayerState playerState);

    Hand getUserHand(Long currentUser);

    void setNextPlayer(Player player, Player needNextPlayer);
}
