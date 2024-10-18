public abstract class MovingGameElement
{
     //classe pour tout elements en mouvements du jeu (voitures + insectes)

     private int pos_x;
     private int pos_y;
     private int speed;
     private int randomSpeed;
     private int increaseSpeed;

     public MovingGameElement(int pos_x,int pos_y,int speed)
     {
         this.pos_x = pos_x ;
         this.pos_y = pos_y ;
         this.speed = speed;
     }
 
     public int getPosX(){
         return pos_x;
     }
     
     public int getPosY(){
         return pos_y;
     }
     
     public void setPosX(int new_pos){
         pos_x = new_pos;
     }
     
     public void setPosY(int new_pos){
         pos_y = new_pos;
     }
 
     public int getSpeed()
     {
         return speed;
     }
 
     public void setSpeed(int new_speed)
     {
         speed = new_speed;
     }

     public void move() {  //permet de faire bouger la voiture ou l'insecte -> public car on l'utilise dans plusieurs classes
        pos_x = pos_x + speed;
    }

    public int getRandomSpeed(int maxRandom,int minRandom) { 
        randomSpeed = (int)(Math.random()*(maxRandom + minRandom)); 
        return randomSpeed;
    }


     public abstract String getType();
    
     // methode décrivant le comportement de tout les objets qui bougent, le passage en parametres permet de diminuer la presence de constante magique dans le code et d'avoir des elements du board sans devoir le passer en param
     public abstract void movementType(int elemFromBoard); 
     
    



    
     
}