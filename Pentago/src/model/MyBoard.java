package model;

import java.util.LinkedList;
import java.util.List;

public class MyBoard {
	
	private static final int width = 6;
	
	private Block block1, block2, block3, block4;
	private int utility;

	public MyBoard(Block b1, Block b2, Block b3, Block b4) {
		block1 = b1;
		block2 = b2; 
		block3 = b3;
		block4 = b4;
		utility = 0;
	}
	
	public int calculateUtility(char maxToken) {
		int w3 = tokensInRow(this, 3, 'w') + tokensInColumn(3, 'w') + tokensInDiagonal(3, 'w');
		int b3 = tokensInRow(this, 3, 'b') + tokensInColumn(3, 'b') + tokensInDiagonal(3, 'b');
		int w4 = tokensInRow(this, 4, 'w') + tokensInColumn(4, 'w') + tokensInDiagonal(4, 'w');
		int b4 = tokensInRow(this, 4, 'b') + tokensInColumn(4, 'b') + tokensInDiagonal(4, 'b');
		int w5 = tokensInRow(this, 5, 'w') + tokensInColumn(5, 'w') + tokensInDiagonal(5, 'w');
		int b5 = tokensInRow(this, 5, 'b') + tokensInColumn(5, 'b') + tokensInDiagonal(5, 'b');
		
		if (maxToken == 'w') {
			return w3 * 15 + w4 * 30 + w5 * 100 - b3 * 15 - b4 * 30 - b5 * 100;
		} else {
			return b3 * 15 + b4 * 30 + b5 * 100 - w3 * 15 - w4 * 30 - w5 * 100;
		}
	}
	
	public void setUtility(int value) {
		utility = value;
	}
	
	public int getUtility() {
		return utility;
	}
	
	public boolean isLegalMove(int block, int position) {
		if (block == 1) {
			return block1.isLegalMove(position);
		} else if (block == 2) {
			return block2.isLegalMove(position);
		} else if (block == 3) {
			return block3.isLegalMove(position);
		} else {
			return block4.isLegalMove(position);
		}
	}
	
	public MyBoard makeMove(int block, int position, char token) {
		if (block == 1) {
			return new MyBoard(block1.move(position, token), block2, block3, block4);
		} else if (block == 2) {
			return new MyBoard(block1, block2.move(position, token), block3, block4);
		} else if (block == 3) {
			return new MyBoard(block1, block2, block3.move(position, token), block4);
		} else {
			return new MyBoard(block1, block2, block3, block4.move(position, token));
		}
	}
	
	public MyBoard rotate(int block, char direction) {
		if (block == 1 && direction == 'l') {
			return new MyBoard(block1.rotateLeft(), block2, block3, block4);
		} else if (block == 1 && direction == 'r') {
			return new MyBoard(block1.rotateRight(), block2, block3, block4);
		} else if (block == 2 && direction == 'l') {
			return new MyBoard(block1, block2.rotateLeft(), block3, block4);
		} else if (block == 2 && direction == 'r') {
			return new MyBoard(block1, block2.rotateRight(), block3, block4);
		} else if (block == 3 && direction == 'l') {
			return new MyBoard(block1, block2, block3.rotateLeft(), block4);
		} else if (block == 3 && direction == 'r') {
			return new MyBoard(block1, block2, block3.rotateRight(), block4);
		} else if (block == 4 && direction == 'l') {
			return new MyBoard(block1, block2, block3, block4.rotateLeft());
		} else {
			return new MyBoard(block1, block2, block3, block4.rotateRight());
		}
	}
	
	public String checkWin() {
		int w5 = tokensInRow(this, 5, 'w') + tokensInColumn(5, 'w') + tokensInDiagonal(5, 'w');
		int b5 = tokensInRow(this, 5, 'b') + tokensInColumn(5, 'b') + tokensInDiagonal(5, 'b');
		
		StringBuilder sb = new StringBuilder();
		if (w5 > 0) {
			sb.append('w');
		} 
		if (b5 > 0) {
			sb.append('b');
		}
		return sb.toString();
	}
	
