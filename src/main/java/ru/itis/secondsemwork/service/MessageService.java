package ru.itis.secondsemwork.service;

import ru.itis.secondsemwork.model.dto.request.MessageRequest;
import ru.itis.secondsemwork.model.dto.response.MessageResponse;

import java.util.List;

public interface MessageService {
    MessageResponse sendMessage(MessageRequest request, Long userId);

    List<MessageResponse> getMessagesFromChat(Long chatId);

    List<MessageResponse> getMessagesFromDefaultChat();
}
