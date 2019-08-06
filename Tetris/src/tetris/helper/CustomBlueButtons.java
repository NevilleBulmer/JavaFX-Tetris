/*
 * Class used to overide the normal button visuals and replace them with custom versions, I.e. images, color, font, size.
 */
package tetris.helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

/**
 *
 * @author Neville Bulmer
 */
public class CustomBlueButtons extends Button
{
    // Sets the location of the custom font for the button.
    private final String PATH_TO_FONT = "src/tetris/helper/assets/kenvector_future.ttf";
    // Sets the location of the custom style for the button when being pressed.
    private final String STYLE_FOR_BUTTON_PRESS = "-fx-background-color: transparent; -fx-background-image: url(' tetris/helper/assets/blue_button_pressed.png')";
    // Sets the location of the custom style for the button when not being pressed.
    private final String STYLE_FOR_BUTTON_RELEASE = "-fx-background-color: transparent; -fx-background-image: url(' tetris/helper/assets/blue_button.png')";
    
    // Custom button constructor.
    public CustomBlueButtons(String text)
    {
        // Sets the text of the button equal to the text given when initialising the button.
        setText(text);
        // Set the buttons font.
        setButtonFont();
        // Set the prefered width of the button.
        setPrefWidth(190);
        // Set the prefered height of the button.
        setPrefHeight(49);
        // Set style of the button to the variable STYLE_FOR_BUTTON_RELEASE, I.e. not pressed by default.
        setStyle(STYLE_FOR_BUTTON_RELEASE);
        
        // Initialise the method for instantiating the button listeners.
        initializeButtonListeners();
    }
    
    // Method responsible for setting the custom font, along with a backup font.
    private void setButtonFont()
    {
        // Try block responsible for setting the custom theme, if not found or it cant be applied then a default is used.
        // Default is Verdana, 23
        try 
        {
            // Set custom font.
            setFont(Font.loadFont(new FileInputStream(PATH_TO_FONT), 23));
        } catch (FileNotFoundException ex) 
        {
            // Set default if needed.
            setFont(Font.font("Verdana", 23));
        }
    }
    
    // Method responsible for setting the custom styling to a pressed button.
    private void setPressedButtonStyling()
    {
        // Set the custom style.
        setStyle(STYLE_FOR_BUTTON_PRESS);
        // Set the prefered height.
        setPrefHeight(45);
        // Set the layout.
        setLayoutY(getLayoutY() + 4);
    }
    
    // Method responsible for setting the custom styling to a none pressed button.
    private void setReleasedButtonStyling()
    {
        // Set the custom style.
        setStyle(STYLE_FOR_BUTTON_RELEASE);
        // Set the prefered height.
        setPrefHeight(49);
        // Set the layout.
        setLayoutY(getLayoutY() - 4);
    }
    
    // Method responsible for initializing all button listeners, I.e. on click, on mouse enter.
    private void initializeButtonListeners()
    {
        // On mouse pressed event initialiser for setting the style to pressed.
        setOnMousePressed(new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle(MouseEvent event)
            {
                // If the button pressed detected was the primary mouse button, then add pressed styling.
                if(event.getButton().equals(MouseButton.PRIMARY))
                {
                    // Pressed method instantiation.
                    setPressedButtonStyling();
                }
            }
        });
        
        // On mouse pressed event initialiser for setting the style to released.
        setOnMouseReleased(new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle(MouseEvent event)
            {
                // If the button released detected was the primary mouse button, then remove pressed styling.
                if(event.getButton().equals(MouseButton.PRIMARY))
                {
                    // Released method instantiation.
                    setReleasedButtonStyling();
                }
            }
        });
        
        // On mouse entered event initialiser for setting a drop shadow effect to the button.
        setOnMouseEntered(new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle(MouseEvent event)
            {
                // Apply a drop shadow on mouse enter.
                setEffect(new DropShadow());
            }
        });
        
        // On mouse exited event initialiser for removing a drop shadow effect from the button.
        setOnMouseExited(new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle(MouseEvent event)
            {
                // Remove a drop shadow on mouse exit.
                setEffect(null);
            }
        });
    }
}
