package game_bmh43;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 
 * @author Benjamin Hodgson
 * @date 1/15/2018
 * 
 * This class contains the game mechanics and creates 
 *
 */
public class GameEngine {
	
	private final int WIDTH = 800;
	private final int HEIGHT = 600;
	private final Paint BACKGROUND_COLOR = Color.DIMGRAY;
	
	private final int FRAMES_PER_SECOND = 60;
	private final String TITLE;
	
	private final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	
	/**
	 * 
	 * @param fps: number of animation frames per second
	 * @param title: the title of the game
	 */
	public GameEngine(String title) {
		TITLE = title;
	}
	
	/**
	 * Creates the initial start up menu
	 * 
	 * @param primaryStage: the primary game window
	 */
	public void startGame(Stage primaryStage) {
		// set the title of the window
		primaryStage.setTitle(TITLE);
		
		// create, position, and style the play button
		Button playButton = new Button("Play Game");
		playButton.setStyle("-fx-background-color: #FFA500; "
				+ "-fx-font-size: 28 arial; "
				+ "-fx-text-fill: #FFFFFF");
		// add a shadow on mouse hover
		playButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				playButton.setEffect(new Glow());	
			}
		});
		// remove shadow on exit
		playButton.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				playButton.setEffect(null);
			}		
		});
		// handle button click
		playButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// generate the first level
				SceneGenerator firstScene = new SceneGenerator(WIDTH, HEIGHT, BACKGROUND_COLOR);	
				primaryStage.setScene(firstScene.getScene());
			}
		});
		
		// create one top level grid to organize the things in the scene
		VBox menuRoot = new VBox(20, playButton);
		
		// create the scene to display objects
		Scene startMenu = new Scene(menuRoot, WIDTH, HEIGHT);
		
		primaryStage.setScene(startMenu);
	}
	
	/**
	 * 
	 * @param primaryStage: the primary game window
	 */
	public void playGame(Stage primaryStage) {
		System.out.printf("Play!");
		
		// attach "game loop" to timeline to play it
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                                      e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
	}
	
	// Change properties of shapes to animate them 
    private void step (double elapsedTime) {
    	// update objects
    }
	
	/**
	 * 
	 * @param primaryStage: the primary game window
	 */
	public void endGame(Stage primaryStage) {
		System.out.printf("Game over!");
	}
}