package ru.itis.secondsemwork.model;


import ru.itis.secondsemwork.model.enums.PlayerState;
import lombok.*;

import jakarta.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String username;

    String hashPassword;

    Long balance;

    PlayerState state;

    @OneToOne(cascade = CascadeType.ALL)
    Hand currentHand;

    @OneToOne
    Player nextPlayer;

}
