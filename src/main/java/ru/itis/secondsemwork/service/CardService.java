package ru.itis.secondsemwork.service;

import ru.itis.secondsemwork.model.Card;
import ru.itis.secondsemwork.model.dto.request.CardRequest;

public interface CardService {
    Card findOrCreate(CardRequest cardRequest);
}
