package game_bmh43;

import java.util.Random;
import java.util.Scanner;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * 
 * @author Benjamin Hodgson
 * @date 1/15/2018
 *
 * A class to generate the blocks for the Scene in the level
 */
public class SceneGenerator {

	private final int WIDTH = 800;
	private final int HEIGHT = 600;
	private final String BLOCK_FILE = "BlockPositions.txt";
	
	private Scene LEVEL_SCENE;
	private int LEVEL_NUM;
	private Pane LEVEL_PANE;
		
	public SceneGenerator(int levelNum) {
		LEVEL_NUM = levelNum;
		buildScene();
	}
	
	/**
	 * 
	 * @return LEVEL_SCENE: the scene object to be displayed
	 */
	public Scene getScene() {
		return LEVEL_SCENE;
	}
	
	/**
	 * 
	 * @return LEVEL_PANE: the Pane Parent object in the scene
	 */
	public Pane getPane() {
		return LEVEL_PANE;
	}
	
	/**
	 * Build the scene 
	 */
	private void buildScene() {
		Pane blockSpawns = getPositions();
		LEVEL_SCENE = new Scene(blockSpawns, WIDTH, HEIGHT);
	}
	
	/**
	 * Reads the possible block positions from the text file
	 * 18 possible blocks per row, 12 possible blocks per column
	 */
	private Pane getPositions() {
		Scanner in = new Scanner(getClass().getClassLoader().getResourceAsStream(BLOCK_FILE));
		Random numGenerator = new Random();
		// skip the first 3 informational lines in the file
		in.nextLine();
		in.nextLine();
		in.nextLine();
		// read the rest of the positions in the file
		Pane blockPos = new Pane();
		blockPos.setStyle("-fx-background-color: #696969");
		while (in.hasNextInt()) {
			int xPos = in.nextInt();
			int yPos = in.nextInt();
			// conditional to determine if a block appears
			int blockSpawn = numGenerator.nextInt(101);
			if (blockSpawn <= 40 + (LEVEL_NUM * 10)) {
				// generate block
				Block addBlock = new Block(xPos, yPos);
				blockPos.getChildren().add(addBlock.getNode());
			}
		}
		in.close();
		LEVEL_PANE = blockPos;
		return blockPos;		
	}
}
