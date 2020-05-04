package game;

import animations.AnimationRunner;
import animations.EndScreen;
import animations.HighScoresAnimation;
import assistclasses.Counter;
import assistclasses.KeyPressStoppableAnimation;
import biuoop.DialogManager;
import biuoop.KeyboardSensor;
import files.HighScoresTable;
import indicators.ScoreInfo;
import interfaces.LevelInformation;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Describes GameFlow Class.
 * Author - Ofir Cohen.
 */
public class GameFlow {

    private AnimationRunner animationRunner;
    private KeyboardSensor keyboardSensors;
    private Counter numOfLives;
    private Counter score;
    private HighScoresTable highScoresTable;


    /**
     * Constructor.
     *
     * @param animationRunner runs Animation object.
     * @param keyboardSensor  KeyboardSensor.
     * @param table           high scores table.
     */
    public GameFlow(AnimationRunner animationRunner, KeyboardSensor keyboardSensor, HighScoresTable table) {
        this.animationRunner = animationRunner;
        this.keyboardSensors = keyboardSensor;
        this.highScoresTable = table;
        this.numOfLives = new Counter(7);
        this.score = new Counter(0);
    }

    /**
     * @param levels List of game levels.
     */
    public void runLevels(List<LevelInformation> levels) {
        boolean loseFlag = false;
        for (LevelInformation levelInfo : levels) {
            GameLevel level = new GameLevel(this.animationRunner, levelInfo, score, numOfLives);
            level.initialize();
            while (level.getNumberOfLives() > 0 && level.getRemainedBlocks() > 0) {
                level.playOneTurn();
            }

            if (numOfLives.getValue() == 0) {
                loseFlag = true;
                break;
            }
        }
        if (loseFlag) {
            EndScreen loseEndScreen = new EndScreen(true, this.score.getValue());
            KeyPressStoppableAnimation loseEndScreenKey =
                    new KeyPressStoppableAnimation(keyboardSensors, keyboardSensors.SPACE_KEY, loseEndScreen);
            this.animationRunner.run(loseEndScreenKey);
        } else {
            EndScreen winEndScreen = new EndScreen(false, this.score.getValue());
            KeyPressStoppableAnimation winEndScreenKey =
                    new KeyPressStoppableAnimation(keyboardSensors, keyboardSensors.SPACE_KEY, winEndScreen);
            this.animationRunner.run(winEndScreenKey);

        }

        if (this.highScoresTable.getRank(this.score.getValue()) > 0) {
            DialogManager dialog = this.animationRunner.getGui().getDialogManager();
            String name = dialog.showQuestionDialog("Name", "What is your name?", "");
            ScoreInfo scoreInfo = new ScoreInfo(name, this.score.getValue());
            this.highScoresTable.add(scoreInfo);

            try {
                this.highScoresTable.save(new File("highscores.txt"));
            } catch (IOException e) {
                System.out.println("Something went wrong while writing to 'highscores.txt' File.");
            }
        }

        HighScoresAnimation highScoresAnimation = new HighScoresAnimation(this.highScoresTable);
        KeyPressStoppableAnimation scoresAnimationKey =
                new KeyPressStoppableAnimation(keyboardSensors, keyboardSensors.SPACE_KEY, highScoresAnimation);
        this.animationRunner.run(scoresAnimationKey);
        return;
    }
}