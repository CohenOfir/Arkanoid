package files;

import assistclasses.ColorParser;
import geometricshapes.Point;
import geometricshapes.Rectangle;
import interfaces.BlockCreator;
import spritesandcollidables.Block;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * BlocksDefinitionReader Class.
 * Author - Ofir Cohen.
 */
public class BlocksDefinitionReader {

    /**
     * extracts default, sdef, bdef lines into lists and returns the List.
     *
     * @param reader java.io.Reader.
     * @return ArrayList blocksLevelDescription.
     */
    public static ArrayList<ArrayList<String>> blocksLevelDescription(java.io.Reader reader) {
        BufferedReader is = null;
        String[] arraySplitter;
        ArrayList<String> defaultSeparatedBySpacesList = new ArrayList<>();
        ArrayList<String> bdefLinesList = new ArrayList<>();
        ArrayList<String> sdefLinesList = new ArrayList<>();
        ArrayList<ArrayList<String>> defaultBdefSdefList = new ArrayList<>();
        String line;
        try {
            is = new BufferedReader(reader);
            line = is.readLine().trim();
            while (line != null) {
                if (line.trim().startsWith("default")) {
                    arraySplitter = line.split(" ");
                    for (String string : arraySplitter) {
                        if (string.contains("default")) {
                            continue;
                        } else {
                            defaultSeparatedBySpacesList.add(string.trim());
                        }
                    }
                } else if (line.trim().startsWith("bdef")) {
                    bdefLinesList.add(line);
                } else if (line.trim().startsWith("sdef")) {
                    sdefLinesList.add(line);
                }
                line = is.readLine().trim();
            }
        } catch (IOException e) {
            System.out.println(" Failed reading !");
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    System.out.println("Failed closing the File");
                }
            }
            defaultBdefSdefList.add(defaultSeparatedBySpacesList);
            defaultBdefSdefList.add(bdefLinesList);
            defaultBdefSdefList.add(sdefLinesList);
            return defaultBdefSdefList;
        }
    }

    /**
     * keys to the left string, values to the right string (string:string).
     *
     * @param defaultSeparatedBySpacesList list.
     * @return Map mappingDefaultLine.
     */
    public static Map<String, String> mappingDefaultLine(ArrayList<String> defaultSeparatedBySpacesList) {
        Map<String, String> defaultLineMap = new TreeMap<>();
        String[] stringArray;
        if (defaultSeparatedBySpacesList.size() == 0) {
            return null;
        } else {
            for (String str : defaultSeparatedBySpacesList) {
                if (str.contains(":")) {
                    stringArray = str.split(":");
                    String key = stringArray[0].trim();
                    String value = stringArray[1].trim();
                    defaultLineMap.put(key, value);
                }
            }
        }
        return defaultLineMap;
    }

    /**
     * keys to the left string, values to the right string (string:string).
     *
     * @param defLinesList List<String>
     * @return Map mappingDefLines Map<String, Map<String, String>>
     */
    public static Map<String, Map<String, String>> mappingDefLines(List<String> defLinesList) {
        Map<String, String> mapPairsNoSymbol = new TreeMap<>();
        Map<String, Map<String, String>> mapBySymbols = new TreeMap<String, Map<String, String>>();
        String[] splitBySpaceArray;
        String symbolStr = null;
        for (String line : defLinesList) {
            splitBySpaceArray = line.split(" ");
            for (int i = 1; i < splitBySpaceArray.length; i++) {
                String[] splitByColonArray = splitBySpaceArray[i].split(":");
                if (splitByColonArray[0].trim().equals("symbol")) {
                    symbolStr = splitByColonArray[1].trim();
                } else {
                    mapPairsNoSymbol.put(splitByColonArray[0].trim(), splitByColonArray[1].trim());
                }
            }
            if (symbolStr == null) {
                return null;
            }
            mapBySymbols.put(symbolStr, mapPairsNoSymbol);
            mapPairsNoSymbol = new TreeMap<>();
        }
        return mapBySymbols;
    }

    /**
     * @param mappingBdefLines map(bdef definition, definition's values).
     * @return blockCreatorMap a Map with BlockCreators values.
     */
    public static Map<String, BlockCreator> creatorMap(Map<String, Map<String, String>> mappingBdefLines) {
        String firstKeys = null;
        Map<String, String> values = new TreeMap<>();
        Map<String, BlockCreator> blockCreatorMap = new TreeMap<>();
        String imageStr;
        for (Map.Entry<String, Map<String, String>> m : mappingBdefLines.entrySet()) {
            Map<Integer, Color> integerColorMap = new TreeMap<>();
            Map<Integer, Image> integerImageMap = new TreeMap<>();
            firstKeys = m.getKey();
            values = m.getValue();
            int height = Integer.parseInt(values.get("height"));
            int width = Integer.parseInt(values.get("width"));
            int hitPoints = Integer.parseInt(values.get("hit_points"));
            String strokeStr;
            if (values.containsKey("stroke")) {
                strokeStr = values.get("stroke");
            } else {

                strokeStr = null;
            }
            Color stroke = null;
            if (strokeStr != null) {
                try {
                    stroke = ColorParser.colorFromString(strokeStr);

                } catch (Exception e) {
                    e.getMessage();
                }
            }
            final Color finalStroke = stroke;
            ColorParser colorsParser = new ColorParser();
            for (String keys : values.keySet()) {
                if (keys.trim().equals("fill")) {
                    if (values.get(keys).trim().startsWith("image")) {
                        imageStr = values.get(keys).replace("image(", "").replace(")",
                                "");
                        try {
                            integerImageMap.put(1, ImageIO.read(ClassLoader.getSystemClassLoader().
                                    getResourceAsStream(imageStr)));
                        } catch (IOException e) {
                            System.out.println("Failed loading Image");
                            System.exit(1);
                        }
                    } else {
                        try {
                            integerColorMap.put(1, colorsParser.colorFromString(values.get(keys)));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            System.exit(1);
                        }
                    }
                } else if (keys.startsWith("fill-")) {
                    int k = Integer.parseInt(keys.replace("fill-", "").trim());
                    if (values.get(keys).trim().startsWith("image")) {
                        imageStr = values.get(keys).replace("image(", "").replace(")", "");
                        try {
                            integerImageMap.put(k, ImageIO.read(ClassLoader.getSystemClassLoader().
                                    getResourceAsStream(imageStr)));
                        } catch (IOException e) {
                            System.out.println("Failed loading Image");
                            System.exit(1);
                        }
                    } else {
                        try {
                            integerColorMap.put(k, colorsParser.colorFromString(values.get(keys)));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            System.exit(1);
                        }
                    }
                }
            }
            //Create anonymous Class
            BlockCreator blockCreator = new BlockCreator() {
                @Override
                public Block create(int xpos, int ypos) {
                    Rectangle rect = new Rectangle(new Point(xpos, ypos), width, height);
                    Block blockGame = new Block(rect, integerImageMap, integerColorMap, hitPoints);

                    blockGame.addColor(finalStroke);
                    return blockGame;
                }
            };
            blockCreatorMap.put(firstKeys, blockCreator);
        }
        return blockCreatorMap;
    }

    /**
     * @param mappingSdefLines map (sdef definition, definition's values)
     * @return sdefStrInteger a Map with the widths of the Spacers (in the Map's values).
     */

    public static Map<String, Integer> spacesMap(Map<String, Map<String, String>> mappingSdefLines) {
        Map<String, Integer> sdefStrInteger = new TreeMap<>();
        for (Map.Entry<String, Map<String, String>> entry : mappingSdefLines.entrySet()) {
            sdefStrInteger.put(entry.getKey(), Integer.parseInt(entry.getValue().get("width")));
        }
        return sdefStrInteger;
    }

    /**
     * @param defLinesMap    the bdef Map
     * @param defaultLineMap the default line Map
     */
    public static void missingKeyTryDefault(Map<String, Map<String, String>> defLinesMap,
                                            Map<String, String> defaultLineMap) {
        String[] defaultFields = {"width", "height", "hit_points", "stroke"};
        if (defaultLineMap != null) {
            for (Map.Entry<String, Map<String, String>> defMapEntry : defLinesMap.entrySet()) {
                for (String defaultKeyStr : defaultFields) {
                    if ((!(defMapEntry.getValue().containsKey("fill")) && !(defaultLineMap.containsKey("fill")))
                            && (!(defMapEntry.getValue().containsKey("fill-1"))
                            && !(defaultLineMap.containsKey("fill-1")))) {
                        System.out.println("Fields: \"fill or fill-1\""
                                + " are missing in both: bdef and default lines");
                        System.exit(0);
                    } else if (!(defMapEntry.getValue().containsKey("fill"))
                            && !(defMapEntry.getValue().containsKey("fill-1"))) {
                        if (defaultLineMap.containsKey("fill")) {
                            defMapEntry.getValue().put("fill", defaultLineMap.get("fill"));
                        } else if (defaultLineMap.containsKey("fill-1")) {
                            defMapEntry.getValue().put("fill-1", defaultLineMap.get("fill-1"));
                        }
                    }

                    if (!(defMapEntry.getValue().containsKey(defaultKeyStr))
                            && !(defaultLineMap.containsKey(defaultKeyStr)) && !defaultKeyStr.equals("stroke")) {
                        System.out.println("The following field: \"" + defaultKeyStr
                                + "\" is missing in both: bdef and default lines");
                    } else if (!(defMapEntry.getValue().containsKey(defaultKeyStr))
                            && (defaultLineMap.containsKey(defaultKeyStr))) {
                        defMapEntry.getValue().put(defaultKeyStr, defaultLineMap.get(defaultKeyStr));
                    }
                }
            }
        }
    }

    /**
     * @param reader Text File reader.
     * @return BlocksFromSymbolsFactory
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        List<ArrayList<String>> blocksLeveldesc = blocksLevelDescription(reader);
        if (blocksLeveldesc == null) {
            System.out.println("no lines were found in the text");
            System.exit(0);
        }
        Map<String, String> defaultLineMap = mappingDefaultLine(blocksLeveldesc.get(0));
        Map<String, Map<String, String>> bdefMap = mappingDefLines(blocksLeveldesc.get(1));
        Map<String, Map<String, String>> sdefMap = mappingDefLines(blocksLeveldesc.get(2));
        missingKeyTryDefault(bdefMap, defaultLineMap);
        Map<String, BlockCreator> blockCreatorMap = creatorMap(bdefMap);
        Map<String, Integer> stringIntegerMap = spacesMap(sdefMap);
        BlocksFromSymbolsFactory blocksFromSymbolsFactory =
                new BlocksFromSymbolsFactory(stringIntegerMap, blockCreatorMap);
        return blocksFromSymbolsFactory;
    }
}
