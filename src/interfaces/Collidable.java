package interfaces;

import assistclasses.Velocity;
import geometricshapes.Ball;
import geometricshapes.Point;
import geometricshapes.Rectangle;

/**
 * Describes collidable interface.
 *
 * @author Ofir Cohen
 */
public interface Collidable {
    /**
     * @return collision rectangle for collidable.
     */
    Rectangle getCollisionRectangle();

    /**
     * @param collisionPoint  - collision point to check intersection with.
     * @param currentVelocity - current ball velocity.
     * @param hitter          - ball involving the hit.
     * @return new velocity, according to hit position on the block.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}