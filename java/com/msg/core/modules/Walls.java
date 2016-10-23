package com.msg.core.modules;

import java.util.ArrayList;
import java.util.List;

import com.msg.core.util.BlockData;

public class Walls {
	/**
	 * 
	 * @param x
	 *            x width of the structure
	 * @param z
	 *            z width of the structure.
	 * @param style
	 *            3 object int array, contains 3 block ID's for corners, walls,
	 *            and then floors/accents.
	 * @return 2d int array representing the wall layout.
	 */
	public static List<BlockData[][]> gen(int x, int z, int[] style) {
		List<BlockData[][]> walls = new ArrayList<BlockData[][]>();
		for (int k = 0; k < 3; k++) {
			BlockData[][] layer = new BlockData[x][z];
			// create the outer layer for the wall layer
			// -1 is handled by generator to preserve the existing block in that space.
			BlockData[] outerLayer = new BlockData[z];
			for(int i = 0;i < z;i++ )
				outerLayer[i]=new BlockData(-1,-1);
			layer[0]=outerLayer;
			// create the floor 2d int array.
			for (int i = 1; i < x - 1; i++) {
				// when first or last row, use corner+wall for block types,
				// otherwise use walls+floors
				int styleSet = (i == 1 || i == x - 2) ? 0 : 1;
				layer[i][0] = new BlockData(-1,-1);
				layer[i][1] = new BlockData(style[styleSet], -1);
				for (int j = 2; j < z - 2; j++)
					layer[i][j] = new BlockData(style[styleSet + 1], -1);
				layer[i][z - 2] = new BlockData(style[styleSet],-1);
				layer[i][z - 1] = new BlockData(-1,-1);
			}
			layer[x-1]=outerLayer;
			
			walls.add(layer);
		}
		return walls;
	}
}
