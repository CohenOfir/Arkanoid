package hitlisteners;

import assistclasses.Counter;
import game.GameLevel;
import geometricshapes.Ball;
import interfaces.HitListener;
import spritesandcollidables.Block;

/**
 * BlockRemover Class.
 * author: Ofir Cohen.
 */
public class BlockRemover implements HitListener {
    //members
    private GameLevel gameLevel;
    private Counter remainingBlocks;

    /**
     * the constructor.
     *
     * @param gameLevel            the gameLevel we want to be able to remove the Blocks from
     * @param remainingBlocks - Counter object which contains number of the remaining Blocks in this GameLevel.
     */
    public BlockRemover(GameLevel gameLevel, Counter remainingBlocks) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = remainingBlocks;
    }

    /**
     * @param beingHit the Block that was involved in the hit
     * @param hitter   the Ball that was involved in the hit
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        //if the Block's life reaches 0 after the hit
        if (beingHit.getHitPoints() == 0) {
            //remove this Block from the GameLevel and the HitListeners List
            beingHit.removeFromGame(this.gameLevel);
            beingHit.removeHitListener(this);
            //decreasing the amount of the Block in the GameLevel by one.
            this.remainingBlocks.decrease(1);
        }

        if (this.remainingBlocks.getValue() == 0) {
            this.gameLevel.getScore().increase(100);
        }
    }
}
