package game_bmh43;

public class Player {
	
	private int LIVES;
	private int BALLS;
	private int ABILITY_COINS;

	public Player() {
		ABILITY_COINS = 0;
		BALLS = 1;
		generateLives();
	}
	
	/**
	 * 
	 * @return LIVES: the number of remaining lives for the player
	 */
	public int getLives() {
		return LIVES;
	}
	
	/**
	 * 
	 * @return BALLS: the number of Balls the player can fire
	 */
	public int getBalls() {
		return BALLS;
	}
	
	/**
	 * 
	 * @return ABILITY_COINS: the number of available ability coins
	 */
	public int getAbilityCoins() {
		return ABILITY_COINS;
	}
	
	/**
	 * Gives the player one life
	 */
	public void addLife() {
		LIVES++;
	}
	
	/**
	 * Gives the player one ball
	 */
	public void addBall() {
		BALLS++;
	}
	
	/**
	 * Decrements one from the players lives
	 */
	public void loseLife() {
		LIVES = LIVES - 1;
	}
	
	/**
	 * Decrements one from the players Balls
	 */
	public void loseBall() {
		BALLS = BALLS - 1;
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
