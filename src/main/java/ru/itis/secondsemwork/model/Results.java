package ru.itis.secondsemwork.model;

import ru.itis.secondsemwork.model.enums.ResultEnum;
import lombok.*;

import jakarta.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Results {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    Player player;

    @ManyToOne
    Game game;

    ResultEnum result;
}
