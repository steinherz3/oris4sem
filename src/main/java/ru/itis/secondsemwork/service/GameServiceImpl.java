package ru.itis.secondsemwork.service;

import ru.itis.secondsemwork.model.Chat;
import ru.itis.secondsemwork.model.Game;
import ru.itis.secondsemwork.model.Hand;
import ru.itis.secondsemwork.model.Player;
import ru.itis.secondsemwork.model.dto.request.DeckRequest;
import ru.itis.secondsemwork.model.dto.request.GameRequest;
import ru.itis.secondsemwork.model.dto.response.GameResponse;
import ru.itis.secondsemwork.model.dto.response.MessageResponse;
import ru.itis.secondsemwork.model.enums.GameState;
import ru.itis.secondsemwork.model.enums.PlayerState;
import ru.itis.secondsemwork.repository.GameRepository;
import ru.itis.secondsemwork.util.CardMapper;
import ru.itis.secondsemwork.util.GameMapper;
import ru.itis.secondsemwork.util.game.Calculations;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final RestTemplate restTemplate;

    private final PlayerService playerService;

    private final GameRepository repository;

    private final GameMapper mapper;

    private final CardMapper cardMapper;

    private final ResultService resultService;

    private ChatService chatService;

    private final MessageService messageService;

    @Autowired
    public void setChatService(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    @Transactional
    public GameResponse createGame(GameRequest request, Long currentUser) {
        Player player = playerService.findEntityById(currentUser);
        if (PlayerState.OUT_OF_GAME.equals(player.getState())) {
            DeckRequest deckRequest = restTemplate.getForObject("https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1", DeckRequest.class);
            assert deckRequest != null;
            Game game = Game.builder()
                    .activePlayer(player)
                    .dealer(player)
                    .players(new ArrayList<>(Collections.singletonList(player)))
                    .deckId(deckRequest.getDeck_id())
                    .state(GameState.PREPARING)
                    .price(request.getPrice())
                    .maxPlayers(request.getMaxPlayers())
                    .build();
            playerService.setPlayerState(currentUser, PlayerState.IN_GAME);
            game = repository.save(game);
            chatService.createChat(game.getId());
            return mapper.toResponse(game);
        }
        throw new RuntimeException();
    }

    @Override
    public GameResponse startGame(Long gameId, Long currentUser) {
        Game game = repository.findById(gameId).orElseThrow();
        Player player = playerService.findEntityById(currentUser);
        if (GameState.PREPARING.equals(game.getState()) && player.equals(game.getActivePlayer())) {
            game.setState(GameState.STARTED);
            Player needNextPlayer = game.getActivePlayer();
            while (needNextPlayer.getNextPlayer() != null) {
                needNextPlayer = needNextPlayer.getNextPlayer();
            }
            playerService.setNextPlayer(player, needNextPlayer);
            game.setActivePlayer(game.getActivePlayer().getNextPlayer());
            return mapper.toResponse(repository.save(game));
        }
        throw new RuntimeException();
    }

    @Override
    public GameResponse joinToGame(Long gameId, Long currentUser) {
        Game game = repository.findById(gameId).orElseThrow();
        Player player = playerService.findEntityById(currentUser);
        if (PlayerState.OUT_OF_GAME.equals(player.getState()) && game.getPlayers().size() < game.getMaxPlayers()) {
            game.getPlayers().add(player);
            playerService.setPlayerState(currentUser, PlayerState.IN_GAME);
            Player needNextPlayer = game.getActivePlayer();
            while (needNextPlayer.getNextPlayer() != null) {
                needNextPlayer = needNextPlayer.getNextPlayer();
            }
            playerService.setNextPlayer(player, needNextPlayer);
            return mapper.toResponse(repository.save(game));
        }
        throw new RuntimeException();
    }

    @Override
    public void leaveGame(Long gameId, Long currentUser) {
        Game game = repository.findById(gameId).orElseThrow();
        Player player = playerService.findEntityById(currentUser);
        if (game.getPlayers().remove(player)) {
            playerService.setPlayerState(currentUser, PlayerState.OUT_OF_GAME);
            repository.save(game);
            playerService.clearHand(currentUser);
            Player needNextPlayer = game.getActivePlayer();
            while (needNextPlayer.getNextPlayer() != player) {
                needNextPlayer = needNextPlayer.getNextPlayer();
            }
            if (player.getNextPlayer() !=null) {
                playerService.setNextPlayer(player.getNextPlayer(), needNextPlayer);
                playerService.setNextPlayer(null, player);
            } else {
                playerService.setNextPlayer(null, needNextPlayer);
            }
        }
    }

    @Override
    public GameResponse drawCard(Long gameId, Long currentUser) {
        Game game = repository.findById(gameId).orElseThrow();
        Player player = playerService.findEntityById(currentUser);
        if (player.equals(game.getActivePlayer())) {
            if (player.equals(game.getDealer()) && 17 < Calculations.calculateScores(player.getCurrentHand())) {
                pass(gameId, currentUser);
            }
            DeckRequest request = restTemplate.getForObject("https://deckofcardsapi.com/api/deck/" + game.getDeckId() + "/draw/?count=" + 1, DeckRequest.class);
            assert request != null;
            Optional.ofNullable(player.getCurrentHand()).orElse(Hand.builder()
                    .player(player)
                    .cards(new ArrayList<>())
                    .build()).getCards().add(cardMapper.toEntity(request.getCards().get(0)));
            repository.save(game);
            if (Calculations.calculateScores(player.getCurrentHand()) >= 21) {
                pass(gameId, currentUser);
            }
        }
        throw new RuntimeException();
    }

    @Override
    public GameResponse pass(Long gameId, Long currentUser) {
        Game game = repository.findById(gameId).orElseThrow();
        Player player = playerService.findEntityById(currentUser);
        if (player.equals(game.getActivePlayer())) {
            if (player.equals(game.getDealer())) {
                Player dealer = game.getDealer();
                Integer count = Calculations.calculateScores(dealer.getCurrentHand());
                for (Player playerInFor : game.getPlayers()
                ) {
                    if (!playerInFor.equals(dealer)) {
                        if ((count > 21 && !Calculations.BlackJack(dealer.getCurrentHand())) || (
                                count < Calculations.calculateScores(playerInFor.getCurrentHand()) && Calculations.calculateScores(playerInFor.getCurrentHand()) <= 21
                        ) || Calculations.BlackJack(playerInFor.getCurrentHand())) {
                            resultService.win(gameId, playerInFor.getId());
                        } else {
                            resultService.lose(gameId, playerInFor.getId());
                        }
                    }
                    else
                    {
                        resultService.dealer(gameId, currentUser);
                    }
                    playerService.setNextPlayer(null, playerInFor);
                    playerService.setPlayerState(playerInFor.getId(), PlayerState.OUT_OF_GAME);
                    playerService.clearHand(playerInFor.getId());
                }

                game.setState(GameState.ENDED);
            } else {
                game.setActivePlayer(
                        game.getActivePlayer().getNextPlayer());
            }
            return mapper.toResponse(repository.save(game));
        }
        throw new RuntimeException();
    }

//    @Override
//    public void drawCard(String deckId, int count, long userId){
//        if (currentUser == userId) {
//            DeckRequest request = restTemplate.getForObject("https://deckofcardsapi.com/api/deck/" + deckId + "/draw/?count=" + count, DeckRequest.class);
//            List<Card> cards = new ArrayList<>();
//            for (CardRequest card : request.getCards()
//            ) {
//                System.out.println(card.getValue());
//                cards.add(Card.builder()
//                        .value(CardValue.parse(card.getValue()))
//                        .build());
//            }
//            Hand hand = Hand.builder()
//                    .cards(cards)
//                    .build();
//            System.out.println(Calculations.calculateScores(hand));
//            currentUser++;
//        }
//    }

    @Override
    public Game findEntityById(Long gameId) {
        return repository.findById(gameId).orElseThrow();
    }

    @Override
    public Chat getGameChat(Long gameId) {
        return Optional.ofNullable(repository.findById(gameId).orElseThrow().getChat()).orElse(chatService.createChat(gameId));
    }

    @Override
    public List<MessageResponse> getMessagesFromGame(Long gameId){
        return messageService.getMessagesFromChat(repository.findById(gameId).orElseThrow().getChat().getId());
    }

    @Override
    public GameResponse getGameResponse(Long gameId) {
        return mapper.toResponse(repository.findById(gameId).orElseThrow());
    }

    @Override
    public List<GameResponse> findAllPreparing() {
        return mapper.toResponse(repository.findByState(GameState.PREPARING));
    }

    @Override
    public void update(Long gameId, GameRequest request, Long id) {
        Game game = repository.findById(gameId).orElseThrow();
        Player player = playerService.findEntityById(id);
        if (game.getDealer().equals(player)) {
            if (request.getMaxPlayers() != null) {
                game.setMaxPlayers(request.getMaxPlayers());
            }
            if (request.getPrice() != null) {
                game.setPrice(request.getPrice());
            }
            repository.save(game);
        }
    }

    @Override
    public void delete(Long gameId, Long id) {
        Game game = repository.findById(gameId).orElseThrow();
        Player player = playerService.findEntityById(id);
        if (game.getDealer().equals(player)) {
            repository.delete(game);
        }
    }
}
