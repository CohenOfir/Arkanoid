package geometricshapes;

/** Represent a Point.
 * @author Ofir Cohen
 */
public class Point {

    private double x;
    private double y;

    /** Constructor function for Points.
     * @param x - x coordinate.
     * @param y - y coordinate.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /** Calculating distance between 2 points.
     * @param other - second point for calculation.
     * @return double - distance between the 2 point.
     */
    public double distance(Point other) {
        return Math.sqrt(((other.x - this.x) * (other.x - this.x)) + ((other.y - this.y) * (other.y - this.y)));
    }

    /** Calculating distance between 2 points.
     * @param other - second point for comparison.
     * @return boolean - true if the 2 point equals.
     */
    public boolean equals(Point other) {
        if ((other.x == this.x) && (other.y == this.y)) {
            return true;
        }
        return false;
    }

    /**
     * @return point x coordinate.
     */
    public double getX() {
        return this.x;
    }

    /**
     * @return point x coordinate.
     */
    public double getY() {
        return this.y;
    }

}