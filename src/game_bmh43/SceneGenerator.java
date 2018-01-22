package game_bmh43;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

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
	private final String TYPE_STANDARD = "Standard";
    private final String TYPE_TWO = "Two-Hit";
    private final String TYPE_THREE = "Three-Hit";
    private final String TYPE_SPEED = "Speed";
	
	private Scene LEVEL_SCENE;
	private int LEVEL_NUM;
	private Pane LEVEL_PANE;
	private ArrayList<Block> GENERATED_BLOCKS;
	
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
	 * 
	 * @return GENERATED_BLOCKS: a list of blocks added
	 */
	public ArrayList<Block> getBlockList() {
		return GENERATED_BLOCKS;
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
		GENERATED_BLOCKS = new ArrayList<Block>();
		while (in.hasNextInt()) {
			int xPos = in.nextInt();
			int yPos = in.nextInt();
			// conditional to determine if a block appears
			int blockSpawn = numGenerator.nextInt(11);
			if (blockSpawn <= 4 + LEVEL_NUM) {
				// generate block
				String type = TYPE_STANDARD;
				int typeSpawn = numGenerator.nextInt(8);
				if (typeSpawn + LEVEL_NUM > 6) {
					type = TYPE_THREE;
				}
				else if (typeSpawn + LEVEL_NUM > 5) {
					type = TYPE_TWO;
				}
				else if (typeSpawn + LEVEL_NUM == 5) {
					type = TYPE_SPEED;
				}
				Block addBlock = new Block(xPos, yPos, type);
				GENERATED_BLOCKS.add(addBlock);
				// add block to the scene as a scene node
				blockPos.getChildren().add(addBlock.getBlock());
			}
		}
		in.close();
		LEVEL_PANE = blockPos;
		return blockPos;		
	}
}