	private int tokensInRow(MyBoard b, int numberTokens, char token) {
		int result = 0;
		result += tokensInRow(numberTokens, token, b.block1, b.block2);
		result += tokensInRow(numberTokens, token, b.block3, b.block4);
		return result;
	}
	
	private int tokensInRow(int numberTokens, char token, Block first, Block second) {
		int result = 0;		
		String[] sequences = buildSequence(numberTokens, token);
		
		for (int i = 0; i < width / 2; i++) {
			StringBuilder sb = new StringBuilder();
			if (i == 2) {
				sb.append(first.getValue().substring(i * width / 2));
				sb.append(second.getValue().substring(i * width / 2));
			} else {
				sb.append(first.getValue().substring(i * width / 2, i + width / 2));			
				sb.append(second.getValue().substring(i * width / 2, i + width / 2));
			}
			if (sb.toString().contains(sequences[0]) || sb.toString().contains(sequences[1])) {
				result++;
			}
		}
		return result;
	}
	
	private String[] buildSequence(int numberTokens, char token) {
		String[] s = new String[2];
		
		StringBuilder sequence = new StringBuilder();
		for (int i = 0; i < numberTokens; i++) {
			sequence.append(token);
		}
		
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		if (numberTokens == 3) { 
			sb1.append(".."); // a sequence of tokens with an empty space in the beginning
			sb1.append(sequence); 	
			sb2.append(sequence); // a sequence of tokens with an empty space in the end	
			sb2.append("..");
			
		} else if (numberTokens == 4) {
			sb1.append("."); // a sequence of tokens with an empty space in the beginning
			sb1.append(sequence); 	
			sb2.append(sequence); // a sequence of tokens with an empty space in the end	
			sb2.append(".");
			
		} else { // a sequence of 5 tokens without an empty space
			sb1 = sequence;
			sb2 = sequence;
		}
		s[0] = sb1.toString();
		s[1] = sb2.toString();
		
		return s;
	}
	
	private int tokensInColumn(int numberTokens, char token) {
		MyBoard afterRightRotation = new MyBoard(block3.rotateRight(), block1.rotateRight(), block4.rotateRight(),
				block2.rotateRight());
		return tokensInRow(afterRightRotation, numberTokens, token);
	}
	
	private int tokensInDiagonal(int numberTokens, char token) {
		int result = 0;
		String[] sequences = buildSequence(numberTokens, token);
		List<String> allStrings = buildDiagonalStrings();
		
		for (String s: allStrings) {
			if (s.contains(sequences[0]) || s.contains(sequences[1])) {
				result++;
			}
		}
		return result;
	}
	
	private List<String> buildDiagonalStrings() {
		List<String> allStrings = new LinkedList<>();
		
		int [] positions = {2, 6};
		StringBuilder sb = new StringBuilder();
		sb.append(buildString(block1, positions));
		sb.append(block2.getValueAt(7));
		sb.append(buildString(block4, positions));
		allStrings.add(sb.toString());
		
		int[] positions1 = {1, 5, 9};
		sb = new StringBuilder();
		sb.append(buildString(block1, positions1));
		sb.append(buildString(block4, positions1));
		allStrings.add(sb.toString());
		
		int[] positions2 = {4, 8};
		sb = new StringBuilder();
		sb.append(buildString(block1, positions2));
		sb.append(block3.getValueAt(3));
		sb.append(buildString(block4, positions2));
		allStrings.add(sb.toString());
		
		int[] positions3 = {2, 4};
		sb = new StringBuilder();
		sb.append(buildString(block2, positions3));
		sb.append(block1.getValueAt(9));
		sb.append(buildString(block3, positions));
		allStrings.add(sb.toString());
		
		int[] positions4 = {3, 5, 7};
		sb = new StringBuilder();
		sb.append(buildString(block2, positions4));
		sb.append(buildString(block3, positions4));
		allStrings.add(sb.toString());
		
		int[] positions5 = {6, 8};
		sb = new StringBuilder();
		sb.append(buildString(block2, positions5));
		sb.append(block4.getValueAt(1));
		sb.append(buildString(block3, positions5));
		
		return allStrings;
	}
	
	private String buildString(Block block, int[] positions) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < positions.length; i++) {
			sb.append(block.getValueAt(positions[i]));
		}
		return sb.toString();
	}

}
