package model;

import java.util.Objects;

/**
 * Represents the block 3x3 of the gaming board.
 * @author Evgeniia Shcherbinina
 *
 */
public class Block {
	
	private final static String emptyBlock = ".........";
	private final static int size = 9;
	
	/**
	 * A block is represented as a string.
	 */
	private String myBlock;

	/**
	 * Constructor.
	 * @param theBlock
	 */
	public Block(String theBlock) {
		myBlock = theBlock;
	}
	
	/**
	 * An empty constructor.
	 */
	public Block() {
		this(emptyBlock);
	}
	
	/**
	 * Returns the string representation of the block.
	 * @return
	 */
	public String getValue() {
		return myBlock;
	}
	
	/**
	 * Returns the token (w or b or empty(.)) at the given position.
	 * @param position
	 * @return a character of the string at the given position
	 */
	public char getValueAt(int position) {
		return myBlock.charAt(position - 1);
	}
	
	/**
	 * Whether it is legal to move to the given position.
	 * @param position
	 * @return true if the position is empty, false otherwise
	 */
	public boolean isLegalMove(int position) {
		return myBlock.charAt(position - 1) == '.';
	}
	
	/**
	 * Sets the given token at the given position.
	 * @param position
	 * @param token
	 * @return a new block, which contains all previous moves and the current move.
	 */
	public Block move(int position, char token) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			if (i == position -1) {
				sb.append(token);
			} else {
				sb.append(myBlock.charAt(i));
			}
		}
		return new Block(sb.toString());
	}
	
	/**
	 * Rotates a block to the left.
	 * @return a new block after the left rotation
	 */
	public Block rotateLeft() {
		StringBuilder sb = new StringBuilder();
		sb.append(myBlock.charAt(2));
		sb.append(myBlock.charAt(5));
		sb.append(myBlock.charAt(8));
		sb.append(myBlock.charAt(1));
		sb.append(myBlock.charAt(4));
		sb.append(myBlock.charAt(7));
		sb.append(myBlock.charAt(0));
		sb.append(myBlock.charAt(3));
		sb.append(myBlock.charAt(6));
		return new Block(sb.toString());
	}
	
	/**
	 * Rotates a block to the right.
	 * @return a new block after the right rotation
	 */
	public Block rotateRight() {
		StringBuilder sb = new StringBuilder();
		sb.append(myBlock.charAt(6));
		sb.append(myBlock.charAt(3));
		sb.append(myBlock.charAt(0));
		sb.append(myBlock.charAt(7));
		sb.append(myBlock.charAt(4));
		sb.append(myBlock.charAt(1));
		sb.append(myBlock.charAt(8));
		sb.append(myBlock.charAt(5));
		sb.append(myBlock.charAt(2));
		return new Block(sb.toString());
	}
	
	@Override
    public boolean equals(final Object theOther) {        
        boolean returnValue = false;
        
        if (this == theOther) {
            returnValue = true;
        } else if (theOther != null && this.getClass() == theOther.getClass()) {
            final Block otherBlock = (Block) theOther;
            
            returnValue = Objects.equals(myBlock, otherBlock.myBlock);
        } 
        
        return returnValue;
    } 
}
