package ru.itis.secondsemwork.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CardValue {
    ACE("ACE",11L), KING("KING", 10L), QUEEN("QUEEN", 10L),
    JACK("JACK", 10L), TEN("10", 10L), NINE("9", 9L), EIGHT("8", 8L),
    SEVEN("7",7L), SIX("6",6L), FIVE("5",5L), FOUR("4",4L), THREE("3",3L), TWO("2",2L);

    private final String toParse;

    private final Long value;

    public static CardValue parse(String parsing){
        for (CardValue cardValue: CardValue.values())
            if (cardValue.getToParse().equals(parsing)){
                return cardValue;
            }
        throw new IllegalArgumentException("no such option: " + parsing);
    }
}
