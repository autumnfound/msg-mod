package com.msg.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
	private Map<Integer, Integer> currentStructures;

	public MsgWorldGen() {
		super();
		factory = new MsgFactory();
		perlin = new PerlinNoise();
		currentStructures = new HashMap<Integer, Integer>();
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
		// pick an (x,z) for building to spawn.
		int x = chunkX * 16 + random.nextInt(16);
		int z = chunkZ * 16 + random.nextInt(16);

		int xWidth = random.nextInt(10) + 8;
		int zWidth = random.nextInt(10) + 8;

		float freq = 30f; // smaller means it will spawn more often
		float p = perlin.noise2(x / freq, z / freq);

		if (p > 0.85f) {
			System.out.println(p + " x:" + x + " z:" + z);
		}
		if (p < 0.91f) {
			// not attempting at this x,z
			return;
		}

		// check map to see if a structure has spawned nearby.
		for (int i = chunkX - 1; i <= chunkX + 1; i++) {
			Integer retrievedVal = currentStructures.get(i);
			if (retrievedVal != null) {
				for (int j = chunkZ - 1; j <= chunkZ + 1; j++) {
					if (retrievedVal == j) {
						// match found, do not spawn
						return;
					}
				}
			}
		}

		System.out.println("Attempting to spawn a building...");

		int dX = xWidth / 2;
		int dZ = zWidth / 2;
		
		int maxVariance = 4;
		int y = (int) BlockUtil.getTopBlockY(world, x+dX, z+dZ);
		if (!world.getBlock(x+dX, y - 1, z+dZ).isBlockNormalCube()) {
			return; // above liquid, do not spawn
		}
		
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (Math.abs(BlockUtil.getTopBlockY(world, x - dX * i, z - dZ * j) - y) > maxVariance) {
					return; // too much height variance
				}
			}
		}

		List<BlockData[][]> struct = factory.createStructure(xWidth, zWidth);
		if (struct != null) {
			// random chance of inverting X & Z offsets
			boolean inverse = random.nextInt(100) > 50;
			
			placeStructure(struct, world, x, y, z, inverse);
			fillUnderStructure(struct, world, x, y, z, inverse);
		}
		// record where building spawned.
		currentStructures.put(chunkX, chunkZ);

		System.out.println("[MSG] Spawned a building!! (" + x + "," + y + "," + z + ")");
	}

	/**
	 * Places structure in the world based on the structure array list passed in
	 * from the generate method.
	 * 
	 * @param structure
	 *            array list containing layers of the structure to place.
	 * @param world
	 *            world object to place the structure into.
	 * @param x
	 *            X coordinate of the corner of the structure.
	 * @param y
	 *            Y coordinate of the corner of the structure.
	 * @param z
	 *            Z coordinate of the corner of the structure.
	 */
	private void placeStructure(List<BlockData[][]> structure, World world, int x, int y, int z, boolean swapXZValues) {
		if (structure != null) {
			int i = 0;
			for (Iterator<BlockData[][]> iter = structure.iterator(); iter.hasNext(); i++) {
				BlockData[][] layer = iter.next();
				for (int xOffset = 0; xOffset < layer.length; xOffset++) {
					for (int zOffset = 0; zOffset < layer[xOffset].length; zOffset++) {
						if (layer[xOffset][zOffset].getBlockID() >= 0) {
							// invert the offsets to simulate rotating the
							// buildings.
							if (swapXZValues) {
								world.setBlock(x + zOffset, y + i, z + xOffset,
										Block.getBlockById(layer[xOffset][zOffset].getBlockID()));
								// set block metadata w/ swapped x/z offsets
								if (layer[xOffset][zOffset].getMetadata() >= 0) {
									world.setBlockMetadataWithNotify(x + zOffset, y + i, z + xOffset,
											layer[xOffset][zOffset].rotateStairs().getMetadata(), 3);
								}
								// set the blocks with standard method.
							} else {
								world.setBlock(x + xOffset, y + i, z + zOffset,
										Block.getBlockById(layer[xOffset][zOffset].getBlockID()));
								// set block metadata
								if (layer[xOffset][zOffset].getMetadata() >= 0) {
									world.setBlockMetadataWithNotify(x + xOffset, y + i, z + zOffset,
											layer[xOffset][zOffset].getMetadata(), 3);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Writes blocks underneath structures to avoid floating buildings. Writes
	 * until solid block is found.
	 * 
	 * @param structure
	 *            array list containing layers of the structure to place.
	 * @param world
	 *            world object to place the structure into.
	 * @param x
	 *            X coordinate of the corner of the structure.
	 * @param y
	 *            Y coordinate of the corner of the structure.
	 * @param z
	 *            Z coordinate of the corner of the structure.
	 * @param inverse
	 *            whether the building X & Z offsets have been reversed
	 */
	private void fillUnderStructure(List<BlockData[][]> structure, World world, int x, int y, int z, boolean inverse) {
		for (int xOffset = 0; xOffset < structure.get(0).length; xOffset++) {
			for (int zOffset = 0; zOffset < structure.get(0)[0].length; zOffset++) {
				int currentY = y;
				if (!inverse) {
					while (!world.getBlock(x + xOffset, currentY - 1, z + zOffset).isBlockNormalCube()) {
						currentY--;
						world.setBlock(x + xOffset, currentY, z + zOffset, Block.getBlockById(3));
					}
				} else {
					while (!world.getBlock(x + zOffset, currentY - 1, z + xOffset).isBlockNormalCube()) {
						currentY--;
						world.setBlock(x + zOffset, currentY, z + xOffset, Block.getBlockById(3));
					}
				}
			}
		}
	}

}
