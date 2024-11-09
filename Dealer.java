package GameLogic;

import java.util.ArrayList;

public class Dealer {
    private Deck theDeck;
    private ArrayList<Card> dealersHand;

    public Dealer() {
        theDeck = new Deck(); // Initialize theDeck
        dealersHand = new ArrayList<>(); // Initialize the dealer's hand
    }

    public ArrayList<Card> dealHand() {
        dealersHand.clear();
        // Add three cards from theDeck to dealersHand
        for (int i = 0; i < 3; i++) {
            if (!theDeck.isEmpty()) {
                dealersHand.add(theDeck.remove(0));
            }
        }
//        System.out.println(theDeck.size());
//        dealersHand.add(new Card('H', 3));
        return dealersHand;
    }

    public void printDealerDeck(ArrayList<Card> decks) {
        for (Card card : decks) {
            System.out.println(card.getSuit() + " " + card.getValue());
        }
    }

    public Deck getTheDeck() {
        return theDeck;
    }

    public int getSize() {
        return theDeck.size();
    }


}
