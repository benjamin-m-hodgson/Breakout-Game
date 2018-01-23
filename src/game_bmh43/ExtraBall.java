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
 * Extra ball power up class
 *
 */
public class ExtraBall implements PowerUp {
	private Circle BALL;
    private final int BALL_RADIUS = 10;
    private final Color BALL_COLOR = Color.BEIGE;
	
	/**
	 * Generates a power up 
	 * 
	 * @param scenePane: the root of the level scene to add the nodes to
	 */
	public ExtraBall(double x, double y, Pane scenePane) {
		BALL = new Circle(BALL_RADIUS, BALL_COLOR);
		BALL.setCenterX(x);
    	BALL.setCenterY(y);
    	BALL.setOpacity(0.7);
    	BALL.setEffect(new MotionBlur());
    	scenePane.getChildren().add(BALL);
	}

	@Override
	public Circle getShape() {
		return BALL;
	}

	@Override
	public void update(double elapsedTime) {
    	double newY = BALL.getCenterY() + BALL.getTranslateY() + 
    			(DECELERATION * elapsedTime);
    	BALL.setCenterY(newY);
		
	}
	
	@Override
	public boolean inBounds(Scene gameScene) {
		// hits left
    	if (BALL.getCenterX() + BALL.getTranslateX() - BALL.getRadius() <= 0) {
    		return false;
    	}
    	// hit right
    	if (BALL.getCenterX() + BALL.getTranslateX() + BALL.getRadius() >= gameScene.getWidth()) {
    		return false;
    	}
    	// hit top
    	if (BALL.getCenterY() + BALL.getTranslateY() + BALL.getRadius() <= 35) {
    		return false;
    	}
    	if (BALL.getCenterY() + BALL.getTranslateY() + BALL.getRadius() >= gameScene.getHeight()) {
    		return false;
    	}
		return true;
	}

	@Override
	public boolean isColliding(Paddle gamePaddle) {
		Shape paddleHit = Shape.intersect(BALL, gamePaddle.getShape());
    	if (paddleHit.getBoundsInLocal().getWidth() != -1) {
    		return true;
        }
    	return false;
	}

	@Override
	public void fade() {
		FadeTransition blockFade = new FadeTransition(Duration.seconds(1), BALL);
		blockFade.setFromValue(1.0);
		blockFade.setToValue(0.0);
		blockFade.play();
	}
}
