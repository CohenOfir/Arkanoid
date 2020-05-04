package files;

import animations.AnimationRunner;
import biuoop.KeyboardSensor;
import game.GameFlow;
import interfaces.LevelInformation;
import interfaces.Menu;
import interfaces.Task;
import tasks.StartGameTask;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.List;

/**
 * LevelSetReader Class.
 * Author - Ofir Cohen.
 */
public class LevelSetReader {

    private AnimationRunner ar;
    private KeyboardSensor ks;
    private Menu<Task<Void>> menu;
    private String levelPath;
    private HighScoresTable scoresTable;

    /**
     *
     * @param ar          AnimationRunner.
     * @param menu        main menu.
     * @param levelPath   File path.
     * @param ks          Keyboard sensor.
     * @param scoresTable High scores table.
     */
    public LevelSetReader(AnimationRunner ar, Menu<Task<Void>> menu,
                                      String levelPath, KeyboardSensor ks, HighScoresTable scoresTable) {
        this.ar = ar;
        this.ks = ks;
        this.menu = menu;
        this.levelPath = levelPath;
        this.scoresTable = scoresTable;
    }

    /**
     * Read level set.
     */
    public void read() {
        try {
            String lineStr;
            InputStreamReader is =
                    new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(levelPath));

            LineNumberReader lineReader = new LineNumberReader(is);
            lineStr = lineReader.readLine();
            List<LevelInformation> levels;
            String[] arrayByColon;
            String leftSideStr;
            String rightSideStr = null;
            String stopButton = null;
            while (lineStr != null) {
                if (lineReader.getLineNumber() % 2 == 0) {
                    try {
                        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(lineStr);
                        InputStreamReader streamReader = new InputStreamReader(inputStream);
                        LevelSpecificationReader levelSpReader = new LevelSpecificationReader();
                        levels = levelSpReader.fromReader(streamReader);
                        StartGameTask gameTask = new StartGameTask(new GameFlow(ar, ks, scoresTable), levels);
                        menu.addSelection(stopButton, rightSideStr, gameTask);
                    } catch (Exception e) {
                        System.out.println("something went wrong while reading from set level level Text File");
                    }
                } else {
                    arrayByColon = lineStr.split(":");
                    leftSideStr = arrayByColon[0].trim();
                    stopButton = leftSideStr.substring(0, 1);
                    rightSideStr = arrayByColon[1].trim();

                }

                lineStr = lineReader.readLine();
            }
        } catch (Exception e) {
            System.out.println("couldn't read the Level Set Text File");
        }

    }
}
