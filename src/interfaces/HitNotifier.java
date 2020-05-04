package interfaces;

/**
 * Describes HitNotifier interface.
 *
 * Author - Ofir Cohen
 */
public interface HitNotifier {
    /**
     * @param hl - hit listner to be added.
     */
    void addHitListener(HitListener hl);

    /**
     * @param hl - hit listner to be removed.
     */
    void removeHitListener(HitListener hl);
}