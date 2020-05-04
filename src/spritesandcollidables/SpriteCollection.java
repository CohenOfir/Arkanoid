package spritesandcollidables;

import biuoop.DrawSurface;
import interfaces.Sprite;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a SpriteCollection.
 * author - Ofir Cohen.
 */
public class SpriteCollection {

    private List<Sprite> spritesList;

    /**
     * Constructor.
     */
    public SpriteCollection() {
        this.spritesList = new ArrayList<Sprite>();
    }

    /**
     * @return Sprites list.
     */
    public List<Sprite> getSpritesList() {
        return this.spritesList;
    }

    /**
     * @param s sprite object to add to sprite list.
     */
    public void addSprite(Sprite s) {
        spritesList.add(s);
    }

    /**
     * call timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        List<Sprite> spriteList = new ArrayList<Sprite>(this.spritesList);
        for (Sprite spriteObject : spriteList) {
            spriteObject.timePassed();
        }
    }

    /**
     * @param d - draw surface to be drawing on.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite spriteObject : spritesList) {
            spriteObject.drawOn(d);
        }
    }
}