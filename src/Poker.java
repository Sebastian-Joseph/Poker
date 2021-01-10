import java.util.Scanner;
import java.util.ArrayList;

public class Poker {

    private Player p;
    private Deck deck;
    private final int FULL_HAND_SIZE = 5;

    private Scanner in;

    public Poker(String[] testHand){


        in = new Scanner(System.in);

        p = new Player();
        deck = new Deck();
        System.out.println(deck);


        for (String card : testHand) {

            char readSuit = card.charAt(0);
            int suit = 0;
            switch (readSuit) {
                case 'c':
                    suit = 1;
                    break;
                case 'd':
                    suit = 2;
                    break;
                case 'h':
                    suit = 3;
                    break;
                case 's':
                    suit = 4;
                    break;
            }

            int rank = Integer.parseInt(card.substring(1));

            Card newCard = new Card(suit, rank);
            p.addCard(newCard);
        }
    }

    public Poker(){
        in = new Scanner(System.in);
        p = new Player();
        deck = new Deck();
        System.out.println(deck);


    }

    public void play(){
        System.out.println("Welcome to Poker! Ready to lose all of your money?");

        boolean start = true;
        while(start && p.getBankroll() > 0){

            System.out.println("\nNew Round");
            System.out.println("\nChips: " + p.getBankroll());

            System.out.print("Enter [y]es to start a round, [n]o to end: ");
            String round = in.next().toLowerCase();

            if(round.equals("n")){
                start = false;
            }

            else if(round.equals("y")){
                p.bets(0);
                while(true){
                    System.out.print("How many chips do you want"
                            + " to bet? 1-25 only: ");
                    double bet = in.nextDouble(); // set bet


                    if( bet < 1 || bet > 25 ){
                        System.out.println("\nError. Your bet is not between"
                                + " 1-25. Enter again.");
                        continue;
                    }

                    if( bet > p.getBankroll() ){
                        System.out.println("\nSorry, you're running"
                                + " out of chips.");
                        break;
                    }

                    p.bets(bet);

                   deck.reStart();
                   System.out.println(deck);


                    if(p.getHand().size() == 0){
                        this.deal5Cards();


                    }


                    p.sortHand();
                    System.out.println("Your Hand: " + p.getHand());

                    while(true){
                        System.out.print("Do you want to exchange?"
                                + " [y]es or [n]o: ");
                        String exch = in.next().toLowerCase();


                        if(exch.equals("y")){
                            this.exchangeCard();

                            p.sortHand();
                            System.out.println("\nYour New Hand: "
                                    + p.getHand());
                            break;
                        }

                        else if(exch.equals("n")){
                            break;
                        }

                        else{
                            System.out.println("ERROR! Please enter y or n.");
                            continue;
                        }
                    }

                    System.out.println("\nPrizes!");

                    String combo = checkHand( p.getHand() );
                    System.out.printf("You got: %s! \n", combo);


                    double ChipsWon = getPayout(combo) * bet;
                    System.out.printf("You gained: %s chips. \n", ChipsWon);


                    p.winnings(ChipsWon);
                    System.out.printf("You now have: %s chips "
                            + "in total. \n", p.getBankroll());


                    p.removeAllCards();
                    break;
                }
                continue;
            }


            else{
                System.out.println("Error. Please enter y or n only!");
                continue;
            }
        }
        System.out.println("Thanks for playing! Come back so we can rob you again!");
    }



    private Card newDealtCard(){


        Card newCard = null;

        boolean dealt = true;
        while(dealt){
            newCard = deck.deal();
            dealt = false;


            for (Card dealtCard : p.getHand()) {

                if (newCard.compareTo(dealtCard) == 0){
                    dealt = true;
                }
            }

        }
        return newCard;
    }

    private void deal5Cards(){


        for(int i=0; i<FULL_HAND_SIZE; i++){

            p.addCard( this.newDealtCard() );
        }
    }


