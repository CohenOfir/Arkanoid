package assistclasses;

import geometricshapes.Point;

/** Represent a Velocity.
 * @author Ofir Cohen
 */
public class Velocity {

    private double dx;
    private double dy;

    /** Constructor function for Velocities.
     * @param dx - x axis change rate.
     * @param dy - y axis change rate.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /** "Constructor" function for Velocities.
     * @param angle - initial angle for motion.
     * @param speed - balls position change rate.
     * @return Velocity -
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        angle = Math.toRadians(angle);
        double dx = Math.sin(angle) * (speed);
        double dy = Math.cos(angle) * (speed);
        return new Velocity(dx, -dy);
    }

    /**
     * @return x axis change rate.
     */
    public double getDx() {
        return (this.dx);
    }

    /**
     * @return y axis change rate.
     */
    public double getDy() {
        return (this.dy);
    }

    /** Function Take a point with position (x,y) and return a new point with position (x+dx, y+dy).
     * @param p - Point.
     * @return newPoint - (p+dx, y+dx)
     */
    public Point applyToPoint(Point p) {
        Point newPoint = new Point((p.getX() + this.dx), (p.getY() + this.dy));
        return newPoint;
    }
}