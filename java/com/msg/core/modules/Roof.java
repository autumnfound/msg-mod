package com.msg.core.modules;

import java.util.ArrayList;
import java.util.List;

import com.msg.core.util.BlockData;
import com.msg.core.util.BlockUtil;

import net.minecraft.block.Block;

public class Roof {
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
		List<BlockData[][]> roof = new ArrayList<BlockData[][]>();
		int layerCount = (int) Math.floor(xWidth / 2);
		// how many layers are needed for roof, as roof shrinks 2 per layer.
		// round the end result down
		for (int currentLayer = 0; currentLayer < layerCount; currentLayer++) {
			BlockData[][] layer = new BlockData[xWidth][zWidth];

			for (int xOffset = 0; xOffset < xWidth; xOffset++) {
				// when the x layer would denote the edge of the roof
				if (currentLayer == xOffset || xOffset == xWidth - 1 - currentLayer) {
					for (int zOffset = 0; zOffset < zWidth; zOffset++) {
						if (xOffset < layerCount) {
							// if first half of building
							layer[xOffset][zOffset] = new BlockData(Block.getBlockById(style[0]), BlockUtil.META_STAIR_EAST_FACING, true);
						} else {
							// if second half of building.
							layer[xOffset][zOffset] = new BlockData(Block.getBlockById(style[0]), BlockUtil.META_STAIR_WEST_FACING, true);
						}
					}
					// odd width, write wall blocks between stairs.
					if (xWidth % 2 == 1 && currentLayer == layerCount - 1) {
						for (int zOffset = 0; zOffset < zWidth; zOffset++) {
							// layerCount will represent middle of 0-based array
							layer[layerCount][zOffset] = new BlockData(Block.getBlockById(style[2]), -1);
						}
					}
				} else if (currentLayer > xOffset || xOffset > xWidth - 1 - currentLayer) {
					// check if current x layer denotes a z layer outside the building.
					for (int zOffset = 0; zOffset < zWidth; zOffset++) {
						// when outside of roof, write non-writing ID's
						layer[xOffset][zOffset] = new BlockData(null, -1);
					}
				} else {
					// writing inner walls between edges of roof.
					// write BlockData for outer edge of current array.
					for (int outerEdgeZ : new int[] { 0, zWidth - 1 }) {
						// checks if previous/next x layer would be a roof
						if (xOffset == currentLayer + 1 || xOffset == xWidth - 2 - currentLayer) {
							if (xOffset < layerCount) {
								// if first half of building
								layer[xOffset][outerEdgeZ] = new BlockData(Block.getBlockById(style[0]),
										BlockUtil.META_STAIR_WEST_FACING + BlockUtil.META_STAIR_INVERSE, true);
							} else {
								// if second half of building.
								layer[xOffset][outerEdgeZ] = new BlockData(Block.getBlockById(style[0]),
										BlockUtil.META_STAIR_EAST_FACING + BlockUtil.META_STAIR_INVERSE, true);
							}
						} else {
							// when not before/after a roof layer, write non-writing
							// ID's for outer edge.
							layer[xOffset][0] = new BlockData(null, -1);
							layer[xOffset][zWidth - 1] = new BlockData(null, -1);
						}
					}
					// write inner walls as well as air within the building.
					layer[xOffset][1] = new BlockData(Block.getBlockById(style[1]), -1);
					for (int zOffset = 2; zOffset < zWidth - 2; zOffset++) {
						layer[xOffset][zOffset] = new BlockData(Block.getBlockById(0), -1);
					}
					layer[xOffset][zWidth - 2] = new BlockData(Block.getBlockById(style[1]), -1);
				}
			}
			roof.add(layer);
		}
		return roof;
	}
}
