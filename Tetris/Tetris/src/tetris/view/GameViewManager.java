/*
 *
 * ViewManager class, handles everything seen on the screen in the menu.
 *
*/

package tetris.view;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tetris.helper.Controller;
import tetris.helper.CustomAnimationTimer;
import tetris.helper.MediumLabelInformation;
import tetris.helper.Tetrino;

public class GameViewManager extends Application 
{
    // The player move.
    public static final int PLAYER_MOVE = 25;
    // The player move size.
    public static final int PLAYER_MOVE_SIZE = 25;
    // Sets the max width equal to the player move size multiply 12.
    public static final int MAX_WIDTH = PLAYER_MOVE_SIZE * 12;
    // Sets the max height equal to the player move size multiply 24.
    public static final int MAX_HEIGHT = PLAYER_MOVE_SIZE * 24;
    public static int[][] GAME_GRID_VIEW = new int[MAX_WIDTH / PLAYER_MOVE_SIZE][MAX_HEIGHT / PLAYER_MOVE_SIZE];
    // Mane pane for the game view.
    private static final Pane group = new Pane();
    // Tetrino ojbect.
    private static Tetrino object;
    // Scene with maine Pane added and height and width set.
    private static final Scene scene = new Scene(group, MAX_WIDTH + 150, MAX_HEIGHT);
    // PLayers score.
    public static int score = 0;
    // Int defining the top of the game board.
    private static int top = 0;
    // Boolean used to check if the game is running or not.
    private static boolean game = true;
    // Creates a variable which is set equal to the makeTetrino method from controller.
    private static Tetrino nextTetrino = Controller.makeTetrino();
    // Lines, used to trck the player lines created.
    private static int linesNo = 0;
    
    // Creates a satge to be passed to the meny class.
    static Stage classStage = new Stage();

    // AnimationTimer to track the animation progress.
    private AnimationTimer gameAnimationTimer;
    
    String nextPeiceToDisplay;
    
    // Used to speed up/slow down the game.
    long currentSpeedOfGame;
    
    ImageView nextPlayPeice, nextPlayPeiceHolder;
    
