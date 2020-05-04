package backgrounds;

import biuoop.DrawSurface;
import interfaces.Sprite;

import java.awt.Color;
import java.awt.Image;

/**
 *
 * LevelBackground Class.
 * Author - Ofir Cohen.
 */
public class LevelBackground implements Sprite {
    private Color color;
    private Image image;

    /**
     * constructor.
     *
     * @param color color
     * @param image image
     */
    public LevelBackground(Color color, Image image) {
        this.color = color;
        this.image = image;
    }

    /**
     *
     * @param d - GUI's DrawSurface.
     */
    public void drawOn(DrawSurface d) {
        if (this.image != null) {
            d.drawImage(0, 0, this.image);
        } else {
            d.setColor(color);
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        }

    }

    /**
     * notify the sprite that time has passed.
     */
    public void timePassed() {

    }

}
