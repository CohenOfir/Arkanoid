package indicators;

import assistclasses.Counter;
import biuoop.DrawSurface;
import game.GameLevel;
import interfaces.Sprite;

import java.awt.Color;

/**
 * LivesIndicator Class.
 * author - Ofir Cohen.
 */
public class LivesIndicator implements Sprite {
    //where to Print
    public static final int TEXT_HEIGHT = 20;
    public static final int TEXT_WIDTH = 100;
    private Counter livesCounter;

    /**
     * Constructor.
     *
     * @param livesCounter counts the player's lives
     */
    public LivesIndicator(Counter livesCounter) {
        this.livesCounter = livesCounter;
    }

    /**
     * @param d - the DrawSurface of the GUI that was created.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText(TEXT_WIDTH, TEXT_HEIGHT, "Lives: " + Integer.toString(this.livesCounter.getValue()), 20);
    }

    /**
     * notify the sprite that time has passed.
     */
    public void timePassed() {

    }

    /**
     * @param g - the GemeLevel that we want to add this LivesIndicator to.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

}
