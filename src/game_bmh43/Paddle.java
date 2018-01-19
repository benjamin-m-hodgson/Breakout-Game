package game_bmh43;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle {

	private Rectangle PADDLE;
    private final double PADDLE_WIDTH = 70;
    private final double PADDLE_HEIGHT = 10;
    private final Color PADDLE_COLOR = Color.WHITESMOKE;
    private final Color BOOST_COLOR = Color.GREEN;
    private final Color STRETCH_COLOR = Color.PURPLE;
    private final Color THROW_COLOR = Color.RED;
    private int PADDLE_SPEED = 30;		// screen width is off by 40 for block area
    
    private int PADDLE_WAIT = 0;
    private boolean ACTIVATED = false;
    private boolean hasBall = false;
   
    private double xMax;
	
	public Paddle(Scene levelScene) {
		xMax = levelScene.getWidth();
		PADDLE = new Rectangle(levelScene.getWidth() / 2, 
    			levelScene.getHeight() - PADDLE_HEIGHT/2 - 20,
    			PADDLE_WIDTH, PADDLE_HEIGHT);
    	PADDLE.setFill(PADDLE_COLOR);
    	// respond to input
        levelScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
	}
	
    
    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        if (code == KeyCode.RIGHT && 
        		(PADDLE.getX() + PADDLE_SPEED) <= xMax) {
            PADDLE.setX(PADDLE.getX() + PADDLE_SPEED);
        }
        else if (code == KeyCode.LEFT &&
        		(PADDLE.getX() - PADDLE_SPEED) >= 0) {
            PADDLE.setX(PADDLE.getX() - PADDLE_SPEED);
        }
    }
	
	/**
	 * 
	 * @return Paddle cast to a Node object
	 */
	public Node getNode() {
		return (Node) PADDLE;
	}
}
