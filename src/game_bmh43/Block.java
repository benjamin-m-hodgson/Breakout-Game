package game_bmh43;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Block {
	private Rectangle BLOCK;
    private final double BLOCK_WIDTH = 20;
    private final double BLOCK_HEIGHT = 20;
    private final Color BLOCK_STANDARD = Color.ORANGE;
    private final Color BLOCK_TWO = Color.AQUA;
    private final Color BLOCK_THREE = Color.HOTPINK;
    private final Color BLOCK_SPEED = Color.GREENYELLOW;
    private final String TYPE_STANDARD = "Standard";
    private final String TYPE_TWO = "Two-Hit";
    private final String TYPE_THREE = "Three-Hit";
    private final String TYPE_SPEED = "Speed";
    
    private String TYPE;
    private boolean DEAD;
    private int HITS;
    private int POINTS;
	
	public Block(int x, int y, String type) {
		BLOCK = new Rectangle(x, y,
    			BLOCK_WIDTH, BLOCK_HEIGHT);
		if (type.equals(TYPE_STANDARD)) {
			HITS = 1;
			BLOCK.setFill(BLOCK_STANDARD);
		}
		if (type.equals(TYPE_SPEED)) {
			HITS = 1;
			BLOCK.setFill(BLOCK_SPEED);
		}
		if (type.equals(TYPE_TWO)) {
			HITS = 2;
			BLOCK.setFill(BLOCK_TWO);
		}
		if (type.equals(TYPE_THREE)) {
			HITS = 3;
			BLOCK.setFill(BLOCK_THREE);
		}
		TYPE = type;
		POINTS = 10 * HITS;
	}
	
	/**
	 * 
	 * @return Block cast to a Node object
	 */
	public Node getNode() {
		return (Node) BLOCK;
	}
	
	/**
	 * 
	 * @return Block as it's Shape object
	 */
	public Shape getBlock() {
		return BLOCK;
	}
	
	/**
	 * 
	 * @return The number of Points this block is worth
	 */
	public int getPoints() {
		return POINTS;
	}
	
	/**
	 * Handle the destruction of the block
	 */
	public void handleHit() {
		HITS = HITS - 1;
		if (HITS == 0) {
			DEAD = true;
		}
		updateColor();
	}
	
	/**
	 * Returns if the block can't take any more hits 
	 */
	public boolean isDead() {
		return DEAD;
	}
	
	/**
	 * 
	 * Gives the correct block color corresponding to the number of hits remaining
	 */
	private void updateColor() {
		if (HITS == 1) {
			if (TYPE.equals(TYPE_SPEED)) {
				BLOCK.setFill(BLOCK_SPEED);
			}
			else { 
				BLOCK.setFill(BLOCK_STANDARD);
			}
		}
		else if (HITS == 2) {
			BLOCK.setFill(BLOCK_TWO);
			//System.out.println(HITS);
		}
		else if (HITS == 3) {
			BLOCK.setFill(BLOCK_THREE);
		}
	}
}
