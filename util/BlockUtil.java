package com.msg.core.util;

import net.minecraft.world.World;

public class BlockUtil {

	public static final int META_STAIR_NORTH_FACING = 0x3;
	public static final int META_STAIR_SOUTH_FACING = 0x2;
	public static final int META_STAIR_EAST_FACING = 0x0;
	public static final int META_STAIR_WEST_FACING = 0x1;
	public static final int META_STAIR_INVERSE = 0x4;

	public static final int META_DOOR_HINGE_RIGHT = 0x0;
	public static final int META_DOOR_HINGE_LEFT = 0x1;
	public static final int META_DOOR_TOP = 0x8;
	public static final int META_DOOR_BOTTOM = 0x0;
	public static final int META_DOOR_NORTH_FACING = 0x3;
	public static final int META_DOOR_SOUTH_FACING = 0x1;
	public static final int META_DOOR_EAST_FACING = 0x0;
	public static final int META_DOOR_WEST_FACING = 0x2;

	/**
	 * Returns the y position of the highest non-air block of a given (x,z)
	 * coordinate.
	 * 
	 * @param world
	 *            world to check block y position in
	 * @param x
	 *            x position to check.
	 * @param z
	 *            z position to check.
	 * @return y position of highest non-air block for given x,z.
	 */
	public static int getTopBlockY(World world, int x, int z) {

		int y = 63;
		for (; !world.isAirBlock(x, y + 1, z); y++) {
			// intentionally empty
		}

		return y;
	}
}