package ru.itis.secondsemwork.util.game;

import ru.itis.secondsemwork.model.Card;
import ru.itis.secondsemwork.model.Hand;

public class Calculations {

    public static Integer calculateScores(Hand hand){
        int value = 0;
        int aces = 0;
        for (Card card: hand.getCards()
        ) {
            value+= card.getValue().getValue();
            if ("ACE".equals(card.getValue().getToParse())){
                aces++;
            }
        }
        if (value>21 && hand.getCards().size()>2){
            value = value - aces*10;
        }
        return value;
    }

    public static boolean BlackJack(Hand hand){
        int value = calculateScores(hand);
        return (value == 21 || value == 22) && hand.getCards().size() == 2;
    }
}
