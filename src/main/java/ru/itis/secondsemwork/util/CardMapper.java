package ru.itis.secondsemwork.util;

import ru.itis.secondsemwork.model.Card;
import ru.itis.secondsemwork.model.dto.request.CardRequest;
import ru.itis.secondsemwork.model.dto.response.CardResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardMapper {
    Card toEntity(CardRequest cardRequest);


    @Mapping(target = "url", source = "image")
    @Mapping(target = "value", source = "value.value")
    @Mapping(target = "suit", source = "suit")
    CardResponse toResponse(Card card);
}
