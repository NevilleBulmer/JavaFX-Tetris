/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris.helper;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tetrino 
{
    public Rectangle a;
    public Rectangle b;
    public Rectangle c;
    public Rectangle d;
    Color color;
    private String name;
    public int tetrino = 1;

    public Tetrino(Rectangle a, Rectangle b, Rectangle c, Rectangle d) 
    {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public Tetrino(Rectangle a, Rectangle b, Rectangle c, Rectangle d, String name) 
    {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.name = name;

        switch (name) 
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
        
        this.a.setFill(color);
        this.b.setFill(color);
        this.c.setFill(color);
        this.d.setFill(color);
    }


    public String getName() 
    {
        return name;
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