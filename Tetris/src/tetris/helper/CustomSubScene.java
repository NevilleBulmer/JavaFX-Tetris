/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.helper;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.util.Duration;

/**
 *
 * @author Neville Bulmer
 */
public class CustomSubScene extends SubScene
{
    // Sets the location of the custom font for the button.
    private final String PATH_TO_FONT = "src/helper/assets/kenvector_future.ttf";
    // Sets the location of the custom style for the button when being pressed.
    private final String STYLE_FOR_SUBMENU_BACKGROUND = "helper/assets/yellow_panel.png";
    
    // Boolean used to check if the subScene is hidden or visible.
    private boolean isHidden;

    public CustomSubScene() 
    {
        // Overrides and creates a super for the constructor, which takes the anchor pane along with the height and width of the submenu.
        super(new AnchorPane(), 700, 400);
        
        // Sets the prefered width of the sub menu.
        prefWidth(700);
        // Sets the prefered width of the sub menu.
        prefHeight(400);
        // Creates a back ground image and sets it equal to the variable STYLE_FOR_SUBMENU_BACKGROUND, along with its prefered width, height and repeat.
        BackgroundImage image = new BackgroundImage(new Image(STYLE_FOR_SUBMENU_BACKGROUND, 700, 400, false, true), 
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        
        // Casts submenu to anchor pane of root.
        AnchorPane submenuRoot = (AnchorPane) this.getRoot();
        // Adds the background to the anchor pane and assigns it as the background image.
        submenuRoot.setBackground(new Background(image));
        
        //Sets the boolean isHidden to true, I.e. it is currentl hidden.
        isHidden = true;
        
        // Sets the X layout of the sub scene.
        setLayoutX(1240);
        // Sets the Y layout of the sub scene.
        setLayoutY(180);
    }
    
    public void animateSubScene()
    {
        // Instantiates a translate translation equal to translate for the animation.
        TranslateTransition transition = new TranslateTransition();
        // Sets the duration of the transition to 0.3 seconds.
        transition.setDuration(Duration.seconds(0.3));
        // Sets the transition node.
        transition.setNode(this);
        
        if(isHidden)
        {
            // Sets the X value I.e. left most part of the transition.
            transition.setToX(-800);
            // Sets isHidden to false, I.e. not hidden.
            isHidden = false;
        } else {
            // Sets the X value I.e. hides the subScene.
            transition.setToX(0);
            // Sets isHidden to true, I.e. hidden.
            isHidden = true;
        }
        
        // Starts the transition.
        transition.play();
    }
    
    // Used to return the sub scene anchorpane.
    public AnchorPane getSubScenePane()
    {
        // return the casted anchor pane.
        return (AnchorPane) this.getRoot();
    }
}
