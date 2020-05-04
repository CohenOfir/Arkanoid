package indicators;

import assistclasses.Counter;
import biuoop.DrawSurface;
import game.GameLevel;
import interfaces.Sprite;

import java.awt.Color;

/**
 * ScoreIndicator Class.
 * author - Ofir Cohen.
 */
public class ScoreIndicator implements Sprite {
    public static final int START_COORDINATE = 0;
    public static final int HEIGHT = 25;
    public static final int WIDTH = 800;
    public static final int TEXT_HEIGHT = 20;
    public static final int TEXT_WIDTH = 300;

    //member
    private Counter score;

    /**
     * Constructor.
     *
     * @param score indicates the player's Score
     */
    public ScoreIndicator(Counter score) {
        this.score = score;
    }

    /**
     * @param d - Gui's DrawSurface.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.WHITE);
        d.fillRectangle(START_COORDINATE, START_COORDINATE, WIDTH, HEIGHT);
        d.setColor(Color.BLACK);
        d.drawText(TEXT_WIDTH, TEXT_HEIGHT, "Score: " + Integer.toString(this.score.getValue()), 20);

    }

    /**
     * notify time has passed.
     */
    public void timePassed() {

    }

    /**
     * this function adds this ScoreIndicator to the given GemeLevel.
     *
     * @param g - the GemeLevel that we want to add this ScoreIndicator to.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
