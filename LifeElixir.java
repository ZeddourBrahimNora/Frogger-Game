public class LifeElixir extends FixedGameElement {

    //fonctionnalité supplémentaire qui donne au frogger un point de vie en plus si celui-ci ramasse la potion 
    public LifeElixir(int pos_x, int pos_y) {
        super(pos_x, pos_y);
    }

    @Override
    public String getType() {
        return "lifeElixir";
    }

    @Override
    public void triggerAction(Board board) {
        board.addLife(1);
    }

    public static String getPathToImage() {
        return "potionVie.png";
    }

    
    
    
}
