/*
 * ViewManager class, handles everything seen on the screen in the menu.
 */
package tetris.view;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.stage.Stage;

import tetris.helper.CustomBlueButtons;

/**
 *
 * @author Neville Bulmer
 */
public class MenuViewManager
{
    private static final String 
        // Holds the location for the game logo.    
        GAME_LOGO_ASSET = "tetris/view/assets/game_logo.png", 
        // Holds the location for the menu back ground image.    
        MENU_BOARD_BACKGROUND_IMAGE = "tetris/view/assets/game_menu_background.png";
    
    // Sets the height of the menu window.
    private static final int MENU_WINDOW_HEIGHT = 800;
    // Sets the width of the menu window.
    private static final int MENU_WINDOW_WIDTH = 600;
    // Sets the width of the game menu.
    private static final int MENU_BUTTON_X = 50;
    // Sets the height of the game menu.
    private static final int MENU_BUTTON_Y = 50;

    // Arraylist which holds the menu buttons.
    List<CustomBlueButtons> blueMenuButtonList;
    
    // Anchor pane.
    private final AnchorPane mainPane;
    // Scene.
    private final Scene mainScene;
    // Stage.
    private final Stage mainStage;
    
    // ViewManager constructor.
    public MenuViewManager()
    {
        // Instantiate an array list.
        blueMenuButtonList = new ArrayList<>();
        // Sets mainPane equal to an anchorpane.
        mainPane = new AnchorPane();
        // Sets mainScene equal to a scene, which takes the anchor pane along with the width and height of the window.
        mainScene = new Scene(mainPane, MENU_WINDOW_WIDTH, MENU_WINDOW_HEIGHT);
        // Sets mainStage equal to a stage.
        mainStage = new Stage();
        // Sets the scene inside of the main stage.
        mainStage.setScene(mainScene);
        // Disables resizing of the main stage.
        mainStage.setResizable(false);
        // Sets the title of the main view.
        mainStage.setTitle("Tetris");
        
        // Instantiate the method responsible for the background.
        createBackground();
        
        // Instantiates the createButtons method.
        createButtons();
    }
    
    // Method which add the menu buttons to the menu.
    private void addBlueButtonsToMenu(CustomBlueButtons button)
    {
        // Sets the layout of X for the menu.
        button.setLayoutX(MENU_BUTTON_X);
        // Sets the layout of Y plus the button lists size * 100 for the menu.
        button.setLayoutY(MENU_BUTTON_Y + blueMenuButtonList.size() * 100);
        
        // Adds all buttons to the button array list.
        blueMenuButtonList.add(button);
        // Adds the button array list to the main pane.
        mainPane.getChildren().add(button);
    }
    
    private void createButtons()
    {
        // Instantiates the menu buttons I.e. play button.
        createPlayGameButton();
        // Instantiates the menu buttons I.e. scores button.
        createCheckScoresButton();
        // Instantiates the menu buttons I.e. help button.
        createHelpButton();
        // Instantiates the menu buttons I.e. credits button.
        createCreditsButton();
        // Instantiates the menu buttons I.e. quit button.
        createQuitButton();
    }
    
    /* ----- START BUTTON / MENU AND MENU CONTENT START ----- */
    // Method responsible for creating the play button and adding it to the menu,
    // also handles the listener for when the button is clicked and passes in the animated sub scene.
    private void createPlayGameButton()
    {
        // Instantiate the CustomYellowButtons class and give it the text for the button.
        CustomBlueButtons startGameButton = new CustomBlueButtons("PLAY");
        // Adds the scores button to the menu of buttons.
        addBlueButtonsToMenu(startGameButton);
        // Adds an on action listener to the button.
        startGameButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) 
            {
                GameViewManager ctc = new GameViewManager();

                try {
                    // Then call its start() method in the following way:
                    mainStage.hide();
                    ctc.start(GameViewManager.classStage);
                } catch (Exception ex) {
                    Logger.getLogger(MenuViewManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    /* ----- CREDITS BUTTON / MENU AND MENU CONTENT START ----- */
    // Method responsible for creating the credits button and adding it to the menu,
    // also handles the listener for when the button is clicked and passes in the animated sub scene.
    private void createCreditsButton()
    {
        // Instantiate the CustomYellowButtons class and give it the text for the button.
        CustomBlueButtons checkCreditsButton = new CustomBlueButtons("CREDIT");
        // Adds the credits button to the menu of buttons.
        addBlueButtonsToMenu(checkCreditsButton);
        // Adds an on action listener to the button.
        checkCreditsButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) 
            {
                // If the button is clicked load the corresponsing sub scene.
                System.out.println("CREDIT");
            }
        });
    }
    
    /* ----- SCORES BUTTON / MENU AND MENU CONTENT START ----- */
    // Method responsible for creating the scores button and adding it to the menu,
    // also handles the listener for when the button is clicked and passes in the animated sub scene.
    private void createCheckScoresButton()
    {
        // Instantiate the CustomYellowButtons class and give it the text for the button.
        CustomBlueButtons checkScoresButton = new CustomBlueButtons("SCORES");
        // Adds the scores button to the menu of buttons.
        addBlueButtonsToMenu(checkScoresButton);
        // Adds an on action listener to the button.
        checkScoresButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) 
            {
                // If the button is clicked load the corresponsing sub scene.                
                System.out.println("SCORES");
            }
        });
    }
    
    /* ----- HELP BUTTON / MENU AND MENU CONTENT START ----- */
    // Method responsible for creating the help button and adding it to the menu,
    // also handles the listener for when the button is clicked and passes in the animated sub scene.
    private void createHelpButton()
    {
        // Instantiate the CustomYellowButtons class and give it the text for the button.
        CustomBlueButtons checkHelpButton = new CustomBlueButtons("HELP");
        // Adds the help button to the menu of buttons.
        addBlueButtonsToMenu(checkHelpButton);
        // Adds an on action listener to the button.
        checkHelpButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) 
            {
                // If the button is clicked load the corresponsing sub scene.
               System.out.println("HELP");
            }
        });
    }
    /* ----- HELP BUTTON / MENU AND MENU CONTENT END ----- */
    
    
    // Method responsible for creating the quit button and adding it to the menu,
    // also handles the listener for when the button is clicked and quits the game.
    private void createQuitButton()
    {
        // Instantiate the CustomYellowButtons class and give it the text for the button.
        CustomBlueButtons quitGameButton = new CustomBlueButtons("QUIT");
        // Adds the quit button to the menu of buttons.
        addBlueButtonsToMenu(quitGameButton);
        // Adds an on action listener to the button.
        quitGameButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) 
            {
                // If the button is clicked then exit the game.
                System.exit(0);
            }
        });
    }

    // Method responisble for generating the background image.
    private void createBackground()
    {
        // Creates an image and sets it to the background image, along with its height and width.
        Image backgroundImage = new Image(MENU_BOARD_BACKGROUND_IMAGE, 610, 810, false, true);
        // Creates a new background image and set the image above to the image to be used along with the repeats, size.
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        
        // Adds the background image the mainPane, AnchorPane.
        mainPane.setBackground(new Background(background));
    }
    
    // returns the main stage.
    public Stage getMainStage()
    {
        // return mainStage.
        return mainStage;
    }
}