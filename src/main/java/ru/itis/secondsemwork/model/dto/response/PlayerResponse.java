package ru.itis.secondsemwork.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerResponse {
    String username;
    List<CardResponse> cards;
    Long totalPoints;
}
