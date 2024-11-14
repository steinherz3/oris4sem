package ru.itis.secondsemwork.util;

import ru.itis.secondsemwork.model.Message;
import ru.itis.secondsemwork.model.Player;
import ru.itis.secondsemwork.model.dto.response.MessageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(target = "author", source = "player", qualifiedByName = "getAuthorUsername")
    MessageResponse toResponse(Message save);

    List<MessageResponse> toResponse(List<Message> byChatEmpty);

    @Named("getAuthorUsername")
    default String getAuthorUsername(Player player) {
        return player.getUsername();
    }
}
