public class PokerTest {

    //this class must remain unchanged
    //your code must work with this test class

    public static void main(String[] args){
        if (args.length<1){
            Poker g = new Poker();
            g.play();
        }
        else{
            Poker g = new Poker(args);
            g.play();
        }
    }
}