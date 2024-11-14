package ru.itis.secondsemwork.controller;

import ru.itis.secondsemwork.model.dto.request.GameRequest;
import ru.itis.secondsemwork.model.dto.request.MessageRequest;
import ru.itis.secondsemwork.model.dto.response.GameResponse;
import ru.itis.secondsemwork.service.GameService;
import ru.itis.secondsemwork.service.HandService;
import ru.itis.secondsemwork.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final GameService service;

    private final HandService handService;

    private final MessageService messageService;

    @GetMapping("/test/create")
    public void create(){
        GameResponse response = service.createGame(new GameRequest(0L,3L), 1L);
        System.out.println(response.toString());
    }

    @GetMapping("/test/start/{userId}")
    public void start(@PathVariable Long userId){
        service.startGame(1L,userId);
    }

    @GetMapping("/test/join/{userId}")
    public void joint(@PathVariable Long userId){
        service.joinToGame(1L, userId);
    }

    @GetMapping("/test/leave/{userId}")
    public void leave(@PathVariable Long userId){
        service.leaveGame(1L, userId);
    }

    @GetMapping("/test/draw/{userId}")
    public void draw(@PathVariable Long userId){
        handService.drawCard(1L, userId);
       messagingTemplate.convertAndSend("/topic/game/1", service.getGameResponse(1L));
    }

    @GetMapping("/test/pass/{userId}")
    public void pass(@PathVariable Long userId){
        service.pass(1L, userId);
    }

    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("/test/message")
    public void message(){
        messagingTemplate.convertAndSend("/topic/chat", messageService.sendMessage(new MessageRequest(null, "test"),
                1L));
    }

    @GetMapping("/test/getMessage")
    public void getMessages(){
        System.out.println(messageService.getMessagesFromDefaultChat().toString());
    }
}
