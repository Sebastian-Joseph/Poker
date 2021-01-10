import java.util.Random;

public class Deck {

    private final static int TOTAL_CARDS = 52;
    private Card[] deck;
    private int top;


    public Deck(){
        deck = new Card[TOTAL_CARDS];
        top = 52;



        int cardPosition = 0;
        for(int s=1; s<=4; s++){
            for(int r=1; r<=13; r++){
                deck[cardPosition] = new Card(s,r);
                cardPosition++;
            }
        }
    }

    private void shuffle(){
        Random r = new Random();

        for (int i=0; i < TOTAL_CARDS; i++) {
            int randomPosition = r.nextInt(TOTAL_CARDS);
            Card tempCard = deck[i];
            deck[i] = deck[randomPosition];
            deck[randomPosition] = tempCard;
        }
    }

    public Card deal(){

        return deck[--top];

    }


    public void reStart(){
        top = 52;
        this.shuffle();
    }


    public String toString(){

        String showDeck = "";
        int card_num = 1;

        for(Card cards : deck){
            showDeck += String.format("%d: %s\n", card_num, cards);
            card_num++;
        }
        return showDeck;
    }
}