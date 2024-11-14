package ru.itis.secondsemwork.service;

import ru.itis.secondsemwork.model.Card;
import ru.itis.secondsemwork.model.Game;
import ru.itis.secondsemwork.model.Hand;
import ru.itis.secondsemwork.model.Player;
import ru.itis.secondsemwork.model.dto.request.CardRequest;
import ru.itis.secondsemwork.model.dto.request.DeckRequest;
import ru.itis.secondsemwork.model.enums.GameState;
import ru.itis.secondsemwork.repository.HandRepository;
import ru.itis.secondsemwork.util.game.Calculations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class HandServiceImpl implements HandService{

    private final RestTemplate restTemplate;

    private final PlayerService playerService;

    private final GameService gameService;

    private final HandRepository repository;

    private final CardService cardService;


    @Override
    public void drawCard(Long gameId, Long currentUser){
        Game game = gameService.findEntityById(gameId);
        Player player = playerService.findEntityById(currentUser);
        if (!game.getState().equals(GameState.STARTED)){
            throw new RuntimeException();
        }
        if (player.equals(game.getActivePlayer())){
            Hand hand = playerService.getUserHand(currentUser);
            if (player.equals(game.getDealer())&&17< Calculations.calculateScores(hand)){
                gameService.pass(gameId, currentUser);
            }
            DeckRequest request = restTemplate.getForObject("https://deckofcardsapi.com/api/deck/" + game.getDeckId() + "/draw/?count=" + 1, DeckRequest.class);
            assert request != null;
            //hand.getCards().add(cardMapper.toEntity(request.getCards().get(0)));
            CardRequest cardRequest = request.getCards().get(0);
            Card card = cardService.findOrCreate(cardRequest);
            hand.getCards().add(card);
            repository.save(hand);
            if (Calculations.calculateScores(hand)>=21){
                gameService.pass(gameId, currentUser);
            }
            return;
        }
        throw new RuntimeException();
    }


}
