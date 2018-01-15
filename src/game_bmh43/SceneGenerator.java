package game_bmh43;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;

/**
 * 
 * @author Benjamin Hodgson
 * @date 1/15/2018
 *
 * A class to generate the scene to be displayed for a level
 */
public class SceneGenerator {

	private Scene LEVEL_SCENE;
		
	public SceneGenerator(int width, int height, Paint background) {
		// create one top level collection to organize the things in the scene
        Group root = new Group();
        // create the scene to display objects
		LEVEL_SCENE = new Scene(root, width, height, background);
	}
	
	/**
	 * 
	 * @return LEVEL_SCENE: the scene object to be displayed
	 */
	public Scene getScene() {
		return LEVEL_SCENE;
	}
}
