public class PurpleCar extends Cars
{
   private int coinsInGame = 6; // c'est le nombre total de pieces present dans le jeu 
   private int maxSpeedPurpleCar = 20; // vitesse max que la voiture peut atteindre
   private int minSpeedPurpleCar = 10;// vitesse min que la voiture peut atteindre

    public PurpleCar(int pos_x,int pos_y,int speed)
    {
        super(pos_x,pos_y,speed);
    }

    @Override   
    public String getType()
    {
        return "purpleCar";
    }
    
    
    public static String getPathToImage(){
        return "purpleCarResized-removebg-preview.png";
    }
   
    // la voiture est lente quand y'a bcp de pieces et rapide quand y'en a pas bcp
    @Override
    public void movementType(int coinCounter) {
           
    if (coinCounter < coinsInGame) { // si le nombre total de pieces est plus petit que le nombre de piece actuel sur le plateau on met une nouvelle vitesse = dimunition des pieces
        setSpeed(maxSpeedPurpleCar);
    } else {
        setSpeed(minSpeedPurpleCar);
    }

    }

    

   
    
}