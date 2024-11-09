package GameLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ThreeCardLogic {


    static void sortCardsByRank(ArrayList<Card> cards) {
        // Sort in descending order based on card value
        Collections.sort(cards, new Comparator<Card>() {
            @Override
            public int compare(Card c1, Card c2) {
                return Integer.compare(c2.getValue(), c1.getValue()); // Compare in reverse order for descending
            }
        });
    }

    public static int evalHand(ArrayList<Card> hand) {
        int handRanking = 0;
        sortCardsByRank(hand);
        if (hand.size() == 3) {
            if ((hand.get(0).getSuit() == hand.get(1).getSuit()) && (hand.get(1).getSuit() == hand.get(2).getSuit())) {
                handRanking = 4;
                if ((hand.get(0).getValue() == hand.get(1).getValue() - 1) && (hand.get(1).getValue() == hand.get(2).getValue() - 1)) {
                    handRanking = 1;
                }
            }
            else if ((hand.get(0).getValue() == hand.get(1).getValue()) && (hand.get(1).getValue() == hand.get(2).getValue())) {
                handRanking = 2;
            }
            else if ((hand.get(0).getValue() == hand.get(1).getValue() - 1) && (hand.get(1).getValue() == hand.get(2).getValue() - 1)) {
                handRanking = 3;
            }
            else if ((hand.get(0).getValue() == hand.get(1).getValue()) || (hand.get(1).getValue() == hand.get(2).getValue()) || (hand.get(0).getValue() == hand.get(2).getValue())) {
                handRanking = 5;
            }
        }
        return handRanking;
    }
}
