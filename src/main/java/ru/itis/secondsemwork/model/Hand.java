package ru.itis.secondsemwork.model;

import lombok.*;

import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Hand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    Player player;

    @ManyToMany( cascade = CascadeType.ALL)
    List<Card> cards;
}
