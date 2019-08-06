/*
 *
 * ViewManager class, handles everything seen on the screen in the menu.
 *
*/

package tetris.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tetris.helper.Controller;
import tetris.helper.CustomAnimationTimer;
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
    private static final Pane group = new Pane();
    private static Tetrino object;
    private static final Scene scene = new Scene(group, MAX_WIDTH + 150, MAX_HEIGHT);
    public static int score = 0;
    private static int top = 0;
    private static boolean game = true;
    private static Tetrino nextTetrino = Controller.makeTetrino();
    private static int linesNo = 0;
    
    // Creates a satge to be passed to the meny class.
    static Stage classStage = new Stage();

    // AnimationTimer to track the animation progress.
    private AnimationTimer gameAnimationTimer;
    private long lastUpdate = 300 ;
    @Override
    public void start(Stage stage) throws Exception 
    {
        // for i which if used to fill the array of the game grid with empty values on game start.
        for (int[] a : GAME_GRID_VIEW) 
        {
            // Filling the array.
            Arrays.fill(a, 0);
        }
        
        classStage = stage;

        Line line = new Line(MAX_WIDTH, 0, MAX_WIDTH, MAX_HEIGHT);
        Text scoretext = new Text("Score: ");
        scoretext.setStyle("-fx-font: 20 arial;");
        scoretext.setY(50);
        scoretext.setX(MAX_WIDTH + 5);
        Text level = new Text("Lines: ");
        level.setStyle("-fx-font: 20 arial;");
        level.setY(100);
        level.setX(MAX_WIDTH + 5);
        level.setFill(Color.GREEN);
        group.getChildren().addAll(scoretext, line, level);

        Tetrino a = nextTetrino;
        group.getChildren().addAll(a.tetrinoPeiceOne, a.tetrinoPeiceTwo, a.tetrinoPeiceThree, a.tetrinoPeiceFour);
        moveOnKeyPress(a);
        object = a;
        nextTetrino = Controller.makeTetrino();
        classStage.setScene(scene);
        classStage.setTitle("Tetris");
        classStage.show();
        
        CustomAnimationTimer timer = new CustomAnimationTimer(500) 
        {
            @Override
            public void handle() 
            {
                if 
                (
                    object.tetrinoPeiceOne.getY() == 0 || 
                    object.tetrinoPeiceTwo.getY() == 0 || 
                    object.tetrinoPeiceThree.getY() == 0 || 
                    object.tetrinoPeiceFour.getY() == 0
                )
                    top++;
                else
                    top = 0;

                if (top == 2) 
                {
                    // GAME OVER
                    Text over = new Text("GAME OVER");
                    over.setFill(Color.RED);
                    over.setStyle("-fx-font: 70 arial;");
                    over.setY(250);
                    over.setX(10);
                    group.getChildren().add(over);
                    game = false;
                }
                // Exit
                if (top == 15) 
                {
                    System.exit(0);
                }

                if (game) 
                {
                    MoveDown(object);
                    scoretext.setText("Score: " + Integer.toString(score));
                    level.setText("Lines: " + Integer.toString(linesNo));
                }
            }
        };

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