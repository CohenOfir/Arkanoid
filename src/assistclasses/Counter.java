package assistclasses;

/**
 * Counter Class.
 * author - Ofir Cohen.
 */
public class Counter {

    private int count;

    /**
     * constructor.
     *
     * @param count - the counter
     */
    public Counter(int count) {
        this.count = count;
    }

    /**
     * @param number the number we want to add
     */
    public void increase(int number) {
        this.count += number;
    }

    /**
     * @param number - number we want to subtract.
     */
    public void decrease(int number) {
        this.count -= number;
    }

    /**
     * @return this.count
     */
    public int getValue() {
        return this.count;
    }
}
