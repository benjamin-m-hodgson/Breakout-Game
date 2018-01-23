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
    private final Color BOOST_COLOR = Color.DARKSEAGREEN;
    private final Color STRETCH_COLOR = Color.MEDIUMPURPLE;
    private final Color CATCH_COLOR = Color.INDIANRED;
    
    private final double FRAMES_PER_SECOND = 1;
	private final double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    
    private int PADDLE_SPEED = 8;
    private int BOOST_SPEED = 12;
    
    private Timeline TIME;
    
    private boolean HAS_BALL = false;
    private boolean CATCH = false;
    private boolean BOOST = false;
    private boolean STRETCH = false;
    
    private int WAIT;
	
	public Paddle(Scene levelScene, int levelNum) {
		WAIT = 0;
		PADDLE_WIDTH = 80 - (levelNum * 10);
		STRETCH_WIDTH = PADDLE_WIDTH + 40;
		PADDLE = new Rectangle(levelScene.getWidth() / 2 - PADDLE_WIDTH/2, 
    			levelScene.getHeight() - PADDLE_HEIGHT/2 - 20,
    			PADDLE_WIDTH, PADDLE_HEIGHT);
    	PADDLE.setFill(PADDLE_COLOR);
    	TIME = new Timeline(); 
    	TIME.setCycleCount(Animation.INDEFINITE);
    	KeyFrame colorBall = new KeyFrame(Duration.millis(MILLISECOND_DELAY), 
    			e -> paddleStep(SECOND_DELAY));
    	TIME.getKeyFrames().add(colorBall);
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
		if (BOOST) {
			return BOOST_SPEED;
		}
		else {
			return PADDLE_SPEED;
		}
	}
	
	/**
	 * Uses the Paddles catch ability
	 */
	public void setCatch() {
		CATCH = true;
		PADDLE.setFill(CATCH_COLOR);
		TIME.play();
	}
	
	/** 
	 * 
	 * @return Whether or not the paddle can catch
	 */
	public boolean canCatch() {
		return CATCH && !HAS_BALL;
	}
	
	/**
	 * Updates that the paddle has caught a ball
	 */
	public void hasBall() {
		HAS_BALL = true;
	}
	
	/**
	 * Uses the Paddles boost ability
	 */
	public void setBoost() {
		BOOST = true;
		PADDLE.setFill(BOOST_COLOR);
		TIME.play();
	}
	
	/**
	 * Uses the Paddles stretch ability
	 */
	public void setStretch() {
		STRETCH = true;
		PADDLE.setFill(STRETCH_COLOR);
		PADDLE.setWidth(STRETCH_WIDTH);
		TIME.play();
	}
	
	/**
	 * 
	 * @return Whether or not a Paddle ability is activated
	 */
	public boolean getActivated() {
		return CATCH || BOOST || STRETCH;
	}
	
	/**
	 * Resets the paddle color to its original default
	 */
	public void resetColor() {
		PADDLE.setFill(PADDLE_COLOR);
	}
	
	/**
     * Updates the Paddle animation
     * 
     * @param elapsedTime: time since last frame
     */
	private void paddleStep(double elapsedTime) {
		if (CATCH || BOOST || STRETCH) {
			WAIT++;
			if (WAIT == 10 || HAS_BALL) {
				WAIT = 0;
				PADDLE.setFill(PADDLE_COLOR);
				if (STRETCH) {
					PADDLE.setWidth(PADDLE_WIDTH);
				}
				CATCH = false;
				BOOST = false;
				STRETCH = false;
				HAS_BALL = false;
				TIME.stop();
			}
		}
	}
}
