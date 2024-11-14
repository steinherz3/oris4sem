package ru.itis.secondsemwork.service;

import ru.itis.secondsemwork.model.Message;
import ru.itis.secondsemwork.model.dto.request.MessageRequest;
import ru.itis.secondsemwork.model.dto.response.MessageResponse;
import ru.itis.secondsemwork.repository.MessageRepository;
import ru.itis.secondsemwork.util.MessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{

    private GameService gameService;

    private PlayerService playerService;

    private final MessageRepository repository;

    private final MessageMapper mapper;


    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    @Autowired
    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }


    public MessageResponse sendMessage(MessageRequest request, Long userId) {
        Message message = Message.builder()
                .player(playerService.findEntityById(userId))
                .text(request.getText())
                .build();

        if (request.getGameId()!=null) {
            message.setChat( gameService.getGameChat(request.getGameId()));
        }
        return mapper.toResponse(repository.save(message));
    }


    @Override
    public List<MessageResponse> getMessagesFromChat(Long chatId){
        return mapper.toResponse(repository.findByChatId(chatId));
    }

    @Override
    public List<MessageResponse> getMessagesFromDefaultChat(){
        return mapper.toResponse(repository.findByChatEmpty());
    }
}
