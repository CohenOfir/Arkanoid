package spritesandcollidables;

import assistclasses.Velocity;
import biuoop.DrawSurface;
import game.GameLevel;
import geometricshapes.Ball;
import geometricshapes.Line;
import geometricshapes.Point;
import geometricshapes.Rectangle;
import interfaces.HitListener;
import interfaces.Collidable;
import interfaces.HitNotifier;
import interfaces.Sprite;

import java.awt.Image;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * Represent a Ball.
 *
 * @author Ofir Cohen
 */
public class Block implements Collidable, Sprite, HitNotifier {

    private Rectangle rectangle;
    private int lifeSpan;
    private java.awt.Color color;
    private List<HitListener> hitListeners;
    private Color strikeColor;
    private Map<Integer, Image> imageMap;
    private Map<Integer, Color> colorMap;

    /**
     * @return rectangle representing the block.
     */
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    /**
     * constructor.
     *
     * @param upperLeft - Point start of the rectangle.
     * @param width     - rectangle width.
     * @param wide      - rectangle wide.
     */
    public Block(Point upperLeft, double width, double wide) {

        this.rectangle = new Rectangle(upperLeft, width, wide);
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * constructor.
     *
     * @param upperLeft - Point start of the rectangle.
     * @param width     - rectangle width.
     * @param wide      - rectangle wide.
     * @param lifeSpan  - blocks life count.
     * @param color     - blocks color.
     */
    public Block(Point upperLeft, double width, double wide, int lifeSpan, java.awt.Color color) {
        this.rectangle = new Rectangle(upperLeft, width, wide);
        this.lifeSpan = lifeSpan;
        this.color = color;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * constructor of this class.
     *
     * @param rectangle represent the block.
     * @param color     specifies the color of the block
     */
    public Block(Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.color = color;
        this.lifeSpan = 1;
        //an array that contains all the spritesandcollidables.Block that want to be notified of hit events
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * @param rectangle - rectangle represents the block.
     * @param color     - blocks color.
     * @param lifeSpan  - blocks life count.
     */
    public Block(Rectangle rectangle, Color color, int lifeSpan) {
        this.rectangle = rectangle;
        this.color = color;
        this.lifeSpan = lifeSpan;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     *
     * @param rectangle - rectangle.
     * @param imageMap - image to display in block.
     * @param colorMap - specifies color of the block.
     * @param lifeSpan - number of hit points.
     */
    public Block(Rectangle rectangle, Map<Integer, Image> imageMap, Map<Integer, Color> colorMap, int lifeSpan) {
        this.rectangle = rectangle;
        this.imageMap = imageMap;
        this.colorMap = colorMap;
        this.lifeSpan = lifeSpan;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * @param strikecolor - color to add.
     */
    public void addColor(Color strikecolor) {
        this.strikeColor = strikecolor;
    }

    /**
     * @param gameLevel - GameLevel to add current block into.
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
        gameLevel.addCollidable(this);
    }

    /**
     * Function draw balls on draw surface.
     *
     * @param surface - draw surface for drawing.
     */
    public void drawOn(DrawSurface surface) {
        if (imageMap != null && imageMap.containsKey(lifeSpan)) {
            Image rectImage = imageMap.get(lifeSpan);
            surface.drawImage((int) this.rectangle.getUpperLeft().getX(),
                    (int) this.rectangle.getUpperLeft().getY(), rectImage);

        } else if (colorMap != null && colorMap.containsKey(lifeSpan)) {
            Color fillColor = colorMap.get(lifeSpan);
            surface.setColor(fillColor);
            surface.fillRectangle((int) this.rectangle.getUpperLeft().getX(),
                    (int) this.rectangle.getUpperLeft().getY(),
                    (int) this.rectangle.getWidth(), (int) this.rectangle.getHeight());
            if (strikeColor != null) {
                surface.setColor(strikeColor);
            }
            surface.drawRectangle((int) this.rectangle.getUpperLeft().getX(),
                    (int) this.rectangle.getUpperLeft().getY(),
                    (int) this.rectangle.getWidth(), (int) this.rectangle.getHeight());
        } else {
            if (colorMap != null && colorMap.containsKey(1)) {
                Color fillColor = colorMap.get(1);
                surface.setColor(fillColor);
                surface.fillRectangle((int) this.rectangle.getUpperLeft().getX(),
                        (int) this.rectangle.getUpperLeft().getY(),
                        (int) this.rectangle.getWidth(), (int) this.rectangle.getHeight());
                surface.setColor(Color.BLACK);
                surface.drawRectangle((int) this.rectangle.getUpperLeft().getX(),
                        (int) this.rectangle.getUpperLeft().getY(),
                        (int) this.rectangle.getWidth(), (int) this.rectangle.getHeight());
            } else if (imageMap != null && imageMap.containsKey(1)) {
                Image rectImage = imageMap.get(1);
                surface.drawImage((int) this.rectangle.getUpperLeft().getX(),
                        (int) this.rectangle.getUpperLeft().getY(), rectImage);
            } else {
                surface.setColor(this.color);
                surface.fillRectangle((int) this.rectangle.getUpperLeft().getX(),
                        (int) this.rectangle.getUpperLeft().getY(),
                        (int) this.rectangle.getWidth(), (int) this.rectangle.getHeight());
            }
        }
    }

    /**
     * currently noting, later on.
     */
    public void timePassed() {

    }

    /**
     * Function determines new Velocity for ball after block hitting.
     *
     * @param collisionPoint  - collision point to check intersection with.
     * @param currentVelocity - current ball velocity.
     * @param hitter          - ball participant in the hit event.
     * @return new velocity, according to hit position on the block.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Velocity newVelocity = currentVelocity;
        Line upLine = this.rectangle.getUpperLine();
        Line bottomLine = this.rectangle.getLowerLine();
        Line rightLine = this.rectangle.getRightLine();
        Line leftLine = this.rectangle.getLeftLine();


        //corner hits
        /*
        if ((upLine.isPointOnLine(collisionPoint) || bottomLine.isPointOnLine(collisionPoint))
                && ((rightLine.isPointOnLine(collisionPoint)) || (leftLine.isPointOnLine(collisionPoint)))) {
            return (new Velocity((-currentVelocity.getDx()), (-currentVelocity.getDy())));
        }*/

        //up or down hit
        if (upLine.isPointOnLine(collisionPoint)) {
            if (currentVelocity.getDy() >= 0 || collisionPoint.getY() == 0) {
                newVelocity = new Velocity(currentVelocity.getDx(), -(currentVelocity.getDy()));
            }
            //currentVelocity = new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        }
        if (bottomLine.isPointOnLine(collisionPoint)) {
            if (currentVelocity.getDy() <= 0 || collisionPoint.getY() == 600) {
                newVelocity = new Velocity(currentVelocity.getDx(), -(currentVelocity.getDy()));
            }
        }
        //right or left hit
        if (rightLine.isPointOnLine(collisionPoint)) {
            if (currentVelocity.getDx() <= 0 || collisionPoint.getX() == 800) {
                newVelocity = new Velocity(-(currentVelocity.getDx()), currentVelocity.getDy());
            }
        }
        if (leftLine.isPointOnLine(collisionPoint)) {
            if (currentVelocity.getDx() >= 0 || collisionPoint.getX() == 0) {
                newVelocity = new Velocity(-(currentVelocity.getDx()), currentVelocity.getDy());
            }
        }
        this.lifeSpan--;
        this.notifyHit(hitter);
        return newVelocity;
    }

    /**
     * @param hitter - ball involved the hit.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitlisteners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * @param gameLevel - gameLevel to remove block from.
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeCollidable(this);
        gameLevel.removeSprite(this);
    }

    /**
     * @param hl an Object that is notified of hit event.
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * @param hl an Object that is notified of hit event.
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * @return - number of hit points left for block.
     */
    public int getHitPoints() {
        return this.lifeSpan;
    }

    /**
     *
     * @return rectangle width.
     */
    public double getWidth() {
        return this.rectangle.getWidth();
    }
}
