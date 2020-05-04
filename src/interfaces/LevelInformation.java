package interfaces;

import assistclasses.Velocity;
import spritesandcollidables.Block;

import java.util.List;

/**
 * Describe LevelInformation interface.
 */
public interface LevelInformation {
    /**
     * @return number of balls in this level.
     */
    int numberOfBalls();

    /**
     * @return list contains all the velocity of each ball in level.
     */
    List<Velocity> initialBallVelocities();

    /**
     * @return paddle's velocity.
     */
    int paddleSpeed();

    /**
     * @return paddle's width.
     */
    int paddleWidth();

    /**
     * @return string with the level's Name
     */
    String levelName();

    /**
     * @return Sprite describes the level's Background
     */
    Sprite getBackground();

    /**
     * @return list that contains all Blocks in Level.
     */
    List<Block> blocks();


    /**
     * @return int Number of blocks to be removed.
     */
    int numberOfBlocksToRemove();
}
