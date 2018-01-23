package game_bmh43;

import java.util.ArrayList;
import java.util.Random;

import game_bmh43.Ball;
import game_bmh43.Block;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
    private boolean LEVEL_KEY = false;
    private Stage GAME_STAGE;
    
    private ObjectManager SPRITES = new ObjectManager();
    private Player PLAYER = new Player();
    ArrayList<Object> REMOVE_LIST = new ArrayList<Object>();
    ArrayList<PowerUp> POWER_LIST = new ArrayList<PowerUp>();
    
    private Label LEVEL_LABEL;
    private Label LIVES_LABEL;
    private Label BALLS_LABEL;
    private Label ABILITY_LABEL;
    private VBox LEVEL_HEADER;
    
    private int LEVEL;
    private int BALL_SPEED; 
	
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
		LEVEL = 1;
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
		//System.out.printf("Game over!");
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
     	VBox ruleBox = generateRuleBox(ruleTitle);
     	// create one top level grid to organize the things in the scene
     	VBox menuRoot = new VBox(20, gameTitle, ruleBox, playButton);
     	menuRoot.setAlignment(Pos.CENTER);
     	menuRoot.setStyle("-fx-background-color: #696969");
     	// create the scene to display objects
     	Scene startMenu = new Scene(menuRoot, WIDTH, HEIGHT);
     	return startMenu;
    }
    
    /**
     * Generates the vertical box containing the rules to the game
     * 
     * @param ruleTitle: the underlined title for the vertical box
     * @return ruleBox: the vertical box containing the rules
     */
    private VBox generateRuleBox(Label ruleTitle) {
		VBox ruleBox = new VBox(10, ruleTitle);
		ruleBox.setAlignment(Pos.CENTER);
     	ruleBox.setMaxWidth(500);
     	ruleBox.setStyle("-fx-text-fill: #FFFFFF; "
     			+ "-fx-border-style: solid inside; "
     			+ "-fx-border-width: 2; "
     			+ "-fx-border-radius: 5; "
     			+ "-fx-border-color: #FFA500; "
     			+ "-fx-background-color: #696969");
     	Label controls = new Label();
     	controls.setFont(new Font("Futura", 14));
     	controls.setText("1. Use the 'LEFT' and 'RIGHT' arrow keys to move the paddle\n" + 
     			"2. Press 'SPACE' to launch any available Balls\n" + 
     			"3. Press 'F' to use an ability coin and catch the next ball to touch the paddle\n" +
     			"4. Press 'G' to use an ability coin and stretch the paddle "
     				+ "for a short period\n" + 
     			"5. Press 'E' to use an ability coin and give the paddle a short speed boost\n");
     	controls.setStyle("-fx-text-fill: #FFFFFF");
     	controls.setAlignment(Pos.CENTER);
     	ruleBox.getChildren().add(controls);
    	return ruleBox;
    	
    }
    
    /**
     * Method generates the scene for the level to be played
     * levelScene represents the Scene populated with blocks
     * 
     * @param levelNum: the number of the level to be generated
     */
    private void generateLevel() {
    	// make sure the stage is clear of all scenes
    	GAME_STAGE.setScene(new Scene(new Pane()));
    	SceneGenerator level = new SceneGenerator(LEVEL);
    	Scene levelScene = level.getScene();
    	Pane levelPane = level.getPane();
    	ArrayList<Block> blockList = level.getBlockList();
    	// clear the ObjectManager
    	SPRITES.resetBalls();
    	SPRITES.resetBlocks();
    	SPRITES.resetSprites();
    	PLAYING = false;
    	LEVEL_GENERATED = false;
    	BALL_SPEED = 100 + LEVEL * 10;
    	// add the new blocks into the ObjectManager
    	for (Block nodeBlock : blockList) {
    		SPRITES.addBlock(nodeBlock);
    	}
    	//System.out.print(SPRITES.numBlocks());
    	// add the paddle
    	Paddle gamePaddle = new Paddle(levelScene, LEVEL);
    	SPRITES.addPaddle(gamePaddle);
    	levelPane.getChildren().add(gamePaddle.getShape());
    	// add the level heading
    	LEVEL_HEADER = generateLevelHeader();
    	LEVEL_HEADER.relocate(levelScene.getWidth()/2 - LEVEL_HEADER.getMinWidth()/2, 
    			levelScene.getHeight()/2 - LEVEL_HEADER.getMinHeight()/2);
    	levelPane.getChildren().add(LEVEL_HEADER);
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
    	// display levels
    	LEVEL_LABEL = generateStatsLabel("Level: ");
    	// display lives
    	LIVES_LABEL = generateStatsLabel("Lives remaining: ");
     	// display balls
    	BALLS_LABEL = generateStatsLabel("Balls available: ");
     	// display ability coins
     	ABILITY_LABEL = generateStatsLabel("Ability points: ");
     	// combine the labels
     	HBox statsBar = new HBox(WIDTH/6, LEVEL_LABEL, LIVES_LABEL, BALLS_LABEL, ABILITY_LABEL);
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
     * 
     * @param text: The text to be put on the label
     * @return a Label containing the text
     */
    private Label generateStatsLabel(String text) {
    	Label someLabel = new Label(text);
    	someLabel.setFont(new Font("Futura", 14));
     	someLabel.setStyle("-fx-text-fill: #FFFFFF");
     	return someLabel; 
    }
    
    /**
     * Generates a Ball from the paddle with random x-velocity between the bounds
     * -10 <= x <= 10
     * 
     */
    private void generateBall() {
    	Random numGenerator = new Random();
    	int xRand = numGenerator.nextInt(21) - 10;
    	Rectangle Paddle = SPRITES.getPaddle().getShape();
    	Pane levelPane = (Pane) GAME_STAGE.getScene().getRoot();
    	Ball newBall = new Ball(Paddle.getX() + Paddle.getWidth() / 2, 
    			levelPane.getHeight() - Paddle.getHeight() * 4,
    			xRand, BALL_SPEED, LEVEL);
    	SPRITES.addBall(newBall);
    	PLAYER.loseBall(); 
    	levelPane.getChildren().add(newBall.getBall());
    }
    
    /**
     * Generates a PowerUp
     */
    private void generatePowerUp(double x, double y) {
    	Pane levelPane = (Pane) GAME_STAGE.getScene().getRoot();
    	Random numGenerator = new Random();
    	int choice = numGenerator.nextInt(11);
    	if (choice == 0) {
    		// shadow ball generate
    	}
    	else if (choice >= 1 && choice < 3) {
    		ExtraLife lifeUp = new ExtraLife(x, y, levelPane);
    		POWER_LIST.add(lifeUp);
    	}
    	else if (choice >= 3 && choice < 7) {
    		ExtraBall ballUp = new ExtraBall(x, y, levelPane);
    		POWER_LIST.add(ballUp);
    	}
    	else {
    		AbilityCoin abilityUp = new AbilityCoin(x, y, levelPane);
    		POWER_LIST.add(abilityUp);
    	}
    }
    
    /**
     * Generates the end of the level screen
     */
    private void endLevel() {
    	// make sure the stage is clear of all scenes
    	GAME_STAGE.setScene(new Scene(new Pane()));
        // create and style the button box
        Button replayButton = new Button("Replay Level");
        replayButton.setStyle("-fx-background-color: #FFA500; "
        		+ "-fx-font-size: 28 futura; "
        		+ "-fx-text-fill: #FFFFFF");
        replayButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				replayButton.setEffect(new Glow());	
				//System.out.println("Hello");
			}
        });
        replayButton.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				replayButton.setEffect(null);
			}
        });
        replayButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				generateLevel();
			}
        });
        Button exitButton = new Button("Quit Game");
        exitButton.setStyle("-fx-background-color: #FFA500; "
        		+ "-fx-font-size: 28 futura; "
        		+ "-fx-text-fill: #FFFFFF");
        exitButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				exitButton.setEffect(new Glow());	
				//System.out.println("Hello");
			}
        });
        exitButton.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				exitButton.setEffect(null);
			}
        });
        exitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				Platform.exit();
				System.out.println("Test");
			}
        });
        // create and style the next level box
        Button nextButton = new Button("Next Level");
        nextButton.setStyle("-fx-background-color: #FFA500; "
        		+ "-fx-font-size: 28 futura; "
          		+ "-fx-text-fill: #FFFFFF");
        nextButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
    		@Override
    		public void handle(MouseEvent arg0) {
    			nextButton.setEffect(new Glow());	
    		}
        });
        nextButton.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				nextButton.setEffect(null);
			}
        });
        nextButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				LEVEL++;
				if (LEVEL > 5) {
					endGame();
				}
				else {
					generateLevel();
				}
			}
        });
        // generate the complete start Menu
     	Scene nextMenu = generateNextMenu(replayButton, exitButton, nextButton);
     	GAME_STAGE.setScene(nextMenu);
    }
    
    /**
     * Generate the menu screen to display the next level options
     * 
     * @param replayButton: button to replay the current level
     * @param exitButton: button to exit the game
     * @param nextButton: button to advance to the next level
     * @return Scene nextMenu: the complete next menu displayed to the user
     */
    private Scene generateNextMenu(Button replayButton, Button exitButton, Button nextButton) {
    	// create and style the title 
        Label endTitle = new Label("Lost Level " + LEVEL);
        endTitle.setFont(new Font("Futura", 45));
        endTitle.setEffect(new Glow());
        endTitle.setStyle("-fx-text-fill: #FFFFFF");
        HBox buttonBox = new HBox(40, replayButton, exitButton);
        buttonBox.setPickOnBounds(false);
        // win
        if (SPRITES.numBlocks() == 0) {
        	buttonBox.getChildren().add(nextButton);
        	endTitle.setText("Completed Level " + LEVEL);
        }
     	buttonBox.setAlignment(Pos.CENTER);
     	buttonBox.setMaxWidth(WIDTH);
     	buttonBox.setStyle("-fx-text-fill: #FFFFFF; "
     			+ "-fx-background-color: #696969");
     	// create one top level grid to organize the things in the scene
     	VBox nextRoot = new VBox(40, endTitle, buttonBox);
     	nextRoot.setAlignment(Pos.CENTER);
     	nextRoot.setStyle("-fx-background-color: #696969");
     	nextRoot.setPickOnBounds(false);
     	// create the scene to display objects
     	Scene nextMenu = new Scene(nextRoot, WIDTH, HEIGHT);
     	return nextMenu;
    }
    
    /**
	 * Change properties of shapes to animate them 
	 * 
	 * @param elapsedTime: time since last animation update
	 */
    private void step (double elapsedTime) {
    	if ((SPRITES.numBlocks() == 0 ||
    			PLAYER.isDead()) && PLAYING) {
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
    		checkSprites(gameBall);
    		gameBall.checkBounds(GAME_STAGE.getScene());
    		gameBall.update(elapsedTime);
    		if (gameBall.isDead()) {
    			REMOVE_LIST.add(gameBall);
    			PLAYER.loseLife();
    			if (!PLAYER.isDead()) {
    				PLAYER.addBall();
    			}
    		}
    		//System.out.println("Handled Ball update");
    	}
    	// handle Power Ups
    	handlePowerUps(elapsedTime);
    	// clean up
    	handleRemoval();
    	// update statistics
    	updateStats();
    }
    
    /**
     * Updates the values of the display strings in the statistics bar.
     * Waits until a ball is in play before displaying statistics bar.
     */
    private void updateStats() {
    	if (PLAYING) {
    		LEVEL_LABEL.setText("Level: " + String.valueOf(LEVEL));
    		LIVES_LABEL.setText("Lives remaining: " + String.valueOf(PLAYER.getLives()));
    		BALLS_LABEL.setText("Balls availible: " + String.valueOf(PLAYER.getBalls()));
    		ABILITY_LABEL.setText("Ability coins: " + String.valueOf(PLAYER.getAbilityCoins()));
    		PLAYER.updateDead();
    		//System.out.println("Handled stats update");
    	}
    }
    
    // Update each time a key is pressed
    private void handleKeyPress(KeyCode code) {
    	if (code == KeyCode.SPACE) {
        	SPACE_KEY = true;
        	if (PLAYING) {
    			// handle additional ball on Press to avoid animation glitches
    			if (PLAYER.getBalls() > 0) {
    				generateBall();
    			}
    		}
        }
    	if (code == KeyCode.RIGHT) {
            RIGHT_KEY = true;
        }
        if (code == KeyCode.LEFT) {
            LEFT_KEY = true;
        }
        if (code == KeyCode.B) {
        	PLAYER.addBall();
        }
        if (code == KeyCode.L) {
        	PLAYER.addLife();
        }
        if (code == KeyCode.DIGIT1) {
        	PLAYER = new Player();
        	LEVEL = 1;
        	generateLevel();
        }
        if (code == KeyCode.DIGIT2) {
        	PLAYER = new Player();
        	LEVEL = 2;
        	generateLevel();
        }
        if (code == KeyCode.DIGIT3) {
        	PLAYER = new Player();
        	LEVEL = 3;
        	generateLevel();
        }
        if (code == KeyCode.DIGIT4) {
        	PLAYER = new Player();
        	LEVEL = 4;
        	generateLevel();
        }
        if (code == KeyCode.DIGIT5) {
        	PLAYER = new Player();
        	LEVEL = 5;
        	generateLevel();
        }
        // catch and throw
        if (code == KeyCode.F && 
        		!SPRITES.getPaddle().getActivated() &&
        		PLAYER.getAbilityCoins() > 0) {
        	SPRITES.getPaddle().setCatch();
        	PLAYER.loseAbility();
        }
        // stretch
        if (code == KeyCode.G && 
        		!SPRITES.getPaddle().getActivated() &&
        		PLAYER.getAbilityCoins() > 0) {
        	SPRITES.getPaddle().setStretch();
        	PLAYER.loseAbility();
        }
        // boost
        if (code == KeyCode.E && 
        		!SPRITES.getPaddle().getActivated() &&
        		PLAYER.getAbilityCoins() > 0) {
        	SPRITES.getPaddle().setBoost();
        	PLAYER.loseAbility();
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
    		if (!PLAYING) {
        		PLAYING = true;
        		// remove level heading
        		Pane levelPane = (Pane) GAME_STAGE.getScene().getRoot();
        		levelPane.getChildren().remove(LEVEL_HEADER);
        		// generate ball
        		generateBall();
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
     * Handles applying PowerUps
     */
    private void handlePowerUps(double elapsedTime) {
    	for (PowerUp power : POWER_LIST) {
    		power.update(elapsedTime);
    		if (power.isColliding(SPRITES.getPaddle())) {
    			if (power instanceof ExtraLife) {
    				power.fade();
    				Pane levelPane = (Pane) GAME_STAGE.getScene().getRoot();
            		levelPane.getChildren().remove(power.getShape());
            		PLAYER.addLife();
    			}
    			else if (power instanceof ExtraBall) {
    				power.fade();
    				Pane levelPane = (Pane) GAME_STAGE.getScene().getRoot();
            		levelPane.getChildren().remove(power.getShape());
            		PLAYER.addBall();
    			}
    			else if (power instanceof AbilityCoin) {
    				power.fade();
    				Pane levelPane = (Pane) GAME_STAGE.getScene().getRoot();
            		levelPane.getChildren().remove(power.getShape());
            		PLAYER.addAbility();
    			}
    		REMOVE_LIST.add(power);
    		}
    	}
    }
    
    /**
     * Removes the out of play balls off the screen and the broken blocks
     */
    private void handleRemoval() {
    	Pane levelPane = (Pane) GAME_STAGE.getScene().getRoot();
    	for (Object removeObject : REMOVE_LIST) {
			if (removeObject instanceof Block) {
				Block otherBlock = (Block) removeObject;
				SPRITES.removeBlock(otherBlock);
				FadeTransition blockFade = new FadeTransition(Duration.seconds(1),
		    			otherBlock.getBlock());
				blockFade.setFromValue(1.0);
				blockFade.setToValue(0.0);
				blockFade.play();
				levelPane.getChildren().remove(otherBlock.getBlock());
			}
			else if(removeObject instanceof Ball) {
				Ball otherBall = (Ball) removeObject;
				SPRITES.removeBall(otherBall);
				levelPane.getChildren().remove(otherBall.getBall());
			}
			else if(removeObject instanceof PowerUp) {
				PowerUp otherPower = (PowerUp) removeObject;
				POWER_LIST.remove(otherPower);
			}
		}
    	// reset the removal list for the next step
    	REMOVE_LIST.clear();
    }
    
    /**
     * Checks if the given argument collides with another game Object
     * NOTE: code seems repetitive. May consider revising. 
     * 
     * @param gameBall: Ball object to check against other objects
     */
    private void checkSprites(Ball gameBall) {
    	// reset the ball miss counter
    	Shape paddleHit = Shape.intersect(gameBall.getBall(), SPRITES.getPaddle().getShape());
    	if (paddleHit.getBoundsInLocal().getWidth() != -1 && SPRITES.getPaddle().canCatch()) {
    		REMOVE_LIST.add(gameBall);
    		Pane levelPane = (Pane) GAME_STAGE.getScene().getRoot();
    		levelPane.getChildren().remove(gameBall);
    		PLAYER.addBall();
    		SPRITES.getPaddle().hasBall();
    	}
    	else if(paddleHit.getBoundsInLocal().getWidth() != -1) {
            gameBall.handleCollision(SPRITES.getPaddle(), SECOND_DELAY);
        }
    	// check if the ball collides with any game objects
		for (Object otherObject : SPRITES.getObjects()) {
			checkCollision(gameBall, otherObject);
		}
    }
    
    /**
     * Checks whether the gameBall collides with a specific otherObject
     * 
     * @param gameBall: Ball object to check
     * @param otherObject: Object to check
     */
    private void checkCollision(Ball gameBall, Object otherObject) {	
    	// can't collide with itself
    	if (!otherObject.equals(gameBall) &&
				otherObject instanceof Ball) {
			// check for collisions
			// with shapes, can check precisely
			Ball otherBall = (Ball) otherObject;
			Shape intersect = Shape.intersect(gameBall.getBall(), otherBall.getBall());
			if (intersect.getBoundsInLocal().getWidth() != -1) {
	            gameBall.handleCollision(otherBall, SECOND_DELAY);
	        }
		}
		else if (otherObject instanceof Block) {
			// check for collisions
			// with shapes, can check precisely
			// if intersect shape position is less than block position
			Block otherBlock = (Block) otherObject;
			Shape intersect = Shape.intersect(gameBall.getBall(), otherBlock.getBlock());
			if (intersect.getBoundsInLocal().getWidth() != -1) {
	            gameBall.handleCollision(otherBlock, SECOND_DELAY);
	            if (otherBlock.isDead()) {
	            	// prepare to destroy block
    				REMOVE_LIST.add(otherBlock);
    				double xPos = otherBlock.getBlock().getBoundsInParent().getMinX() + 
    						otherBlock.getBlock().getBoundsInParent().getWidth() / 2;
    				double yPos = otherBlock.getBlock().getBoundsInParent().getMinY() + 
    						otherBlock.getBlock().getBoundsInParent().getHeight() / 2;
    				generatePowerUp(xPos, yPos);
    				
	            }
			}
		}
    }
}