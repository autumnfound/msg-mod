package com.msg.core.modules;

import java.util.ArrayList;
import java.util.List;

import com.msg.core.util.BlockData;
import com.msg.core.util.BlockUtil;

public class Roof {
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
		List<BlockData[][]> roof = new ArrayList<BlockData[][]>();
		int layerCount = (int) Math.floor(x / 2);
		// how many layers are needed for roof, as roof shrinks 2 per layer.
		// round the end result down
		for (int i = 0; i < layerCount; i++) {
			BlockData[][] layer = new BlockData[x][z];
			for (int j = 0; j < x; j++) {
				// when the x layer would denote the edge of the roof
				if (i == j || j == x - 1 - i) {
					for (int k = 0; k < z; k++)
						if (j <= layerCount)
							layer[j][k] = new BlockData(style[0], BlockUtil.META_WEST_FACING);
						else
							layer[j][k] = new BlockData(style[0], BlockUtil.META_EAST_FACING);

					if (x % 2 == 1 && i == layerCount - 1)
						// odd width, write wall blocks in center of
						for (int k = 0; k < z; k++)
						// layercount will represent middle of 0-based array
						layer[layerCount][k] = new BlockData(style[2],-1);
				} else if (i > j || j > x - 1 - i) {
					// when outside of roof, write non-writing ID's
					for (int k = 0; k < z; k++)
						layer[j][k] = new BlockData(-1,-1);
				} else {
					// otherwise is outer wall, print wall material.
					layer[j][0] = new BlockData(-1,-1);
					layer[j][1] = new BlockData(style[1],-1);
					for (int k = 2; k < z - 2; k++)
						layer[j][k] = new BlockData(0,-1);
					layer[j][z - 2] = new BlockData(style[1],-1);
					layer[j][z - 1] = new BlockData(-1,-1);
				}
			}
			roof.add(layer);
		}

		return roof;
	}
}
