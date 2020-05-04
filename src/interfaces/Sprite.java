package interfaces;

import biuoop.DrawSurface;
/**
 * Describes Sprite interface.
 *
 * @author Ofir Cohen
 */
public interface Sprite {
    /**
     *
     * @param d - draw surface to be drawing on.
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed.
     */
    void timePassed();
}
