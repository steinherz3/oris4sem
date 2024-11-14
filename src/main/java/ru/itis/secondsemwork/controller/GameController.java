package ru.itis.secondsemwork.controller;

import ru.itis.secondsemwork.model.dto.request.GameRequest;
import ru.itis.secondsemwork.model.dto.response.GameResponse;
import ru.itis.secondsemwork.security.details.UserDetailsImpl;
import ru.itis.secondsemwork.service.GameService;
import ru.itis.secondsemwork.service.HandService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
public class GameController {

    private final SimpMessagingTemplate messagingTemplate;

    private final GameService gameService;

    private final HandService handService;


    @MessageMapping("/game.action")
    public void sendUpdate(Long gameId){
        messagingTemplate.convertAndSend("/topic/game/"+gameId, gameService.getGameResponse(gameId));
    }

    @GetMapping("/api/game/{gameId}")
    public GameResponse getGameById(@PathVariable Long gameId){
        return gameService.getGameResponse(gameId);
    }

    @PutMapping("/api/game/draw/{gameId}")
    public void drawCard(@PathVariable Long gameId, Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        handService.drawCard(gameId, userDetails.getUser().getId());
        sendUpdate(gameId);
    }

    @PutMapping("/api/game/pass/{gameId}")
    public void pass(@PathVariable Long gameId, Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        gameService.pass(gameId, userDetails.getUser().getId());
        sendUpdate(gameId);
    }

    @PutMapping("/api/game/start/{gameId}")
    public void start(@PathVariable Long gameId, Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        gameService.startGame(gameId, userDetails.getUser().getId());
        sendUpdate(gameId);
    }

    @PutMapping("/api/game/leave/{gameId}")
    public RedirectView leaveGame (@PathVariable Long gameId, Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        gameService.leaveGame(gameId, userDetails.getUser().getId());
        sendUpdate(gameId);
        return new RedirectView("/main");
    }

    @PostMapping("/api/game/create")
    public RedirectView createGame(Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        GameResponse game = gameService.createGame(new GameRequest(0L, 3L), userDetails.getUser().getId());
        return new RedirectView("/game?id="+game.getId());
    }

    @PatchMapping("/api/game/update/{gameId}")
    public void gameUpdate(@PathVariable Long gameId, GameRequest request, Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        gameService.update(gameId, request, userDetails.getUser().getId());
    }

    @DeleteMapping("/api/game/delete/{gameId}")
    public void gameDelete(@PathVariable Long gameId, Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        gameService.delete(gameId, userDetails.getUser().getId());
    }
}
