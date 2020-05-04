package assistclasses;

import geometricshapes.Point;
import interfaces.Collidable;

/**
 * Describes CollisionInfo object.
 *
 * @author Ofir Cohen
 */
public class CollisionInfo {

    private Point collisionPoint;
    private Collidable collisionObject;

    /**
     *
     * @param collisionPoint - point of collision.
     * @param collisionObject - collision type.
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }
    /**
     *
     * @return - Point of collision.
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     *
     * @return collision object.
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}