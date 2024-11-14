package ru.itis.secondsemwork.model;

import ru.itis.secondsemwork.model.enums.GameState;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long price;

    Long maxPlayers;

    @ManyToMany
    List<Player> players;

    @ManyToOne
    Player activePlayer;

    @ManyToOne
    Player dealer;

    @OneToOne
    Chat chat;

    String deckId;

    GameState state;

}
