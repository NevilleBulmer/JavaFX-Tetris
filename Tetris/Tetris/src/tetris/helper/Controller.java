/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris.helper;

import javafx.scene.shape.Rectangle;
import tetris.view.GameViewManager;


public class Controller 
{
    // Getting the numbers and the GAME_GRID_VIEW from Tetris
    public static final int MOVE = GameViewManager.PLAYER_MOVE;
    public static final int PLAYER_MOVE_SIZE = GameViewManager.PLAYER_MOVE_SIZE;
    public static int MAX_WIDTH = GameViewManager.MAX_WIDTH;
    public static int MAX_HEIGHT = GameViewManager.MAX_HEIGHT;
    public static int[][] GAME_GRID_VIEW = GameViewManager.GAME_GRID_VIEW;

    public static void MoveRight(Tetrino tetrino) 
    {
        if (
            tetrino.tetrinoPeiceOne.getX() + MOVE <= MAX_WIDTH - PLAYER_MOVE_SIZE && 
            tetrino.tetrinoPeiceTwo.getX() + MOVE <= MAX_WIDTH - PLAYER_MOVE_SIZE && 
            tetrino.tetrinoPeiceThree.getX() + MOVE <= MAX_WIDTH - PLAYER_MOVE_SIZE && 
            tetrino.tetrinoPeiceFour.getX() + MOVE <= MAX_WIDTH - PLAYER_MOVE_SIZE
           ) 
        {

            int movea = GAME_GRID_VIEW[((int) tetrino.tetrinoPeiceOne.getX() / PLAYER_MOVE_SIZE) + 1][((int) tetrino.tetrinoPeiceOne.getY() / PLAYER_MOVE_SIZE)];
            int moveb = GAME_GRID_VIEW[((int) tetrino.tetrinoPeiceTwo.getX() / PLAYER_MOVE_SIZE) + 1][((int) tetrino.tetrinoPeiceTwo.getY() / PLAYER_MOVE_SIZE)];
            int movec = GAME_GRID_VIEW[((int) tetrino.tetrinoPeiceThree.getX() / PLAYER_MOVE_SIZE) + 1][((int) tetrino.tetrinoPeiceThree.getY() / PLAYER_MOVE_SIZE)];
            int moved = GAME_GRID_VIEW[((int) tetrino.tetrinoPeiceFour.getX() / PLAYER_MOVE_SIZE) + 1][((int) tetrino.tetrinoPeiceFour.getY() / PLAYER_MOVE_SIZE)];

            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) 
            {
                tetrino.tetrinoPeiceOne.setX(tetrino.tetrinoPeiceOne.getX() + MOVE);
                tetrino.tetrinoPeiceTwo.setX(tetrino.tetrinoPeiceTwo.getX() + MOVE);
                tetrino.tetrinoPeiceThree.setX(tetrino.tetrinoPeiceThree.getX() + MOVE);
                tetrino.tetrinoPeiceFour.setX(tetrino.tetrinoPeiceFour.getX() + MOVE);
            }
        }
    }

    public static void MoveLeft(Tetrino tetrino) 
    {
        if (
            tetrino.tetrinoPeiceOne.getX() - MOVE >= 0 && 
            tetrino.tetrinoPeiceTwo.getX() - MOVE >= 0 && 
            tetrino.tetrinoPeiceThree.getX() - MOVE >= 0 && 
            tetrino.tetrinoPeiceFour.getX() - MOVE >= 0
           )
        {

            int movea = GAME_GRID_VIEW[((int) tetrino.tetrinoPeiceOne.getX() / PLAYER_MOVE_SIZE) - 1][((int) tetrino.tetrinoPeiceOne.getY() / PLAYER_MOVE_SIZE)];
            int moveb = GAME_GRID_VIEW[((int) tetrino.tetrinoPeiceTwo.getX() / PLAYER_MOVE_SIZE) - 1][((int) tetrino.tetrinoPeiceTwo.getY() / PLAYER_MOVE_SIZE)];
            int movec = GAME_GRID_VIEW[((int) tetrino.tetrinoPeiceThree.getX() / PLAYER_MOVE_SIZE) - 1][((int) tetrino.tetrinoPeiceThree.getY() / PLAYER_MOVE_SIZE)];
            int moved = GAME_GRID_VIEW[((int) tetrino.tetrinoPeiceFour.getX() / PLAYER_MOVE_SIZE) - 1][((int) tetrino.tetrinoPeiceFour.getY() / PLAYER_MOVE_SIZE)];

            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) 
            {
                tetrino.tetrinoPeiceOne.setX(tetrino.tetrinoPeiceOne.getX() - MOVE);
                tetrino.tetrinoPeiceTwo.setX(tetrino.tetrinoPeiceTwo.getX() - MOVE);
                tetrino.tetrinoPeiceThree.setX(tetrino.tetrinoPeiceThree.getX() - MOVE);
                tetrino.tetrinoPeiceFour.setX(tetrino.tetrinoPeiceFour.getX() - MOVE);
            }
        }
    }

    public static Tetrino makeTetrino()
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