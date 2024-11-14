package ru.itis.secondsemwork.controller;

import ru.itis.secondsemwork.model.dto.request.MessageRequest;
import ru.itis.secondsemwork.security.details.UserDetailsImpl;
import ru.itis.secondsemwork.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    private MessageService messageService;

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload MessageRequest request, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        messagingTemplate.convertAndSend("/topic/chat", messageService.sendMessage(request,
                userDetails.getUser().getId()));
    }
}
