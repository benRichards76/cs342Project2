package main.java;
import java.util.ArrayList;


public class Dealer {
    private Deck theDeck;
    private ArrayList<Card> dealersHand;

    public Dealer() {
        theDeck = new Deck(); // Initialize theDeck
        dealersHand = new ArrayList<>(); // Initialize the dealer's hand
    }

    public ArrayList<Card> dealHand() {
//        dealersHand.clear();
        // Add three cards from theDeck to dealersHand
        ArrayList<Card> hand = new ArrayList<>();
        if (theDeck.size() <= 34) {
            theDeck = new Deck();
        }
        for (int i = 0; i < 3; i++) {
            hand.add(theDeck.remove(0));
        }
//        System.out.println(hand);
        return hand;
    }

    public void printDealerDeck(ArrayList<Card> decks) {
        for (Card card : decks) {
            System.out.println(card.getSuit() + "-" + card.getValue());
        }
    }

    public Deck getTheDeck() {
        return theDeck;
    }

    public int getSize() {
        return theDeck.size();
    }

    public void setHand(ArrayList<Card> hand) {
        dealersHand = hand;
    }

    public ArrayList<Card> getHand() {
        return dealersHand;
    }


}