    private void exchangeCard(){


        while(true){

            System.out.print("\nHow many cards do you want"
                    + " to exchange? (1-3): ");
            int numOfExch = in.nextInt();

            if(numOfExch < 1 || numOfExch > 3){
                System.out.println("\nError. At least 1 and at most 3 cards!");
                continue;
            }


            else{
                int[] selectedPos = new int[numOfExch];

                int i = 0;
                while(i < numOfExch){
                    System.out.print("Which card? (1-5): ");
                    int cardPos = in.nextInt();

                    boolean validPos = true;
                    while(validPos){
                        validPos = false;
                        if(cardPos < 1 || cardPos > 5){
                            System.out.print("\nCards are at position 1-5 only."
                                    + "\nPlease enter again: ");
                            cardPos = in.nextInt();
                            validPos = true; // repromt
                        }

                        else{
                            for(int existedPos : selectedPos){
                                if(cardPos == existedPos){
                                    System.out.print("\nYou already chose this "
                                            + "card! \nPick another one: ");
                                    cardPos = in.nextInt();
                                    validPos = true;
                                }
                            }
                        }
                    }


                    selectedPos[i] = cardPos;


                    p.getHand().set(cardPos-1, this.newDealtCard());


                    i++;
                }
            }

            break;
        }
    }




    public String checkHand(ArrayList<Card> hand){

        if (isRoyalFlush(hand)) {
            return "Royal Flush";
        }
        else if (isStraightFlush(hand)) {
            return "Straight Flush";
        }
        else if (isFourOfAKind(hand)) {
            return "Four of a Kind";
        }
        else if (isFullHouse(hand)) {
            return "Full House";
        }
        else if (isFlush(hand)) {
            return "Flush";
        }
        else if (isStraight(hand)) {
            return "Straight";
        }
        else if (isThreeOfAKind(hand)) {
            return "Three of a Kind";
        }
        else if (isTwoPairs(hand)) {
            return "Two Pairs";
        }
        else if (isOnePair(hand)) {
            return "One Pair";
        }
        else{
            return "No Pair";
        }

    }


    private int numOfMatchRanks(ArrayList<Card> hand){

        int match = 1;
        for(int i=0; i < FULL_HAND_SIZE-1; i++){

            for(int j=i+1; j< FULL_HAND_SIZE; j++){

                if( hand.get(i).getRank() == hand.get(j).getRank() ){

                    match++;
                }
            }
        }
        return match;
    }



    private boolean isOnePair(ArrayList<Card> hand){


        if(this.numOfMatchRanks(hand) == 2){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isTwoPairs(ArrayList<Card> hand){

        if(this.numOfMatchRanks(hand) == 3){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isThreeOfAKind(ArrayList<Card> hand){

        if(this.numOfMatchRanks(hand) == 4){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isFullHouse(ArrayList<Card> hand){

        if(this.numOfMatchRanks(hand) == 5){
            return true;
        }
        else{
            return false;
        }
    }
    private boolean isFourOfAKind(ArrayList<Card> hand){

        if(this.numOfMatchRanks(hand) == 7){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isStraight(ArrayList<Card> hand){

        for(int i=0; i<FULL_HAND_SIZE-1; i++){
            int current = hand.get(i).getRank();
            int next = hand.get(i+1).getRank();
            if(current == 1 && next == 10){
                continue;
            }

            if( current != next-1 ){
                return false;
            }
        }
        return true;
    }

    private boolean isFlush(ArrayList<Card> hand){

        for(int i=0; i<FULL_HAND_SIZE-1; i++){

            int currentSuit = hand.get(i).getSuit();
            int nextSuit = hand.get(i+1).getSuit();

            if(currentSuit != nextSuit){
                return false;
            }
        }

        return true;
    }

    private boolean isStraightFlush(ArrayList<Card> hand){

        if( this.isFlush(hand) && this.isStraight(hand) ){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isRoyalFlush(ArrayList<Card> hand){

        int lastCard = hand.get(FULL_HAND_SIZE-1).getRank();
        int firstCard = hand.get(0).getRank();
        if( this.isFlush(hand) && lastCard==13 &&
                firstCard==1 && this.isStraight(hand) ){
            return true;
        }
        return false;
    }



    private int getPayout(String combo){

        if(combo.equals("Royal Flush")){
            return 100;
        }
        else if(combo.equals("Straight Flush")){
            return 50;
        }
        else if(combo.equals("Four of a Kind")){
            return 25;
        }
        else if(combo.equals("Full House")){
            return 15;
        }
        else if(combo.equals("Flush")){
            return 10;
        }
        else if(combo.equals("Straight")){
            return 5;
        }
        else if(combo.equals("Three of a Kind")){
            return 3;
        }
        else if(combo.equals("Two Pairs")){
            return 2;
        }
        else if(combo.equals("One Pair")){
            return 1;
        }
        else{
            return 0;
        }
    }


}