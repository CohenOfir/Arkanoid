package hitlisteners;

import assistclasses.Counter;
import geometricshapes.Ball;
import interfaces.HitListener;
import spritesandcollidables.Block;

/**
 * ScoreTrackingListener Class.
 * author: Ofir Cohen.
 */
public class ScoreTrackingListener implements HitListener {

    private Counter currentScore;

    /**
     * Constructor.
     *
     * @param scoreCounter - contains the score of the player
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * Updates the score by the rules that were described in the Class Doc'.
     *
     * @param beingHit the Block that was involved in the hit
     * @param hitter   the Ball that was involved in the hit
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        currentScore.increase(5);
        if (beingHit.getHitPoints() == 0) {
            currentScore.increase(10);
        }
    }
}
