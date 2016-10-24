package com.msg.core.modules;

import java.util.ArrayList;
import java.util.List;

import com.msg.core.util.BlockData;

public class Walls {
	/**
	 * 
	 * @param xWidth
	 *            x width of the structure
	 * @param zWidth
	 *            z width of the structure.
	 * @param style
	 *            3 object int array, contains 3 block ID's for corners, walls,
	 *            and then floors/accents.
	 * @return 2d int array representing the wall layout.
	 */
	public static List<BlockData[][]> gen(int xWidth, int zWidth, int[] style) {
		List<BlockData[][]> walls = new ArrayList<BlockData[][]>();
		for (int layerCount = 0; layerCount < 3; layerCount++) {
			BlockData[][] layer = new BlockData[xWidth][zWidth];
			
			// -1 is handled by generator to preserve the existing block in that
			// space.
			BlockData[] outerLayer = new BlockData[zWidth];
			for (int i = 0; i < zWidth; i++)
				outerLayer[i] = new BlockData(-1, -1);
			layer[0] = outerLayer;
			// create the floor 2d int array.
			for (int xOffset = 1; xOffset < xWidth - 1; xOffset++) {
				// when first or last row, use corner+wall for block types,
				// otherwise use walls+floors
				int styleSet = (xOffset == 1 || xOffset == xWidth - 2) ? 0 : 1;
				// write non-writing block around edge of layer.
				layer[xOffset][0] = new BlockData(-1, -1);
				
				layer[xOffset][1] = new BlockData(style[styleSet], -1);
				for (int zOffset = 2; zOffset < zWidth - 2; zOffset++) {
					layer[xOffset][zOffset] = new BlockData(style[styleSet + 1], -1);
				}
				layer[xOffset][zWidth - 2] = new BlockData(style[styleSet], -1);
				
				// write non-writing block around edge of layer.
				layer[xOffset][zWidth - 1] = new BlockData(-1, -1);
			}
			layer[xWidth - 1] = outerLayer;
			
			walls.add(layer);
		}
		return walls;
	}
}
