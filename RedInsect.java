public class RedInsect extends Insect{

    
    private int initSpeedRedInsect = 10;

    public RedInsect(int pos_x,int pos_y,int speed) {
        super(pos_x,pos_y,speed);
    }

    @Override
    public String getType()
    {
        return "redInsect";
    }

    
    public static String getPathToImage(){
        return "redInsect.png";
    }


    @Override
    public void triggerAction(Board board) {
        board.incScore(15);        
    }

    private void increaseSpeed()
    {
        setSpeed(getSpeed() + getSpeed());
    }
   
    @Override
    public void movementType(int speedBlueInsect) { // speedBlueInsect car j'ai besoin de connaitre la vitesse de l'insecte bleu pour determiner celle de l'insecte rouge
                
        if (speedBlueInsect > getSpeed() ) { // si l'insecte bleue va plus vite que l'insecte rouge -> on augmente la vitesse de l'insecte rouge
            increaseSpeed();
        }else{ 
            // mettre la vitesse normale 
            setSpeed(initSpeedRedInsect);
        }
       

    }

    

    
}