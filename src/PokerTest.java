public class PokerTest {

    

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