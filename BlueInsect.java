public class BlueInsect extends Insect{

    private int minSpeedBlueInsect = 10;
    private int maxSpeedBlueInsect = 15;

    public BlueInsect(int pos_x,int pos_y,int speed) {
        super(pos_x,pos_y,speed);
    }

    @Override
    public String getType()
    {
        return "blueInsect";
    }


    public static String getPathToImage(){
        return "blueInsect.png";
    }

    @Override
    public void triggerAction(Board board) {
        board.incScore(5);        
    }

    @Override
    public void movementType(int startingPos) {
       setSpeed(getRandomSpeed(maxSpeedBlueInsect, minSpeedBlueInsect));
    }

    
    
  

    

}