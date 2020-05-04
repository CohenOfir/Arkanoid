package animations;

import biuoop.DrawSurface;
import biuoop.GUI;
import interfaces.Animation;

/**
 * AnimationRunner Class.
 * Author - Ofir Cohen.
 */
public class AnimationRunner {

    private GUI gui;
    private int framesPerSecond;

    /**
     * Constructor.
     *
     * @param gui             GUI.
     * @param framesPerSecond determines frames per second.
     */
    public AnimationRunner(GUI gui, int framesPerSecond) {
        this.gui = gui;
        this.framesPerSecond = framesPerSecond;
    }

    /**
     *
     * @param animation - given Animation.
     */
    public void run(Animation animation) {
        biuoop.Sleeper sleeper = new biuoop.Sleeper();
        int millisecondsPerFrame = 1000 / this.framesPerSecond;
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            animation.doOneFrame(d);
            gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

    /**
     *
     * @return gui
     */
    public GUI getGui() {
        return gui;
    }
}
