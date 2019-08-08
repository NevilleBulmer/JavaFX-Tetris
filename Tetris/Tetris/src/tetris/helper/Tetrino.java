/*
 * Helper data class for gengerating the tetrino's.
 */

package tetris.helper;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/*
 * @author Neville Bulmer
*/

public class Tetrino 
{
    // Rectangle which is used to represent the first block of a tetrino.
    public Rectangle tetrinoPeiceOne;
    // Rectangle which is used to represent the second block of a tetrino.
    public Rectangle tetrinoPeiceTwo;
    // Rectangle which is used to represent the third block of a tetrino.
    public Rectangle tetrinoPeiceThree;
    // Rectangle which is used to represent the fourth block of a tetrino.
    public Rectangle tetrinoPeiceFour;
    Color color;
    private String tetrinoName;
    public int tetrino = 1;

    public Tetrino(Rectangle tetrinoPeiceOne, Rectangle tetrinoPeiceTwo, Rectangle tetrinoPeiceThree, Rectangle tetrinoPeiceFour) 
    {
        this.tetrinoPeiceOne = tetrinoPeiceOne;
        this.tetrinoPeiceTwo = tetrinoPeiceTwo;
        this.tetrinoPeiceThree = tetrinoPeiceThree;
        this.tetrinoPeiceFour = tetrinoPeiceFour;
    }

    public Tetrino(Rectangle tetrinoPeiceOne, Rectangle tetrinoPeiceTwo, Rectangle tetrinoPeiceThree, Rectangle tetrinoPeiceFour, String tetrinoName) 
    {
        this.tetrinoPeiceOne = tetrinoPeiceOne;
        this.tetrinoPeiceTwo = tetrinoPeiceTwo;
        this.tetrinoPeiceThree = tetrinoPeiceThree;
        this.tetrinoPeiceFour = tetrinoPeiceFour;
        this.tetrinoName = tetrinoName;

        switch (tetrinoName) 
        {
        case "j":
                color = Color.SLATEGRAY;
                break;
        case "l":
                color = Color.DARKGOLDENROD;
                break;
        case "o":
                color = Color.INDIANRED;
                break;
        case "s":
                color = Color.FORESTGREEN;
                break;
        case "t":
                color = Color.CADETBLUE;
                break;
        case "z":
                color = Color.HOTPINK;
                break;
        case "i":
                color = Color.SANDYBROWN;
                break;

        }
        
        /*
         * Fills the tetrino peices with color.
        */
        this.tetrinoPeiceOne.setFill(color);
        this.tetrinoPeiceTwo.setFill(color);
        this.tetrinoPeiceThree.setFill(color);
        this.tetrinoPeiceFour.setFill(color);
    }


    public String getName() 
    {
        return tetrinoName;
    }


    public void changeForm() 
    {
        if (tetrino != 4) {
                tetrino++;
        } else {
                tetrino = 1;
        }
    }
}