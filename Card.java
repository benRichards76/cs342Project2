package GameLogic;

public class Card {
    private char suit;
    private int value;

    public Card(char suit, int value) {
        if ((suit != 'C' && suit != 'D' && suit != 'S' && suit != 'H') || (value < 2 || value > 14)) {
            return;
        }
        this.suit = suit;
        this.value = value;
    }

    @Override
    public String toString() {
        return suit + "-" + value;
    }

    public char getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

}
