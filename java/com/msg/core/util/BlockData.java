package com.msg.core.util;

public class BlockData {
	private int blockID;
	private int metadata;
	
	public BlockData(int blockID, int metadata) {
		this.blockID = blockID;
		this.metadata = metadata;
	}
	
	public int getMetadata() {
		return metadata;
	}
	public void setMetadata(int metadata) {
		this.metadata = metadata;
	}
	public int getBlockID() {
		return blockID;
	}
	public void setBlockID(int blockID) {
		this.blockID = blockID;
	}
}
