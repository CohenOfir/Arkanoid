package indicators;

import game.GameLevel;
import biuoop.DrawSurface;
import interfaces.LevelInformation;
import interfaces.Sprite;

import java.awt.Color;

/**
 * indicates the level's name
 * Author - Ofir cohen.
 */
public class LevelNameIndicator implements Sprite {

    //where to Print
    public static final int TEXT_HEIGHT = 20;
    public static final int TEXT_WIDTH = 500;
    private LevelInformation levelInfo;

    /**
     * Constructor.
     *
     * @param levelInfo - LevelInformation.
     */
    public LevelNameIndicator(LevelInformation levelInfo) {
        this.levelInfo = levelInfo;
    }

    /**
     *
     * @param d - the DrawSurface of the GUI.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText(TEXT_WIDTH, TEXT_HEIGHT, "Level Name: " + levelInfo.levelName(), 20);
    }

    /**
     * notify the sprite that time has passed.
     */
    public void timePassed() {

    }

    /**
     *
     * @param g -  GemeLevel to be adding to.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

}
