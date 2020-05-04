package assistclasses;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import interfaces.Animation;

/**
 * KeyPressStoppableAnimation Class.
 * Author - Ofir Cohen.
 */
public class KeyPressStoppableAnimation implements Animation {

    private KeyboardSensor sensor;
    private String key;
    private Animation animation;
    private boolean stop;
    private boolean isAlreadyPressed;

    /**
     * Constructor.
     *
     * @param sensor    KeyboardSensor
     * @param key       describes the key that stops the Animation
     * @param animation the Animation we want to stop.
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.sensor = sensor;
        this.key = key;
        this.animation = animation;
        this.stop = false;
        this.isAlreadyPressed = true;
    }

    /**
     * @param d - drawsurface given from the AnimationRunner
     */
    public void doOneFrame(DrawSurface d) {
        this.animation.doOneFrame(d);
        if (this.sensor.isPressed(key) && !(isAlreadyPressed)) {
            this.stop = true;
        } else if (!this.sensor.isPressed(key)) {
            this.isAlreadyPressed = false;
        }
    }

    /**
     * @return Boolean - true if animation should stop, false otherwise.
     */
    public boolean shouldStop() {
        return this.stop;
    }
}
