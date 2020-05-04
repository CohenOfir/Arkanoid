package interfaces;

import biuoop.DrawSurface;

/**
 * Describes Animation interface.
 */
public interface Animation {

    /**
     * @param d - DrawSurface given from AnimationRunner.
     */
    void doOneFrame(DrawSurface d);

    /**
     * @return Boolean value - determines when to stop the animation.
     */
    boolean shouldStop();
}