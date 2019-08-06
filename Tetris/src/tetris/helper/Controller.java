/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris.helper;

import javafx.scene.shape.Rectangle;
import tetris.Tetris;

public class Controller 
{
    // Getting the numbers and the GAME_GRID_VIEW from Tetris
    public static final int MOVE = Tetris.PLAYER_MOVE;
    public static final int PLAYER_MOVE_SIZE = Tetris.PLAYER_MOVE_SIZE;
    public static int MAX_WIDTH = Tetris.MAX_WIDTH;
    public static int MAX_HEIGHT = Tetris.MAX_HEIGHT;
    public static int[][] GAME_GRID_VIEW = Tetris.GAME_GRID_VIEW;

    public static void MoveRight(Tetrino tetrino) 
    {
        if (
            tetrino.a.getX() + MOVE <= MAX_WIDTH - PLAYER_MOVE_SIZE && 
            tetrino.b.getX() + MOVE <= MAX_WIDTH - PLAYER_MOVE_SIZE && 
            tetrino.c.getX() + MOVE <= MAX_WIDTH - PLAYER_MOVE_SIZE && 
            tetrino.d.getX() + MOVE <= MAX_WIDTH - PLAYER_MOVE_SIZE
           ) 
        {

            int movea = GAME_GRID_VIEW[((int) tetrino.a.getX() / PLAYER_MOVE_SIZE) + 1][((int) tetrino.a.getY() / PLAYER_MOVE_SIZE)];
            int moveb = GAME_GRID_VIEW[((int) tetrino.b.getX() / PLAYER_MOVE_SIZE) + 1][((int) tetrino.b.getY() / PLAYER_MOVE_SIZE)];
            int movec = GAME_GRID_VIEW[((int) tetrino.c.getX() / PLAYER_MOVE_SIZE) + 1][((int) tetrino.c.getY() / PLAYER_MOVE_SIZE)];
            int moved = GAME_GRID_VIEW[((int) tetrino.d.getX() / PLAYER_MOVE_SIZE) + 1][((int) tetrino.d.getY() / PLAYER_MOVE_SIZE)];

            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) 
            {
                tetrino.a.setX(tetrino.a.getX() + MOVE);
                tetrino.b.setX(tetrino.b.getX() + MOVE);
                tetrino.c.setX(tetrino.c.getX() + MOVE);
                tetrino.d.setX(tetrino.d.getX() + MOVE);
            }
        }
    }

    public static void MoveLeft(Tetrino tetrino) 
    {
        if (
            tetrino.a.getX() - MOVE >= 0 && 
            tetrino.b.getX() - MOVE >= 0 && 
            tetrino.c.getX() - MOVE >= 0 && 
            tetrino.d.getX() - MOVE >= 0
           )
        {

            int movea = GAME_GRID_VIEW[((int) tetrino.a.getX() / PLAYER_MOVE_SIZE) - 1][((int) tetrino.a.getY() / PLAYER_MOVE_SIZE)];
            int moveb = GAME_GRID_VIEW[((int) tetrino.b.getX() / PLAYER_MOVE_SIZE) - 1][((int) tetrino.b.getY() / PLAYER_MOVE_SIZE)];
            int movec = GAME_GRID_VIEW[((int) tetrino.c.getX() / PLAYER_MOVE_SIZE) - 1][((int) tetrino.c.getY() / PLAYER_MOVE_SIZE)];
            int moved = GAME_GRID_VIEW[((int) tetrino.d.getX() / PLAYER_MOVE_SIZE) - 1][((int) tetrino.d.getY() / PLAYER_MOVE_SIZE)];

            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) 
            {
                tetrino.a.setX(tetrino.a.getX() - MOVE);
                tetrino.b.setX(tetrino.b.getX() - MOVE);
                tetrino.c.setX(tetrino.c.getX() - MOVE);
                tetrino.d.setX(tetrino.d.getX() - MOVE);
            }
        }
    }

    public static Tetrino makeRect() 
    {
        int block = (int) (Math.random() * 100);

        String name;

        Rectangle 
            a = new Rectangle(PLAYER_MOVE_SIZE-1, PLAYER_MOVE_SIZE-1), 
            b = new Rectangle(PLAYER_MOVE_SIZE-1, PLAYER_MOVE_SIZE-1), 
            c = new Rectangle(PLAYER_MOVE_SIZE-1, PLAYER_MOVE_SIZE-1),
            d = new Rectangle(PLAYER_MOVE_SIZE-1, PLAYER_MOVE_SIZE-1);

        if (block < 15) 
        {
            a.setX(MAX_WIDTH / 2 - PLAYER_MOVE_SIZE);
            b.setX(MAX_WIDTH / 2 - PLAYER_MOVE_SIZE);
            b.setY(PLAYER_MOVE_SIZE);
            c.setX(MAX_WIDTH / 2);
            c.setY(PLAYER_MOVE_SIZE);
            d.setX(MAX_WIDTH / 2 + PLAYER_MOVE_SIZE);
            d.setY(PLAYER_MOVE_SIZE);

            name = "j";

        } else if (block < 30) 
        {
            a.setX(MAX_WIDTH / 2 + PLAYER_MOVE_SIZE);
            b.setX(MAX_WIDTH / 2 - PLAYER_MOVE_SIZE);
            b.setY(PLAYER_MOVE_SIZE);
            c.setX(MAX_WIDTH / 2);
            c.setY(PLAYER_MOVE_SIZE);
            d.setX(MAX_WIDTH / 2 + PLAYER_MOVE_SIZE);
            d.setY(PLAYER_MOVE_SIZE);

            name = "l";

        } else if (block < 45) 
        {
            a.setX(MAX_WIDTH / 2 - PLAYER_MOVE_SIZE);
            b.setX(MAX_WIDTH / 2);
            c.setX(MAX_WIDTH / 2 - PLAYER_MOVE_SIZE);
            c.setY(PLAYER_MOVE_SIZE);
            d.setX(MAX_WIDTH / 2);
            d.setY(PLAYER_MOVE_SIZE);

            name = "o";

        } else if (block < 60) 
        {
            a.setX(MAX_WIDTH / 2 + PLAYER_MOVE_SIZE);
            b.setX(MAX_WIDTH / 2);
            c.setX(MAX_WIDTH / 2);
            c.setY(PLAYER_MOVE_SIZE);
            d.setX(MAX_WIDTH / 2 - PLAYER_MOVE_SIZE);
            d.setY(PLAYER_MOVE_SIZE);

            name = "s";

        } else if (block < 75) 
        {
            a.setX(MAX_WIDTH / 2 - PLAYER_MOVE_SIZE);
            b.setX(MAX_WIDTH / 2);
            c.setX(MAX_WIDTH / 2);
            c.setY(PLAYER_MOVE_SIZE);
            d.setX(MAX_WIDTH / 2 + PLAYER_MOVE_SIZE);

            name = "t";

        } else if (block < 90) 
        {
            a.setX(MAX_WIDTH / 2 + PLAYER_MOVE_SIZE);
            b.setX(MAX_WIDTH / 2);
            c.setX(MAX_WIDTH / 2 + PLAYER_MOVE_SIZE);
            c.setY(PLAYER_MOVE_SIZE);
            d.setX(MAX_WIDTH / 2 + PLAYER_MOVE_SIZE + PLAYER_MOVE_SIZE);
            d.setY(PLAYER_MOVE_SIZE);

            name = "z";

        } else { 
            a.setX(MAX_WIDTH / 2 - PLAYER_MOVE_SIZE - PLAYER_MOVE_SIZE);
            b.setX(MAX_WIDTH / 2 - PLAYER_MOVE_SIZE);
            c.setX(MAX_WIDTH / 2);
            d.setX(MAX_WIDTH / 2 + PLAYER_MOVE_SIZE);
            name = "i";
        }

        return new Tetrino(a, b, c, d, name);
    }
}