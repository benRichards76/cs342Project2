import java.util.ArrayList;

public class Player {
    private ArrayList<Card> hand;
    private int anteBet;
    private int playBet;
    private int pairPlusBet;
    private int totalWinnings;

    // No-argument constructor
    public Player() {
        hand = new ArrayList<Card>();
        anteBet = 5;
        playBet = 0;
        pairPlusBet = 0;
        totalWinnings = 0;
    }

    public Player(ArrayList<Card> hand, int anteBet, int playBet, int pairPlusBet, int totalWinnings) {
        this.hand = hand;
        this.anteBet = anteBet;
        this.playBet = playBet;
        this.pairPlusBet = pairPlusBet;
        this.totalWinnings = totalWinnings;
    }


    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public int getAnteBet() {
        return anteBet;
    }

    public void setAnteBet(int anteBet) {
        this.anteBet = anteBet;
    }

    public int getPlayBet() {
        return playBet;
    }

    public void setPlayBet(int playBet) {
        this.playBet = playBet;
    }

    public int getPairPlusBet() {
        return pairPlusBet;
    }

    public void setPairPlusBet(int pairPlusBet) {
        this.pairPlusBet = pairPlusBet;
    }

    public int getTotalWinnings() {
        return totalWinnings;
    }

    public void setTotalWinnings(int totalWinnings) {
        this.totalWinnings = totalWinnings;
    }
}