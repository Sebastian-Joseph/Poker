import java.util.ArrayList;
import java.util.Collections;

public class Player {


    private ArrayList<Card> hand;
    private double bankroll;
    private double bet;


    public Player(){
        hand = new ArrayList<Card>();
        bankroll = 500;
        bet = 0;
    }

    public void addCard(Card c){
        hand.add(c);
    }

    public void removeCard(Card c){

        hand.remove(c);
    }

    public void removeAllCards(){

        hand.clear();
    }

    public void bets(double amt){
        bet = amt;
        bankroll -= amt;
    }

    public void winnings(double odds){
        bankroll += odds;
    }

    public double getBankroll(){
        return bankroll;
    }

    public ArrayList<Card> getHand(){
        return hand;
    }

    public void sortHand(){
        Collections.sort(hand);
    }
}

