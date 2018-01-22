package game_bmh43;

import java.util.Random;

import javafx.scene.layout.Pane;

/**
 * 
 * @author Benjamin Hodgson
 * @date 1/22/18
 * 
 * A class to generate power ups to put into the level scene
 *
 */
public class PowerUpGenerator {

	/**
	 * Generates a power up 
	 * 
	 * @param scenePane: the root of the level scene to add the nodes to
	 */
	public void generatePowerUp(double x, double y, Pane scenePane) {
		Random numGenerator = new Random();
		int getChance = numGenerator.nextInt(10);
		
	}
	
	/**
	 * Generates a shadow ball power up
	 * 
	 * @return
	 */
	private Ball generateShadowBall(double x, double y) {
		return null;
		
	}
	
}
