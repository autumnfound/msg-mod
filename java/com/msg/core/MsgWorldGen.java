package com.msg.core;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.msg.core.util.BlockData;
import com.msg.core.util.BlockUtil;
import com.msg.core.util.PerlinNoise;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class MsgWorldGen implements IWorldGenerator {
	private MsgFactory factory;
	private PerlinNoise perlin;

	public MsgWorldGen() {
		super();
		factory = new MsgFactory();
		perlin = new PerlinNoise();
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
		// pick an (x,z) for building to spawn.
		int x = chunkX * 16 + random.nextInt(16);
		int z = chunkZ * 16 + random.nextInt(16);

		int xWidth = random.nextInt(6) + 8;
		int zWidth = random.nextInt(6) + 8;

		float freq = 60f; // smaller means it will spawn more often
		float p = perlin.noise2(x / freq, z / freq);

		if (p > 0.85f) {
			System.out.println(p + " x:" + x + " z:" + z);
		}
		if (p < 0.91f) {
			// not attempting at this x,z
			return;
		}
		System.out.println("Attempting to spawn a building...");

		int maxVariance = 3;
		int y = (int) BlockUtil.getTopBlockY(world, x, z);
		if (!world.getBlock(x, y - 1, z).isBlockNormalCube()) {
			return; // above liquid, do not spawn
		}
		int dX = xWidth / 2;
		int dZ = zWidth / 2;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (Math.abs(BlockUtil.getTopBlockY(world, x - dX * i, z - dZ * j) - y) > maxVariance) {
					return; // too much height variance
				}
			}
		}

		List<BlockData[][]> struct = factory.createStructure(xWidth, zWidth);
		if (struct != null) {
			placeStructure(struct, world, x, y, z);
			// backFillStructure(struct, world, x, y, z);
		}

		System.out.println("[MSG] Spawned a building!! (" + x + "," + y + "," + z + ")");
	}

	/**
	 * Places structure in the world based on the structure array list passed in
	 * from the generate method.
	 * 
	 * @param structure
	 *            arraylist containing layers of the structure to place.
	 * @param world
	 *            world object to place the structure into.
	 * @param x
	 *            X coordinate of the corner of the structure.
	 * @param y
	 *            Y coordinate of the corner of the structure.
	 * @param z
	 *            Z coordinate of the corner of the structure.
	 */
	private void placeStructure(List<BlockData[][]> structure, World world, int x, int y, int z) {
		if (structure != null) {
			int i = 0;
			for (Iterator<BlockData[][]> iter = structure.iterator(); iter.hasNext(); i++) {
				BlockData[][] layer = iter.next();
				for (int xOffset = 0; xOffset < layer.length; xOffset++) {
					for (int zOffset = 0; zOffset < layer[xOffset].length; zOffset++) {
						if (layer[xOffset][zOffset].getBlockID() >= 0) {
							world.setBlock(x + xOffset, y + i, z + zOffset,
									Block.getBlockById(layer[xOffset][zOffset].getBlockID()));
							if (layer[xOffset][zOffset].getMetadata() >= 0) {
								world.setBlockMetadataWithNotify(x + xOffset, y + i, z + zOffset, layer[xOffset][zOffset].getMetadata(), 3);
							}
						}
					}
				}
			}
		}
	}

}
