package com.msg.core.modules;

import java.util.ArrayList;
import java.util.List;

import com.msg.core.util.BlockData;
import com.msg.core.util.BlockUtil;

import net.minecraft.block.Block;

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
				outerLayer[i] = new BlockData(null, -1);
			layer[0] = outerLayer;
			// create the floor 2d int array.
			for (int xOffset = 1; xOffset < xWidth - 1; xOffset++) {
				// when first or last row, use corner+wall for block types,
				// otherwise use walls+floors
				int styleSet = (xOffset == 1 || xOffset == xWidth - 2) ? 0 : 1;
				// write non-writing block around edge of layer.
				layer[xOffset][0] = new BlockData(null, -1);

				layer[xOffset][1] = new BlockData(Block.getBlockById(style[styleSet]), -1);
				for (int zOffset = 2; zOffset < zWidth - 2; zOffset++) {
					layer[xOffset][zOffset] = new BlockData(Block.getBlockById(style[styleSet + 1]), -1);
				}
				layer[xOffset][zWidth - 2] = new BlockData(Block.getBlockById(style[styleSet]), -1);

				// write non-writing block around edge of layer.
				layer[xOffset][zWidth - 1] = new BlockData(null, -1);
			}
			layer[xWidth - 1] = outerLayer;

			walls.add(layer);
		}
		return walls;
	}

	public static List<BlockData[][]> generateStairs(List<BlockData[][]> currentRoom, int[] style) {
		// get the current room width's
		int xWidth = currentRoom.get(0).length;
		int zWidth = currentRoom.get(0)[0].length;

		boolean isXWidest = xWidth >= zWidth;

		// set offsets according to which side is the longest, and gets stair
		// direction.
		int xOffset;
		int zOffset;
		int stairMetadata;

		int count = 0;
		for (BlockData[][] layer : currentRoom) {
			if (isXWidest) {
				// start at point so stairs end at 3rd last block of interior.
				xOffset = xWidth - 4 - currentRoom.size() + count;
				// set to the interior wall.
				zOffset = 2;
				// set the metadata for the
				stairMetadata = BlockUtil.META_STAIR_EAST_FACING;
			} else {
				xOffset = 2;
				zOffset = zWidth - 4 - currentRoom.size() + count;
				stairMetadata = BlockUtil.META_STAIR_SOUTH_FACING;
			}

			// set the first block in the offset to a set of stairs w/ metadata.
			layer[xOffset][zOffset].setBlock(Block.getBlockById(style[0]));
			layer[xOffset][zOffset].setMetadata(stairMetadata);
			layer[xOffset][zOffset].setStairs(true);

			for (int i = count; i <= currentRoom.size(); i++) {
				// increment offset depending on which wall is being written to
				if (isXWidest) {
					xOffset++;
				} else {
					zOffset++;
				}

				// clear metadata and set area behind/under stairs to solid
				// block
				layer[xOffset][zOffset].setBlock(Block.getBlockById(style[1]));
				layer[xOffset][zOffset].setMetadata(-1);
			}
			count++;
		}
		return currentRoom;
	}

	/**
	 * Generate doors within the existing current room list.
	 * 
	 * @param currentRoom
	 *            current 2d array of block objects that represent the room.
	 * @param style
	 *            int array containing the blocks used in the creation of the
	 *            doors.
	 */
	public static void generateDoors(List<BlockData[][]> currentRoom, int[] style) {
		int xWidth = currentRoom.get(0).length;
		int zWidth = currentRoom.get(0)[0].length;

		boolean isXWidest = xWidth >= zWidth;

		int xOffset;
		int zOffset;

		if (isXWidest) {
			xOffset = (int) (Math.ceil(xWidth / 2));
			zOffset = zWidth - 2;
		} else {
			xOffset = xWidth - 2;
			zOffset = (int) (Math.ceil(zWidth / 2));
		}

		boolean genDoubleDoor = isXWidest ? xWidth % 2 == 0 : zWidth % 2 == 0;
		for (int i = 0; i < 2; i++) {
			BlockData[][] layer = currentRoom.get(i);
			Block blockRef = style[i] >= 0 ? Block.getBlockById(64) : null;
			// clear metadata and set area behind/under stairs to solid
			// block
			layer[xOffset][zOffset].setBlock(blockRef);
			layer[xOffset][zOffset].setMetadata(BlockUtil.META_DOOR_BOTTOM
					| (isXWidest ? BlockUtil.META_DOOR_NORTH_FACING : BlockUtil.META_DOOR_WEST_FACING));
			layer[xOffset][zOffset].setDoor(true);

			// increment offset depending on which wall is being written to
			if (genDoubleDoor) {
				if (isXWidest) {
					layer[xOffset - 1][zOffset].setBlock(blockRef);
					layer[xOffset - 1][zOffset]
							.setMetadata(BlockUtil.META_DOOR_BOTTOM | BlockUtil.META_DOOR_NORTH_FACING);
					layer[xOffset - 1][zOffset].setDoor(true);
				} else {
					layer[xOffset][zOffset - 1].setBlock(blockRef);
					layer[xOffset][zOffset - 1]
							.setMetadata(BlockUtil.META_DOOR_BOTTOM | BlockUtil.META_DOOR_WEST_FACING);
					layer[xOffset][zOffset - 1].setDoor(true);
				}
			}
		}
	}
}
