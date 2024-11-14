package ru.itis.secondsemwork.service;

import ru.itis.secondsemwork.model.Results;
import ru.itis.secondsemwork.model.enums.ResultEnum;
import ru.itis.secondsemwork.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService{

    private PlayerService playerService;

    private GameService gameService;

    @Autowired
    @Lazy
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    @Autowired
    @Lazy
    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    private final ResultRepository repository;
    @Override
    public void win(Long gameId, Long currentUser) {
        Results results = Results.builder()
                .player(playerService.findEntityById(currentUser))
                .game(gameService.findEntityById(gameId))
                .result(ResultEnum.WINNER)
                .build();
        repository.save(results);
    }

    @Override
    public void lose(Long gameId, Long currentUser) {
        Results results = Results.builder()
                .player(playerService.findEntityById(currentUser))
                .game(gameService.findEntityById(gameId))
                .result(ResultEnum.LOOSER)
                .build();
        repository.save(results);
    }

    @Override
    public void dealer(Long gameId, Long currentUser) {
        Results results = Results.builder()
                .player(playerService.findEntityById(currentUser))
                .game(gameService.findEntityById(gameId))
                .result(ResultEnum.DEALER)
                .build();
        repository.save(results);
    }
}
