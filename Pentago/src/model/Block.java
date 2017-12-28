package model;

public class Block {
	
	private final static String emptyBlock = ".........";
	private final static int size = 9;
	
	private String myBlock;

	public Block(String theBlock) {
		myBlock = theBlock;
	}
	
	public Block() {
		this(emptyBlock);
	}
	
	public String getValue() {
		return myBlock;
	}
	
	public char getValueAt(int position) {
		return myBlock.charAt(position - 1);
	}
	
	public boolean isLegalMove(int position) {
		return myBlock.charAt(position - 1) == '.';
	}
	
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
}