    @Override
    public void start(Stage stage) throws Exception 
    {
        // for i which if used to fill the array of the game grid with empty values on game start.
        for (int[] a : GAME_GRID_VIEW) 
        {
            // Filling the array.
            Arrays.fill(a, 0);
        }
        
        // Instantiates a classStage equal to Stage, used to show and hide.
        classStage = stage;

        // Instantiates a vertical line to seperate the game view.
        Line viewSeperatorLine = new Line(MAX_WIDTH, 0, MAX_WIDTH, MAX_HEIGHT);
        // Adds a label for information purpouses to the game I.e. Score.
        MediumLabelInformation currentPlayerScore = new MediumLabelInformation("Score: ", 50, MAX_WIDTH + 410);
        
        // Adds a label for information purpouses to the game I.e. Lines.
        MediumLabelInformation currentPlayerLinesCount = new MediumLabelInformation("Lines: ", 100, MAX_WIDTH + 405);
        
        // Adds a label for information purpouses to the game I.e. Up Next.
        MediumLabelInformation upNext = new MediumLabelInformation("Up Next", 200, MAX_WIDTH + 445);
        
        // Adds viewSeperatorLine, currentPlayerScore, currentPlayerLinesCount, upNext to the Pane.
        group.getChildren().addAll(viewSeperatorLine, currentPlayerScore, currentPlayerLinesCount, upNext);

        // a is equal to nextTetrino.
        Tetrino a = nextTetrino;
        // Adds all tetrino peices to the the Pane.
        group.getChildren().addAll(a.tetrinoPeiceOne, a.tetrinoPeiceTwo, a.tetrinoPeiceThree, a.tetrinoPeiceFour);
        moveOnKeyPress(a);
        // Instainaties an object.
        object = a;
        // nextTetrino is equal to the make tetrino method from the controller.
        nextTetrino = Controller.makeTetrino();
        // Adds the scene to the stage.
        classStage.setScene(scene);
        // Sets the title for the stage.
        classStage.setTitle("Tetris");
        // Shows the stage.
        classStage.show();
        
        // Instantiates a new ImageView to hold the next 
        // player peice to be generated and displayed to the player.
        nextPlayPeice = new ImageView();
        
        // The current speed of the game.
        currentSpeedOfGame = 500;
        
        // Instantiates and adds the custom CustomAnimationTimer and sets its delay timer.
        CustomAnimationTimer timer = new CustomAnimationTimer(currentSpeedOfGame) 
        {
            @Override
            public void handle() 
            {
                // If statement checking if the Y values/positions of the tetrino peices equal zero.
                if 
                (
                    object.tetrinoPeiceOne.getY() == 0 || 
                    object.tetrinoPeiceTwo.getY() == 0 || 
                    object.tetrinoPeiceThree.getY() == 0 || 
                    object.tetrinoPeiceFour.getY() == 0
                )
                {
                    // Increments top by one.
                    top++;
                } else {
                    // Else set top to zero
                    top = 0;
                }

                // is top equals 2 then game over.
                if (top == 2) 
                {
                    // Instantiate text gameOver and set its text to GAME OVER.
                    Text gameOver = new Text("GAME OVER");
                    // Sets the fill color of gameOver.
                    gameOver.setFill(Color.RED);
                    // Sets the styke of gameOver.
                    gameOver.setStyle("-fx-font: 70 arial;");
                    // Sets the Y value/position of gameOver.
                    gameOver.setY(250);
                    // Sets the X value/position of gameOver.
                    gameOver.setX(10);
                    // Adds gameOver to the Pane.
                    group.getChildren().add(gameOver);
                    // Sets game to false, I.e. game over.
                    game = false;
                }
                
                // If top equals 15 then exit system.
                if (top == 15) 
                {
                    // Exits the system.
                    System.exit(0);
                }

                // If game I.e. is the game is running and game over has not occured.
                if (game) 
                {
                    // Instantiate the move down method.
                    MoveDown(object);
                    // Sets the text of scoreText to Score: plus the value of the current score.
                    currentPlayerScore.setText("Score: " + Integer.toString(score));
                    // Sets the text of currentPlayerLevel to Score: plus the value of the current lines count.
                    currentPlayerLinesCount.setText("Lines: " + Integer.toString(linesNo));
                }
                
                // Decreases the current game speed by one every second.
                currentSpeedOfGame--;
                
                // Switch, used for checking tetrino names and assigning (Up next) images
                switch(nextTetrino.getName())
                {
                    // Case for tetrinoT.
                    case "t":
                        // Sets the ImageView to null, I.e. stops over lapping images.
                        nextPlayPeice.setImage(null);
                        // Sets the string nextPeiceToDislpay equal to the file path of the corresponding image.
                        nextPeiceToDisplay = "tetris/view/assets/tetrino_complete_assets/tetrinoT.png";
                        // Instantiates nextPlayerPeice and passes in the file path from above.
                        nextPlayPeice = new ImageView(nextPeiceToDisplay);
                    
                        break;
                    
                    // Case for tetrinoS.
                    case "s":
                        // Sets the ImageView to null, I.e. stops over lapping images.
                        nextPlayPeice.setImage(null);
                        // Sets the string nextPeiceToDislpay equal to the file path of the corresponding image.
                        nextPeiceToDisplay = "tetris/view/assets/tetrino_complete_assets/tetrinoS.png";
                        // Instantiates nextPlayerPeice and passes in the file path from above.
                        nextPlayPeice = new ImageView(nextPeiceToDisplay);
                    
                        break;
                        
                    // Case for tetrinoZ.
                    case "z":
                        // Sets the ImageView to null, I.e. stops over lapping images.
                        nextPlayPeice.setImage(null);
                        // Sets the string nextPeiceToDislpay equal to the file path of the corresponding image.
                        nextPeiceToDisplay = "tetris/view/assets/tetrino_complete_assets/tetrinoZ.png";
                        // Instantiates nextPlayerPeice and passes in the file path from above.
                        nextPlayPeice = new ImageView(nextPeiceToDisplay);
                    
                        break;
                        
                    // Case for tetrinoL.
                    case "l":
                        // Sets the ImageView to null, I.e. stops over lapping images.
                        nextPlayPeice.setImage(null);
                        // Sets the string nextPeiceToDislpay equal to the file path of the corresponding image.
                        nextPeiceToDisplay = "tetris/view/assets/tetrino_complete_assets/tetrinoL.png";
                        // Instantiates nextPlayerPeice and passes in the file path from above.
                        nextPlayPeice = new ImageView(nextPeiceToDisplay);
                    
                        break;
                        
                    // Case for tetrinoJ.    
                    case "j":
                        // Sets the ImageView to null, I.e. stops over lapping images.
                        nextPlayPeice.setImage(null);
                        // Sets the string nextPeiceToDislpay equal to the file path of the corresponding image.
                        nextPeiceToDisplay = "tetris/view/assets/tetrino_complete_assets/tetrinoJ.png";
                        // Instantiates nextPlayerPeice and passes in the file path from above.
                        nextPlayPeice = new ImageView(nextPeiceToDisplay);
                    
                        break;
                        
                    // Case for tetrinoI.
                    case "i":
                        // Sets the ImageView to null, I.e. stops over lapping images.
                        nextPlayPeice.setImage(null);
                        // Sets the string nextPeiceToDislpay equal to the file path of the corresponding image.
                        nextPeiceToDisplay = "tetris/view/assets/tetrino_complete_assets/tetrinoI.png";
                        // Instantiates nextPlayerPeice and passes in the file path from above.
                        nextPlayPeice = new ImageView(nextPeiceToDisplay);
                    
                        break;
                        
                    // Case for tetrinoO.
                    case "o":
                        // Sets the ImageView to null, I.e. stops over lapping images.
                        nextPlayPeice.setImage(null);
                        // Sets the string nextPeiceToDislpay equal to the file path of the corresponding image.
                        nextPeiceToDisplay = "tetris/view/assets/tetrino_complete_assets/tetrinoO.png";
                        // Instantiates nextPlayerPeice and passes in the file path from above.
                        nextPlayPeice = new ImageView(nextPeiceToDisplay);
                    
                        break;
                }
                
                nextPlayPeiceHolder = new ImageView("tetris/helper/assets/grey_panel.png");
                // Sets the Y value/position of nextPlayerPeice.
                nextPlayPeiceHolder.setY(260);
                // Sets the X value/position of nextPlayerPeice using the max_width + 10.
                nextPlayPeiceHolder.setX(MAX_WIDTH + 25);
                
                nextPlayPeiceHolder.setScaleY(nextPlayPeice.getScaleY());
                
                // Adds the nextPlayerPeice to the Pane.
                group.getChildren().add(nextPlayPeiceHolder);
                
                
                // Sets the Y value/position of nextPlayerPeice.
                nextPlayPeice.setY(270);
                // Sets the X value/position of nextPlayerPeice using the max_width + 10.
                nextPlayPeice.setX(MAX_WIDTH + 50);
                // Adds the nextPlayerPeice to the Pane.
                group.getChildren().add(nextPlayPeice);
            }
        };

        // Starts the timer.
        timer.start();
       
    }

