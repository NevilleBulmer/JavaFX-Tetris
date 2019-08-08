/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

/**
 *
 * @author bulme
 */
public class SmallInformationLabel extends Label
{
    // Sets the location of the custom font.
    private final String PATH_TO_FONT = "src/helper/assets/kenvector_future.ttf";
    
    public final static String BACKGRUND_IMAGE = "helper/assets/player_score_visual_indicator.png";
    
    public SmallInformationLabel(String text)
    {
        // Sets prefered width.
        setPrefWidth(185);
        // Sets the prefered height.
        setPrefHeight(40);
        // Set the text which is passed through using the text variable.
        setText(text);
        // Sets the text, passed thought using the text variable.
        setWrapText(true);
        // Sets the label font.
        setLabelFont();
        // Sets the alignment to center.
        setAlignment(Pos.CENTER);
        // Sets the paddinf to 10 on every side.
        setPadding(new Insets(10, 10, 10, 10));
        
        // Creates a back ground image and sets it equal to the variable BACKGRUND_IMAGE, along with its repeat, true, false.
        BackgroundImage backgroundImage = new BackgroundImage(new Image(BACKGRUND_IMAGE, 500, 49, false, true), 
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        
        // Sets the background image equal to backgroundImage.
        setBackground(new Background(backgroundImage));
    }
    
    private void setLabelFont()
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
}
