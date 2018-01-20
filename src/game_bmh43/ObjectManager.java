package game_bmh43;

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.shape.Shape;

public class ObjectManager {
	
	ArrayList<Block> BLOCK_LIST;
	ArrayList<Ball> BALL_LIST;
	ArrayList<Object> SPRITE_LIST;
	
	public ObjectManager() {
		BLOCK_LIST = new ArrayList<Block>();
		BALL_LIST = new ArrayList<Ball>();
		SPRITE_LIST = new ArrayList<Object>();
	}
	
	/**
	 * 
	 * @return SPRITE_LIST: a list of the balls and blocks in the scene
	 */
	public ArrayList<Object> getObjects() {
		return SPRITE_LIST;
	}
	
	/**
	 * Adds a block to the block list
	 */
	public void addBlock(Block newBlock) {
		BLOCK_LIST.add(newBlock);
		SPRITE_LIST.add(newBlock);
	}
	
	/**
	 * Adds a ball to the ball list
	 */
	public void addBall(Ball newBall) {
		BALL_LIST.add(newBall);
		SPRITE_LIST.add(newBall);
	}
	
	/**
	 * 
	 * @return the number of Blocks in the scene
	 */
	public int numBlocks() {
		return BLOCK_LIST.size();
	}
	
	/**
	 * 
	 * @return the number of Balls in the scene
	 */
	public int numBalls() {
		return BALL_LIST.size();
	}
	
	/**
	 * Empties the list of Blocks
	 */
	public void resetBlocks() {
		BLOCK_LIST.clear();
	}
	
	/**
	 * Empties the list of Balls
	 */
	public void resetBalls() {
		BALL_LIST.clear();
	}
}
