package com.msg.core.util;

/**
 * Block data class for storing and retrieving data about a block and its
 * metadata.
 * 
 * @author Martin Lowe
 *
 */
public class BlockData {
	private int blockID;
	private int metadata;
	private boolean isStairs = false;

	/**
	 * Default constructor for BlockData, sets the initial blockID and metadata.
	 * 
	 * @param blockID
	 *            numerical blockID for given block
	 * @param metadata
	 *            metadata value for the block (between 0-15)
	 */
	public BlockData(int blockID, int metadata) {
		this.blockID = blockID;
		this.metadata = metadata;
	}
	
	/**
	 * Overloaded constructor for BlockData, sets the block data as a stair type.
	 * 
	 * @param blockID
	 *            numerical blockID for given block
	 * @param metadata
	 *            metadata value for the block (between 0-15)
	 */
	public BlockData(int blockID, int metadata, boolean isStairs) {
		this.blockID = blockID;
		this.metadata = metadata;
		this.isStairs = isStairs;
	}

	/**
	 * @return the blockID
	 */
	public int getBlockID() {
		return blockID;
	}

	/**
	 * @param blockID
	 *            the blockID to set
	 */
	public void setBlockID(int blockID) {
		this.blockID = blockID;
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
	 * @return 
	 */
	public void setStairs(boolean isStairs) {
		this.isStairs = isStairs;
	}

	public BlockData rotateStairs() {
		if (isStairs) {
			metadata+=2;
		}
		return this;
	}

}
