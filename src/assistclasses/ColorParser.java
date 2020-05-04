package assistclasses;

import java.awt.Color;

/**
 * ColorParser Class.
 * Author - Ofir Cohen.
 */
public class ColorParser {

    /**
     * @param string describes a Color
     * @return Color.
     */
    public static Color colorFromString(String string)  {
        string = string.substring("color(".length());

        if (string.startsWith("RGB(")) {
            string = string.substring("RGB(".length());
            string = string.replace("))", "");
            String[] parts = string.split(",");
            if (!(parts.length == 3)) {
                System.out.println("RGB doesn't have 3 definitions");
            }
            int r = Integer.valueOf(parts[0]);
            int g = Integer.valueOf(parts[1]);
            int b = Integer.valueOf(parts[2]);
            return new Color(r, g, b);
        } else {
            string = string.replace(")", "");
            if (string.equals("black")) {
                return Color.BLACK;
            } else if (string.equals("white")) {
                return Color.WHITE;
            } else if (string.equals("cyan")) {
                return Color.CYAN;
            } else if (string.equals("blue")) {
                return Color.BLUE;
            } else if (string.equals("gray")) {
                return Color.GRAY;
            } else if (string.equals("lightGray")) {
                return Color.LIGHT_GRAY;
            } else if (string.equals("orange")) {
                return Color.ORANGE;
            } else if (string.equals("yellow")) {
                return Color.YELLOW;
            } else if (string.equals("pink")) {
                return Color.PINK;
            } else if (string.equals("green")) {
                return Color.GREEN;
            } else if (string.equals("red")) {
                return Color.RED;
            } else {
                System.out.println("color not defined");
            }
        }
        return null;
    }
}