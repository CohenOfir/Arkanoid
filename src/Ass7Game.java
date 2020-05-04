import animations.AnimationRunner;
import animations.HighScoresAnimation;
import animations.MenuAnimation;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import files.HighScoresTable;
import files.LevelSetReader;
import interfaces.Menu;
import interfaces.Task;
import tasks.QuitGameTask;
import tasks.ShowHighScoresTask;

import java.io.File;

/**
 * Ass7Game Class.
 * Author - Ofir Cohen.
 */
public class Ass7Game {

    /**
     *
     * @param args - command line arguments.
     */
    public static void main(String[] args) {
        GUI gui = new GUI("Arkanoid", 800, 600);
        KeyboardSensor keyboardSensor = gui.getKeyboardSensor();
        AnimationRunner animationRunner = new AnimationRunner(gui, 60);

        String levelSetsPath;
        if (args.length != 0) {
            levelSetsPath = args[0];
        } else {
            levelSetsPath = "level_sets.txt";
        }


        HighScoresTable highScoresTable = new HighScoresTable(5);
        try {
            highScoresTable.load(new File("highscores.txt"));
        } catch (Exception e) {
            System.out.println("Failed to load highscores table");
        }

        HighScoresAnimation highScoresAnimation = new HighScoresAnimation(highScoresTable);


        while (true) {
            Menu<Task<Void>> mainMenu =
                    new MenuAnimation<Task<Void>>(keyboardSensor, animationRunner, "Arkanoid");
            ShowHighScoresTask showHighScoresTask =
                    new ShowHighScoresTask(animationRunner, highScoresAnimation, keyboardSensor);
            QuitGameTask quitGameTask = new QuitGameTask();
            Menu<Task<Void>> levelsMenu =
                    new MenuAnimation<Task<Void>>(keyboardSensor, animationRunner, "Level Sets");
            LevelSetReader levelSetReader =
                    new LevelSetReader(animationRunner, levelsMenu, levelSetsPath, keyboardSensor, highScoresTable);
            levelSetReader.read();

            mainMenu.addSubMenu("s", "Start", levelsMenu);
            mainMenu.addSelection("h", "HighScore", showHighScoresTask);
            mainMenu.addSelection("q", "Quit", quitGameTask);

            animationRunner.run(mainMenu);
            Task<Void> task = mainMenu.getStatus();
            task.run();
            ((MenuAnimation<Task<Void>>) mainMenu).setShouldStop(false);

        }

    }

}
