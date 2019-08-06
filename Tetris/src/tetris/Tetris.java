package tetris;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
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
import tetris.helper.Tetrino;

public class Tetris extends Application 
{
    // The variables
    public static final int PLAYER_MOVE = 25;
    public static final int PLAYER_MOVE_SIZE = 25;
    public static int MAX_WIDTH = PLAYER_MOVE_SIZE * 12;
    public static int MAX_HEIGHT = PLAYER_MOVE_SIZE * 24;
    public static int[][] GAME_GRID_VIEW = new int[MAX_WIDTH / PLAYER_MOVE_SIZE][MAX_HEIGHT / PLAYER_MOVE_SIZE];
    private static Pane group = new Pane();
    private static Tetrino object;
    private static Scene scene = new Scene(group, MAX_WIDTH + 150, MAX_HEIGHT);
    public static int score = 0;
    private static int top = 0;
    private static boolean game = true;
    private static Tetrino nextObj = Controller.makeRect();
    private static int linesNo = 0;

    public static void main(String[] args) 
    {
            launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception 
    {
        for (int[] a : GAME_GRID_VIEW) 
        {
                Arrays.fill(a, 0);
        }

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

        Tetrino a = nextObj;
        group.getChildren().addAll(a.a, a.b, a.c, a.d);
        moveOnKeyPress(a);
        object = a;
        nextObj = Controller.makeRect();
        stage.setScene(scene);
        stage.setTitle("Tetris");
        stage.show();

        Timer fall = new Timer();
        TimerTask task = new TimerTask() 
        {
            public void run() 
            {
                Platform.runLater(new Runnable() 
                {
                    public void run() 
                    {
                        if 
                        (
                            object.a.getY() == 0 || 
                            object.b.getY() == 0 || 
                            object.c.getY() == 0 || 
                            object.d.getY() == 0
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
                });
            }
        };
        fall.schedule(task, 0, 300);
    }

    private void moveOnKeyPress(Tetrino tetrino) 
    {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() 
        {
            @Override
            public void handle(KeyEvent event) 
            {
                switch (event.getCode()) 
                {
                case RIGHT:
                    Controller.MoveRight(tetrino);
                    break;
                case DOWN:
                    MoveDown(tetrino);
                    score++;
                    break;
                case LEFT:
                    Controller.MoveLeft(tetrino);
                    break;
                case UP:
                    MoveTurn(tetrino);
                    break;
                }
            }
        });
    }

    private void MoveTurn(Tetrino tetrino) 
    {
        int f = tetrino.tetrino;

        Rectangle a = tetrino.a;
        Rectangle b = tetrino.b;
        Rectangle c = tetrino.c;
        Rectangle d = tetrino.d;

        switch (tetrino.getName()) 
        {
            case "j":
                if (f == 1 && cB(a, 1, -1) && cB(c, -1, -1) && cB(d, -2, -2)) 
                {
                    MoveRight(tetrino.a);
                    MoveDown(tetrino.a);
                    MoveDown(tetrino.c);
                    MoveLeft(tetrino.c);
                    MoveDown(tetrino.d);
                    MoveDown(tetrino.d);
                    MoveLeft(tetrino.d);
                    MoveLeft(tetrino.d);

                    tetrino.changeForm();
                    break;
                }

                if (f == 2 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, -2, 2)) 
                {
                    MoveDown(tetrino.a);
                    MoveLeft(tetrino.a);
                    MoveLeft(tetrino.c);
                    MoveUp(tetrino.c);
                    MoveLeft(tetrino.d);
                    MoveLeft(tetrino.d);
                    MoveUp(tetrino.d);
                    MoveUp(tetrino.d);

                    tetrino.changeForm();
                    break;
                }

                if (f == 3 && cB(a, -1, 1) && cB(c, 1, 1) && cB(d, 2, 2)) 
                {
                    MoveLeft(tetrino.a);
                    MoveUp(tetrino.a);
                    MoveUp(tetrino.c);
                    MoveRight(tetrino.c);
                    MoveUp(tetrino.d);
                    MoveUp(tetrino.d);
                    MoveRight(tetrino.d);
                    MoveRight(tetrino.d);

                    tetrino.changeForm();
                    break;
                }

                if (f == 4 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 2, -2)) 
                {
                    MoveUp(tetrino.a);
                    MoveRight(tetrino.a);
                    MoveRight(tetrino.c);
                    MoveDown(tetrino.c);
                    MoveRight(tetrino.d);
                    MoveRight(tetrino.d);
                    MoveDown(tetrino.d);
                    MoveDown(tetrino.d);

                    tetrino.changeForm();
                    break;
                }
                break;

            case "l":
                if (f == 1 && cB(a, 1, -1) && cB(c, 1, 1) && cB(b, 2, 2)) 
                {
                    MoveRight(tetrino.a);
                    MoveDown(tetrino.a);
                    MoveUp(tetrino.c);
                    MoveRight(tetrino.c);
                    MoveUp(tetrino.b);
                    MoveUp(tetrino.b);
                    MoveRight(tetrino.b);
                    MoveRight(tetrino.b);

                    tetrino.changeForm();
                    break;
                }

                if (f == 2 && cB(a, -1, -1) && cB(b, 2, -2) && cB(c, 1, -1)) 
                {
                    MoveDown(tetrino.a);
                    MoveLeft(tetrino.a);
                    MoveRight(tetrino.b);
                    MoveRight(tetrino.b);
                    MoveDown(tetrino.b);
                    MoveDown(tetrino.b);
                    MoveRight(tetrino.c);
                    MoveDown(tetrino.c);

                    tetrino.changeForm();
                    break;
                }

                if (f == 3 && cB(a, -1, 1) && cB(c, -1, -1) && cB(b, -2, -2)) 
                {
                    MoveLeft(tetrino.a);
                    MoveUp(tetrino.a);
                    MoveDown(tetrino.c);
                    MoveLeft(tetrino.c);
                    MoveDown(tetrino.b);
                    MoveDown(tetrino.b);
                    MoveLeft(tetrino.b);
                    MoveLeft(tetrino.b);

                    tetrino.changeForm();
                    break;
                }

                if (f == 4 && cB(a, 1, 1) && cB(b, -2, 2) && cB(c, -1, 1)) 
                {
                    MoveUp(tetrino.a);
                    MoveRight(tetrino.a);
                    MoveLeft(tetrino.b);
                    MoveLeft(tetrino.b);
                    MoveUp(tetrino.b);
                    MoveUp(tetrino.b);
                    MoveLeft(tetrino.c);
                    MoveUp(tetrino.c);

                    tetrino.changeForm();
                    break;
                }
                break;

            case "o":
                break;

            case "s":
                if (f == 1 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, 0, 2)) 
                {
                    MoveDown(tetrino.a);
                    MoveLeft(tetrino.a);
                    MoveLeft(tetrino.c);
                    MoveUp(tetrino.c);
                    MoveUp(tetrino.d);
                    MoveUp(tetrino.d);

                    tetrino.changeForm();
                    break;
                }

                if (f == 2 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 0, -2)) 
                {
                    MoveUp(tetrino.a);
                    MoveRight(tetrino.a);
                    MoveRight(tetrino.c);
                    MoveDown(tetrino.c);
                    MoveDown(tetrino.d);
                    MoveDown(tetrino.d);

                    tetrino.changeForm();
                    break;
                }

