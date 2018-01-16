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

	private final int WIDTH = 700;
	private final int HEIGHT = 500;
	
	private Scene LEVEL_SCENE;
	private int LEVEL_NUM;
		
	public SceneGenerator(int levelNum) {
		LEVEL_NUM = levelNum;
		
		// create one top level collection to organize the things in the scene
        //Group root = new Group();
        // create the scene to display objects
		//LEVEL_SCENE = new Scene(root, WIDTH, HEIGHT);
	}
	
	/**
	 * 
	 * @return LEVEL_SCENE: the scene object to be displayed
	 */
	public Scene getScene() {
		return LEVEL_SCENE;
	}
}
