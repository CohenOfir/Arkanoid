package game;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import interfaces.Animation;

/**
 * PauseScreen Class.
 * Author - Ofir Cohen.
 */
public class PauseScreen implements Animation {

    private KeyboardSensor keyboard;
    private boolean stop;

    /**
     * Constructor.
     *
     * @param k KeyboardSensor.
     */
    public PauseScreen(KeyboardSensor k) {
        this.keyboard = k;
        this.stop = false;
    }

    /**
     *
     * @param d  drawSurface  given from AnimationRunner.
     */
    public void doOneFrame(DrawSurface d) {
        d.drawText(d.getWidth() / 5, d.getHeight() / 2, "paused -- press space to continue", 32);
    }

    /**
     *
     * @return Boolean needToStop.
     */
    public boolean shouldStop() {
        return this.stop;
    }
}
