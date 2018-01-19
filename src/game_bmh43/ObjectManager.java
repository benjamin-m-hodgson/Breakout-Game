package game_bmh43;

import java.util.ArrayList;

import javafx.scene.Node;

public class ObjectManager {
	
	ArrayList<Node> BLOCK_LIST;
	ArrayList<Ball> BALL_LIST;
	
	public ObjectManager() {
		BLOCK_LIST = new ArrayList<Node>();
		BALL_LIST = new ArrayList<Ball>();
	}
	
	/**
	 * Adds a ball to the ball list
	 */
	public void addBlock(Node newBlock) {
		BLOCK_LIST.add(newBlock);
	}
	
	/**
	 * Adds a ball to the ball list
	 */
	public void addBall(Ball newBall) {
		BALL_LIST.add(newBall);
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
