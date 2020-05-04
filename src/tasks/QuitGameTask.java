package tasks;

import interfaces.Task;

/**
 * QuitGame Class.
 * Author - Ofir Cohen.
 */
public class QuitGameTask implements Task<Void> {

    /**
     * exits Game.
     *
     * @return null.
     */
    public Void run() {
        System.exit(1);
        return null;
    }
}
