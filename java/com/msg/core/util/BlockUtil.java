package com.msg.core.util;

import net.minecraft.world.World;

public class BlockUtil {

	public static final int META_STAIR_NORTH_FACING = 2;
	public static final int META_STAIR_SOUTH_FACING = 3; //3
	public static final int META_STAIR_EAST_FACING = 1; //1
	public static final int META_STAIR_WEST_FACING = 0;
	
	public static final int META_STAIR_INVERSE = 4; 
	
	/**
	 * Returns the y position of the highest non-air block of a given (x,z) coordinate.
	 * @param world
	 * 		world to check block y position in
	 * @param x
	 * 		x position to check.
	 * @param z
	 * 		z position to check.
	 * @return
	 * 		y position of highest non-air block for given x,z.
	 */
	public static int getTopBlockY(World world, int x,int z) {
		int y = 63;
        while(!world.isAirBlock(x,y+1,z)){y++;}
        return y;
	}
}
