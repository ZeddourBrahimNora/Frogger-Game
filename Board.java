import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;


import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Board extends JPanel implements ActionListener {

    private final int B_WIDTH = 500;
    private final int B_HEIGHT = 500;
    private final int DOT_SIZE = 10;
    private final int RAND_POS = 22;
    private final int DELAY = 140;
    private final int void_x = -1 * B_WIDTH; // permet de faire disparaitre certains elements du plateau de jeu
    private final int void_y = -1 * B_HEIGHT;
    private final int totalLevel = 3; 

    private int speed; 
    // coordonnes de la hitbox pour detecter les collisions d'element de taille moyenne : voitures,insectes
    private int hitbox_x = 34;
    private int hitbox_y = 24;
    // position du frogger
    private int pos_xFrogger;
    private int pos_yFrogger;
    //position du background
    private int pos_xBackground;
    private int pos_yBackground;
    // coordonnees voiture bleue
    private int pos_xBlueCar;
    private int pos_yBlueCar;
    // coordonnees voiture rouge
    private int pos_xRedCar;
    private int pos_yRedCar;
    // coordonnees voiture orange
    private int pos_xOrangeCar;
    private int pos_yOrangeCar;
    // coordonnees voiture mauve
    private int pos_xPurpleCar;
    private int pos_yPurpleCar;
    // coordonnees insecte rouge
    private int pos_xRedInsect;
    private int pos_yRedInsect;
    private int speedRedInsect;
    // coordonnees insecte bleu
    private int pos_xBlueInsect;
    private int pos_yBlueInsect;
    private int speedBlueInsect;
    //coordonnees arbustes
    private int pos_xBush1;
    private int pos_xBush2;
    private int pos_yBush1;
    private int pos_yBush2;


    //coordonnes des routes et de la berne central en partant du bas de l'ecran 
    private int startingPos = 0;// debut de la bande
    private int lowerPavement = 470; // position du bas de l'ecran/trottoir
    private int upperPavement = 20; // position du haut de l'ecran/trottoir
    private int posYlifeMsg = B_WIDTH-DOT_SIZE*7; // position laquelle le niveau sera afficher sur l'ecran 
    private int posXHighestScoreMsg = 390;
  
    private int coinCounter;

    private int score;
    private int highestScore;
    private int currentLevel; // utile pour savoir a quel niveau on est 

    private int lifePoints; // points de vie de la grenouille
    private int lifeElixir; // potion de vie -> fonctionnalite supplementaire
    private int pill; // pillule qui rends le frogger invincible pendant un certain laps de temps
    private long pillPeriod = 10000; // temps en miliseconds durant lequel le frogger sera sous l'effet de la pillule

   
    
    private ArrayList<FixedGameElement> fixedGameElementList;
    private ArrayList<Cars> carsList;
    private ArrayList<Insect> insectList;
    private ArrayList<Bush> bushList;

    

    HashMap<String, ImageIcon> fixedGameElementImageMap;
    HashMap<String, ImageIcon> carsImageMap;
    HashMap<String, ImageIcon> insectImageMap;
    
    private PurpleCar purpleCar;
    private OrangeCar orangeCar;
    private BlueCar blueCar;
    private RedCar redCar;

    private RedInsect redInsect;
    private BlueInsect blueInsect;

    private Bush bush1;
    private Bush bush2;


    private boolean leftDirection = false;
    private boolean rightDirection = false;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;
    private boolean invincible = false; // pour collision pillule

    private Timer timer;

    private Image frog; 
    private Image frogPill;
    private Image backgroundImg;
    

    public Board() {
        initBoard();
    }
    
    private void initBoard() { // on initialise le plan/plateau de jeu

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
    }

    // charger les images 
    private void loadImages() {
	
	    fixedGameElementImageMap = new HashMap<String, ImageIcon>();
        carsImageMap = new HashMap<String, ImageIcon>();
        insectImageMap = new HashMap<String, ImageIcon>();
        ///////// image des coins ////////// 
        ImageIcon imageIconCoin = new ImageIcon(Coin.getPathToImage()); 
        fixedGameElementImageMap.put("coin",imageIconCoin);
        ////// images des insectes ////////////
	    ImageIcon imageIconBlueInsect = new ImageIcon(BlueInsect.getPathToImage()); 
        insectImageMap.put("blueInsect",imageIconBlueInsect);
        ImageIcon imageIconRedInsect = new ImageIcon(RedInsect.getPathToImage()); 
        insectImageMap.put("redInsect",imageIconRedInsect);
        /////// image de la grenouille ////////////
        ImageIcon imageIconFrog = new ImageIcon("frogDot.png");
        frog = imageIconFrog.getImage();
        /////// image de la grenouille invincible ////////////
        ImageIcon imageIconFrogPill = new ImageIcon("pillFrog.png");
        frogPill = imageIconFrogPill.getImage();
        /////// images des Background //////////////
        ImageIcon ImageIconBackground = new ImageIcon("background2.png");
        backgroundImg = ImageIconBackground.getImage();
        /////// images des voitures //////////////
        ImageIcon imageIconOrangeCar = new ImageIcon(OrangeCar.getPathToImage());
        carsImageMap.put("orangeCar", imageIconOrangeCar);
        ImageIcon imageIconPurpleCar = new ImageIcon(PurpleCar.getPathToImage());
        carsImageMap.put("purpleCar", imageIconPurpleCar);
        ImageIcon imageIconRedCar = new ImageIcon(RedCar.getPathToImage());
        carsImageMap.put("redCar", imageIconRedCar);
        ImageIcon imageIconBlueCar = new ImageIcon(BlueCar.getPathToImage());
        carsImageMap.put("blueCar", imageIconBlueCar);
        /////// image de la pillule //////////////
        ImageIcon imageIconPill = new ImageIcon(Pill.getPathToImage());
        fixedGameElementImageMap.put("pill",imageIconPill);
        /////// image de l'elixir de vie //////////////
        ImageIcon imageIconLifeElixir = new ImageIcon(LifeElixir.getPathToImage());
        fixedGameElementImageMap.put("lifeElixir",imageIconLifeElixir);
        /////// image des buissons //////////////
        ImageIcon imageIconBush = new ImageIcon(Bush.getPathToImage());
        fixedGameElementImageMap.put("bush",imageIconBush);
    }

    private void initGame() {
        score = 0;
        coinCounter = 4;
        speed = 10; // la vitesse des voitures/insectes
        lifePoints = 3; // 4 vies au début du jeu
        lifeElixir = 1;
        pill = 1;
        currentLevel = 1; 

        addFixedElementToList();
        initCarsPosition();
        initCars();
        initInsectsPositions();
        initInsects();
        positionFrogger();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    
    private void addFixedElementToList()
    {  
       fixedGameElementList = new ArrayList<FixedGameElement>();

       fixedGameElementList.add(new LifeElixir(getRandomCoordinate(), getRandomCoordinate()));
   
       fixedGameElementList.add(new Pill(getRandomCoordinate(), getRandomCoordinate()));
    
       // les coins se mettent dans des positions randoms
       for(int i = 0; i < coinCounter ; i++){
          fixedGameElementList.add(new Coin(getRandomCoordinate(), getRandomCoordinate()));
       }
    }
    //methode pour initialiser les varbustes
    private void initBush(){
        pos_xBush1 = 150;        
        pos_yBush1 = 255;
        pos_xBush2 = 350;
        pos_yBush2 = 255;
       
        bush1= new Bush(pos_xBush1,pos_yBush1);
        bush2 = new Bush(pos_xBush2,pos_yBush2);

        fixedGameElementList.add(bush1);
        fixedGameElementList.add(bush2);
       
    }
    //methode pour initialiser les voitures + les ajouter dans la liste
    private void initCars()
    {
        carsList = new ArrayList<Cars>();
        orangeCar = new OrangeCar(pos_xOrangeCar, pos_yOrangeCar, speed);
        purpleCar = new PurpleCar(pos_xPurpleCar, pos_yPurpleCar, speed);
        redCar = new RedCar(pos_xRedCar, pos_yRedCar, speed);
        blueCar = new BlueCar(pos_xBlueCar, pos_yBlueCar, speed);
        // ajouter a la liste
        carsList.add(orangeCar);
        carsList.add(purpleCar);     
        carsList.add(redCar);
        carsList.add(blueCar);
    }

    //methode pour initialiser les positions de depart des voitures
    private void initCarsPosition()
    {   
        pos_xBlueCar = startingPos;
        pos_yBlueCar = 190;
        // coordonnees voiture rouge
        pos_xRedCar = startingPos;
        pos_yRedCar = 60;
        // coordonnees voiture orange
        pos_xOrangeCar = startingPos;
        pos_yOrangeCar = 350;
        // coordonnees voiture mauve
        pos_xPurpleCar = startingPos;
        pos_yPurpleCar = 400;
    }
    //methode pour faire bouger les voitures selons leurs caracteristiques
    private void moveTheCars()
    {
        for (Cars cars : carsList) {
            cars.move();
            cars.movementType(startingPos); // pour la voiture orange
            cars.movementType(coinCounter); // pour la voiture mauve
            cars.movementType(pos_yFrogger);// pour la voiture bleue
            cars.movementType(pos_yFrogger);// pour la voiture rouge

            if (cars.getPosX() > B_WIDTH) { // si la voiture est a la fin de sa bande -> qu'elle atteint la largeur b_width
                cars.setPosX(startingPos);
            }
        }
    }
    
    
    //methode pour initialiser les insectes
    private void initInsects()
    {   
        insectList = new ArrayList<Insect>();
        blueInsect = new BlueInsect(pos_xBlueInsect, pos_yBlueInsect, speedBlueInsect);
        redInsect = new RedInsect(pos_xRedInsect, pos_yRedInsect, speedRedInsect);
        //ajouter a la liste
        insectList.add(blueInsect);
        insectList.add(redInsect);
    }
     // methode pour initialiser les positions de depart des insectes 
    private void initInsectsPositions()
    {
        //coordonnees insecte rouge
        pos_xRedInsect = startingPos;
        pos_yRedInsect = 120;
        speedRedInsect = 10;
        //coordonnees insecte bleu
        pos_xBlueInsect = startingPos;
        pos_yBlueInsect = 300;
        speedBlueInsect = 10;
    }

    //methode pour faire bouger les insectes selons leurs caracteristiques
    private void moveTheInsects()
    {
        for (Insect insect : insectList) {
            insect.move();
            speedBlueInsect = blueInsect.getSpeed(); // pour detecter la vitesse de l'insecte bleue dans RedInsect sans devoir l'instancier

            insect.movementType(startingPos); // pour l'insecte bleu

            insect.movementType(speedBlueInsect); // pour l'insecte rouge

            if (insect.getPosX() > B_WIDTH) { // si la voiture est a la fin de sa bande -> qu'elle atteint la largeur b_width
                insect.setPosX(startingPos);
            }
        }
    }

    // methode avec les coordonnees initiales du frogger
    private void positionFrogger()
    {
        pos_xFrogger = B_WIDTH/2; // au milieu de l'axe x 
        pos_yFrogger = lowerPavement;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
    
    // methode pour dessiner les elements fixes du jeu 
    private void drawFixedGameElements (Graphics g){
         // dessiner le frogger
         g.drawImage(frog, pos_xFrogger, pos_yFrogger,this);
         // dessinger le frogger lorsqu'il est invincible 
         changeFrogColor(g);
          // dessiner les elements fixes
          for(FixedGameElement elem: fixedGameElementList){
            g.drawImage(fixedGameElementImageMap.get(elem.getType()).getImage(), elem.getPosX(), elem.getPosY(), this);
        }
    }

    // methode pour dessiner les voitures + insectes
    private void drawMovingGameElements(Graphics g)
    {
         // dessiner les voitures
         for (Cars elem: carsList) {
            g.drawImage(carsImageMap.get(elem.getType()).getImage(), elem.getPosX(), elem.getPosY(), this);
        }
           // dessiner les insectes
        for (Insect elem: insectList){
            g.drawImage(insectImageMap.get(elem.getType()).getImage(), elem.getPosX(), elem.getPosY(), this);
        }


    }



    private void doDrawing(Graphics g) {
        
        if (inGame) { 
    
            // dessiner le background            
            g.drawImage(backgroundImg, pos_xBackground, pos_yBackground,this);                

            drawFixedGameElements(g);
            drawMovingGameElements(g);
            displayScoreAndLifeOnScreen(g);

           Toolkit.getDefaultToolkit().sync();   

        } else { 
            gameOver(g);
        }     
    }
    

    // message a afficher losque le jeu est perdu
    private void gameOver(Graphics g) {
        
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 12);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }

    // afficher le score et la vie sur l'ecran de jeu
    private void displayScoreAndLifeOnScreen(Graphics g)
    {
        String strLife = "Life : ";
        String strScore = "Score : ";
        String strLevel = "Level : ";
        String strHighestScore = "Highest Score : ";
       // private int pos 
        Font small = new Font("Helvetica", Font.BOLD, 10);
        g.setFont(small);
        g.setColor(Color.white);
      
        g.drawString(strLife + lifePoints,posYlifeMsg,lowerPavement); // affice le string avec le message + les points de vie a la position indique (en haut a gauche)
        g.drawString(strLevel + currentLevel,startingPos,lowerPavement);
        g.drawString(strScore + score,startingPos,upperPavement); // afficher le score
        g.drawString(strHighestScore + highestScore,posXHighestScoreMsg,upperPavement); // afficher le + grand score

    }

    // verifier les collisions des elements fixes
    private void checkFixedGameElementCollision() {

        for(FixedGameElement elem: fixedGameElementList){ // on parcourt tt les elements fixes et on check les collisions entre les elem && le frogger

                if ((pos_xFrogger == elem.getPosX()) && (pos_yFrogger == elem.getPosY())){ // d'abord regarder si y'a collision avec l'element
                    elem.setPosX(void_x);  // on le fait disparaitre dans le vide
                    elem.setPosY(void_y);
                    elem.triggerAction(this); 
                     if (elem.getType() == "pill") { // si on touche la pillule on active l'invincibilite
                    invincible = true;
                    } 
                } 
             }
        }
        
    // collisions avec les buissons
    private void checkBushCollision() {
        for(FixedGameElement elem: fixedGameElementList) // je parcourt tt la liste d'elements fixe
        {
            if (elem.getType()== "bush") {// si je trouve des buissons je check leurs collisions
                if ((pos_yFrogger < (elem.getPosY() + hitbox_y) && pos_yFrogger > (elem.getPosY() - hitbox_y) ) && (pos_xFrogger < (elem.getPosX() + hitbox_x) && pos_xFrogger > (elem.getPosX() - hitbox_x))) {
                    positionFrogger(); // si y'a collision reset la position
                }
            }
            
        }
    }
 
    //methode qui change l'apparence du frogger lorsque celui ci mange la pillule
    private void changeFrogColor(Graphics g) {
        if (invincible == true) {
            g.drawImage(frogPill,pos_xFrogger,pos_yFrogger,this);
        }
    }
    
    
    // code realiser a l'aide de documentation java -> voir rapport pour le lien de la source
    public void cancelEffects(){

        new java.util.Timer().schedule( 
        new java.util.TimerTask() {
            @Override
            public void run() {
                invincible = false; // on met fin a l'invincibilite
                checkCarsCollision(); // on reverifie les collisions et on enleve des pts de vie si y'a collision
            }
        }, 
        pillPeriod //si la periode indiquer en milisecondes est passee on effectue les taches (dans run)
    );

    }
    
    // verifier les collisions des elements qui bougent (voitures + insectes)
    private void checkCarsCollision()
    {
        // il s'agit de la hitbox de la voiture les valeurs ont ete choisi en fonction des pixels des voitures auxquels on a retirer 10 pixels pour le x et le y / longueur et largeur
        for (Cars elem : carsList) 
        {
            if (invincible == false) // si le frogger n'est pas invincible on va check les collisions et les degats
            { 
                // on utilise une hitbox avec une forme invisible (rectangle) rattaché/qui suit l'objet (voiture) et on verifie si tout les cotes de la hitbox sont vides si le frogger est detecter autour de la hitbox cela signifie qu'il y a collision
                if ((pos_yFrogger < (elem.getPosY() + hitbox_y) && pos_yFrogger > (elem.getPosY() - hitbox_y) ) && (pos_xFrogger < (elem.getPosX() + hitbox_x) && pos_xFrogger > (elem.getPosX() - hitbox_x)))
                {
                    decreaseLife();// on enleve un pts de vie lorsqu'il y'a collision
                    positionFrogger(); // si y'a collision on remet le frogger a sa position initiale c-a-d en bas de l'ecran
                } 
            } 
        }
           
    }
    //collisions des insectes avec le meme systeme de detection pour les voitures mais ici les insectes disparaissent lorsqu'on les touche
    private void checkInsectCollision()
    {
        for (Insect elem : insectList) {
        // on utilise une hitbox avec une forme invisible (rectangle) rattaché/qui suit l'objet (voiture) et on verifie si tout les cotes de la hitbox sont vides si le frogger est detecter autour de la hitbox cela signifie qu'il y a collision
        if ((pos_yFrogger < (elem.getPosY() + hitbox_y) && pos_yFrogger > (elem.getPosY() - hitbox_y) ) && (pos_xFrogger < (elem.getPosX() + hitbox_x) && pos_xFrogger > (elem.getPosX() - hitbox_x))){
            elem.setPosX(void_x);  // on le fait disparaitre dans le vide
            elem.setPosY(void_y);
            elem.triggerAction(this); 
        }
     }

    }
    private void initAllGameElements (){
        fixedGameElementList.clear();
        carsList.clear();
        insectList.clear();
        addFixedElementToList();
        initCarsPosition();
        initCars();
        initInsects();
        initInsectsPositions();
        initBush();
    }
   
     // initialiser tout les elements du niveau 2 -> buissons
     private void initLevel2GameElements()
     {
        speed = 11;
        coinCounter = 5;
        pill = 1;
        lifeElixir = 1;
        positionFrogger();
        initAllGameElements();
     }

     // initialiser tout les elements du niveau 3 
     private void initLevel3GameElements()
     {
        speed = 12;
        coinCounter = 6;
        pill = 1;
        positionFrogger();
        initBush();
        initAllGameElements();
     }
  
    // check le nombre de vies du frogger -> si il est mort ou pas sinon il va au prochain level
    private void checkLifeAndNxtLevel(){
        if (lifePoints ==0) { 
            inGame = false; 
        }else if(coinCounter == 0 && pos_yFrogger == 0){ // si on a ramasser toutes les pieces et que le frogger est en haut de l'ecran
            currentLevel++;  //incrementer le niveau 
            initLevels(); // methode qui va initialiser les niveaux
        }
    }
    
    //methode pour initialiser les niveaux avec tout ce dont on a besoin dedans 
    private void initLevels(){
        //pas besoin d'initiliaser le niveau 1 on le fait directement dans le board
        if(currentLevel == 2) {
            initLevel2GameElements();
        }
        else if(currentLevel == 3){
            initLevel3GameElements();
        }else if(currentLevel > totalLevel) // si j'arrive au dernier niveau
        {
            inGame = false;
        }           
                
    }
    // methode a appeler pour diminuer les points de vies du frogger 
    private void decreaseLife()
    {
        lifePoints--;
    }
   
    // methode pour la fonctionnalite supplementaire qui rajoute un point de vie au frogger si celui-ci ramasse un elixir de vie 
    public void addLife(int extraLife)
    {
        lifePoints += extraLife;
    }
    // augmenter le score 
    public void incScore(int valueToIncrease){
    	score += valueToIncrease;
    }
    //diminuer le montant de coins en fonction de ce que le joueur ramasse comme piece
    public void decreaseCoinAmount(){ 
    	coinCounter -=1;
    }

    //check et enregistre le meilleur score
    private void readFileBestScore()
    {
        try {
            File myObj = new File("score.txt");
            Scanner myReader = new Scanner(myObj);
            String strScore = "" ;// creer un string ds lequel on va stocker notre score 
            while (myReader.hasNextLine()) {
                if (score > highestScore) {
                    highestScore = score;
                    writeIntoFileBestScore();
                } 
                strScore = myReader.nextLine();
            }
            highestScore = Integer.parseInt(strScore); // cast
            
      
      myReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    private void writeIntoFileBestScore()
    {
        try {
            FileWriter myWriter = new FileWriter("score.txt");
            myWriter.write(""+highestScore);
            myWriter.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
    private void move() {

        if (leftDirection) {
            pos_xFrogger -= DOT_SIZE;
        }

        if (rightDirection) {
            pos_xFrogger += DOT_SIZE;
        }

        if (upDirection) {
            pos_yFrogger -= DOT_SIZE;
        }

        if (downDirection) {
            pos_yFrogger += DOT_SIZE;
        }
        
    }

    // check les collisions entre le frogger et tout les cotes de l'ecran et le remettre a sa place a chaque fois 
    private void checkCollision() {

        if (pos_yFrogger >= B_HEIGHT) { 
            positionFrogger();
        }

        if (pos_yFrogger < 0) {
           positionFrogger();
        }

        if (pos_xFrogger >= B_WIDTH) {
            positionFrogger();
        }

        if (pos_xFrogger < 0) {
           
            positionFrogger();
        }
        
        if (!inGame) {
            timer.stop();
        }
    }

    // methode qui donne une position random
    private int getRandomCoordinate() { 
        int r = (int) (Math.random() * RAND_POS);
        return ((r * DOT_SIZE));
    }

    // methode appeler lorsqu'une action se produit
    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {
            moveTheCars(); // apres le faire avec un for each pr chaque voiture etc..
            moveTheInsects();
            checkFixedGameElementCollision();
            checkCarsCollision();
            checkInsectCollision();
            checkCollision();
            checkLifeAndNxtLevel();
            readFileBestScore();
            checkBushCollision();
        }

        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
            move();
        }
    }
}
