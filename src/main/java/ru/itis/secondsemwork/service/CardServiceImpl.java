package ru.itis.secondsemwork.service;

import ru.itis.secondsemwork.model.Card;
import ru.itis.secondsemwork.model.dto.request.CardRequest;
import ru.itis.secondsemwork.model.enums.CardSuit;
import ru.itis.secondsemwork.model.enums.CardValue;
import ru.itis.secondsemwork.repository.CardRepository;
import ru.itis.secondsemwork.util.CardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService{

    private final CardRepository repository;
    private final CardMapper mapper;

    @Override
    public Card findOrCreate(CardRequest cardRequest) {
        return repository.findByCode(cardRequest.getCode()).orElse(
                Card.builder()
                        .code(cardRequest.getCode())
                        .value(CardValue.parse(cardRequest.getValue()))
                        .image(cardRequest.getImage())
                        .suit(CardSuit.valueOf(cardRequest.getSuit()))
                        .build()
        );
    }
}
