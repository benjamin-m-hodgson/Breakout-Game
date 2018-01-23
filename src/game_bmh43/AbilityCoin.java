package game_bmh43;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.effect.MotionBlur;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class AbilityCoin implements PowerUp{

	private Circle ABILITY;
    private final int ABILITY_RADIUS = 10;
    private final Color ABILITY_COLOR = Color.GOLD;
	
	/**
	 * Generates a power up 
	 * 
	 * @param scenePane: the root of the level scene to add the nodes to
	 */
	public AbilityCoin(double x, double y, Pane scenePane) {
		ABILITY = new Circle(ABILITY_RADIUS, ABILITY_COLOR);
    	ABILITY.setCenterX(x);
    	ABILITY.setCenterY(y);
    	
    	ABILITY.setOpacity(0.7);
    	ABILITY.setEffect(new MotionBlur());
    	scenePane.getChildren().add(ABILITY);
	}

	@Override
	public Circle getShape() {
		return ABILITY;
	}

	@Override
	public void update(double elapsedTime) {
    	double newY = ABILITY.getCenterY() + ABILITY.getTranslateY() + 
    			(DECELERATION * elapsedTime);
    	ABILITY.setCenterY(newY);
		
	}
	
	@Override
	public boolean inBounds(Scene gameScene) {
		// hits left
    	if (ABILITY.getCenterX() + ABILITY.getTranslateX() - ABILITY.getRadius() <= 0) {
    		return false;
    	}
    	// hit right
    	if (ABILITY.getCenterX() + ABILITY.getTranslateX() + ABILITY.getRadius() >= gameScene.getWidth()) {
    		return false;
    	}
    	// hit top
    	if (ABILITY.getCenterY() + ABILITY.getTranslateY() + ABILITY.getRadius() <= 35) {
    		return false;
    	}
    	if (ABILITY.getCenterY() + ABILITY.getTranslateY() + ABILITY.getRadius() >= gameScene.getHeight()) {
    		return false;
    	}
		return true;
	}

	@Override
	public boolean isColliding(Paddle gamePaddle) {
		Shape paddleHit = Shape.intersect(ABILITY, gamePaddle.getShape());
    	if (paddleHit.getBoundsInLocal().getWidth() != -1) {
    		return true;
        }
    	return false;
	}

	@Override
	public void fade() {
		FadeTransition blockFade = new FadeTransition(Duration.seconds(1), ABILITY);
		blockFade.setFromValue(1.0);
		blockFade.setToValue(0.0);
		blockFade.play();
	}
}
