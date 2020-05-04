package game;

import animations.AnimationRunner;
import animations.CountdownAnimation;
import assistclasses.Counter;
import assistclasses.KeyPressStoppableAnimation;
import assistclasses.Velocity;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import geometricshapes.Ball;
import geometricshapes.Point;
import geometricshapes.Rectangle;
import hitlisteners.BallRemover;
import hitlisteners.BlockRemover;
import hitlisteners.ScoreTrackingListener;
import indicators.LevelNameIndicator;
import indicators.LivesIndicator;
import indicators.ScoreIndicator;
import interfaces.Animation;
import interfaces.Collidable;
import interfaces.LevelInformation;
import spritesandcollidables.Block;
import spritesandcollidables.Paddle;
import interfaces.Sprite;
import spritesandcollidables.SpriteCollection;


import java.awt.Color;

/**
 * Describes GameLevel object.
 *
 * @author Ofir Cohen
 */
public class GameLevel implements Animation {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private KeyboardSensor keyboard;
    private Counter remainedBlocks;
    private Counter remainedBalls;
    private Counter score;
    private Counter numberOfLives;
    private AnimationRunner runner;
    private boolean isRunning;
    private LevelInformation levelInfo;


    /**
     *
     * @param runner        AnimationRunner Object
     * @param levelInfo     describes a level in the game
     * @param score         indicates the score of the game
     * @param numberOfLives indicates the remaining lives of the player
     */
    public GameLevel(AnimationRunner runner, LevelInformation levelInfo, Counter score, Counter numberOfLives) {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.remainedBlocks = new Counter(0);
        this.remainedBalls = new Counter(0);
        this.score = score;
        this.numberOfLives = numberOfLives;
        this.isRunning = true;
        this.runner = runner;
        this.keyboard = this.runner.getGui().getKeyboardSensor();
        this.levelInfo = levelInfo;
    }

    /**
     * @param c collidable to adding to environment list.
     */
    public void addCollidable(Collidable c) {
        environment.addCollidable(c);
    }

    /**
     * @param s - sprite to be adding to sprites list.
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    /**
     * @param c - collidable to be removed from collidable list.
     */
    public void removeCollidable(Collidable c) {
        this.environment.getCollidableList().remove(c);
    }

    /**
     * @param s - sprite to be remove from spritesList.
     */
    public void removeSprite(Sprite s) {
        this.sprites.getSpritesList().remove(s);
    }

    /**
     * @return - current score.
     */
    public Counter getScore() {
        return this.score;
    }

    /**
     * @return number of remained blocks.
     */
    public int getRemainedBlocks() {
        return this.remainedBlocks.getValue();
    }

    /**
     * @return number of remained lives.
     */
    public int getNumberOfLives() {
        return this.numberOfLives.getValue();
    }


    /**
     * Initialize a new game: create the Blocks and Ball(and Paddle) and add them to the game.
     */
    public void initialize() {
        BlockRemover blockRemover = new BlockRemover(this, this.remainedBlocks);
        BallRemover ballRemover = new BallRemover(this, remainedBalls);
        ScoreTrackingListener scoreCounter = new ScoreTrackingListener(this.score);

        ScoreIndicator scoreIndicator = new ScoreIndicator(this.score);
        LivesIndicator livesIndicator = new LivesIndicator(numberOfLives);
        LevelNameIndicator levelNameIndicator = new LevelNameIndicator(levelInfo);

        scoreIndicator.addToGame(this);
        livesIndicator.addToGame(this);
        levelNameIndicator.addToGame(this);

        Block topBound = new Block(new Point(0, 25), 800, 20, 0, Color.gray);
        Block buttomBound = new Block(new Point(20, 600), 760, 20, 0, Color.gray);
        buttomBound.addHitListener(ballRemover);
        Block rightBound = new Block(new Point(780, 25), 20, 600, 0, Color.gray);
        Block leftBound = new Block(new Point(0, 25), 20, 600, 0, Color.gray);
        topBound.addToGame(this);
        buttomBound.addToGame(this);
        leftBound.addToGame(this);
        rightBound.addToGame(this);

        for (Block gameBlock : levelInfo.blocks()) {
            gameBlock.addToGame(this);
            remainedBlocks.increase(1);
            gameBlock.addHitListener(blockRemover);
            gameBlock.addHitListener(scoreCounter);
        }
    }

    /**
     * Starts game.
     */
    public void playOneTurn() {
        createBallsOnTopOfPaddle();
        Point rectangleUpperLeft = new Point((800 - levelInfo.paddleWidth()) / 2, 560);
        Rectangle paddleRect = new Rectangle(rectangleUpperLeft, levelInfo.paddleWidth(), 20);
        Paddle gamePaddle = new Paddle(keyboard, Color.orange, paddleRect, levelInfo.paddleSpeed(), 20, 780);
        gamePaddle.addToGame(this);
        this.runner.run(new CountdownAnimation(3, 3, sprites, levelInfo));
        this.isRunning = true;
        this.runner.run(this);
        gamePaddle.removeFromGame(this);
    }

    /**
     * @param d drawSurface given from Animation runner.
     */
    public void doOneFrame(DrawSurface d) {
        this.levelInfo.getBackground().drawOn(d);
        PauseScreen pauseScreen = new PauseScreen(keyboard);
        KeyPressStoppableAnimation keyPressAnimation =
                new KeyPressStoppableAnimation(keyboard, keyboard.SPACE_KEY, pauseScreen);
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();
        if (this.keyboard.isPressed("p")) {
            this.runner.run(keyPressAnimation);
        }
    }

    /**
     * @return true if game should stop, false otherwise.
     */
    public boolean shouldStop() {
        if (this.remainedBlocks.getValue() == 0) {
            return this.isRunning;
        }
        if (this.remainedBalls.getValue() == 0) {
            this.numberOfLives.decrease(1);
            if (this.numberOfLives.getValue() >= 0) {
                return this.isRunning;
            }
        }
        return !this.isRunning;
    }

    /**
     * this method creates the Balls on Paddle.
     */
    public void createBallsOnTopOfPaddle() {
        for (int i = 0; i < levelInfo.numberOfBalls(); i++) {
            Point center = new Point(400, 555);
            Velocity velocity = levelInfo.initialBallVelocities().get(i);
            Ball ballOne = new Ball(center, 5, Color.WHITE, 20, 600, 780, 20);
            ballOne.setGameEnvironment(environment);
            ballOne.setVelocity(velocity);
            ballOne.addToGame(this);
            this.remainedBalls.increase(1);
        }
    }
}