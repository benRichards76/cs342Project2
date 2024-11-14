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
//        for (Card card : hand) {
//            System.out.println(card.getValue());
//        }
//        System.out.println((hand.get(0).getValue() == hand.get(1).getValue() + 1));
        if (hand.size() == 3) {
            if ((hand.get(0).getSuit() == hand.get(1).getSuit()) && (hand.get(1).getSuit() == hand.get(2).getSuit())) {
                handRanking = 4;
                if ((hand.get(0).getValue() == hand.get(1).getValue() + 1) && (hand.get(1).getValue() == hand.get(2).getValue() + 1)) {
                    handRanking = 1;
                }
            }
            else if ((hand.get(0).getValue() == hand.get(1).getValue()) && (hand.get(1).getValue() == hand.get(2).getValue())) {
                handRanking = 2;
            }
            else if ((hand.get(0).getValue() == hand.get(1).getValue() + 1) && (hand.get(1).getValue() == hand.get(2).getValue() + 1)) {
                handRanking = 3;
            }
            else if ((hand.get(0).getValue() == hand.get(1).getValue()) || (hand.get(1).getValue() == hand.get(2).getValue()) || (hand.get(0).getValue() == hand.get(2).getValue())) {
                handRanking = 5;
            }
        }
        return handRanking;
    }

    public static int evalPPWinnings(ArrayList<Card> hand, int bet) {
        int handValue = evalHand(hand);
        int PairPlusWinning = 0;

        if (handValue == 1) { // Pair Plus winnings for a Straight flush hand
            PairPlusWinning = bet + (40 * bet);
        }
        else if (handValue == 2) { // Pair Plus winnings for a Three of a Kind hand
            PairPlusWinning = bet + (30 * bet);
        }
        else if (handValue == 3) { // Pair Plus winnings for a Straight hand
            PairPlusWinning = bet + (6 * bet);
        }
        else if (handValue == 4) { // Pair Plus winnings for a Flush hand
            PairPlusWinning = bet + (3 * bet);
        }
        else if (handValue == 5) { // Pair Plus winnings for a pair hand
            PairPlusWinning = bet + bet;
        }


        return PairPlusWinning;
    }

    public static int compareHands(ArrayList<Card> dealer, ArrayList<Card> player) {
        int dealerHand = evalHand(dealer);
        int playerHand = evalHand(player);

        if (dealerHand == playerHand) {
            sortCardsByRank(dealer);
            sortCardsByRank(player);

            if (dealerHand == playerHand) {
                if (dealer.get(0).getValue() > player.get(0).getValue()) {
                    return 1;
                }
                else if (dealer.get(0).getValue() < player.get(0).getValue()) {
                    return 2;
                }
                else if (dealer.get(0).getValue() == player.get(0).getValue()) {
                    return 0;
                }
            }
        }
        else if (dealerHand < playerHand && dealerHand != 0) {
            return 1;
        }
        else if (dealerHand > playerHand && playerHand == 0) {
            return 1;
        }


        return 2;
    }
}
