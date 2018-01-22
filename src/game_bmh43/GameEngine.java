package game_bmh43;

import java.util.ArrayList;

import game_bmh43.Ball;
import game_bmh43.Block;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 
 * @author Benjamin Hodgson
 * @date 1/15/2018
 * 
 * This class contains the game mechanics
 *
 */
public class GameEngine {
	
	private final int WIDTH = 800;
	private final int HEIGHT = 600;	
	private final int FRAMES_PER_SECOND = 60;
	private final String TITLE;	
	private final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    
    private boolean LEVEL_GENERATED = false;
    private boolean PLAYING = false;
    private boolean SPACE_KEY = false;
    private boolean LEFT_KEY = false;
    private boolean RIGHT_KEY = false;
    private Stage GAME_STAGE;
    
    private ObjectManager SPRITES = new ObjectManager();
    private Player PLAYER = new Player();
    
    private String LIVES_STR = "Lives remaining: ";
    private String ABILITY_STR = "Ability coins: ";
    private String LEVEL_STR;
    
    private int LEVEL = 1;
	
	/**
	 * 
	 * @param fps: number of animation frames per second
	 * @param title: the title of the game
	 */
	public GameEngine(String title) {
		TITLE = title;
	}
	
	/**
	 * Begin the game loop
	 * 
	 * @param primaryStage: the primary game window
	 */
	public void startGame(Stage primaryStage) {
		// set the title of the window
		GAME_STAGE = primaryStage;
		GAME_STAGE.setTitle(TITLE);
		// attach "game loop" to time line to play it
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                                      e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();		
	}
	
