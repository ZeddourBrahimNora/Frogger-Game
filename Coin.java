
public class Coin extends FixedGameElement{


    public Coin(int pos_x, int pos_y) { // on donne une position de base a la piece
    
        super(pos_x,pos_y); // pcq on appelle le constructeur de FixedGameElement 
    }
    
    
    public static String getPathToImage(){
        return "coin.png";
    }
    
    public String getType(){
    	return "coin";
    }

     public void triggerAction(Board board){
    	board.incScore(10);
    	board.decreaseCoinAmount(); // diminue le nbr de coins
    }
}
