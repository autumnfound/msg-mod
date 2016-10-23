package com.msg.core.modules;

import com.msg.core.util.BlockData;

public class Floor {
	/**
	 * 
	 * @param x
	 *            x width of the structure
	 * @param z
	 *            z width of the structure.
	 * @param style
	 *            3 object int array, contains 3 block ID's for corners, walls,
	 *            and then floors/accents.
	 * @return 2d int array representing the floor layout.
	 */
	public static BlockData[][] gen(int x, int z, int[] style) {
		BlockData[][] floor = new BlockData[x][z];
		// create the outer layer for the floor layer
		
		// -1 is handled by generator to preserve the existing block in that
		// space.
		BlockData[] outerLayer = new BlockData[z];
		for (int i = 0; i < z; i++)
			outerLayer[i] = new BlockData(-1, -1);
		floor[0] = outerLayer;
		// create the floor 2d int array.
		for (int i = 1; i < x - 1; i++) {
			// when first or last row, use corner+wall for block types,
			// otherwise use walls+floors
			int styleSet = (i == 1 || i == x - 2) ? 0 : 1;
			floor[i][0] = new BlockData(-1, -1);
			floor[i][1] = new BlockData(style[styleSet], -1);
			for (int j = 2; j < z - 2; j++)
				floor[i][j] = new BlockData(style[styleSet + 1], -1);
			floor[i][z - 2] = new BlockData(style[styleSet], -1);
			floor[i][z - 1] = new BlockData(-1, -1);
		}
		floor[x - 1] = outerLayer;

		return floor;
	}
}
