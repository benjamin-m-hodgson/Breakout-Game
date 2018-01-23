package game_bmh43;

public class Player {
	
	private int LIVES;
	private int BALLS;
	private int ABILITY_COINS;
	private int SCORE;
	private boolean DEAD;

	public Player() {
		ABILITY_COINS = 0;
		SCORE = 0;
		BALLS = 1;
		LIVES = 2;
		DEAD = false;
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
	 * 
	 * @return SCORE: the players score
	 */
	public int getScore() {
		return SCORE;
	}
	
	/**
	 * Adds a number to the player's score
	 * 
	 * @param num: value to be added to score
	 */
	public void addScore(int num) {
		SCORE = SCORE + num;
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
	 * Gives the player one ability coin
	 */
	public void addAbility() {
		ABILITY_COINS++;
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
	 * Decrements one from the players Ability coins
	 */
	public void loseAbility() {
		ABILITY_COINS = ABILITY_COINS - 1;
	}
	
	/**
	 * Checks to see if the player has any remaining lives
	 */
	public void updateDead() {
		if (LIVES < 0) {
			LIVES = 0;
			DEAD = true;
		}
	}
	
	/**
	 * @return if the player has any remaining lives
	 * 
	 */
	public boolean isDead() {
		return DEAD;
	}
}