                if (f == 3 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, 0, 2)) 
                {
                    MoveDown(tetrino.a);
                    MoveLeft(tetrino.a);
                    MoveLeft(tetrino.c);
                    MoveUp(tetrino.c);
                    MoveUp(tetrino.d);
                    MoveUp(tetrino.d);

                    tetrino.changeForm();
                    break;
                }

                if (f == 4 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 0, -2)) 
                {
                    MoveUp(tetrino.a);
                    MoveRight(tetrino.a);
                    MoveRight(tetrino.c);
                    MoveDown(tetrino.c);
                    MoveDown(tetrino.d);
                    MoveDown(tetrino.d);

                    tetrino.changeForm();
                    break;
                }
                break;

            case "t":
                if (f == 1 && cB(a, 1, 1) && cB(d, -1, -1) && cB(c, -1, 1)) 
                {
                    MoveUp(tetrino.a);
                    MoveRight(tetrino.a);
                    MoveDown(tetrino.d);
                    MoveLeft(tetrino.d);
                    MoveLeft(tetrino.c);
                    MoveUp(tetrino.c);

                    tetrino.changeForm();
                    break;
                }

                if (f == 2 && cB(a, 1, -1) && cB(d, -1, 1) && cB(c, 1, 1)) 
                {
                    MoveRight(tetrino.a);
                    MoveDown(tetrino.a);
                    MoveLeft(tetrino.d);
                    MoveUp(tetrino.d);
                    MoveUp(tetrino.c);
                    MoveRight(tetrino.c);

                    tetrino.changeForm();
                    break;
                }

                if (f == 3 && cB(a, -1, -1) && cB(d, 1, 1) && cB(c, 1, -1)) 
                {
                    MoveDown(tetrino.a);
                    MoveLeft(tetrino.a);
                    MoveUp(tetrino.d);
                    MoveRight(tetrino.d);
                    MoveRight(tetrino.c);
                    MoveDown(tetrino.c);

                    tetrino.changeForm();
                    break;
                }

                if (f == 4 && cB(a, -1, 1) && cB(d, 1, -1) && cB(c, -1, -1)) 
                {
                    MoveLeft(tetrino.a);
                    MoveUp(tetrino.a);
                    MoveRight(tetrino.d);
                    MoveDown(tetrino.d);
                    MoveDown(tetrino.c);
                    MoveLeft(tetrino.c);

                    tetrino.changeForm();
                    break;
                }
                break;

            case "z":
                if (f == 1 && cB(b, 1, 1) && cB(c, -1, 1) && cB(d, -2, 0)) 
                {
                    MoveUp(tetrino.b);
                    MoveRight(tetrino.b);
                    MoveLeft(tetrino.c);
                    MoveUp(tetrino.c);
                    MoveLeft(tetrino.d);
                    MoveLeft(tetrino.d);

                    tetrino.changeForm();
                    break;
                }

                if (f == 2 && cB(b, -1, -1) && cB(c, 1, -1) && cB(d, 2, 0)) 
                {
                    MoveDown(tetrino.b);
                    MoveLeft(tetrino.b);
                    MoveRight(tetrino.c);
                    MoveDown(tetrino.c);
                    MoveRight(tetrino.d);
                    MoveRight(tetrino.d);

                    tetrino.changeForm();
                    break;
                }

                if (f == 3 && cB(b, 1, 1) && cB(c, -1, 1) && cB(d, -2, 0)) 
                {
                    MoveUp(tetrino.b);
                    MoveRight(tetrino.b);
                    MoveLeft(tetrino.c);
                    MoveUp(tetrino.c);
                    MoveLeft(tetrino.d);
                    MoveLeft(tetrino.d);

                    tetrino.changeForm();
                    break;
                }

                if (f == 4 && cB(b, -1, -1) && cB(c, 1, -1) && cB(d, 2, 0)) 
                {
                    MoveDown(tetrino.b);
                    MoveLeft(tetrino.b);
                    MoveRight(tetrino.c);
                    MoveDown(tetrino.c);
                    MoveRight(tetrino.d);
                    MoveRight(tetrino.d);

                    tetrino.changeForm();
                    break;
                }
                break;

            case "i":
                if (f == 1 && cB(a, 2, 2) && cB(b, 1, 1) && cB(d, -1, -1)) 
                {
                    MoveUp(tetrino.a);
                    MoveUp(tetrino.a);
                    MoveRight(tetrino.a);
                    MoveRight(tetrino.a);
                    MoveUp(tetrino.b);
                    MoveRight(tetrino.b);
                    MoveDown(tetrino.d);
                    MoveLeft(tetrino.d);

                    tetrino.changeForm();
                    break;
                }

                if (f == 2 && cB(a, -2, -2) && cB(b, -1, -1) && cB(d, 1, 1)) 
                {
                    MoveDown(tetrino.a);
                    MoveDown(tetrino.a);
                    MoveLeft(tetrino.a);
                    MoveLeft(tetrino.a);
                    MoveDown(tetrino.b);
                    MoveLeft(tetrino.b);
                    MoveUp(tetrino.d);
                    MoveRight(tetrino.d);

                    tetrino.changeForm();
                    break;
                }

                if (f == 3 && cB(a, 2, 2) && cB(b, 1, 1) && cB(d, -1, -1)) 
                {
                    MoveUp(tetrino.a);
                    MoveUp(tetrino.a);
                    MoveRight(tetrino.a);
                    MoveRight(tetrino.a);
                    MoveUp(tetrino.b);
                    MoveRight(tetrino.b);
                    MoveDown(tetrino.d);
                    MoveLeft(tetrino.d);

                    tetrino.changeForm();
                    break;
                }

                if (f == 4 && cB(a, -2, -2) && cB(b, -1, -1) && cB(d, 1, 1)) 
                {
                    MoveDown(tetrino.a);
                    MoveDown(tetrino.a);
                    MoveLeft(tetrino.a);
                    MoveLeft(tetrino.a);
                    MoveDown(tetrino.b);
                    MoveLeft(tetrino.b);
                    MoveUp(tetrino.d);
                    MoveRight(tetrino.d);

                    tetrino.changeForm();
                    break;
                }
                break;
            }
    }

    private void RemoveRows(Pane pane) 
    {
        ArrayList<Node> rects = new ArrayList<Node>();
        ArrayList<Integer> lines = new ArrayList<Integer>();
        ArrayList<Node> newrects = new ArrayList<Node>();
        int full = 0;
        for (int i = 0; i < GAME_GRID_VIEW[0].length; i++) 
        {
            for (int j = 0; j < GAME_GRID_VIEW.length; j++) 
            {
                if (GAME_GRID_VIEW[j][i] == 1)
                {
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
            for (Node node : pane.getChildren()) 
            {
                if (node instanceof Rectangle)
                {
                    rects.add(node);
                }
            }
            score += 50;
            linesNo++;

            for (Node node : rects) 
            {
                Rectangle a = (Rectangle) node;
                if (a.getY() == lines.get(0) * PLAYER_MOVE_SIZE) 
                {
                    GAME_GRID_VIEW[(int) a.getX() / PLAYER_MOVE_SIZE][(int) a.getY() / PLAYER_MOVE_SIZE] = 0;
                    pane.getChildren().remove(node);
                } else {
                    newrects.add(node);
                }
            }

            for (Node node : newrects) 
            {
                Rectangle a = (Rectangle) node;
                if (a.getY() < lines.get(0) * PLAYER_MOVE_SIZE) 
                {
                    GAME_GRID_VIEW[(int) a.getX() / PLAYER_MOVE_SIZE][(int) a.getY() / PLAYER_MOVE_SIZE] = 0;
                    a.setY(a.getY() + PLAYER_MOVE_SIZE);
                }
            }

            lines.remove(0);
            rects.clear();
            newrects.clear();

            for (Node node : pane.getChildren()) 
            {
                if (node instanceof Rectangle)
                {
                    rects.add(node);
                }
            }

            for (Node node : rects) 
            {
                Rectangle a = (Rectangle) node;
                try {
                        GAME_GRID_VIEW[(int) a.getX() / PLAYER_MOVE_SIZE][(int) a.getY() / PLAYER_MOVE_SIZE] = 1;
                } catch (ArrayIndexOutOfBoundsException e) 
                {
                }
            }
            rects.clear();
        } while (lines.size() > 0);
    }

    private void MoveDown(Rectangle rect) 
    {
        if (rect.getY() + PLAYER_MOVE < MAX_HEIGHT)rect.setY(rect.getY() + PLAYER_MOVE);

    }

    private void MoveRight(Rectangle rect) 
    {
        if (rect.getX() + PLAYER_MOVE <= MAX_WIDTH - PLAYER_MOVE_SIZE)rect.setX(rect.getX() + PLAYER_MOVE);
    }

    private void MoveLeft(Rectangle rect) 
    {
        if (rect.getX() - PLAYER_MOVE >= 0)rect.setX(rect.getX() - PLAYER_MOVE);
    }

    private void MoveUp(Rectangle rect) 
    {
        if (rect.getY() - PLAYER_MOVE > 0)rect.setY(rect.getY() - PLAYER_MOVE);
    }

    private void MoveDown(Tetrino tetrino) 
    {
        if(
            tetrino.a.getY() == MAX_HEIGHT - PLAYER_MOVE_SIZE || 
            tetrino.b.getY() == MAX_HEIGHT - PLAYER_MOVE_SIZE || 
            tetrino.c.getY() == MAX_HEIGHT - PLAYER_MOVE_SIZE || 
            tetrino.d.getY() == MAX_HEIGHT - PLAYER_MOVE_SIZE || 

            moveA(tetrino) || 
            moveB(tetrino) || 
            moveC(tetrino) || 
            moveD(tetrino)
           ) 
        {
            GAME_GRID_VIEW[(int) tetrino.a.getX() / PLAYER_MOVE_SIZE][(int) tetrino.a.getY() / PLAYER_MOVE_SIZE] = 1;
            GAME_GRID_VIEW[(int) tetrino.b.getX() / PLAYER_MOVE_SIZE][(int) tetrino.b.getY() / PLAYER_MOVE_SIZE] = 1;
            GAME_GRID_VIEW[(int) tetrino.c.getX() / PLAYER_MOVE_SIZE][(int) tetrino.c.getY() / PLAYER_MOVE_SIZE] = 1;
            GAME_GRID_VIEW[(int) tetrino.d.getX() / PLAYER_MOVE_SIZE][(int) tetrino.d.getY() / PLAYER_MOVE_SIZE] = 1;
            RemoveRows(group);

            Tetrino a = nextObj;
            nextObj = Controller.makeRect();
            object = a;
            group.getChildren().addAll(a.a, a.b, a.c, a.d);
            moveOnKeyPress(a);
        }

        if (
            tetrino.a.getY() + PLAYER_MOVE < MAX_HEIGHT && 
            tetrino.b.getY() + PLAYER_MOVE < MAX_HEIGHT && 
            tetrino.c.getY() + PLAYER_MOVE < MAX_HEIGHT && 
            tetrino.d.getY() + PLAYER_MOVE < MAX_HEIGHT
           ) 
        {
            int movea = GAME_GRID_VIEW[(int) tetrino.a.getX() / PLAYER_MOVE_SIZE][((int) tetrino.a.getY() / PLAYER_MOVE_SIZE) + 1];
            int moveb = GAME_GRID_VIEW[(int) tetrino.b.getX() / PLAYER_MOVE_SIZE][((int) tetrino.b.getY() / PLAYER_MOVE_SIZE) + 1];
            int movec = GAME_GRID_VIEW[(int) tetrino.c.getX() / PLAYER_MOVE_SIZE][((int) tetrino.c.getY() / PLAYER_MOVE_SIZE) + 1];
            int moved = GAME_GRID_VIEW[(int) tetrino.d.getX() / PLAYER_MOVE_SIZE][((int) tetrino.d.getY() / PLAYER_MOVE_SIZE) + 1];

            if (movea == 0 && movea == moveb && moveb == movec && movec == moved)
            {
                tetrino.a.setY(tetrino.a.getY() + PLAYER_MOVE);
                tetrino.b.setY(tetrino.b.getY() + PLAYER_MOVE);
                tetrino.c.setY(tetrino.c.getY() + PLAYER_MOVE);
                tetrino.d.setY(tetrino.d.getY() + PLAYER_MOVE);
            }
        }
    }

    private boolean moveA(Tetrino tetrino) 
    {
        return (GAME_GRID_VIEW[(int) tetrino.a.getX() / PLAYER_MOVE_SIZE][((int) tetrino.a.getY() / PLAYER_MOVE_SIZE) + 1] == 1);
    }

    private boolean moveB(Tetrino tetrino) 
    {
        return (GAME_GRID_VIEW[(int) tetrino.b.getX() / PLAYER_MOVE_SIZE][((int) tetrino.b.getY() / PLAYER_MOVE_SIZE) + 1] == 1);
    }

    private boolean moveC(Tetrino tetrino)
    {
        return (GAME_GRID_VIEW[(int) tetrino.c.getX() / PLAYER_MOVE_SIZE][((int) tetrino.c.getY() / PLAYER_MOVE_SIZE) + 1] == 1);
    }

    private boolean moveD(Tetrino tetrino) 
    {
        return (GAME_GRID_VIEW[(int) tetrino.d.getX() / PLAYER_MOVE_SIZE][((int) tetrino.d.getY() / PLAYER_MOVE_SIZE) + 1] == 1);
    }

    private boolean cB(Rectangle rect, int x, int y) 
    {
        boolean xb = false;
        boolean yb = false;
        if (x >= 0)
                xb = rect.getX() + x * PLAYER_MOVE <= MAX_WIDTH - PLAYER_MOVE_SIZE;
        if (x < 0)
                xb = rect.getX() + x * PLAYER_MOVE >= 0;
        if (y >= 0)
                yb = rect.getY() - y * PLAYER_MOVE > 0;
        if (y < 0)
                yb = rect.getY() + y * PLAYER_MOVE < MAX_HEIGHT;
        return xb && yb && GAME_GRID_VIEW[((int) rect.getX() / PLAYER_MOVE_SIZE) + x][((int) rect.getY() / PLAYER_MOVE_SIZE) - y] == 0;
    }
}