package files;

import interfaces.BlockCreator;
import spritesandcollidables.Block;

import java.util.Map;

/**
 * BlocksFromSymbolsFactory Class.
 * Check if symbol is block or space.
 * Author - Ofir Cohen.
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * constructor.
     *
     * @param spacerWidths  Maps of the Spaces (sdef)
     * @param blockCreators Maps of the Blocks (bdef)
     */
    public BlocksFromSymbolsFactory(Map<String, Integer> spacerWidths, Map<String, BlockCreator> blockCreators) {
        this.spacerWidths = spacerWidths;
        this.blockCreators = blockCreators;
    }

    /**
     * returns true if 's' is a valid space symbol.
     *
     * @param string String
     * @return boolean
     */
    public boolean isSpaceSymbol(String string) {
        return this.spacerWidths.containsKey(string);
    }

    /**
     * returns true if its block symbol.
     *
     * @param string String
     * @return boolean
     */
    public boolean isBlockSymbol(String string) {
        return this.blockCreators.containsKey(string);
    }

    /**
     * @param string letter of the Block
     * @param xpos   x Coordinate
     * @param ypos   y Coordinate
     * @return Block.
     */
    public Block getBlock(String string, int xpos, int ypos) {
        return this.blockCreators.get(string).create(xpos, ypos);
    }

    /**
     *
     * @param string represents letter of the block
     * @return returns spacer Width Integer.
     */
    public int getSpaceWidth(String string) {
        return this.spacerWidths.get(string);
    }
}
