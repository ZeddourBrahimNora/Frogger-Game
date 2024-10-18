
public abstract class Insect extends MovingGameElement {

 
    public Insect(int pos_x, int pos_y,int speed) { 
        
      super(pos_x,pos_y,speed); // pcq on appelle le constructeur de MovingGameElement 
    }
    
   
    public abstract void triggerAction(Board board);
    
    
    
}
