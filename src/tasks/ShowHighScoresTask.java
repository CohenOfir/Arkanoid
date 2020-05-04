package tasks;

import animations.AnimationRunner;
import assistclasses.KeyPressStoppableAnimation;
import biuoop.KeyboardSensor;
import interfaces.Animation;
import interfaces.Task;

/**
 * ShowHighScoresTask Class.
 * Author - Ofir Cohen.
 */
public class ShowHighScoresTask implements Task<Void> {

    private AnimationRunner runner;
    private Animation highScoresAnimation;
    private KeyboardSensor keyboard;

    /**
     * Constructor.
     *
     * @param ar              Animation Runner..
     * @param highScoresAnimation Animation to run.
     * @param keyboard            keyboard.
     */
    public ShowHighScoresTask(AnimationRunner ar, Animation highScoresAnimation, KeyboardSensor keyboard) {
        this.runner = ar;
        this.keyboard = keyboard;
        this.highScoresAnimation = highScoresAnimation;
    }

    /**
     * runs Animation.
     *
     * @return null.
     */
    public Void run() {
        this.runner.run(new KeyPressStoppableAnimation(keyboard, keyboard.SPACE_KEY, this.highScoresAnimation));
        return null;
    }

}
