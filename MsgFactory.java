package com.msg.core;

import java.util.ArrayList;
import java.util.List;

import com.msg.core.modules.Floor;
import com.msg.core.modules.Roof;
import com.msg.core.modules.Walls;
import com.msg.core.util.BlockData;

public class MsgFactory {

	public List<BlockData[][]> createStructure(int xWidth, int zWidth, int floorCount) {
		List<BlockData[][]> structure = new ArrayList<BlockData[][]>();

		for (int i = 0; i < floorCount; i++) {
			BlockData[][] floor = Floor.gen(xWidth, zWidth, new int[] { 17, 4, 5 });
			if (floorCount > 0 && i != 0) {
				Floor.generateStairwell(floor, new int[] { 53 });
			}

			List<BlockData[][]> walls = Walls.gen(xWidth, zWidth, new int[] { 17, 5, 0 });
			if (floorCount > 0 && i != floorCount - 1) {
				Walls.generateStairs(walls, new int[] { 53, 5 });
			}

			if (i == 0) {
				Walls.generateDoors(walls, new int[] { 64, -1 });
			}

			structure.add(floor);
			structure.addAll(walls);
		}
		structure.addAll(Roof.gen(xWidth, zWidth, new int[] { 53, 5, 5 }));

		return structure;
	}

}
