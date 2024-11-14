package ru.itis.secondsemwork.service;

import ru.itis.secondsemwork.model.Hand;
import ru.itis.secondsemwork.model.Player;
import ru.itis.secondsemwork.model.dto.request.PlayerRequest;
import ru.itis.secondsemwork.model.enums.PlayerState;
import ru.itis.secondsemwork.repository.PlayerRepository;
import ru.itis.secondsemwork.util.PlayerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository repository;
    private final PlayerMapper mapper;
    @Override
    public Player findEntityById(Long userId) {
        return repository.findById(userId).orElseThrow();
    }

    @Override
    public void createPlayer(PlayerRequest request){
        Player player = mapper.toEntity(request);
        player.setState(PlayerState.OUT_OF_GAME);
        repository.save(player);
    }

    @Override
    public void clearHand(Long currentUser) {
        Player player = repository.findById(currentUser).orElseThrow();
        player.setCurrentHand(null);
        repository.save(player);
    }

    @Override
    public void setPlayerState(Long currentUser, PlayerState playerState) {
        Player player = repository.findById(currentUser).orElseThrow();
        player.setState(playerState);
        repository.save(player);
    }

    @Override
    public Hand getUserHand(Long currentUser){
        Player player = repository.findById(currentUser).orElseThrow();
        Hand hand = Optional.ofNullable(player.getCurrentHand()).orElse(Hand.builder()
                .player(player)
                .cards(new ArrayList<>())
                .build());
        player.setCurrentHand(hand);
        repository.save(player);
        return player.getCurrentHand();
    }

    @Override
    public void setNextPlayer(Player player, Player needNextPlayer) {
        needNextPlayer.setNextPlayer(player);
        repository.save(needNextPlayer);
    }
}
