package game_bmh43;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.effect.MotionBlur;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

/**
 * 
 * @author Benjamin Hodgson
 * @date 1/22/18
 * 
 * Extra life power up class
 *
 */
public class ExtraLife implements PowerUp {
	private Circle LIFE;
    private final int LIFE_RADIUS = 10;
    private final Color LIFE_COLOR = Color.CHARTREUSE;
	
	/**
	 * Generates a power up 
	 * 
	 * @param scenePane: the root of the level scene to add the nodes to
	 */
	public ExtraLife(double x, double y, Pane scenePane) {
		LIFE = new Circle(LIFE_RADIUS, LIFE_COLOR);
    	LIFE.setCenterX(x);
    	LIFE.setCenterY(y);
    	
    	LIFE.setOpacity(0.7);
    	LIFE.setEffect(new MotionBlur());
    	scenePane.getChildren().add(LIFE);
	}

	@Override
	public Circle getShape() {
		return LIFE;
	}

	@Override
	public void update(double elapsedTime) {
    	double newY = LIFE.getCenterY() + LIFE.getTranslateY() + 
    			(DECELERATION * elapsedTime);
    	LIFE.setCenterY(newY);
		
	}
	
	@Override
	public boolean inBounds(Scene gameScene) {
		// hits left
    	if (LIFE.getCenterX() + LIFE.getTranslateX() - LIFE.getRadius() <= 0) {
    		return false;
    	}
    	// hit right
    	if (LIFE.getCenterX() + LIFE.getTranslateX() + LIFE.getRadius() >= gameScene.getWidth()) {
    		return false;
    	}
    	// hit top
    	if (LIFE.getCenterY() + LIFE.getTranslateY() + LIFE.getRadius() <= 35) {
    		return false;
    	}
    	if (LIFE.getCenterY() + LIFE.getTranslateY() + LIFE.getRadius() >= gameScene.getHeight()) {
    		return false;
    	}
		return true;
	}

	@Override
	public boolean isColliding(Paddle gamePaddle) {
		Shape paddleHit = Shape.intersect(LIFE, gamePaddle.getShape());
    	if (paddleHit.getBoundsInLocal().getWidth() != -1) {
    		return true;
        }
    	return false;
	}

	@Override
	public void fade() {
		FadeTransition blockFade = new FadeTransition(Duration.seconds(1), LIFE);
		blockFade.setFromValue(1.0);
		blockFade.setToValue(0.0);
		blockFade.play();
	}
}
