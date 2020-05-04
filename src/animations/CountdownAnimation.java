package animations;

import biuoop.DrawSurface;
import interfaces.Animation;
import interfaces.LevelInformation;
import spritesandcollidables.SpriteCollection;

import java.awt.Color;

/**
 * CountdownAnimation Class.
 * Author - Ofir Cohen.
 */
public class CountdownAnimation implements Animation {

    private double numOfSeconds;
    private int countFrom;
    private SpriteCollection sprites;
    private boolean needToStop;
    private int counter;
    private LevelInformation levelInfo;

    /**
     * Constructor.
     *
     * @param numOfSeconds seconds animation will last
     * @param countFrom    from which number to countdown
     * @param sprites      all the Sprites of the GameLevel.
     * @param levelInfo    describes LevelInformation of each level.
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection sprites,
                              LevelInformation levelInfo) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.sprites = sprites;
        this.needToStop = false;
        this.counter = 0;
        this.levelInfo = levelInfo;
    }

    /**
     * @param d  drawSurface given from the AnimationRunner
     */
    public void doOneFrame(DrawSurface d) {
        biuoop.Sleeper sleeper = new biuoop.Sleeper();
        levelInfo.getBackground().drawOn(d);
        sprites.drawAllOn(d);
        if (levelInfo.levelName().equals("Direct Hit")) {
            d.setColor(Color.WHITE);
        }
        if (counter == 0) {
            d.drawText(d.getWidth() / 2, d.getHeight() / 2, "3", 50);
        } else if (counter == 1) {
            d.drawText(d.getWidth() / 2, d.getHeight() / 2, "2", 50);
            sleeper.sleepFor(1000);
        } else if (counter == 2) {
            d.drawText(d.getWidth() / 2, d.getHeight() / 2, "1", 50);
            sleeper.sleepFor(1000);
        } else if (counter == 3) {
            d.drawText(d.getWidth() / 2, d.getHeight() / 2, "GO", 50);
            sleeper.sleepFor(1000);
        } else {
            this.needToStop = true;
            sleeper.sleepFor(1000);
        }
        counter++;
    }

    /**
     *
     * @return Boolean needToStop.
     */
    public boolean shouldStop() {
        return needToStop;
    }
}
