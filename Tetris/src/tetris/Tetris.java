package tetris;

import javafx.application.Application;
import javafx.stage.Stage;
import tetris.view.MenuViewManager;

public class Tetris extends Application 
{
    @Override
    public void start(Stage primaryStage) 
    {
        // Initialise the manager class.
        MenuViewManager manager = new MenuViewManager();
        // Set the primary stage equal to the manager classes get main stage method.
        primaryStage = manager.getMainStage();
        // Show the primary stage
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
    }
}