package files;

import assistclasses.ColorParser;
import assistclasses.Velocity;
import backgrounds.LevelBackground;
import interfaces.LevelInformation;
import interfaces.Sprite;
import spritesandcollidables.Block;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * LevelSpecificationReader Class.
 * Author - Ofir Cohen.
 */
public class LevelSpecificationReader {


    /**
     * @param reader java.io.Reader.
     * @return List blocksLevelDescription
     */
    public static ArrayList<String> blocksDescription(BufferedReader reader) {
        ArrayList<String> levelLines = null;
        String startLevelLine;
        try {
            levelLines = new ArrayList<String>();
            while (true) {
                startLevelLine = reader.readLine().trim();
                if (startLevelLine.equals("START_LEVEL") || startLevelLine == null) {
                    break;
                }
            }
            String endLevelLine;
            if (startLevelLine.equals("START_LEVEL")) {
                endLevelLine = reader.readLine().trim();
            } else {
                endLevelLine = startLevelLine;
            }
            while (true) {
                if (endLevelLine == null) {
                    return null;
                } else if (endLevelLine.equals("END_LEVEL")) {
                    break;
                } else {
                    if (!endLevelLine.isEmpty()) {
                        levelLines.add(endLevelLine);
                    }
                }
                endLevelLine = reader.readLine().trim();
            }
        } catch (IOException e) {
            System.out.println(" Failed reading file");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Failed closing File");
                }
            }
        }
        return levelLines;
    }

    /**
     * @param reader File reader.
     * @return List<LevelInformation>.
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        BufferedReader bufferedReader = new BufferedReader(reader);
        List<LevelInformation> levelsInfoAfterParse = new ArrayList<>();
        List<List<String>> levelTxtLines = new ArrayList<>();
        try {
            levelTxtLines = levelDescription(bufferedReader);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        try {
            for (int i = 0; i < levelTxtLines.size(); i++) {
                LevelInformation levelAfterParse = levelInformationCreator(levelTxtLines.get(i));
                levelsInfoAfterParse.add(levelAfterParse);
            }
        } catch (NullPointerException e) {
            System.out.println("Level txt File is empty");
        }
        return levelsInfoAfterParse;
    }

    /**
     * @param reader File reader.
     * @return List levelDescription.
     */
    public static List<List<String>> levelDescription(BufferedReader reader) {
        List<List<String>> levelLines = new ArrayList<>();
        String startLevelLine;
        try {
            startLevelLine = reader.readLine().trim();
        } catch (IOException e) {
            System.out.println("couldn't read level definition text file");
            return null;
        }
        while (startLevelLine != null) {
            List<String> oneLevelInfo = new ArrayList<>();
            try {
                while (true) {
                    startLevelLine = reader.readLine().trim();
                    if (startLevelLine.contains("START_LEVEL") || startLevelLine == null) {
                        break;
                    }
                }
                String endLevelLine;
                if (startLevelLine.equals("START_LEVEL")) {
                    endLevelLine = reader.readLine().trim();
                } else {
                    endLevelLine = startLevelLine;
                }
                while (true) {
                    if (endLevelLine == null) {
                        return null;
                    } else if (endLevelLine.equals("END_LEVEL")) {
                        break;
                    } else {
                        if (!endLevelLine.isEmpty()) {
                            oneLevelInfo.add(endLevelLine);
                        }
                    }
                    endLevelLine = reader.readLine().trim();
                }
                levelLines.add(oneLevelInfo);
                startLevelLine = endLevelLine;
            } catch (IOException e) {
                System.out.println(" Failed reading file");
            } catch (NullPointerException e) {
                return levelLines;
            }
        }
        return levelLines;
    }

    /**
     *
     * @param levelLines level description.
     * @return levelLinesMap.
     */
    public Map<String, String> mapLevelLines(List<String> levelLines) {
        Map<String, String> levelLinesMap = new TreeMap<>();
        String[] stringArray;
        for (String line : levelLines) {
            if (line.contains(":")) {
                stringArray = line.split(":");
                String key = stringArray[0].trim();
                String value = stringArray[1].trim();
                levelLinesMap.put(key, value);
            }
        }
        return levelLinesMap;
    }

    /**
     * @param levelLinesMap map describes level line descriptions.
     * @return level name String.
     */
    public String levelNameParser(Map<String, String> levelLinesMap) {
        String levelName = "level_name";
        return levelLinesMap.get(levelName).trim();
    }

    /**
     *
     * @param levelLinesMap map describes level line descriptions.
     * @return Level Ball Velocities.
     */
    public List<Velocity> ballVelocitiesParser(Map<String, String> levelLinesMap) {
        String ballVelocity = "ball_velocities";
        String ballVelocityValue = levelLinesMap.get(ballVelocity).trim();
        List<Velocity> velocities = new ArrayList<>();
        for (String velocity : ballVelocityValue.split(" ")) {
            String[] dxDy = velocity.split(",");
            Velocity vel = new Velocity(Double.parseDouble(dxDy[0]), Double.parseDouble(dxDy[1]));
            Velocity vel2 = Velocity.fromAngleAndSpeed(vel.getDx(), vel.getDy());
            velocities.add(new Velocity(vel2.getDx() / 60, vel2.getDy() / 60));
        }
        return velocities;
    }

    /**
     *
     * @param levelLinesMap map describes level line descriptions.
     * @return interfaces.Sprite.
     */
    public Sprite backgroundParser(Map<String, String> levelLinesMap) {
        Sprite backGround;
        String backgroundValue = levelLinesMap.get("background").trim();
        String imageStr;
        Image image;
        LevelBackground levelBackground = null;
        Color color = null;
        if (backgroundValue.contains("image")) {
            imageStr = backgroundValue.trim().replace("image(", "").replace(")", "");
            try {
                image = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(imageStr));
                levelBackground = new LevelBackground(null, image);
            } catch (IOException e) {
                System.out.println("Failed uploading background image");
                System.exit(1);
            }
        } else {
            ColorParser colorParser = new ColorParser();
            try {
                color = colorParser.colorFromString(backgroundValue.trim());
            } catch (Exception e) {
                System.out.println("Failed parsing color");
                e.getMessage();
            }
            levelBackground = new LevelBackground(color, null);
        }
        backGround = levelBackground;
        return backGround;
    }

    /**
     *
     * @param levelLinesMap map describes level line descriptions.
     * @return int paddle's speed.
     */
    public int paddleSpeedParser(Map<String, String> levelLinesMap) {
        String paddleSpeed = levelLinesMap.get("paddle_speed").trim();
        return Integer.parseInt(paddleSpeed);
    }

    /**
     *
     * @param levelLinesMap map describes level line descriptions.
     * @return Int paddle's width.
     */
    public int paddleWidthParser(Map<String, String> levelLinesMap) {
        String paddleWidth = levelLinesMap.get("paddle_width").trim();
        return Integer.parseInt(paddleWidth);
    }


    /**
     *
     * @param levelLinesMap map describes level line descriptions.
     * @return Int Level's Blocks number
     */
    public int numBlocksParser(Map<String, String> levelLinesMap) {
        String numBlocs = levelLinesMap.get("num_blocks").trim();
        return Integer.parseInt(numBlocs);
    }

    /**
     *
     * @param blockDefLines map describes level.
     * @param blocksChars   member describes the symbol of a Block.
     * @return List of level Blocks.
     */
    public List<Block> blocksListParser(Map<String, String> blockDefLines, List<String> blocksChars) {
        List<Block> blockList = new ArrayList<Block>();
        int xPos = Integer.parseInt(blockDefLines.get("blocks_start_x"));
        int yPos = Integer.parseInt(blockDefLines.get("blocks_start_y"));
        int yPosIndicator = yPos;
        int xPosIndicator;
        int rowsHeights = Integer.parseInt(blockDefLines.get("row_height"));
        String blockFile = blockDefLines.get("block_definitions");
        BlocksFromSymbolsFactory blocksFromSymbolsFactory = null;
        try {
            //FileInputStream fileInputStream = new FileInputStream(new File(blockFile));
            InputStreamReader inputStreamReader = new InputStreamReader(ClassLoader.getSystemClassLoader().
                    getResourceAsStream(blockFile));
            blocksFromSymbolsFactory = BlocksDefinitionReader.fromReader(inputStreamReader);

        } catch (Exception e) {
            System.out.println("Something went wrong whole loading file");
        }
        int startLine = 0;
        int endLine = 0;
        for (int i = 0; i < blocksChars.size(); i++) {
            if (!(blocksChars.get(i).contains("START_BLOCKS"))) {
                startLine++;
                continue;
            }
            startLine++;
            break;
        }
        for (int i = startLine; i < blocksChars.size(); i++) {
            if (!(blocksChars.get(i).contains("END_BLOCKS"))) {
                endLine++;
                continue;
            }
            break;
        }
        endLine = endLine + startLine;
        for (int i = startLine; i < endLine; i++) {
            String blocksRow = blocksChars.get(i);
            xPosIndicator = xPos;
            for (int j = 0; j < blocksRow.length(); j++) {
                String symbol = Character.toString(blocksRow.charAt(j));
                if (blocksFromSymbolsFactory.isSpaceSymbol(symbol)) {
                    xPosIndicator += blocksFromSymbolsFactory.getSpaceWidth(symbol);
                    continue;
                }
                if (blocksFromSymbolsFactory.isBlockSymbol(symbol)) {
                    Block block = blocksFromSymbolsFactory.getBlock(symbol, xPosIndicator, yPosIndicator);
                    blockList.add(block);
                    xPosIndicator += block.getWidth();
                    continue;
                } else {
                    System.out.println("Error - you've entered a false block or a false space block symbol.");
                    // System.exit(-1);
                    continue;
                }
            }
            yPosIndicator += rowsHeights;
            xPosIndicator = xPos;
        }
        return blockList;
    }

    /**
     * @param levelLines List contains all level lines of level definition File.
     * @return the interfaces.LevelInformation of the level.
     */
    public LevelInformation levelInformationCreator(List<String> levelLines) {
        Map<String, String> levelLinesMap = mapLevelLines(levelLines);
        String thisLevelName = levelNameParser(levelLinesMap);
        int thisPaddleSpeed = paddleSpeedParser(levelLinesMap);
        int thisPaddleWidth = paddleWidthParser(levelLinesMap);
        List<Velocity> ballsVelocities = ballVelocitiesParser(levelLinesMap);
        Sprite thisBackground = backgroundParser(levelLinesMap);
        int blockToRemove = numBlocksParser(levelLinesMap);
        List<Block> blocksAfterParse = blocksListParser(levelLinesMap, levelLines);
        return new LevelInfoReader(ballsVelocities, thisPaddleSpeed, thisPaddleWidth, thisLevelName,
                thisBackground, blocksAfterParse, blockToRemove);
    }

}
