package interfaces;

/**.
 * Task Interface.
 * @param <T>
 */
public interface Task<T> {
    /**
     * runs the desired animation according to key press.
     *
     * @return Void.
     */
    T run();
}
