package spritesandcollidables;

import assistclasses.Velocity;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.GameLevel;
import geometricshapes.Ball;
import geometricshapes.Line;
import geometricshapes.Point;
import geometricshapes.Rectangle;
import interfaces.Collidable;
import interfaces.Sprite;

import java.awt.Color;

/**
 * Represent a Paddle.
 *
 * @author Ofir Cohen
 */
public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Color color;
    private Rectangle rectangle;
    private double velocity;
    private double leftBorder;
    private double rightBorder;

    /**
     * @param keyboard    - reading keys from keyboard
     * @param color       - Color of the Paddle.
     * @param rectangle   - Rectangle that describes the Paddle.
     * @param velocity    - paddle speed.
     * @param leftBorder  - X coordinate which the paddle cant pass from the left.
     * @param rightBorder - X coordinate which the paddle cant pass from the right.
     */
    public Paddle(KeyboardSensor keyboard, Color color, Rectangle rectangle, double velocity,
                  double leftBorder, double rightBorder) {
        Point upperLeft = new Point(rectangle.getUpperLeft().getX(), rectangle.getUpperLeft().getY());
        this.keyboard = keyboard;
        this.color = color;
        this.velocity = velocity;
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
        this.rectangle = new Rectangle(upperLeft, rectangle.getWidth(), rectangle.getHeight());
    }

    /**
     * moving the paddle to the left.
     */
    public void moveLeft() {
        Point newUpperLeft;
        double velocitySlower = (velocity / 60);
        if (this.rectangle.getUpperLeft().getX() - velocitySlower < this.leftBorder) {
            newUpperLeft = new Point(this.leftBorder, this.rectangle.getUpperLeft().getY());
            this.rectangle.setUpperLeft(newUpperLeft);
        } else {
            newUpperLeft = new Point(this.rectangle.getUpperLeft().getX() - velocitySlower,
                    this.rectangle.getUpperLeft().getY());
            this.rectangle.setUpperLeft(newUpperLeft);
        }
    }

    /**
     * moving the paddle to the right.
     **/
    public void moveRight() {
        Point newUpperLeft;
        double velocitySlower = (velocity / 60);
        if (this.rectangle.getUpperRight().getX() + velocitySlower > this.rightBorder) {
            newUpperLeft = new Point(this.rightBorder - this.rectangle.getWidth(),
                    this.rectangle.getUpperLeft().getY());
            this.rectangle.setUpperLeft(newUpperLeft);
        } else {
            newUpperLeft = new Point(this.rectangle.getUpperLeft().getX() + velocitySlower,
                    this.rectangle.getUpperLeft().getY());
            this.rectangle.setUpperLeft(newUpperLeft);
        }
    }

    /**
     *
     */
    public void timePassed() {
        //if the right arrow in the keyboard is pressed
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight();
        }
        //if the left arrow in the keyboard is pressed
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft();
        }
    }

    /**
     * drawing the paddle and its lines.
     *
     * @param d - the DrawSurface of the GUI of the GameLevel create.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) this.rectangle.getUpperLeft().getX(), (int) this.rectangle.getUpperLeft().getY(),
                (int) this.rectangle.getWidth(), (int) this.rectangle.getHeight());
        d.setColor(Color.BLACK);
        d.drawRectangle((int) this.rectangle.getUpperLeft().getX(), (int) this.rectangle.getUpperLeft().getY(),
                (int) this.rectangle.getWidth(), (int) this.rectangle.getHeight());

    }

    /**
     * @return rectangle representing the block.
     */
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    /**
     * @param collisionPoint  - collision point to check intersection with.
     * @param currentVelocity - current ball velocity.
     * @param hitter - ball participate the hit event.
     * @return new velocity according to balls hit location on the paddle.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Line upperL = this.rectangle.getUpperLine();
        Line lowerL = this.rectangle.getLowerLine();
        double fifthPaddle = (this.rectangle.getWidth() / 5);
        double speed = Math.sqrt(Math.pow(currentVelocity.getDx(), 2) + Math.pow(currentVelocity.getDy(), 2));
        Velocity newVelocity = currentVelocity;

        if (collisionPoint.getX() <= (fifthPaddle + this.rectangle.getUpperLeft().getX())) {
            newVelocity = Velocity.fromAngleAndSpeed(300, speed);
        } else if (collisionPoint.getX() > (fifthPaddle + this.rectangle.getUpperLeft().getX())
                && collisionPoint.getX() <= (2 * fifthPaddle + this.rectangle.getUpperLeft().getX())) {
            newVelocity = Velocity.fromAngleAndSpeed(330, speed);
        } else if (collisionPoint.getX() > (2 * fifthPaddle + this.rectangle.getUpperLeft().getX())
                && collisionPoint.getX() <= (3 * fifthPaddle + this.rectangle.getUpperLeft().getX())) {
            if (currentVelocity.getDx() == 0) {
                newVelocity = Velocity.fromAngleAndSpeed(180, speed);
            }
            if (upperL.isPointOnLine(collisionPoint) || lowerL.isPointOnLine(collisionPoint)) {
                newVelocity = new Velocity(currentVelocity.getDx(), (currentVelocity.getDy() * (-1)));
            }
        } else if (collisionPoint.getX() > (3 * fifthPaddle + this.rectangle.getUpperLeft().getX())
                && collisionPoint.getX() <= (4 * fifthPaddle + this.rectangle.getUpperLeft().getX())) {
            newVelocity = Velocity.fromAngleAndSpeed(30, speed);
        } else if (collisionPoint.getX() > (4 * fifthPaddle + this.rectangle.getUpperLeft().getX())
                && collisionPoint.getX() <= (5 * fifthPaddle + this.rectangle.getUpperLeft().getX())) {
            newVelocity = Velocity.fromAngleAndSpeed(60, speed);
        }
        return newVelocity;

    }

    /**
     * Add paddle to the gameLevel.
     *
     * @param gameLevel GameLevel.
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addCollidable(this);
        gameLevel.addSprite(this);
    }

    /**
     *
     * @param gameLevel - GameLevel to be remove game from.
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
        gameLevel.removeCollidable(this);
    }
}