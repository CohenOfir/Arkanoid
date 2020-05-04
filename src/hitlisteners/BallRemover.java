package hitlisteners;

import assistclasses.Counter;
import game.GameLevel;
import geometricshapes.Ball;
import interfaces.HitListener;
import spritesandcollidables.Block;

/**
 * BallRemover Class.
 * author: Ofir Cohen.
 */
public class BallRemover implements HitListener {
    //members
    private GameLevel gameLevel;
    private Counter remainingBalls;

    /**
     * the constructor.
     *
     * @param gameLevel           the gameLevel we want to be able to remove the Balls from
     * @param remainingBalls Counter object which contains number of the remaining Balls in this GameLevel.
     */
    public BallRemover(GameLevel gameLevel, Counter remainingBalls) {
        this.gameLevel = gameLevel;
        this.remainingBalls = remainingBalls;
    }


    /**
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the Ball that's doing the hitting.
     *
     * @param beingHit the Block that was involved in the hit
     * @param hitter   the Ball that was involved in the hit
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        //remove this hitter (Ball) from the GameLevel.
        hitter.removeFromGame(this.gameLevel);
        //decreasing the amount of the Balls in the GameLevel by one.
        this.remainingBalls.decrease(1);
    }
}
