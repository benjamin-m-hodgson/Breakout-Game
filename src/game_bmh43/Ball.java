package game_bmh43;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Ball {

	private Circle BALL;
    private final int BALL_RADIUS = 6;
    private final Color BALL_COLOR = Color.WHITESMOKE;
    private final double BALL_BOOST = 1.5;
    
    private double XVELOCITY;
    private double YVELOCITY;
    private double MULTIPLIER;
    private int SHADOW_TIMER = 3;
    
    private Transition COLOR_CHANGE;
    
    private boolean SHADOW = false;
    private boolean FRENZY = false;
    private boolean DEAD = false;
    
    public Ball(double x, double y) {
    	this(x, y, 0, 0, 1);
    }
    
    public Ball(double xPos, double yPos,
    		double xV, double yV, int levelNum) {
    	BALL = new Circle(BALL_RADIUS, BALL_COLOR);
    	BALL.setCenterX(xPos);
    	BALL.setCenterY(yPos);
    	XVELOCITY = xV;
    	YVELOCITY = yV;
    	MULTIPLIER = 0.9 + (levelNum * 0.1);
    	COLOR_CHANGE = new Transition() {
    		{
    			setCycleCount(Timeline.INDEFINITE);
    			setInterpolator(Interpolator.DISCRETE);
    		}
			@Override
			protected void interpolate(double arg0) {
				BALL.setFill(randomColor());
			}
		};
    }
    
    /**
     * 
     * @return Ball cast to a Node object
     */
    public Node getNode() {
    	return (Node) BALL;
    }
    
    /**
     * 
     * @return Ball as it's Shape object
     */
    public Shape getBall() {
    	return BALL;
    }
    
    /**
     * 
     * @return The radius of the Ball
     */
    public int getRadius() {
    	return BALL_RADIUS;
    }
    
    /**
     * @return Whether the ball is a shadow ball
     */
    public boolean getShadow() {
    	return SHADOW;
    }
    
    /**
     * Update the ball's position
     */
    public void update(double elapsedTime) {
    	if (SHADOW) {
    		SHADOW_TIMER = SHADOW_TIMER - 1;
    		BALL.setOpacity(0.5);
    		if (SHADOW_TIMER == 0) {
    			SHADOW = false;
    			SHADOW_TIMER = 3;
    			BALL.setOpacity(1);
    		}
    	}
    	double newX = BALL.getCenterX() + BALL.getTranslateX() - 
    			(XVELOCITY * MULTIPLIER * elapsedTime);
    	double newY = BALL.getCenterY() + BALL.getTranslateY() - 
    			(YVELOCITY * MULTIPLIER * elapsedTime);
    	BALL.setCenterX(newX);
    	BALL.setCenterY(newY);
    }
    
    /**
     * Checks to make sure the Ball stays in bounds
     * 
     * @param gameScene: the level scene containing the ball
     */
    public void checkBounds(Scene gameScene) {
    	// hits left
    	if (BALL.getCenterX() + BALL.getTranslateX() - BALL.getRadius() <= 0) {
    		XVELOCITY = -XVELOCITY;
    	}
    	// hit right
    	if (BALL.getCenterX() + BALL.getTranslateX() + BALL.getRadius() >= gameScene.getWidth()) {
    		XVELOCITY = -XVELOCITY;
    	}
    	// hit top
    	if (BALL.getCenterY() + BALL.getTranslateY() + BALL.getRadius() <= 35) {
    		YVELOCITY = -YVELOCITY;
    	}
    	if (BALL.getCenterY() + BALL.getTranslateY() + BALL.getRadius() >= gameScene.getHeight() - 20) {
    		DEAD = true;
    	}
    }
    
    /**
     * Checks to see if the Ball goes below the paddle
     */
    public boolean isDead() {
    	return DEAD;
    	
    }
    
    /**
     * Updates the velocity components of the Ball after a collision
     * 
     * @param otherBall: the other Ball this Ball is colliding with
     */
    public void handleCollision(Ball otherBall, double elapsedTime) {
    	// move the ball back one frame
    	backStep(elapsedTime);
    	// update its velocity
    	XVELOCITY = -XVELOCITY;
    	YVELOCITY = -YVELOCITY;
    }
    
    /**
     * Updates the velocity components of the Ball after a collision.
     * Multiple else if statements were used instead of logical or operations
     * to increase readability.
     * 
     * @param otherBlock: the block this Ball is colliding with
     */
    public void handleCollision(Block otherBlock, double elapsedTime) {
    	// move the ball back one frame
    	backStep(elapsedTime);
    	otherBlock.handleHit();
    	XVELOCITY = -XVELOCITY;
    	YVELOCITY = -YVELOCITY;
    }
    
    /**
     * Updates the velocity components of the Ball after a collision
     * 
     * @param gamePaddle: the paddle this Ball is colliding with
     */
    public void handleCollision(Paddle gamePaddle, double elapsedTime) {
    	// move the ball back one frame
    	backStep(elapsedTime);
    	Random numGenerator = new Random();
    	int xRand = numGenerator.nextInt(21) - 10;
    	XVELOCITY = XVELOCITY + xRand;
    	YVELOCITY = -YVELOCITY;
    }
    
    /**
     * 
     * @return FRENZY: true if ball is in frenzy mode, false otherwise
     */
    public boolean inFrenzy() {
    	return FRENZY;
    }
    
    /**
     * Turns on frenzy mode
     */
    public void startFrenzy() {
    	FRENZY = true;
    	COLOR_CHANGE.playFromStart();
    }
    
    /**
     * Turns off frenzy mode
     */
    public void stopFrenzy() {
    	FRENZY = false;
    	COLOR_CHANGE.stop();
    	BALL.setFill(BALL_COLOR);
    }
    
    /**
     * Applies a boost to the ball's speed
     */
    public void applyBoost() {
    	MULTIPLIER = MULTIPLIER + 0.1;
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
    
    /**
     * Moves the ball's position one frame backwards to reduce clipping through objects
     */
    private void backStep(double elapsedTime) {
    	double newX = BALL.getCenterX() + BALL.getTranslateX() + 
    			(XVELOCITY * MULTIPLIER * elapsedTime);
    	double newY = BALL.getCenterY() + BALL.getTranslateY() + 
    			(YVELOCITY * MULTIPLIER * elapsedTime);
    	BALL.setCenterX(newX);
    	BALL.setCenterY(newY);
    }
	
}
