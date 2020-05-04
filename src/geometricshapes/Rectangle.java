package geometricshapes;

import java.util.LinkedList;
import java.util.List;

/**
 * Represent a rectangle.
 *
 * @author Ofir Cohen
 */
public class Rectangle {

    private Point upperLeft;
    private double width;
    private double height;

    /**
     * @param upperLeft - point, upper left corner.
     * @param height    - rectangle height.
     * @param width     - rectangle width.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }
    /**
     * @param line - line to check intersection with the rectangle.
     * @return - list of intersection points between line and the rectangle,
     * if there is no intersecion, return empty list.
     */
    public java.util.List intersectionPoints(Line line) {
        List<Point> list = new LinkedList<Point>();
        Line upperLine = new Line(this.getUpperLeft(), this.getUpperRight());
        Line lowerLine = new Line(this.getLowerLeft(), this.getLowerRight());
        Line rightLine = new Line(this.getLowerRight(), this.getUpperRight());
        Line leftLine = new Line(this.getLowerLeft(), this.getUpperLeft());
        if (upperLine.isIntersecting(line)) {
            list.add(upperLine.intersectionWith(line));
        }
        if (lowerLine.isIntersecting(line)) {
            list.add(lowerLine.intersectionWith(line));
        }
        if (rightLine.isIntersecting(line)) {
            list.add(rightLine.intersectionWith(line));
        }
        if (leftLine.isIntersecting(line)) {
            list.add(leftLine.intersectionWith(line));
        }
        return list;
    }

    /**
     * @return rectangle width.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * @return rectangle height.
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * @return rectangle upper left point.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * @return rectangle upper right point.
     */
    public Point getUpperRight() {
        Point upperRight = new Point(upperLeft.getX() + this.getWidth(), upperLeft.getY());
        return upperRight;
    }

    /**
     * @return rectangle lower left point.
     */
    public Point getLowerLeft() {
        Point lowerLeft = new Point(upperLeft.getX(), upperLeft.getY() + this.getHeight());
        return lowerLeft;
    }

    /**
     * @return rectangle lower right point.
     */
    public Point getLowerRight() {
        Point lowerRight = new Point(upperLeft.getX() + this.getWidth(), upperLeft.getY() + this.getHeight());
        return lowerRight;
    }

    /**
     * @return upper line of the rectangle.
     */
    public Line getUpperLine() {
        Line line = new Line(this.getUpperLeft(), this.getUpperRight());
        return line;
    }

    /**
     * @return lower line of the rectangle.
     */
    public Line getLowerLine() {
        Line line = new Line(this.getLowerLeft(), this.getLowerRight());
        return line;
    }

    /**
     * @return right line of the rectangle.
     */
    public Line getRightLine() {
        Line line = new Line(this.getLowerRight(), this.getUpperRight());
        return line;
    }

    /**
     * @return left line of the rectangle.
     */
    public Line getLeftLine() {
        Line line = new Line(this.getLowerLeft(), this.getUpperLeft());
        return line;
    }

    /**
     * @param newUpperLeft - the new upperLeft.Point of this Rectangle.
     */
    public void setUpperLeft(Point newUpperLeft) {
        this.upperLeft = newUpperLeft;
    }
}