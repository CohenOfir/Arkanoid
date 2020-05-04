package interfaces;

/**
 * Describes Menu interface.
 *
 * @param <T> Generic object.
 */
public interface Menu<T> extends Animation {
    /**
     * add a new selection to the main menu.
     *
     * @param key       - key trigger animation.
     * @param message   - Describes Animation.
     * @param returnVal T - creating and running the specific animation.
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * @return status (play a game, show high score, quit).
     */
    T getStatus();

    /**
     * @param key     - key trigger animation.
     * @param message - Describes Animation.
     * @param subMenu - creating and displaying the specific sub menu
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);
}