package game_bmh43;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Paddle {

	private Rectangle PADDLE;
    private final double PADDLE_WIDTH;
    private final double STRETCH_WIDTH;
    private final double PADDLE_HEIGHT = 10;
    private final Color PADDLE_COLOR = Color.WHITESMOKE;
    private final Color BOOST_COLOR = Color.GREEN;
    private final Color STRETCH_COLOR = Color.PURPLE;
    private final Color CATCH_COLOR = Color.RED;
    private int PADDLE_SPEED = 8;
    private double BOOST_AMOUNT = 1.5;
    
    private Timeline TIME;
    
    private boolean BOOST = false;
    private boolean hasBall = false;
	
	public Paddle(Scene levelScene, int levelNum) {
		PADDLE_WIDTH = 80 - (levelNum * 10);
		STRETCH_WIDTH = PADDLE_WIDTH + 10;
		PADDLE = new Rectangle(levelScene.getWidth() / 2 - PADDLE_WIDTH/2, 
    			levelScene.getHeight() - PADDLE_HEIGHT/2 - 20,
    			PADDLE_WIDTH, PADDLE_HEIGHT);
    	PADDLE.setFill(PADDLE_COLOR);
	}
	
	/**
	 * 
	 * @return Paddle cast to a Node object
	 */
	public Node getNode() {
		return (Node) PADDLE;
	}
	
	/**
	 * 
	 * @return Paddle as it's Shape object
	 */
	public Rectangle getShape() {
		return PADDLE;
	}
	
	/**
	 * 
	 * @return the Paddle's speed
	 */
	public int getSpeed() {
		return PADDLE_SPEED;
	}
}
