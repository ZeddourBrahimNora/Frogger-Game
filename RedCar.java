public class RedCar extends Cars
{

    private int initPosRedCar = 60;
    private int upperRoad1 = 120; 
    private int upperRoad2 = 200;
   
    private int hitbox_x = 34;
    private int hitbox_y = 24;
    private int slowerSpeed = 5;
    private int initSpeed = 10;

    public RedCar(int pos_x,int pos_y,int speed)
    {
        super(pos_x,pos_y,speed);
    }

    @Override   
    public String getType()
    {
        return "redCar";
    }
    
    
    public static String getPathToImage(){
        return "redCarResized-removebg-preview.png";
    }
    
    @Override
    public void movementType(int pos_yFrogger) {

        if ((pos_yFrogger >= (getPosY()-hitbox_y) && pos_yFrogger <= (getPosY()+hitbox_y)) || (pos_yFrogger >= (upperRoad2 - hitbox_x) && pos_yFrogger <= (upperRoad2 + hitbox_y)) || (pos_yFrogger <= (upperRoad1 + hitbox_y) && pos_yFrogger >= (upperRoad1 - hitbox_x))) { // si le frogger se trouve sur les 3 bandes du haut 
            setPosY(pos_yFrogger); // mettre la position de la voiture "sur" celle du frogger pour qu'il la suit
            setSpeed(slowerSpeed);// mettre une vitesse plus lente quand la voiture suit le frogger
        } else {
            setPosY(initPosRedCar); // remettre la voiture dans sa bande si elle depasse sur les trottoirs
            setSpeed(initSpeed); // remettre sa vitesse initiale
        }
       

        
    }

    
}