    // Responsible for detecting key presses.
    private void moveOnKeyPress(Tetrino tetrino) 
    {
        // Set on key press listener/
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() 
        {
            @Override
            public void handle(KeyEvent event) 
            {
                // Switch statement to detec key code.
                switch (event.getCode()) 
                {
                    // Up
                    case RIGHT:
                        // Using the method from the controller, move right.
                        Controller.MoveRight(tetrino);
                        break;
                    case DOWN:
                        // Using the method move down.
                        MoveDown(tetrino);
                        // If moving down, the game is quicker so thew player gains more points, points plus 1 for every down click.
                        score++;
                        break;
                    case LEFT:
                        // Using the method from the controller, move right.
                        Controller.MoveLeft(tetrino);
                        break;
                    case UP:
                        // Using the method move turn to rotate the tetrino.
                        MoveTurn(tetrino);
                        break;
                }
            }
        });
    }

    private void MoveTurn(Tetrino tetrino) 
    {
        // Int used to check for the tetrinso peice's rotations.
        int rotations = tetrino.tetrino;

        // tetrinoPeiceOne is eqaul to tetrinoPeiceOne from the tetrino generator class.
        Rectangle tetrinoPeiceOne = tetrino.tetrinoPeiceOne;
        // tetrinoPeiceTwo is eqaul to tetrinoPeiceTwo from the tetrino generator class.
        Rectangle tetrinoPeiceTwo = tetrino.tetrinoPeiceTwo;
        // tetrinoPeiceThree is eqaul to tetrinoPeiceThree from the tetrino generator class.
        Rectangle tetrinoPeiceThree = tetrino.tetrinoPeiceThree;
        // tetrinoPeiceFour is eqaul to tetrinoPeiceFour from the tetrino generator class.
        Rectangle tetrinoPeiceFour = tetrino.tetrinoPeiceFour;

        // Switch, which checks the returned tetrino's name, I.e. j, l, o, s, t, z, i.
        switch (tetrino.getName()) 
        {
            // If the name is equal to j.
            case "j":
                // If what the rotations are equal to, depending on the number of rotations detected, the method will move each
                // peice of the tetrino the required amount of times.
                if (rotations == 1 && CheckTetrino(tetrinoPeiceOne, 1, -1) && CheckTetrino(tetrinoPeiceThree, -1, -1) && CheckTetrino(tetrinoPeiceThree, -2, -2)) 
                {
                    MoveRight(tetrino.tetrinoPeiceOne);
                    MoveDown(tetrino.tetrinoPeiceOne);
                    MoveDown(tetrino.tetrinoPeiceThree);
                    MoveLeft(tetrino.tetrinoPeiceThree);
                    MoveDown(tetrino.tetrinoPeiceFour);
                    MoveDown(tetrino.tetrinoPeiceFour);
                    MoveLeft(tetrino.tetrinoPeiceFour);
                    MoveLeft(tetrino.tetrinoPeiceFour);

                    // Uses the number of rotations to change the form of the tetrino as a whole.
                    tetrino.changeForm();
                    break;
                }

                // If what the rotations are equal to, depending on the number of rotations detected, the method will move each
                // peice of the tetrino the required amount of times.
                if (rotations == 2 && CheckTetrino(tetrinoPeiceOne, -1, -1) && CheckTetrino(tetrinoPeiceThree, -1, 1) && CheckTetrino(tetrinoPeiceThree, -2, 2)) 
                {
                    MoveDown(tetrino.tetrinoPeiceOne);
                    MoveLeft(tetrino.tetrinoPeiceOne);
                    MoveLeft(tetrino.tetrinoPeiceThree);
                    MoveUp(tetrino.tetrinoPeiceThree);
                    MoveLeft(tetrino.tetrinoPeiceFour);
                    MoveLeft(tetrino.tetrinoPeiceFour);
                    MoveUp(tetrino.tetrinoPeiceFour);
                    MoveUp(tetrino.tetrinoPeiceFour);

                    // Uses the number of rotations to change the form of the tetrino as a whole.
                    tetrino.changeForm();
                    break;
                }

                // If what the rotations are equal to, depending on the number of rotations detected, the method will move each
                // peice of the tetrino the required amount of times.
                if (rotations == 3 && CheckTetrino(tetrinoPeiceOne, -1, 1) && CheckTetrino(tetrinoPeiceThree, 1, 1) && CheckTetrino(tetrinoPeiceThree, 2, 2)) 
                {
                    MoveLeft(tetrino.tetrinoPeiceOne);
                    MoveUp(tetrino.tetrinoPeiceOne);
                    MoveUp(tetrino.tetrinoPeiceThree);
                    MoveRight(tetrino.tetrinoPeiceThree);
                    MoveUp(tetrino.tetrinoPeiceFour);
                    MoveUp(tetrino.tetrinoPeiceFour);
                    MoveRight(tetrino.tetrinoPeiceFour);
                    MoveRight(tetrino.tetrinoPeiceFour);

                    // Uses the number of rotations to change the form of the tetrino as a whole.
                    tetrino.changeForm();
                    break;
                }

                // If what the rotations are equal to, depending on the number of rotations detected, the method will move each
                // peice of the tetrino the required amount of times.
                if (rotations == 4 && CheckTetrino(tetrinoPeiceOne, 1, 1) && CheckTetrino(tetrinoPeiceThree, 1, -1) && CheckTetrino(tetrinoPeiceThree, 2, -2)) 
                {
                    MoveUp(tetrino.tetrinoPeiceOne);
                    MoveRight(tetrino.tetrinoPeiceOne);
                    MoveRight(tetrino.tetrinoPeiceThree);
                    MoveDown(tetrino.tetrinoPeiceThree);
                    MoveRight(tetrino.tetrinoPeiceFour);
                    MoveRight(tetrino.tetrinoPeiceFour);
                    MoveDown(tetrino.tetrinoPeiceFour);
                    MoveDown(tetrino.tetrinoPeiceFour);

                    // Uses the number of rotations to change the form of the tetrino as a whole.
                    tetrino.changeForm();
                    break;
                }
                break;

            // If the name is equal to l.
            case "l":
                // If what the rotations are equal to, depending on the number of rotations detected, the method will move each
                // peice of the tetrino the required amount of times.
                if (rotations == 1 && CheckTetrino(tetrinoPeiceOne, 1, -1) && CheckTetrino(tetrinoPeiceThree, 1, 1) && CheckTetrino(tetrinoPeiceTwo, 2, 2)) 
                {
                    MoveRight(tetrino.tetrinoPeiceOne);
                    MoveDown(tetrino.tetrinoPeiceOne);
                    MoveUp(tetrino.tetrinoPeiceThree);
                    MoveRight(tetrino.tetrinoPeiceThree);
                    MoveUp(tetrino.tetrinoPeiceTwo);
                    MoveUp(tetrino.tetrinoPeiceTwo);
                    MoveRight(tetrino.tetrinoPeiceTwo);
                    MoveRight(tetrino.tetrinoPeiceTwo);

                    // Uses the number of rotations to change the form of the tetrino as a whole.
                    tetrino.changeForm();
                    break;
                }

                // If what the rotations are equal to, depending on the number of rotations detected, the method will move each
                // peice of the tetrino the required amount of times.
                if (rotations == 2 && CheckTetrino(tetrinoPeiceOne, -1, -1) && CheckTetrino(tetrinoPeiceTwo, 2, -2) && CheckTetrino(tetrinoPeiceThree, 1, -1)) 
                {
                    MoveDown(tetrino.tetrinoPeiceOne);
                    MoveLeft(tetrino.tetrinoPeiceOne);
                    MoveRight(tetrino.tetrinoPeiceTwo);
                    MoveRight(tetrino.tetrinoPeiceTwo);
                    MoveDown(tetrino.tetrinoPeiceTwo);
                    MoveDown(tetrino.tetrinoPeiceTwo);
                    MoveRight(tetrino.tetrinoPeiceThree);
                    MoveDown(tetrino.tetrinoPeiceThree);

                    // Uses the number of rotations to change the form of the tetrino as a whole.
                    tetrino.changeForm();
                    break;
                }

                // If what the rotations are equal to, depending on the number of rotations detected, the method will move each
                // peice of the tetrino the required amount of times.
                if (rotations == 3 && CheckTetrino(tetrinoPeiceOne, -1, 1) && CheckTetrino(tetrinoPeiceThree, -1, -1) && CheckTetrino(tetrinoPeiceTwo, -2, -2)) 
                {
                    MoveLeft(tetrino.tetrinoPeiceOne);
                    MoveUp(tetrino.tetrinoPeiceOne);
                    MoveDown(tetrino.tetrinoPeiceThree);
                    MoveLeft(tetrino.tetrinoPeiceThree);
                    MoveDown(tetrino.tetrinoPeiceTwo);
                    MoveDown(tetrino.tetrinoPeiceTwo);
                    MoveLeft(tetrino.tetrinoPeiceTwo);
                    MoveLeft(tetrino.tetrinoPeiceTwo);

                    // Uses the number of rotations to change the form of the tetrino as a whole.
                    tetrino.changeForm();
                    break;
                }

                // If what the rotations are equal to, depending on the number of rotations detected, the method will move each
                // peice of the tetrino the required amount of times.
                if (rotations == 4 && CheckTetrino(tetrinoPeiceOne, 1, 1) && CheckTetrino(tetrinoPeiceTwo, -2, 2) && CheckTetrino(tetrinoPeiceThree, -1, 1)) 
                {
                    MoveUp(tetrino.tetrinoPeiceOne);
                    MoveRight(tetrino.tetrinoPeiceOne);
                    MoveLeft(tetrino.tetrinoPeiceTwo);
                    MoveLeft(tetrino.tetrinoPeiceTwo);
                    MoveUp(tetrino.tetrinoPeiceTwo);
                    MoveUp(tetrino.tetrinoPeiceTwo);
                    MoveLeft(tetrino.tetrinoPeiceThree);
                    MoveUp(tetrino.tetrinoPeiceThree);

                    // Uses the number of rotations to change the form of the tetrino as a whole.
                    tetrino.changeForm();
                    break;
                }
                break;

            // If the name is equal to o.
            case "o":
                break;

            // If the name is equal to s.
            case "s":
                // If what the rotations are equal to, depending on the number of rotations detected, the method will move each
                // peice of the tetrino the required amount of times.
                if (rotations == 1 && CheckTetrino(tetrinoPeiceOne, -1, -1) && CheckTetrino(tetrinoPeiceThree, -1, 1) && CheckTetrino(tetrinoPeiceFour, 0, 2)) 
                {
                    MoveDown(tetrino.tetrinoPeiceOne);
                    MoveLeft(tetrino.tetrinoPeiceOne);
                    MoveLeft(tetrino.tetrinoPeiceThree);
                    MoveUp(tetrino.tetrinoPeiceThree);
                    MoveUp(tetrino.tetrinoPeiceFour);
                    MoveUp(tetrino.tetrinoPeiceFour);

                    // Uses the number of rotations to change the form of the tetrino as a whole.
                    tetrino.changeForm();
                    break;
                }

                // If what the rotations are equal to, depending on the number of rotations detected, the method will move each
                // peice of the tetrino the required amount of times.
                if (rotations == 2 && CheckTetrino(tetrinoPeiceOne, 1, 1) && CheckTetrino(tetrinoPeiceThree, 1, -1) && CheckTetrino(tetrinoPeiceFour, 0, -2)) 
                {
                    MoveUp(tetrino.tetrinoPeiceOne);
                    MoveRight(tetrino.tetrinoPeiceOne);
                    MoveRight(tetrino.tetrinoPeiceThree);
                    MoveDown(tetrino.tetrinoPeiceThree);
                    MoveDown(tetrino.tetrinoPeiceFour);
                    MoveDown(tetrino.tetrinoPeiceFour);

                    // Uses the number of rotations to change the form of the tetrino as a whole.
                    tetrino.changeForm();
                    break;
                }

                // If what the rotations are equal to, depending on the number of rotations detected, the method will move each
                // peice of the tetrino the required amount of times.
                if (rotations == 3 && CheckTetrino(tetrinoPeiceOne, -1, -1) && CheckTetrino(tetrinoPeiceThree, -1, 1) && CheckTetrino(tetrinoPeiceFour, 0, 2)) 
                {
                    MoveDown(tetrino.tetrinoPeiceOne);
                    MoveLeft(tetrino.tetrinoPeiceOne);
                    MoveLeft(tetrino.tetrinoPeiceThree);
                    MoveUp(tetrino.tetrinoPeiceThree);
                    MoveUp(tetrino.tetrinoPeiceFour);
                    MoveUp(tetrino.tetrinoPeiceFour);

                    // Uses the number of rotations to change the form of the tetrino as a whole.
                    tetrino.changeForm();
                    break;
                }

                // If what the rotations are equal to, depending on the number of rotations detected, the method will move each
                // peice of the tetrino the required amount of times.
                if (rotations == 4 && CheckTetrino(tetrinoPeiceOne, 1, 1) && CheckTetrino(tetrinoPeiceThree, 1, -1) && CheckTetrino(tetrinoPeiceFour, 0, -2)) 
                {
                    MoveUp(tetrino.tetrinoPeiceOne);
                    MoveRight(tetrino.tetrinoPeiceOne);
                    MoveRight(tetrino.tetrinoPeiceThree);
                    MoveDown(tetrino.tetrinoPeiceThree);
                    MoveDown(tetrino.tetrinoPeiceFour);
                    MoveDown(tetrino.tetrinoPeiceFour);

                    // Uses the number of rotations to change the form of the tetrino as a whole.
                    tetrino.changeForm();
                    break;
                }
                break;

            // If the name is equal to t.
            case "t":
                // If what the rotations are equal to, depending on the number of rotations detected, the method will move each
                // peice of the tetrino the required amount of times.
                if (rotations == 1 && CheckTetrino(tetrinoPeiceOne, 1, 1) && CheckTetrino(tetrinoPeiceFour, -1, -1) && CheckTetrino(tetrinoPeiceThree, -1, 1)) 
                {
                    MoveUp(tetrino.tetrinoPeiceOne);
                    MoveRight(tetrino.tetrinoPeiceOne);
                    MoveDown(tetrino.tetrinoPeiceFour);
                    MoveLeft(tetrino.tetrinoPeiceFour);
                    MoveLeft(tetrino.tetrinoPeiceThree);
                    MoveUp(tetrino.tetrinoPeiceThree);

                    // Uses the number of rotations to change the form of the tetrino as a whole.
                    tetrino.changeForm();
                    break;
                }

                // If what the rotations are equal to, depending on the number of rotations detected, the method will move each
                // peice of the tetrino the required amount of times.
                if (rotations == 2 && CheckTetrino(tetrinoPeiceOne, 1, -1) && CheckTetrino(tetrinoPeiceFour, -1, 1) && CheckTetrino(tetrinoPeiceThree, 1, 1)) 
                {
                    MoveRight(tetrino.tetrinoPeiceOne);
                    MoveDown(tetrino.tetrinoPeiceOne);
                    MoveLeft(tetrino.tetrinoPeiceFour);
                    MoveUp(tetrino.tetrinoPeiceFour);
                    MoveUp(tetrino.tetrinoPeiceThree);
                    MoveRight(tetrino.tetrinoPeiceThree);

                    // Uses the number of rotations to change the form of the tetrino as a whole.
                    tetrino.changeForm();
                    break;
                }

                // If what the rotations are equal to, depending on the number of rotations detected, the method will move each
                // peice of the tetrino the required amount of times.
                if (rotations == 3 && CheckTetrino(tetrinoPeiceOne, -1, -1) && CheckTetrino(tetrinoPeiceFour, 1, 1) && CheckTetrino(tetrinoPeiceThree, 1, -1)) 
                {
                    MoveDown(tetrino.tetrinoPeiceOne);
                    MoveLeft(tetrino.tetrinoPeiceOne);
                    MoveUp(tetrino.tetrinoPeiceFour);
                    MoveRight(tetrino.tetrinoPeiceFour);
                    MoveRight(tetrino.tetrinoPeiceThree);
                    MoveDown(tetrino.tetrinoPeiceThree);

                    // Uses the number of rotations to change the form of the tetrino as a whole.
                    tetrino.changeForm();
                    break;
                }

                // If what the rotations are equal to, depending on the number of rotations detected, the method will move each
                // peice of the tetrino the required amount of times.
                if (rotations == 4 && CheckTetrino(tetrinoPeiceOne, -1, 1) && CheckTetrino(tetrinoPeiceFour, 1, -1) && CheckTetrino(tetrinoPeiceThree, -1, -1)) 
                {
                    MoveLeft(tetrino.tetrinoPeiceOne);
                    MoveUp(tetrino.tetrinoPeiceOne);
                    MoveRight(tetrino.tetrinoPeiceFour);
                    MoveDown(tetrino.tetrinoPeiceFour);
                    MoveDown(tetrino.tetrinoPeiceThree);
                    MoveLeft(tetrino.tetrinoPeiceThree);

                    // Uses the number of rotations to change the form of the tetrino as a whole.
                    tetrino.changeForm();
                    break;
                }
                break;

            // If the name is equal to z.
            case "z":
                // If what the rotations are equal to, depending on the number of rotations detected, the method will move each
                // peice of the tetrino the required amount of times.
                if (rotations == 1 && CheckTetrino(tetrinoPeiceTwo, 1, 1) && CheckTetrino(tetrinoPeiceThree, -1, 1) && CheckTetrino(tetrinoPeiceFour, -2, 0)) 
                {
                    MoveUp(tetrino.tetrinoPeiceTwo);
                    MoveRight(tetrino.tetrinoPeiceTwo);
                    MoveLeft(tetrino.tetrinoPeiceThree);
                    MoveUp(tetrino.tetrinoPeiceThree);
                    MoveLeft(tetrino.tetrinoPeiceFour);
                    MoveLeft(tetrino.tetrinoPeiceFour);

                    // Uses the number of rotations to change the form of the tetrino as a whole.
                    tetrino.changeForm();
                    break;
                }

                // If what the rotations are equal to, depending on the number of rotations detected, the method will move each
                // peice of the tetrino the required amount of times.
                if (rotations == 2 && CheckTetrino(tetrinoPeiceTwo, -1, -1) && CheckTetrino(tetrinoPeiceThree, 1, -1) && CheckTetrino(tetrinoPeiceFour, 2, 0)) 
                {
                    MoveDown(tetrino.tetrinoPeiceTwo);
                    MoveLeft(tetrino.tetrinoPeiceTwo);
                    MoveRight(tetrino.tetrinoPeiceThree);
                    MoveDown(tetrino.tetrinoPeiceThree);
                    MoveRight(tetrino.tetrinoPeiceFour);
                    MoveRight(tetrino.tetrinoPeiceFour);

                    // Uses the number of rotations to change the form of the tetrino as a whole.
                    tetrino.changeForm();
                    break;
                }

                // If what the rotations are equal to, depending on the number of rotations detected, the method will move each
                // peice of the tetrino the required amount of times.
                if (rotations == 3 && CheckTetrino(tetrinoPeiceTwo, 1, 1) && CheckTetrino(tetrinoPeiceThree, -1, 1) && CheckTetrino(tetrinoPeiceFour, -2, 0)) 
                {
                    MoveUp(tetrino.tetrinoPeiceTwo);
                    MoveRight(tetrino.tetrinoPeiceTwo);
                    MoveLeft(tetrino.tetrinoPeiceThree);
                    MoveUp(tetrino.tetrinoPeiceThree);
                    MoveLeft(tetrino.tetrinoPeiceFour);
                    MoveLeft(tetrino.tetrinoPeiceFour);

                    // Uses the number of rotations to change the form of the tetrino as a whole.
                    tetrino.changeForm();
                    break;
                }

                // If what the rotations are equal to, depending on the number of rotations detected, the method will move each
                // peice of the tetrino the required amount of times.
                if (rotations == 4 && CheckTetrino(tetrinoPeiceTwo, -1, -1) && CheckTetrino(tetrinoPeiceThree, 1, -1) && CheckTetrino(tetrinoPeiceFour, 2, 0)) 
                {
                    MoveDown(tetrino.tetrinoPeiceTwo);
                    MoveLeft(tetrino.tetrinoPeiceTwo);
                    MoveRight(tetrino.tetrinoPeiceThree);
                    MoveDown(tetrino.tetrinoPeiceThree);
                    MoveRight(tetrino.tetrinoPeiceFour);
                    MoveRight(tetrino.tetrinoPeiceFour);

                    // Uses the number of rotations to change the form of the tetrino as a whole.
                    tetrino.changeForm();
                    break;
                }
                break;

            // If the name is equal to i.
            case "i":
                // If what the rotations are equal to, depending on the number of rotations detected, the method will move each
                // peice of the tetrino the required amount of times.
                if (rotations == 1 && CheckTetrino(tetrinoPeiceOne, 2, 2) && CheckTetrino(tetrinoPeiceTwo, 1, 1) && CheckTetrino(tetrinoPeiceFour, -1, -1)) 
                {
                    MoveUp(tetrino.tetrinoPeiceOne);
                    MoveUp(tetrino.tetrinoPeiceOne);
                    MoveRight(tetrino.tetrinoPeiceOne);
                    MoveRight(tetrino.tetrinoPeiceOne);
                    MoveUp(tetrino.tetrinoPeiceTwo);
                    MoveRight(tetrino.tetrinoPeiceTwo);
                    MoveDown(tetrino.tetrinoPeiceFour);
                    MoveLeft(tetrino.tetrinoPeiceFour);

                    // Uses the number of rotations to change the form of the tetrino as a whole.
                    tetrino.changeForm();
                    break;
                }

                // If what the rotations are equal to, depending on the number of rotations detected, the method will move each
                // peice of the tetrino the required amount of times.
                if (rotations == 2 && CheckTetrino(tetrinoPeiceOne, -2, -2) && CheckTetrino(tetrinoPeiceTwo, -1, -1) && CheckTetrino(tetrinoPeiceFour, 1, 1)) 
                {
                    MoveDown(tetrino.tetrinoPeiceOne);
                    MoveDown(tetrino.tetrinoPeiceOne);
                    MoveLeft(tetrino.tetrinoPeiceOne);
                    MoveLeft(tetrino.tetrinoPeiceOne);
                    MoveDown(tetrino.tetrinoPeiceTwo);
                    MoveLeft(tetrino.tetrinoPeiceTwo);
                    MoveUp(tetrino.tetrinoPeiceFour);
                    MoveRight(tetrino.tetrinoPeiceFour);

                    // Uses the number of rotations to change the form of the tetrino as a whole.
                    tetrino.changeForm();
                    break;
                }

                // If what the rotations are equal to, depending on the number of rotations detected, the method will move each
                // peice of the tetrino the required amount of times.
                if (rotations == 3 && CheckTetrino(tetrinoPeiceOne, 2, 2) && CheckTetrino(tetrinoPeiceTwo, 1, 1) && CheckTetrino(tetrinoPeiceFour, -1, -1)) 
                {
                    MoveUp(tetrino.tetrinoPeiceOne);
                    MoveUp(tetrino.tetrinoPeiceOne);
                    MoveRight(tetrino.tetrinoPeiceOne);
                    MoveRight(tetrino.tetrinoPeiceOne);
                    MoveUp(tetrino.tetrinoPeiceTwo);
                    MoveRight(tetrino.tetrinoPeiceTwo);
                    MoveDown(tetrino.tetrinoPeiceFour);
                    MoveLeft(tetrino.tetrinoPeiceFour);

                    // Uses the number of rotations to change the form of the tetrino as a whole.
                    tetrino.changeForm();
                    break;
                }

                // If what the rotations are equal to, depending on the number of rotations detected, the method will move each
                // peice of the tetrino the required amount of times.
                if (rotations == 4 && CheckTetrino(tetrinoPeiceOne, -2, -2) && CheckTetrino(tetrinoPeiceTwo, -1, -1) && CheckTetrino(tetrinoPeiceFour, 1, 1)) 
                {
                    MoveDown(tetrino.tetrinoPeiceOne);
                    MoveDown(tetrino.tetrinoPeiceOne);
                    MoveLeft(tetrino.tetrinoPeiceOne);
                    MoveLeft(tetrino.tetrinoPeiceOne);
                    MoveDown(tetrino.tetrinoPeiceTwo);
                    MoveLeft(tetrino.tetrinoPeiceTwo);
                    MoveUp(tetrino.tetrinoPeiceFour);
                    MoveRight(tetrino.tetrinoPeiceFour);

                    // Uses the number of rotations to change the form of the tetrino as a whole.
                    tetrino.changeForm();
                    break;
                }
                break;
            }
    }

    // Method responsible for removing rows when a user completes a line.
    private void RemoveRows(Pane pane) 
    {
        // Array of nodes.
        ArrayList<Node> rects = new ArrayList<>();
        // Array list of integers.
        ArrayList<Integer> lines = new ArrayList<>();
        // Array list of nodes.
        ArrayList<Node> newrects = new ArrayList<>();
        
        // Int full used to check if a line has been created.
        int full = 0;

        
        for (int i = 0; i < GAME_GRID_VIEW[0].length; i++) 
        {
            for (int[] GAME_GRID_VIEW1 : GAME_GRID_VIEW) {
                if (GAME_GRID_VIEW1[i] == 1) {
                    full++;
                }
            }
            if (full == GAME_GRID_VIEW.length)
            {
                lines.add(i + lines.size());
            }
            full = 0;
        }

        if (lines.size() > 0)
        do {
            pane.getChildren().stream().filter((node) -> (node instanceof Rectangle)).forEach((node) -> {
                rects.add(node);
            });
            score += 50;
            linesNo++;

            rects.stream().forEach((node) -> {
                Rectangle a = (Rectangle) node;
                if (a.getY() == lines.get(0) * PLAYER_MOVE_SIZE) 
                {
                    GAME_GRID_VIEW[(int) a.getX() / PLAYER_MOVE_SIZE][(int) a.getY() / PLAYER_MOVE_SIZE] = 0;
                    pane.getChildren().remove(node);
                } else {
                    newrects.add(node);
                }
            });

            newrects.stream().map((node) -> (Rectangle) node).filter((a) -> (a.getY() < lines.get(0) * PLAYER_MOVE_SIZE)).map((a) -> {
                GAME_GRID_VIEW[(int) a.getX() / PLAYER_MOVE_SIZE][(int) a.getY() / PLAYER_MOVE_SIZE] = 0;
                return a;
            }).forEach((a) -> {
                a.setY(a.getY() + PLAYER_MOVE_SIZE);
            });

            lines.remove(0);
            rects.clear();
            newrects.clear();

            pane.getChildren().stream().filter((node) -> (node instanceof Rectangle)).forEach((node) -> {
                rects.add(node);
            });

            rects.stream().map((node) -> (Rectangle) node).forEach((a) -> {
                try {
                    GAME_GRID_VIEW[(int) a.getX() / PLAYER_MOVE_SIZE][(int) a.getY() / PLAYER_MOVE_SIZE] = 1;
                } catch (ArrayIndexOutOfBoundsException e) 
                {
                }
            });
            rects.clear();
        } while (lines.size() > 0);
    }

    // Method responsible for moving left.
    private void MoveDown(Rectangle rect) 
    {
        // If the rectangles Y position/value plus the players move is less than the max 
        // height of the game board.
        if(rect.getY() + PLAYER_MOVE < MAX_HEIGHT)
        {
            // rectangles Y position/value, get the Y position/value plus the player move.
            rect.setY(rect.getY() + PLAYER_MOVE);
        }
    }

    // Method responsible for moving left.
    private void MoveRight(Rectangle rect) 
    {
        // If the rectangles X position/value plus the players move is less than or equal to 
        // the max height of the game board minus the players move size.
        if (rect.getX() + PLAYER_MOVE <= MAX_WIDTH - PLAYER_MOVE_SIZE)
        {
            // rectangles X position/value, get the X position/value plus the player move.
            rect.setX(rect.getX() + PLAYER_MOVE);
        }
    }

    // Method responsible for moving left.
    private void MoveLeft(Rectangle rect) 
    {
        // If the rectangles X position/value minus the players move is greater than or equal to zero.
        if (rect.getX() - PLAYER_MOVE >= 0)
        {
            // rectangles X position/value, get the X position/value minus the player move.
            rect.setX(rect.getX() - PLAYER_MOVE);
        }
    }

    // Method responsible for moving up, I.e. rotating the tetrino.
    private void MoveUp(Rectangle rect) 
    {
        // If the rectangles Y position/value minus the players move is greater than zero.
        if (rect.getY() - PLAYER_MOVE > 0)
        {
            // rectangles Y position/value, get the Y position/value minus the player move.
            rect.setY(rect.getY() - PLAYER_MOVE);
        }
    }

    // Method responsible for moving down, I.e. speeding up the falling tetrino.
    private void MoveDown(Tetrino tetrino) 
    {
        // Is any of the tetrino's peices plus max height of the player window less the players move size..
        if(
            tetrino.tetrinoPeiceOne.getY() == MAX_HEIGHT - PLAYER_MOVE_SIZE || 
            tetrino.tetrinoPeiceTwo.getY() == MAX_HEIGHT - PLAYER_MOVE_SIZE || 
            tetrino.tetrinoPeiceThree.getY() == MAX_HEIGHT - PLAYER_MOVE_SIZE || 
            tetrino.tetrinoPeiceFour.getY() == MAX_HEIGHT - PLAYER_MOVE_SIZE || 

            moveTetrinoBlockOne(tetrino) || 
            moveTetrinoBlockTwo(tetrino) || 
            moveTetrinoBlockThree(tetrino) || 
            moveTetrinoBlockFour(tetrino)
           ) 
        {
            // Sets the X and Y values/positions to 1 for each tetrino peice.
            GAME_GRID_VIEW[(int) tetrino.tetrinoPeiceOne.getX() / PLAYER_MOVE_SIZE][(int) tetrino.tetrinoPeiceOne.getY() / PLAYER_MOVE_SIZE] = 1;
            GAME_GRID_VIEW[(int) tetrino.tetrinoPeiceTwo.getX() / PLAYER_MOVE_SIZE][(int) tetrino.tetrinoPeiceTwo.getY() / PLAYER_MOVE_SIZE] = 1;
            GAME_GRID_VIEW[(int) tetrino.tetrinoPeiceThree.getX() / PLAYER_MOVE_SIZE][(int) tetrino.tetrinoPeiceThree.getY() / PLAYER_MOVE_SIZE] = 1;
            GAME_GRID_VIEW[(int) tetrino.tetrinoPeiceFour.getX() / PLAYER_MOVE_SIZE][(int) tetrino.tetrinoPeiceFour.getY() / PLAYER_MOVE_SIZE] = 1;
            RemoveRows(group);

            // Sets tetrino a equal to nextTetrino.
            Tetrino Tetrino = nextTetrino;
            // Gets the method makeTetrino from the controller class.
            nextTetrino = Controller.makeTetrino();
            // Sets object to Tetrino, used for adding the tetrinos to the group, I.e. Pane.
            object = Tetrino;
            
            // Add the tetrinos to the group, I.e. Pane.
            group.getChildren().addAll(Tetrino.tetrinoPeiceOne, Tetrino.tetrinoPeiceTwo, Tetrino.tetrinoPeiceThree, Tetrino.tetrinoPeiceFour);
            
            // Detect key presses and act.
            moveOnKeyPress(Tetrino);
        }

        // Is any of the tetrino's peices plus the players move are less than the max height of the player window.
        if (
            tetrino.tetrinoPeiceOne.getY() + PLAYER_MOVE < MAX_HEIGHT && 
            tetrino.tetrinoPeiceTwo.getY() + PLAYER_MOVE < MAX_HEIGHT && 
            tetrino.tetrinoPeiceThree.getY() + PLAYER_MOVE < MAX_HEIGHT && 
            tetrino.tetrinoPeiceFour.getY() + PLAYER_MOVE < MAX_HEIGHT
           ) 
        {
            // Sets the move tetrinoPeiceOne.
            int tetrinoPeiceOne = GAME_GRID_VIEW[(int) tetrino.tetrinoPeiceOne.getX() / PLAYER_MOVE_SIZE][((int) tetrino.tetrinoPeiceOne.getY() / PLAYER_MOVE_SIZE) + 1];
            // Sets the move tetrinoPeiceTwo.
            int tetrinoPeiceTwo = GAME_GRID_VIEW[(int) tetrino.tetrinoPeiceTwo.getX() / PLAYER_MOVE_SIZE][((int) tetrino.tetrinoPeiceTwo.getY() / PLAYER_MOVE_SIZE) + 1];
            // Sets the move tetrinoPeiceThree.
            int tetrinoPeiceThree = GAME_GRID_VIEW[(int) tetrino.tetrinoPeiceThree.getX() / PLAYER_MOVE_SIZE][((int) tetrino.tetrinoPeiceThree.getY() / PLAYER_MOVE_SIZE) + 1];
            // Sets the move tetrinoPeiceFour.
            int tetrinoPeiceFour = GAME_GRID_VIEW[(int) tetrino.tetrinoPeiceFour.getX() / PLAYER_MOVE_SIZE][((int) tetrino.tetrinoPeiceFour.getY() / PLAYER_MOVE_SIZE) + 1];

            // Checks each move of the tetrino peices.
            if (tetrinoPeiceOne == 0 && tetrinoPeiceOne == tetrinoPeiceTwo && tetrinoPeiceTwo == tetrinoPeiceThree && tetrinoPeiceThree == tetrinoPeiceFour)
            {
                // Sets the Y value/position of tetrinoPeiceOne plus the players move.
                tetrino.tetrinoPeiceOne.setY(tetrino.tetrinoPeiceOne.getY() + PLAYER_MOVE);
                // Sets the Y value/position of tetrinoPeiceTwo plus the players move.
                tetrino.tetrinoPeiceTwo.setY(tetrino.tetrinoPeiceTwo.getY() + PLAYER_MOVE);
                // Sets the Y value/position of tetrinoPeiceThree plus the players move.
                tetrino.tetrinoPeiceThree.setY(tetrino.tetrinoPeiceThree.getY() + PLAYER_MOVE);
                // Sets the Y value/position of tetrinoPeiceFour plus the players move.
                tetrino.tetrinoPeiceFour.setY(tetrino.tetrinoPeiceFour.getY() + PLAYER_MOVE);
            }
        }
    }

    // Method responsible for moving tetrino block one.
    private boolean moveTetrinoBlockOne(Tetrino tetrino) 
    {
        // Returns the new position for the tetrino block moved.
        return (GAME_GRID_VIEW[(int) tetrino.tetrinoPeiceOne.getX() / PLAYER_MOVE_SIZE][((int) tetrino.tetrinoPeiceOne.getY() / PLAYER_MOVE_SIZE) + 1] == 1);
    }

    // Method responsible for moving tetrino block two.
    private boolean moveTetrinoBlockTwo(Tetrino tetrino) 
    {
        // Returns the new position for the tetrino block moved.
        return (GAME_GRID_VIEW[(int) tetrino.tetrinoPeiceTwo.getX() / PLAYER_MOVE_SIZE][((int) tetrino.tetrinoPeiceTwo.getY() / PLAYER_MOVE_SIZE) + 1] == 1);
    }

    // Method responsible for moving tetrino block three.
    private boolean moveTetrinoBlockThree(Tetrino tetrino)
    {
        // Returns the new position for the tetrino block moved.
        return (GAME_GRID_VIEW[(int) tetrino.tetrinoPeiceThree.getX() / PLAYER_MOVE_SIZE][((int) tetrino.tetrinoPeiceThree.getY() / PLAYER_MOVE_SIZE) + 1] == 1);
    }

    // Method responsible for moving tetrino block four.
    private boolean moveTetrinoBlockFour(Tetrino tetrino) 
    {
        // Returns the new position for the tetrino block moved.
        return (GAME_GRID_VIEW[(int) tetrino.tetrinoPeiceFour.getX() / PLAYER_MOVE_SIZE][((int) tetrino.tetrinoPeiceFour.getY() / PLAYER_MOVE_SIZE) + 1] == 1);
    }

    // Method Responsible for checking the rotation of a tetrino.
    private boolean CheckTetrino(Rectangle rect, int x, int y)
    {
        // Boolean for the rotation/position of the x value of the tetrino.
        boolean XValue = false;
        // Boolean for the rotation/position of the y value of the tetrino.
        boolean YValue = false;
        
        // If x is greater than or equal to zero.
        if (x >= 0)
        {
            XValue = rect.getX() + x * PLAYER_MOVE <= MAX_WIDTH - PLAYER_MOVE_SIZE;
        }
        
        // If x is less than zero.
        if (x < 0)
        {
            XValue = rect.getX() + x * PLAYER_MOVE >= 0;
        }
        
        // If y is greater than or equal to zero.
        if (y >= 0)
        {
            YValue = rect.getY() - y * PLAYER_MOVE > 0;
        }
        
        // If y is less than zero.
        if (y < 0)
        {
            YValue = rect.getY() + y * PLAYER_MOVE < MAX_HEIGHT;
        }
        
        // Return the valuse from the mathmatics completed to gain the tetrinos position/rotation.
        return XValue && YValue && GAME_GRID_VIEW[((int) rect.getX() / PLAYER_MOVE_SIZE) + x][((int) rect.getY() / PLAYER_MOVE_SIZE) - y] == 0;
    }
    
    // Default main.
    public static void main(String[] args) 
    {
        launch(args);
    }
}