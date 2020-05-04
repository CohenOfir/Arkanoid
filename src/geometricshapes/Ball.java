package geometricshapes;
import assistclasses.CollisionInfo;
import assistclasses.Velocity;
import game.GameEnvironment;
import interfaces.Sprite;
import game.GameLevel;

import biuoop.DrawSurface;

import java.awt.Color;

/**
 * Represent a Ball.
 *
 * @author Ofir Cohen
 */
public class Ball implements Sprite {

    private Point center;
    private int radius;
    private java.awt.Color color;
    private Velocity velocity;
    private Point rectangleBoundStart;
    private Point rectangleBoundEnD;
    private GameEnvironment gameEnvironment;
    private int topBorder;
    private int lowerBorder;
    private int rightBorder;
    private int leftBorder;


    /**
     * Constructor function for Balls.
     *
     * @param center          - ball center point.
     * @param r               - ball radius.
     * @param color           - ball color.
     * @param gameEnvironment - game environment for ball movement.
     */
    public Ball(Point center, int r, java.awt.Color color, GameEnvironment gameEnvironment) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.gameEnvironment = gameEnvironment;
    }

    /**
     * Constructor function for Balls.
     *
     * @param x               - ball center x coordinate.
     * @param y               - ball center y coordinate.
     * @param r               - ball radius.
     * @param color           - ball color.
     * @param gameEnvironment - game environment for ball movement.
     */
    public Ball(double x, double y, int r, java.awt.Color color, GameEnvironment gameEnvironment) {
        Point newPoint = new Point(x, y);
        this.center = newPoint;
        this.radius = r;
        this.color = color;
        this.gameEnvironment = gameEnvironment;
    }

    /**
     * second constructor of geometricshapes.Ball.
     *
     * @param center      - center of the ball.
     * @param r           - radius.
     * @param color       - color of the ball.
     * @param topBorder   - top border of the frame/screen.
     * @param lowerBorder - lower border of the frame/screen.
     * @param rightBorder - right border of the frame/screen.
     * @param leftBorder  -  left border of the frame/screen.
     */
    public Ball(Point center, int r, java.awt.Color color, int topBorder, int lowerBorder, int rightBorder,
                int leftBorder) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.topBorder = topBorder;
        this.lowerBorder = lowerBorder;
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
    }

    /**
     * Constructor function for Balls.
     *
     * @param x     - ball center x coordinate.
     * @param y     - ball center y coordinate.
     * @param r     - ball radius.
     * @param color - ball color.
     */
    public Ball(int x, int y, int r, java.awt.Color color) {
        Point newPoint = new Point(x, y);
        this.center = newPoint;
        this.radius = r;
        this.color = color;
    }

    /**
     * @param environment - game environment to be adding for ball.
     */
    public void setGameEnvironment(GameEnvironment environment) {
        this.gameEnvironment = environment;
    }

    /**
     * @return an integer for ball x coordinate.
     */
    public int getX() {
        return ((int) this.center.getX());
    }

    /**
     * @return an integer for ball y coordinate.
     */
    public int getY() {
        return ((int) this.center.getY());
    }

    /**
     * @return an integer for ball radius.
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * @return awt.Color for ball color.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * Function draw balls on draw surface.
     *
     * @param surface - draw surface for drawing.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.radius);
        surface.setColor(Color.BLACK);
        surface.drawCircle(this.getX(), this.getY(), this.radius);
    }

    /**
     * @param v - Velocity, speed and angle for ball.
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * @param dx - x axis change rate.
     * @param dy - y axis change rate.
     */
    public void setVelocity(double dx, double dy) {
        Velocity newVelocity = new Velocity(dx, dy);
        this.velocity = newVelocity;
    }

    /**
     * @return velocity - return current ball velocity parameter.
     */
    public Velocity getVelocity() {
        return (this.velocity);
    }

    /**
     * Function determines ball motion bounds - initial point.
     *
     * @param x - draw surface for drawing.
     * @param y - draw surface for drawing.
     */
    public void setRectangleBound1(int x, int y) {
        Point newPoint = new Point(x, y);
        this.rectangleBoundStart = newPoint;
    }

    /**
     * Function determines ball motion bounds- end point.
     *
     * @param x - draw surface for drawing.
     * @param y - draw surface for drawing.
     */
    public void setRectangleBound2(int x, int y) {
        Point newPoint = new Point(x, y);
        this.rectangleBoundEnD = newPoint;
    }

    /**
     * Function changes ball velocity according to balls location on the surface.
     */
    public void moveOneStep() {
        Line trajectory = new Line(this.center, this.getVelocity().applyToPoint(this.center));
        CollisionInfo collidingPointInfo = this.gameEnvironment.getClosestCollision(trajectory);

        //ball will collide object in its next movement.
        if (collidingPointInfo != null) {
            this.setVelocity(collidingPointInfo.collisionObject().hit(this, collidingPointInfo.collisionPoint(),
                    this.getVelocity()));
        }
        this.center = this.getVelocity().applyToPoint(this.center);
    }

    /**
     * triggers balls movement according to main program.
     */
    public void timePassed() {
        this.moveOneStep();
    }

    /**
     * @param gameLevel - gameLevel to be adding ball to.
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
    }

    /**
     *
     * @param gameLevel - gameLevel to remove ball from.
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
    }

}