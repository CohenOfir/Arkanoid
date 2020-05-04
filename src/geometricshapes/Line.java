package geometricshapes;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a Line.
 *
 * @author Ofir Cohen
 */
public class Line {

    private Point p1;
    private Point p2;

    /**
     * Constructor function for Lines.
     *
     * @param start - start Point.
     * @param end   - end Point.
     */
    public Line(Point start, Point end) {
        p1 = new Point(start.getX(), start.getY());
        p2 = new Point(end.getX(), end.getY());
    }

    /**
     * Constructor function for Lines.
     *
     * @param x1 - start x coordinate.
     * @param y1 - start y coordinate.
     * @param x2 - end x coordinate
     * @param y2 - end y coordinate.
     */
    public Line(double x1, double y1, double x2, double y2) {
        p1 = new Point(x1, y1);
        p2 = new Point(x2, y2);
    }

    /**
     * @return double - Line length.
     */
    public double length() {
        return (p1.distance(p2));
    }

    /**
     * @return double - Lines middle point.
     */
    public Point middle() {
        double middleX = ((this.p1.getX() + this.p2.getX()) / 2);
        double middleY = ((this.p1.getY() + this.p2.getY()) / 2);
        Point middle = new Point(middleX, middleY);
        return middle;
    }

    /**
     * @return double - Lines start point.
     */
    public Point start() {
        return this.p1;
    }

    /**
     * @return double - Lines end point.
     */
    public Point end() {
        return this.p2;
    }

    /**
     * @param other - second Line for comparison.
     * @return boolean - true if lines intersect.
     */
    public boolean isIntersecting(Line other) {

        if (this.intersectionWith(other) == null) {
            return false;
        }
        return true;
    }

    /**
     * @return boolean - true or false.
     */
    public boolean isHorizontal() {
        return (this.p1.getY() == this.p2.getY());
    }

    /**
     * @return boolean - true if vertical, false otherwise.
     */
    public boolean isVertical() {
        return (this.p1.getX() == this.p2.getX());
    }

    /**
     * @param other - second Line for comparison.
     * @return Point - intersection point, null if lines does not intersect.
     * Function calculate slope- m (using Slope function), and b (y=mx+b).
     */
    public Point intersectionWith(Line other) {
        double m1 = this.slope();
        double m2 = other.slope();

        if (m1 == m2) {
            return null;
        }

        double b1 = (-(this.p1.getX() * m1) + (this.p1.getY()));
        double b2 = (-(other.p1.getX() * m2) + (other.p1.getY()));

        double x = (b2 - b1) / (m1 - m2);
        double y = m1 * x + b1;

        if (this.isVertical() && ((!other.isVertical()) && (!other.isHorizontal()))) {
            x = this.p1.getX();
            y = m2 * x + b2;
        }

        if (((!this.isVertical()) && (!this.isHorizontal())) && (other.isVertical())) {
            x = other.p1.getX();
            y = m1 * x + b1;
        }

        if (this.isHorizontal() && ((!other.isVertical()) && (!other.isHorizontal()))) {
            y = this.p1.getY();
            x = (y - b2) / m2;
        }

        if (((!this.isVertical()) && (!this.isHorizontal())) && other.isHorizontal()) {
            y = other.p1.getY();
            x = (y - b1) / m1;
        }

        if (this.isHorizontal() && other.isVertical()) {
            x = other.p1.getX();
            y = this.p1.getY();
        }
        if (this.isVertical() && other.isHorizontal()) {
            x = this.p1.getX();
            y = other.p1.getY();
        }

        int check = 0;
        //Check if intersection point is in Lines distance range.
        if ((Math.min(this.start().getX(), this.end().getX()) <= x)
                && (Math.max(this.start().getX(), this.end().getX()) >= x)) {
            if ((Math.min(other.start().getX(), other.end().getX()) <= x)
                    && (Math.max(other.start().getX(), other.end().getX()) >= x)) {
                check++;
            }
        }

        if ((Math.min(this.start().getY(), this.end().getY()) <= y)
                && (Math.max(this.start().getY(), this.end().getY()) >= y)) {
            if ((Math.min(other.start().getY(), other.end().getY()) <= y)
                    && (Math.max(other.start().getY(), other.end().getY()) >= y)) {
                check++;
            }
        }

        if (check == 2) {
            return new Point(x, y);
        }
        return null;
    }

    /**
     * @param other - second Line for comparison.
     * @return boolean - true if lines equals.
     */
    public boolean equals(Line other) {
        //check start point to end point.
        if (this.start().equals(other.start()) && this.end().equals(other.end())) {
            return true;
        }

        //check end point to start point.
        if (this.start().equals(other.end()) && this.end().equals(other.start())) {
            return true;
        }

        return false;
    }

    /**
     * @return double - Lines slope (using (y2-y1)/(x2-x1) formula).
     */
    public double slope() {
        if (p2.getX() == p1.getX()) {
            return Double.POSITIVE_INFINITY;
        }

        return ((p2.getY() - p1.getY()) / (p2.getX() - p1.getX()));
    }

    /**
     * @param point - point for comparision.
     * @return - true if point on line. false otherwise.
     */
    public boolean isPointOnLine(Point point) {
        double distance = (point.distance(this.start()) + point.distance(this.end()));
        if (distance - this.length() >= -1 && distance - this.length() <= 1) {
            return true;
        }
        //return ((point.distance(this.start()) + point.distance(this.end())) == this.length());
        return false;
    }

    /**
     * @param rect - rectangle to check intersection with.
     * @return - Point - closest point to line start.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> intersectionsList = new ArrayList<Point>();
        intersectionsList = rect.intersectionPoints(this);
        Point closestIntersection = null;
        if (intersectionsList.isEmpty()) {
            return null;
        }

        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < intersectionsList.size(); i++) {
            double distance = this.start().distance(intersectionsList.get(i));
            if (distance < minDistance) {
                minDistance = distance;
                closestIntersection = new Point(intersectionsList.get(i).getX(),
                        intersectionsList.get(i).getY());
            }
        }
        return closestIntersection;
    }
}

