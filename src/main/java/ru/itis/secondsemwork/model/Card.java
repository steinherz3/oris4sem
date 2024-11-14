package ru.itis.secondsemwork.model;

import ru.itis.secondsemwork.model.enums.CardSuit;
import ru.itis.secondsemwork.model.enums.CardValue;
import lombok.*;

import jakarta.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String code;

    String image;

    CardValue value;

    CardSuit suit;

}
