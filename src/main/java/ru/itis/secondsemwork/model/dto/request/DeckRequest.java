package ru.itis.secondsemwork.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeckRequest {
    boolean success;
    String deck_id;
    int remaining;
    List<CardRequest> cards;
    boolean shuffled;
}
