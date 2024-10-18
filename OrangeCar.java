public class OrangeCar extends Cars
{  
   private int maxSpeedOrangeCar = 19; // vitesse max que la voiture peut atteindre
   private int minSpeedOrangeCar = 10;// vitesse min que la voiture peut atteindre

    public OrangeCar(int pos_x,int pos_y,int speed)
    {
        super(pos_x,pos_y,speed);
    }

    @Override   
    public String getType()
    {
        return "orangeCar";
    }
    
    
    public static String getPathToImage(){
        return "orangeCarResized-removebg-preview.png";
    }
    
    
    // voiture hasardeuse -> accelere et ralentit de maniere random 
    // random speed 
    @Override 
    public void movementType(int speed) {
        setSpeed(getRandomSpeed(maxSpeedOrangeCar, minSpeedOrangeCar)); 
    }

    

   
}