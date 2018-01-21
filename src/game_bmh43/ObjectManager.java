package game_bmh43;

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.shape.Shape;

public class ObjectManager {
	
	private ArrayList<Block> BLOCK_LIST;
	private ArrayList<Ball> BALL_LIST;
	private ArrayList<Object> SPRITE_LIST;
	private Paddle GAME_PADDLE;	
	
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
	 * 
	 * @return BALL_LIST: a list of the balls in the scene
	 */
	public ArrayList<Ball> getBalls() {
		return BALL_LIST;
	}
	
	/**
	 * 
	 * @return GAME_PADDLE: the Paddle object in the scene
	 */
	public Paddle getPaddle() {
		return GAME_PADDLE;
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
	 * Assigns the game paddle
	 * 
	 * @param newPaddle: paddle to be added
	 */
	public void addPaddle(Paddle newPaddle) {
		GAME_PADDLE = newPaddle;
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
	 * Removes a Block from the ObjectManager
	 */
	public void removeBlock(Block otherBlock) {
		int pos = BLOCK_LIST.indexOf(otherBlock);
		BLOCK_LIST.remove(pos);
		for (Object gameObject : SPRITE_LIST) {
			if (gameObject instanceof Block &&
					gameObject.equals(otherBlock)) {
				SPRITE_LIST.remove(gameObject);
			}
		}
	}
	
	/**
	 * Empties the list of Balls
	 */
	public void resetBalls() {
		BALL_LIST.clear();
	}
}
