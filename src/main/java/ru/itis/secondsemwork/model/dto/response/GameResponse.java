package ru.itis.secondsemwork.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameResponse {
    Long id;
    String dealerName;
    String activePlayerName;
    Long price;
    Long maxPlayers;
    Long playerAmount;
    List<PlayerResponse> players;
}
