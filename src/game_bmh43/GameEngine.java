package game_bmh43;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
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
		// attach "game loop" to timeline to play it
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                                      e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();		
	}
	
	/**
	 * 
	 * @param primaryStage: the primary game window
	 */
	public void playGame(Stage primaryStage) {
		//System.out.printf("Play!");        
		// create, position, and style the play button
     	Button playButton = new Button("Play Game");
     	playButton.setStyle("-fx-background-color: #FFA500; "
     			+ "-fx-font-size: 28 futura; "
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
     			generateLevel(primaryStage, 1);
     		}
     	});
     	// generate the complete start Menu
     	Scene startMenu = generateMenu(playButton);
     	primaryStage.setScene(startMenu);
	}
	
	/**
	 * 
	 * @param primaryStage: the primary game window
	 */
	public void endGame(Stage primaryStage) {
		System.out.printf("Game over!");
	}
	
	// Change properties of shapes to animate them 
    private void step (double elapsedTime) {
    	// update objects
    }
    
    /**
     * A method to take a play button and generate the surrounding menu screen 
     * 
     * @param playButton: the play button displayed to the user
     * @return Scene startMenu: the complete start menu displayed to the user
     */
    private Scene generateMenu(Button playButton) {
    	// create and style the title 
     	Label gameTitle = new Label(TITLE);
     	gameTitle.setFont(new Font("Futura", 45));
     	gameTitle.setEffect(new Glow());
     	gameTitle.setStyle("-fx-text-fill: #FFFFFF");
     	// create and style the rule box
     	Label ruleTitle = new Label("Rules");
     	ruleTitle.setFont(new Font("Futura", 20));
     	ruleTitle.setStyle("-fx-text-fill: #FFFFFF; "
     			+ "-fx-underline: true");
     	VBox ruleBox = new VBox(10, ruleTitle);
     	ruleBox.setAlignment(Pos.CENTER);
     	ruleBox.setMaxWidth(500);
     	ruleBox.setStyle("-fx-text-fill: #FFFFFF; "
     			+ "-fx-border-style: solid inside; "
     			+ "-fx-border-width: 2; "
     			+ "-fx-border-radius: 5; "
     			+ "-fx-border-color: #FFA500; "
     			+ "-fx-background-color: #696969");
     	// create one top level grid to organize the things in the scene
     	VBox menuRoot = new VBox(20, gameTitle, ruleBox, playButton);
     	menuRoot.setAlignment(Pos.CENTER);
     	menuRoot.setStyle("-fx-background-color: #696969");
     	// create the scene to display objects
     	Scene startMenu = new Scene(menuRoot, WIDTH, HEIGHT);
     	return startMenu;
    }
    
    /**
     * Method generates the scene for the level to be played
     * 
     * @param primaryStage: the primary game window
     * @param levelNum: the number of the level to be generated
     */
    private void generateLevel(Stage primaryStage, int levelNum) {
    	Scene levelScene = new SceneGenerator(levelNum).getScene();
    	// generate the statistics bar at the top
    	// generate the round text label in the center of the screen
    	// generate the ball and paddle
    }
}