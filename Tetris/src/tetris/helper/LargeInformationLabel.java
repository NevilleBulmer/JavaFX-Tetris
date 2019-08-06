/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.helper;

import static java.awt.Color.blue;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author bulme
 */
public class LargeInformationLabel extends Label
{
    // Sets the location of the custom font.
    private final String PATH_TO_FONT = "src/helper/assets/kenvector_future.ttf";
        
    public LargeInformationLabel(String text, String textColor, int width, int height)
    {
        // Sets prefered width.
        setPrefWidth(width);
        setTextFill(Color.web(textColor));
        // Sets the prefered height.
        setPrefHeight(height);
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
