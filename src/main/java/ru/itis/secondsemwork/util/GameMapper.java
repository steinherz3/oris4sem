package ru.itis.secondsemwork.util;

import ru.itis.secondsemwork.model.Game;
import ru.itis.secondsemwork.model.Player;
import ru.itis.secondsemwork.model.dto.response.GameResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = PlayerMapper.class)
public interface GameMapper {

    @Mapping(target = "dealerName", source = "dealer", qualifiedByName = "getDealerUsername")
    @Mapping(target = "playerAmount", source = "players", qualifiedByName = "getPlayerAmount")
    @Mapping(target = "activePlayerName", source = "activePlayer", qualifiedByName = "getDealerUsername")
    GameResponse toResponse(Game save);

    List<GameResponse> toResponse(List<Game> save);

    @Named("getDealerUsername")
    default String getDealerUsername(Player player){
        return player.getUsername();
    }

    @Named("getPlayerAmount")
    default Long getPlayerAmount(List<Player> players){
        return (long) players.size();
    }
}
