package tasks;

import game.GameFlow;
import interfaces.LevelInformation;
import interfaces.Task;

import java.util.List;

/**
 * StartGameTask Class.
 * Author - Ofir Cohen.
 */
public class StartGameTask implements Task<Void> {
    private GameFlow gameFlow;
    private List<LevelInformation> levels;

    /**
     * constructor.
     *
     * @param gameFlow GameFlow Object.
     * @param levels   List of levels.
     */
    public StartGameTask(GameFlow gameFlow, List<LevelInformation> levels) {
        this.gameFlow = gameFlow;
        this.levels = levels;
    }

    /**
     * runs levels.
     *
     * @return null.
     */
    public Void run() {
        this.gameFlow.runLevels(this.levels);
        return null;
    }
}
