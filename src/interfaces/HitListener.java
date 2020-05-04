package interfaces;

import geometricshapes.Ball;
import spritesandcollidables.Block;

/**
 * Describes HitListener interface.
 *
 * author - Ofir Cohen
 */
public interface HitListener {
    /**
     *
     * @param beingHit - block participant the hit event.
     * @param hitter - ball participant the hit event.
     */
    void hitEvent(Block beingHit, Ball hitter);
}