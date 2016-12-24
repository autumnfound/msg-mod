package com.msg.core.util;

import net.minecraft.block.Block;

/**
 * Block data class for storing and retrieving data about a block and its
 * metadata.
 * 
 * @author Martin Lowe
 *
 */
public class BlockData {
	private Block block;
	private int metadata;
	private boolean isStairs = false;
	private boolean isDoor;

	/**
	 * Default constructor for BlockData, sets the initial blockID and metadata.
	 * 
	 * @param blockID
	 *            numerical blockID for given block
	 * @param metadata
	 *            metadata value for the block (between 0-15)
	 */
	public BlockData(Block block, int metadata) {
		this.block = block;
		this.metadata = metadata;
	}

	/**
	 * Overloaded constructor for BlockData, sets the block data as a stair
	 * type.
	 * 
	 * @param blockID
	 *            numerical blockID for given block
	 * @param metadata
	 *            metadata value for the block (between 0-15)
	 * @param isStairs
	 *            boolean representing whether the block is a stair block.
	 */
	public BlockData(Block block, int metadata, boolean isStairs) {
		this.block = block;
		this.metadata = metadata;
		this.isStairs = isStairs;
	}

	/**
	 * @return the blockID
	 */
	public Block getBlock() {
		return block;
	}

	/**
	 * @param blockID
	 *            the blockID to set
	 */
	public void setBlock(Block block) {
		this.block = block;
	}

	/**
	 * @return the metadata
	 */
	public int getMetadata() {
		return metadata;
	}

	/**
	 * @param metadata
	 *            the metadata to set
	 */
	public void setMetadata(int metadata) {
		if (metadata < 16 && metadata >= 0)
			this.metadata = metadata;
		else
			this.metadata = 0;
	}

	/**
	 * @return the isStairs
	 */
	public boolean isStairs() {
		return isStairs;
	}

	/**
	 * @param isStairs
	 *            the isStairs to set
	 */
	public void setStairs(boolean isStairs) {
		this.isStairs = isStairs;
	}

	/**
	 * @return the isDoor
	 */
	public boolean isDoor() {
		return isDoor;
	}

	/**
	 * @param isDoor
	 *            the isDoor to set
	 */
	public void setDoor(boolean isDoor) {
		this.isDoor = isDoor;
	}

	public BlockData rotateBlock() {
		if (isStairs && metadata >= 0) {
			int newMeta = metadata;
			if (metadata >= BlockUtil.META_STAIR_INVERSE) {
				newMeta -= BlockUtil.META_STAIR_INVERSE;
			}

			newMeta += 2;
			if (newMeta > 3) {
				newMeta = 0;
			}

			if (metadata >= BlockUtil.META_STAIR_INVERSE) {
				newMeta += BlockUtil.META_STAIR_INVERSE;
			}
			metadata = newMeta;
		} else if (isDoor && metadata >= 0) {

			int newMeta = metadata;
			if (metadata >= 8) {
				newMeta -= 8;
			}

			newMeta -= 1;
			if (newMeta < 0) {
				newMeta = 3;
			}

			if (metadata >= 8) {
				newMeta += 8;
			}
			metadata = newMeta;
		}
		return this;
	}
}