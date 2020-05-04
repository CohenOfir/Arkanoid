package animations;

import biuoop.DrawSurface;
import files.HighScoresTable;
import interfaces.Animation;

import java.awt.Color;

/**
 * HighScoresAnimation Class.
 * Author - Ofir Cohen.
 */
public class HighScoresAnimation implements Animation {

    private HighScoresTable highScoresTable;
    private boolean shouldStop;

    /**
     * constructor.
     *
     * @param highScoresTable - HighScoresTable object
     */
    public HighScoresAnimation(HighScoresTable highScoresTable) {
        this.highScoresTable = highScoresTable;
        this.shouldStop = false;
    }

    /**
     * @param d - DrawSurface given from AnimationRunner.
     */
    public void doOneFrame(DrawSurface d) {
        int height = d.getHeight() / 5;
        int nameWidth = d.getWidth() / 6;
        int scoreWidth = d.getWidth() - 250;
        int linesSpace = 45;

        d.setColor(Color.LIGHT_GRAY);
        d.fillRectangle(0, 0, 800, 600);

        d.setColor(Color.yellow);
        d.drawText((d.getWidth() / 3), d.getHeight() / 10, "High Scores", 40);

        d.setColor(Color.GREEN);
        d.drawText(nameWidth, height, "Player's Name", 30);
        d.drawText(scoreWidth, height, "Score", 30);
        d.fillRectangle(nameWidth, height + 5, 500, 2);
        height += linesSpace;

        d.setColor(new Color(255, 207, 0));

        for (int i = 0; i < this.highScoresTable.size(); i++) {
            d.drawText(scoreWidth, height, Integer.toString(highScoresTable.getHighScores().get(i).getScore()), 30);
            d.drawText(nameWidth, height, (highScoresTable.getHighScores().get(i).getName()), 30);
            height += linesSpace;
        }

        d.setColor(Color.BLACK);
        d.drawText(d.getWidth() / 5, d.getHeight() - 120, "Press space to continue", 40);
    }

    /**
     *
     * @return If animation should stop.
     */
    public boolean shouldStop() {
        return this.shouldStop;
    }
}
