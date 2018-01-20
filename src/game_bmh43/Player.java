package game_bmh43;

public class Player {
	
	private int LIVES;

	public Player() {
		generateLives();
	}
	
	/**
	 * Gives the player one life
	 */
	public void addLife() {
		LIVES++;
	}
	
	/**
	 * Decrements one from the players lives
	 */
	public void loseLife() {
		LIVES = LIVES - 1;
	}
	
	/**
	 * Checks to see if the player has any remaining lives
	 */
	public boolean isDead() {
		if (LIVES < 0) {
			LIVES = 0;
			return true;
		}
		return false;
	}
	
	/**
	 * Gives the player the correct starting number of lives
	 */
	private void generateLives() {
		LIVES = 2;
	}
	
}
