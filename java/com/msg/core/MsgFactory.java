package com.msg.core;

import java.util.ArrayList;
import java.util.List;

import com.msg.core.modules.Floor;
import com.msg.core.modules.Roof;
import com.msg.core.modules.Walls;
import com.msg.core.util.BlockData;

public class MsgFactory {

	public List<BlockData[][]> createStructure(int xWidth, int zWidth) {
		List<BlockData[][]> structure = new ArrayList<BlockData[][]>();
		
		structure.add(Floor.gen(xWidth, zWidth, new int[]{17,4,5}));
		structure.addAll(Walls.gen(xWidth, zWidth, new int[]{17,5,0}));
		structure.addAll(Roof.gen(xWidth, zWidth, new int[]{53,5,5}));
		
		/*
		int[][] z = new int[][]{{17,4,4,4,17},
		{4,5,5,5,4},
		{4,5,5,5,4},
		{4,5,5,5,4},
		{17,4,4,4,17}};
		int[][] z1 = new int[][]{{17,4,4,4,17},
			{4,0,0,0,4},
			{4,0,0,0,4},
			{4,0,0,0,4},
			{17,4,4,4,17}};
			
			structure.add(z);
			structure.add(z1);
			structure.add(z1);
			structure.add(z1);
			structure.add(z);
*/
		return structure;
	}

}
