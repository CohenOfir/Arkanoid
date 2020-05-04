package interfaces;

import spritesandcollidables.Block;

/**
 * Block Creator interface.
 * Author - Ofir Cohen.
 */
public interface BlockCreator {
    /**
     * Create a block at the specified location.
     *
     * @param xpos x coordinate of the upperleft Point
     * @param ypos y coordinate of the upperleft Point
     * @return  void
     */
    Block create(int xpos, int ypos);

}
