package ru.itis.secondsemwork.service;

import ru.itis.secondsemwork.model.Chat;
import ru.itis.secondsemwork.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{

    private GameService gameService;

    private final ChatRepository repository;

//    public ChatServiceImpl(ChatRepository repository) {
//        this.repository = repository;
//    }

    @Autowired
    //@Lazy
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public Chat createChat(Long gameId){

        Chat chat = Chat.builder()
                .game(gameService.findEntityById(gameId))
                .build();
        return repository.save(chat);
    }
}