	/**
	 * Create the initial start up menu and handle playButton click
	 * 
	 * @param primaryStage: the primary game window
	 */
	public void playGame() {
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
     			generateLevel();
     		}
     	});
     	// generate the complete start Menu
     	Scene startMenu = generateMenu(playButton);
     	GAME_STAGE.setScene(startMenu);
	}
	
	/**
	 * Ends the game
	 */
	public void endGame() {
		System.out.printf("Game over!");
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
     * levelScene represents the Scene populated with blocks
     * 
     * @param levelNum: the number of the level to be generated
     */
    private void generateLevel() {
    	SceneGenerator level = new SceneGenerator(LEVEL);
    	Scene levelScene = level.getScene();
    	Pane levelPane = level.getPane();
    	ArrayList<Block> blockList = level.getBlockList();
    	// clear the ObjectManager
    	SPRITES.resetBalls();
    	SPRITES.resetBlocks();
    	// add the new blocks into the ObjectManager
    	for (Block nodeBlock : blockList) {
    		SPRITES.addBlock(nodeBlock);
    	}
    	//System.out.print(SPRITES.numBlocks());
    	// add the paddle
    	Paddle gamePaddle = new Paddle(levelScene, LEVEL);
    	SPRITES.addPaddle(gamePaddle);
    	levelPane.getChildren().add(gamePaddle.getNode());
    	// add the level heading
    	VBox levelHeader = generateLevelHeader();
    	levelHeader.relocate(levelScene.getWidth()/2 - levelHeader.getMinWidth()/2, 
    			levelScene.getHeight()/2 - levelHeader.getMinHeight()/2);
    	levelPane.getChildren().add(levelHeader);
    	// set key handler
    	levelScene.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
    	levelScene.setOnKeyReleased(e -> handleKeyRelease(e.getCode()));
    	// generate the statistics bar at the top
    	HBox statsBar = generateStats();
    	statsBar.setAlignment(Pos.TOP_CENTER);
    	levelPane.getChildren().add(statsBar);
    	LEVEL_GENERATED = true;
    	GAME_STAGE.setScene(levelScene);
    }
    
    /**
     * Generates the round header
     * 
     * @param levelNum: the number of the level to be played
     * @return levelHeader: the constructed level header for the scene
     */
    private VBox generateLevelHeader() {
    	// notify them of the level number
    	String levelStr = "Level " + String.valueOf(LEVEL);
    	Label gameTitle = new Label(levelStr);
     	gameTitle.setFont(new Font("Futura", 35));
     	gameTitle.setStyle("-fx-text-fill: #FFFFFF");
     	HBox centerTitle = new HBox(gameTitle);
     	centerTitle.setAlignment(Pos.CENTER);
     	// give instructions as to how to start the level
     	String startText = "Press the spacebar to begin";
    	Label gameStart = new Label(startText);
     	gameStart.setFont(new Font("Futura", 15));
     	gameStart.setStyle("-fx-text-fill: #FFFFFF");
     	HBox centerStart = new HBox(gameStart);
     	centerStart.setAlignment(Pos.CENTER);
     	// combine the two labels
    	VBox levelHeader = new VBox(centerTitle, centerStart);
    	levelHeader.setMaxSize(200, 80);
    	levelHeader.setMinSize(200, 80);
     	levelHeader.setStyle("-fx-text-fill: #FFFFFF; "
     			+ "-fx-border-style: solid inside; "
     			+ "-fx-border-width: 2; "
     			+ "-fx-border-radius: 5; "
     			+ "-fx-border-color: #FFA500; "
     			+ "-fx-background-color: #696969");
     	return levelHeader;
    }
    
    /**
     * 
     * @return statsBar: the statistics bar at the top of the scene
     */
    private HBox generateStats() {
    	// display lives
    	Label livesLabel = new Label(LIVES_STR);
     	livesLabel.setFont(new Font("Futura", 14));
     	livesLabel.setStyle("-fx-text-fill: #FFFFFF");
     	// display levels
    	Label levelsLabel = new Label(LEVEL_STR);
     	levelsLabel.setFont(new Font("Futura", 14));
     	levelsLabel.setStyle("-fx-text-fill: #FFFFFF");
     	// display ability coins
     	Label abilityLabel = new Label(ABILITY_STR);
     	abilityLabel.setFont(new Font("Futura", 14));
     	abilityLabel.setStyle("-fx-text-fill: #FFFFFF");
     	// combine the labels
     	HBox statsBar = new HBox(WIDTH/3, livesLabel, levelsLabel, abilityLabel);
    	statsBar.setMaxSize(WIDTH, 20);
    	statsBar.setMinSize(WIDTH, 20);
    	statsBar.setStyle("-fx-text-fill: #FFFFFF; "
     			+ "-fx-border-style: solid outside; "
     			+ "-fx-border-width: 1; "
     			+ "-fx-border-color: #FFA500; "
     			+ "-fx-background-color: #696969");
    	return statsBar;
    }
    
    /**
     * Updates the values of the display strings in the statistics bar.
     * Waits until a ball is in play before displaying statistics bar.
     */
    private void updateStats() {
    	if (SPRITES.numBalls() > 0) {
    		LIVES_STR = "Lives remaining: " + String.valueOf(PLAYER.getLives());
    		LEVEL_STR = "Level: " + String.valueOf(LEVEL);
    		ABILITY_STR = "Ability coins: " + String.valueOf(PLAYER.getAbilityCoins());
    	}
    }
    
    /**
     * Generates the end of the level screen
     */
    private void endLevel() {
    	
    }
    
    /**
	 * Change properties of shapes to animate them 
	 * 
	 * @param elapsedTime: time since last animation update
	 */
    private void step (double elapsedTime) {
    	if (SPRITES.numBlocks() == 0 ||
    			PLAYER.isDead()) {
    		endLevel();
    	}
    	// handle input
    	if (LEVEL_GENERATED) {
    		processKey();
    	}
    	// update objects
    	for (Ball gameBall : SPRITES.getBalls()) {
    		// handle frenzy
    		if (SPRITES.numBalls() > 3 &&
    				!gameBall.inFrenzy()) {
    			gameBall.startFrenzy();
    		}
    		if (SPRITES.numBalls() <= 3 &&
    				gameBall.inFrenzy()) {
    			gameBall.stopFrenzy();
    		}
    		checkCollisions(gameBall);
    		gameBall.update();
    	}
    	// update statistics
    	updateStats();
    }
    
    // Update each time a key is pressed
    private void handleKeyPress(KeyCode code) {
    	if (code == KeyCode.SPACE) {
        	SPACE_KEY = true;
        }
    	if (code == KeyCode.RIGHT) {
            RIGHT_KEY = true;
        }
        if (code == KeyCode.LEFT) {
            LEFT_KEY = true;
        }
        processKey();
    }
    
    /**
     * Processes how to generate the first ball.
     * Method might seem unnecessary, but this makes for smoother 
     * animations when multiple keys are pressed
     */
    private void processKey() {
    	// get the paddle information
    	Rectangle Paddle = SPRITES.getPaddle().getShape();
    	int paddleSpeed = SPRITES.getPaddle().getSpeed();
    	if (RIGHT_KEY) {
    		if (Paddle.getX() + paddleSpeed + Paddle.getWidth() <= GAME_STAGE.getScene().getWidth()) {
            	Paddle.setX(Paddle.getX() + paddleSpeed);
            }
    	}
    	if (LEFT_KEY) {
    		if (Paddle.getX() - paddleSpeed >= 0) {
            	Paddle.setX(Paddle.getX() - paddleSpeed);
            }
    	}
    	if (SPACE_KEY) {
    		if (PLAYING) {
    			// check for paddle catch ability activation
    		}
    		else {
    			// generate ball
        		PLAYING = true;
    		}
    	}
    }
    
    // What to do each time a key is released
    private void handleKeyRelease(KeyCode code) {
    	if (code == KeyCode.RIGHT && RIGHT_KEY) {
            RIGHT_KEY = false;
        }
        if (code == KeyCode.LEFT && LEFT_KEY) {
            LEFT_KEY = false;
        }
        if (code == KeyCode.SPACE && SPACE_KEY) {
        	SPACE_KEY = false;
        }
    }
    
    /**
     * Checks if the given argument collides with another Ball or Block
     * NOTE: code seems repetitive. May consider revising. 
     * 
     * @param gameBall: Ball object to check for collisions
     */
    private void checkCollisions(Ball gameBall) {
    	Shape paddleHit = Shape.intersect(gameBall.getBall(), SPRITES.getPaddle().getShape());
    	if (paddleHit.getBoundsInLocal().getWidth() != -1) {
            gameBall.handleCollision(SPRITES.getPaddle());
        }
    	for (Object otherObject : SPRITES.getObjects()) {
    		// can't collide with itself
    		if (!otherObject.equals(gameBall) &&
    				otherObject instanceof Ball) {
    			// check for collisions
    			// with shapes, can check precisely
    			Ball otherBall = (Ball) otherObject;
    			Shape intersect = Shape.intersect(gameBall.getBall(), otherBall.getBall());
    			if (intersect.getBoundsInLocal().getWidth() != -1) {
    	            gameBall.handleCollision(otherBall);
    	        }
    		}
    		else if (otherObject instanceof Block) {
    			// check for collisions
    			// with shapes, can check precisely
    			Block otherBlock = (Block) otherObject;
    			Shape intersect = Shape.intersect(gameBall.getBall(), otherBlock.getBlock());
    			if (intersect.getBoundsInLocal().getWidth() != -1) {
    	            gameBall.handleCollision(otherBlock);
    	            otherBlock.handleImplode();
    	            SPRITES.removeBlock(otherBlock);
    	        }
    		}
    		// else its a power up that needs to be applied
    		else {
    			
    		}
    	}
    }
}