package GameLogic;
 import java.util.ArrayList;
 import java.util.Collections;
 import java.util.Comparator;
 import java.util.Random;

public class Deck extends ArrayList<Card> {

    private ArrayList<Card> deckOfCards;

    public Deck() {
        newDeck();
    }

    public void newDeck() {
        deckOfCards = new ArrayList<>();

        char[] suits = {'C', 'D', 'H', 'S'};

        for (char s : suits) {
            for (int v = 2; v <= 14; v++) {
                Card card = new Card(s, v);
                deckOfCards.add(card);
            }
        }

        Collections.shuffle(deckOfCards);
        this.addAll(deckOfCards);
    }

    public void printDeck() {
        for (Card card : deckOfCards) {
            System.out.println(card.getSuit() + " " + card.getValue());
        }
    }

    public int getDeckSize() {
        return deckOfCards.size();
    }

    public ArrayList<Card> getDeck() {
        return deckOfCards;
    }

    public void setDeckOfCards(ArrayList<Card> deckOfCards) {
        this.deckOfCards = deckOfCards;
    }

    public Card getCard(int index) {
        return deckOfCards.get(index);
    }


}
