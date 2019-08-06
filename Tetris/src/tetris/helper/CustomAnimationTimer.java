/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris.helper;

import javafx.animation.AnimationTimer;

/**
 *
 * @author Neville Bulmer
 */
public abstract class CustomAnimationTimer extends AnimationTimer
{
    private long sleepNs = 0;

    long prevTime = 0;

    public CustomAnimationTimer( long sleepMs)
    {
        this.sleepNs = sleepMs * 1_000_000;
    }

    @Override
    public void handle(long now)
    {
        if ((now - prevTime) < sleepNs) 
        {
            return;
        }

        prevTime = now;

        handle();
    }

    public abstract void handle();
}
