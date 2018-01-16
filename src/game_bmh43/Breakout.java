package game_bmh43;

import javafx.application.Application;
import javafx.stage.Stage;


/**
 * 
 * @author Benjamin Hodgson
 * @date 1/15/2018
 * 
 * The driver JavaFX program to start and play a Breakout game variation.
 *
 */
public class Breakout extends Application {
	
	private final String TITLE = "Breakout";
	
	// create the driver for the game
	GameEngine gameDriver = new GameEngine(TITLE);
	
	/**
	 * Initialize the game and begin the game loop 
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// display the menu screen
		gameDriver.startGame(primaryStage);
		// begin the game
		gameDriver.playGame(primaryStage);
		
		primaryStage.show();
	}
	
	/**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }

}
