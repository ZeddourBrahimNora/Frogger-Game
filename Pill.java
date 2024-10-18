public class Pill extends FixedGameElement 
{
    public Pill(int pos_x,int pos_y)
    {
        super(pos_x, pos_y);
    }

    public String getType()
    {
        return "pill";
    }

    public static String getPathToImage(){
        return "pillule.png";
    }

    @Override
    public void triggerAction(Board board) {
       board.cancelEffects();
        
    }

    
}