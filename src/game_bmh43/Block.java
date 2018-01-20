package game_bmh43;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block {
	private Rectangle BLOCK;
    private final double BLOCK_WIDTH = 20;
    private final double BLOCK_HEIGHT = 20;
    private final Color BLOCK_COLOR = Color.ORANGE;
    private final String BLOCK_STANDARD = "Standard block";
    private final String BLOCK_TWO = "Two-Hit block";
    private final String BLOCK_THREE = "Three-hit block";
    private final String BLOCK_SPEED = "Speed block";
	
	public Block(int x, int y) {
		BLOCK = new Rectangle(x, y,
    			BLOCK_WIDTH, BLOCK_HEIGHT);
    	BLOCK.setFill(BLOCK_COLOR);
	}
	
	/**
	 * 
	 * @return Block cast to a Node object
	 */
	public Node getNode() {
		return (Node) BLOCK;
	}
}