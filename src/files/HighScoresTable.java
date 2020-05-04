package files;

import indicators.ScoreInfo;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * HighScoresTable Class.
 * Author - Ofir Cohen.
 */
public class HighScoresTable implements Serializable {

    private int tableSize;
    private List<ScoreInfo> scoresInfoList;

    /**
     * Constructor.
     *
     * @param tableSize - table tableSize.
     */
    public HighScoresTable(int tableSize) {
        this.tableSize = tableSize;
        this.scoresInfoList = new ArrayList<ScoreInfo>();
    }

    /**
     * @param score - high-score to be added.
     */
    public void add(ScoreInfo score) {
        int rank = getRank(score.getScore());
        if (rank > 0) {
            scoresInfoList.add((rank - 1), score);
        }
    }

    /**
     * @return current amount of scores stored in table
     */
    public int size() {
        if (this.scoresInfoList.size() < tableSize) {
            return this.scoresInfoList.size();
        } else {
            return tableSize;
        }
    }

    /**
     * @return current high scores.
     */
    public List<ScoreInfo> getHighScores() {
        return this.scoresInfoList;
    }

    /**
     * @param score - score to compare with past scores.
     * @return - rank index, if score too low returns -1.
     */
    public int getRank(int score) {
        for (int i = 1; i <= size(); i++) {
            if (score > scoresInfoList.get(i - 1).getScore()) {
                return i;
            }
        }

        if (size() < tableSize) {
            return (size() + 1);
        }

        return -1;
    }

    /**
     * Clear table.
     */
    public void clear() {
        this.scoresInfoList.clear();
    }

    /**
     * Load table data from file.
     *
     * @param filename the file's path which contains the Table
     * @throws IOException might throw IOException
     */
    public void load(File filename) throws IOException {
        HighScoresTable scoresTable = HighScoresTable.loadFromFile(filename);
        if (scoresTable != null) {
            this.scoresInfoList = scoresTable.scoresInfoList;
            this.tableSize = scoresTable.tableSize;
        }
    }

    /**
     * @param filename file's path.
     * @throws IOException throw IOException.
     */
    public void save(File filename) throws IOException {
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(filename);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
        } catch (Exception e) {
            System.out.println("Failed writing to file.");
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    System.out.println("Failed closing the file!");
                }
            }
        }
    }

    /**
     * Read a table from file and return it.If the file does not exist, or there is a problem with
     * reading it, an empty table is returned.
     *
     * @param filename file's path
     * @return HighScoresTable, if failed - null.
     */
    public static HighScoresTable loadFromFile(File filename) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (HighScoresTable) objectInputStream.readObject();
        } catch (Exception e) {
            try {
                filename.createNewFile();
            } catch (IOException e1) {
                System.out.println("Failed to create new File");
            }
            HighScoresTable highScoresTable = new HighScoresTable(5);
            try {
                highScoresTable.save(filename);
            } catch (IOException e2) {
                System.out.println("Failed to save new File");
            }
            return highScoresTable;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                System.out.println("Failed to close File");
            }
        }
    }
}
