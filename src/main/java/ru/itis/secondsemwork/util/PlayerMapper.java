package ru.itis.secondsemwork.util;

import ru.itis.secondsemwork.model.Hand;
import ru.itis.secondsemwork.model.Player;
import ru.itis.secondsemwork.model.dto.request.PlayerRequest;
import ru.itis.secondsemwork.model.dto.response.PlayerResponse;
import ru.itis.secondsemwork.util.game.Calculations;
import ru.itis.secondsemwork.util.password.PasswordMapper;
import ru.itis.secondsemwork.util.password.PasswordMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {PasswordMapper.class, CardMapper.class})
public interface PlayerMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hashPassword", source = "password", qualifiedBy = PasswordMapping.class)
    Player toEntity(PlayerRequest request);

    @Mapping(target = "totalPoints", source = "currentHand", qualifiedByName = "getTotalPoints")
    @Mapping(target = "cards", source = "currentHand.cards")
    PlayerResponse toResponse(Player player);

    @Named("getTotalPoints")
    default Long getTotalPoints(Hand hand){
        try {
            return (long) Calculations.calculateScores(hand);
        }catch (Exception e){
            return 0L;
        }
    }
}
