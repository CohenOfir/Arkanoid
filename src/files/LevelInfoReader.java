package files;

import assistclasses.Velocity;
import interfaces.LevelInformation;
import interfaces.Sprite;
import spritesandcollidables.Block;

import java.util.List;

/**
 * LevelInfoReader Class.
 * Author - Ofir Cohen.
 */
public class LevelInfoReader implements LevelInformation {

    private List<Velocity> velocities;
    private int paddleSpeedVar;
    private int paddleWidthVar;
    private String levelNameStr;
    private Sprite levelBackground;
    private List<Block> blocksPattern;
    private int blockToRemove;

    /**
     * Constructor.
     *
     * @param velocities      balls velocities.
     * @param paddleSpeedVar  paddle speed.
     * @param paddleWidthVar  paddle width.
     * @param levelNameStr    name.
     * @param levelBackground Background.
     * @param blocksPattern   Block pattern.
     * @param blockToRemove   number of blocks left to remove.
     */
    public LevelInfoReader(List<Velocity> velocities, int paddleSpeedVar, int paddleWidthVar, String levelNameStr,
                           Sprite levelBackground, List<Block> blocksPattern, int blockToRemove) {
        this.velocities = velocities;
        this.paddleSpeedVar = paddleSpeedVar;
        this.paddleWidthVar = paddleWidthVar;
        this.levelNameStr = levelNameStr;
        this.levelBackground = levelBackground;
        this.blockToRemove = blockToRemove;
        this.blocksPattern = blocksPattern;
    }

    /**
     * @return number of balls.
     */
    public int numberOfBalls() {
        return velocities.size();
    }

    /**
     * @return list tof balls velocities.
     */
    public List<Velocity> initialBallVelocities() {
        return velocities;
    }

    /**
     * @return paddle's velocity.
     */
    public int paddleSpeed() {
        return paddleSpeedVar;
    }

    /**
     * @return paddle's width.
     */
    public int paddleWidth() {
        return paddleWidthVar;
    }

    /***
     * @return level's Name string.
     */
    public String levelName() {
        return levelNameStr;
    }

    /**
     * @return Sprite describes the level's Background
     */
    public Sprite getBackground() {
        return levelBackground;
    }

    /**
     * @return list of Blocks for Level.
     */
    public List<Block> blocks() {
        return blocksPattern;
    }

    /**
     * @return number of blocks to remove.
     */
    public int numberOfBlocksToRemove() {
        return blockToRemove;
    }
}
