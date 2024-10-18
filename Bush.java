public class Bush extends FixedGameElement {

    public Bush(int pos_x, int pos_y) {
        super(pos_x, pos_y);
    }

    @Override
    public String getType() {
        return "bush";
    }

    public static String getPathToImage(){
        return "bushResize.png";
    }

    @Override
    public void triggerAction(Board board) {
        // ne peut pas marcher sur les buissons
        
    }
    
}
