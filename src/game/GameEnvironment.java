package game;

import assistclasses.CollisionInfo;
import geometricshapes.Line;
import geometricshapes.Point;
import interfaces.Collidable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent GameEnvironment object.
 *
 * @author Ofir Cohen
 */
public class GameEnvironment {

    private List<Collidable> collidablesList;

    /**
     * Constructor for Class.
     */
    public GameEnvironment() {
        this.collidablesList = new ArrayList<Collidable>();
    }

    /**
     * @param c - collidable object to be adding to the game environment.
     */
    public void addCollidable(Collidable c) {
        this.collidablesList.add(c);
    }

    /**
     * @return collidable objects list in the game env.
     */
    public List<Collidable> getCollidableList() {
        return this.collidablesList;
    }

    /**
     *
     * @param collidable - collidable object to be removed from list.
     */
    public void removeCollidable(Collidable collidable) {
        this.collidablesList.remove(collidable);
    }

    /**
     * @param trajectory - Line describes balls movement.
     * @return Collision info for collision point with line, if no collision - null.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        Point closestCollision = null;
        Point keeper = null;
        Collidable closestCollidable = null;
        CollisionInfo closestPointCollisionInfo;
        double minDistance = Double.MAX_VALUE;
        for (Collidable collidable : collidablesList) {
            Point closestPoint = trajectory.closestIntersectionToStartOfLine(collidable.getCollisionRectangle());
            double currentDistance = Double.MAX_VALUE;

            if (closestPoint != null) {
                //stores the current distance between the start of the line and the point that was found
                currentDistance = trajectory.start().distance(trajectory.closestIntersectionToStartOfLine(
                        collidable.getCollisionRectangle()));
                //stores the point that was found
                keeper = closestPoint;
            }

            if (currentDistance < minDistance) {
                closestCollision = keeper;
                minDistance = currentDistance;
                closestCollidable = collidable;
            }
        }

        if (closestCollidable == null) {
            return null;
        }

        CollisionInfo closestCollisionInfo = new CollisionInfo(closestCollision, closestCollidable);
        return closestCollisionInfo;
    }

}