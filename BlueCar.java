public class BlueCar extends Cars {

    private int resetSpeed = 10 ; 
    
    public BlueCar(int pos_x,int pos_y,int speed)
    {
        super(pos_x, pos_y, speed);
    }

    @Override   
    public String getType()
    {
        return "blueCar";
    }
    
    
    public static String getPathToImage(){
        return "blueCarResized-removebg-preview.png";
    }


    //la voiture bleue ralenti lorsque frogger est dans la même bande
    @Override
    public void movementType(int pos_yFrogger) {
        if (pos_yFrogger == getPosY()) { // si le frogger est sur la meme bande que la voiture 
            setSpeed(getSpeed()/2); // je ralenti la vitesse de la voiture en la divisant par deux
        }else{
            setSpeed(resetSpeed); // je met une nouvelle vitesse au frogger si il n'est plus sur la meme bande que la voiture 
        }
        
    }

   

    

    

}