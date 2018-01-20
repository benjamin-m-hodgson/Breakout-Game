package game_bmh43;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball {

	private Circle BALL;
    private final int BALL_RADIUS = 12;
    private final Color BALL_COLOR = Color.WHITESMOKE;
    
    private double XVELOCITY;
    private double YVELOCITY;
    private double MULTIPLIER;
    
    private boolean FRENZY = false;
    
    public Ball(double x, double y) {
    	this(x, y, 0, 0);
    }
    
    public Ball(double xPos, double yPos,
    		double xV, double yV) {
    	BALL = new Circle(BALL_RADIUS, BALL_COLOR);
    	BALL.relocate(xPos, yPos);
    	XVELOCITY = xV;
    	YVELOCITY = yV;
    	MULTIPLIER = 1;
    }
    
    /**
     * 
     * @return Ball cast to a Node object
     */
    public Node getNode() {
    	return (Node) BALL;
    }
    
    /**
     * Update the ball's position
     */
    public void update() {
    	// check for collisions to update velocity appropriately
    	double newX = BALL.getTranslateX() + (XVELOCITY * MULTIPLIER);
    	double newY = BALL.getTranslateY() + (YVELOCITY * MULTIPLIER);
    	if (FRENZY) {
    		newX = newX + 2;
    		newY = newY + 2;
    		BALL.setFill(randomColor());
    	}
    	BALL.relocate(newX, newY);
    	
    }
    
    /**
     * Turns on frenzy mode
     */
    public void startFrenzy() {
    	FRENZY = true;
    }
    
    /**
     * Turns off frenzy mode
     */
    public void stopFrenzy() {
    	FRENZY = false;
    	BALL.setFill(BALL_COLOR);
    }
    
    /**
     * Applies a boost to the ball's speed
     */
    public void applyBoost() {
    	MULTIPLIER = 1.3;
    }
    
    /**
     * 
     * @return Color: a Randomly chosen Color
     */
    private Color randomColor() {
    	ArrayList<Color> colorList = new ArrayList<Color>();
    	colorList.add(Color.YELLOW);
    	colorList.add(Color.GREEN); 
    	colorList.add(Color.PURPLE); 
    	colorList.add(Color.RED);
    	colorList.add(Color.BLUE); 
    	colorList.add(Color.PINK);
    	Random numPicker = new Random();
    	Color randColor = colorList.get(numPicker.nextInt(colorList.size()));
    	return randColor;
    }
	
}
