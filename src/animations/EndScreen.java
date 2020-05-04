package animations;

import biuoop.DrawSurface;
import interfaces.Animation;

import java.awt.Color;

/**
 * EndScreen Class.
 * Author - Ofir Cohen.
 */
public class EndScreen implements Animation {
    private boolean isGameOver;
    private boolean stop;
    private int score;

    /**
     * Constructor.
     *
     * @param isGameOver boolean - true if the Game is Over, otherwise false (player wins).
     * @param score      the current score of the player.
     */
    public EndScreen(boolean isGameOver, int score) {
        this.isGameOver = isGameOver;
        this.stop = false;
        this.score = score;
    }

    /**
     * @param d - drawSurface given from AnimationRunner.
     */
    public void doOneFrame(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, 800, 600);
        d.setColor(Color.WHITE);
        if (isGameOver) {
            d.drawText((d.getWidth() / 4) - 10, d.getHeight() / 2,
                    "Game Over. Your score is: " + this.score, 32);
        } else {
            d.drawText((d.getWidth() / 5) + 20, d.getHeight() / 2,
                    "You Win! Your score is: " + this.score, 32);
        }
        d.drawText((d.getWidth() / 5) + 50, 400, "Press 'SPACE' to proceed", 30);
    }

    /**
     * @return Boolean this.stop - true if should stop, false if should run.
     */
    public boolean shouldStop() {
        return this.stop;
    }
}
