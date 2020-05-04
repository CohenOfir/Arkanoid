package indicators;

import java.io.Serializable;

/**
 * ScoreInfo Class.
 * Author - Ofir Cohen.
 */
public class ScoreInfo implements Serializable {

    private String name;
    private int score;

    /**
     * Constructor.
     *
     * @param name  - player's name.
     * @param score - player's score.
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * @return player's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return - player's score.
     */
    public int getScore() {
        return this.score;
    }
}
