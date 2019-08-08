# JavaFX-Tetris
Tetris built using JavaFX, no third party libraries needed.

Tetris, the classic game in which the player moves blocks down the screen with the aim of completing lines.

This version is very basic in terms of the user interface, with that said it has been built in such a way that within the code each block of the tetrino, I.e. four blocks per tetrino, is minipulatable.

More updates coming soon.

# Update One 
Game menu has been added along with the beingings of a re-written architecture, redesign of the game menu and the game view.

# Update Two
Created a custom animation timer class to replace the older method of using a timer in conjunction with a timer task, this allows the ability to speed up the game at certain intervals I.e. level 5 - 10 - 15 - 20.

The custom animation class allow us to use the AnimationTimer built in to JavaFX in hand with cotrollable time in milliseconds.

```
public abstract class CustomAnimationTimer extends AnimationTimer
{
    // Duration to sleep in nano seconds.
    private long sleepNs = 0;
    // Previouse time.
    long prevTime = 0;

    // Constructor, which take the sleep in annos seconds as a variable.
    public CustomAnimationTimer(long sleepMs)
    {
        // Instantiates the sleep in nano seconds to 1 seconds.
        this.sleepNs = sleepMs * 1_000_000;
    }

    // Handle mehod.
    @Override
    public void handle(long now)
    {
        // If now is less than previouse time, less than sleep in nano seconds.
        if ((now - prevTime) < sleepNs) 
        { 
            // Return.
            return;
        }
        
        // Set previouse time equal to now.
        prevTime = now;

        // Call handle.
        handle();
    }

    public abstract void handle();
}
```

# Update Three
Updated the game menu to show more options, updated the game architecture to allow more in-game flexibility, a lot of quality of life fixes and updates.

Implemented up next feature, this feature shows the peice which will be generated next.

Improved the code from feature update two.
