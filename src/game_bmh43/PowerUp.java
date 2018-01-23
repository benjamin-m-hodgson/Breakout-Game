package game_bmh43;

import javafx.scene.Scene;
import javafx.scene.shape.Shape;

public interface PowerUp {
	
	final int DECELERATION = 200;
	
	/**
	 * 
	 * @return The Shape of the PowerUp
	 */
	public Shape getShape();
	
	/**
	 * Updates the position of the PowerUp
	 * 
	 * @param elapsedTime: time since the last frame
	 */
	public void update(double elapsedTime);
	
	/**
     * Checks to make sure the Ball stays in bounds
     * 
     * @param gameScene: the level scene containing the ball
     */
	public boolean inBounds(Scene gameScene);
	
	/**
     * Updates the velocity components of the PowerUp after a collision
     * 
     * @param gamePaddle: the paddle this PowerUp is colliding with
     */
    public boolean isColliding(Paddle gamePaddle);
    
    /**
     * Animation to fade out the PowerUp
     */
    public void fade();
	
}
