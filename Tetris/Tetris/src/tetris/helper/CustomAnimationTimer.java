/*
 * Class used to create a custom animation timer for the mane games run time.
 */

package tetris.helper;

import javafx.animation.AnimationTimer;

/**
 *
 * @author Neville Bulmer
 */
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
