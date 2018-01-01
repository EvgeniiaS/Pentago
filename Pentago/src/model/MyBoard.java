package model;

import java.util.ArrayList;
import java.util.Objects;

/**
 * REpresents a game board.
 * @author Evgeniia Shcherbinina
 *
 */
public class MyBoard {
	
	/**
	 * The width of the game board.
	 */
	private static final int width = 6;
	
	/**
	 * Blocks of this game board.
	 */
	private Block block1, block2, block3, block4;
	/**
	 * Utility of this game board.
	 */
	private int utility;
	/**
	 * The last move, which led to this board.
	 */
	private String lastMove;

	/**
	 * A constructor.
	 * @param b1
	 * @param b2
	 * @param b3
	 * @param b4
	 * @param theLastMove
	 */
	public MyBoard(Block b1, Block b2, Block b3, Block b4, String theLastMove) {
		block1 = b1;
		block2 = b2; 
		block3 = b3;
		block4 = b4;
		utility = 0;
		lastMove = theLastMove;
	}
	
	/**
	 * An empty constructor.
	 */
	public MyBoard() {
		this(new Block(), new Block(), new Block(), new Block(), "");
	}
	
	/**
	 * Calculates the utility of this board.
	 * @param maxToken a token of the max player.
	 * @return the utility
	 */
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
	
	/**
	 * Sets the utility.
	 * @param value
	 */
	public void setUtility(int value) {
		utility = value;
	}
	
	/**
	 * Returns the utility.
	 * @return
	 */
	public int getUtility() {
		return utility;
	}
	
	/**
	 * Returns the last move.
	 * @return
	 */
	public String getLastMove() {
		return lastMove;
	}
	
	/**
	 * Whether it is legal to place a token at the given position in the given block.
	 * @param block
	 * @param position
	 * @return true if legal, false otherwise
	 */
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
	
	/**
	 * Places a token at the given position in the given block.
	 * @param block
	 * @param position
	 * @param token
	 * @return a new board after the current move
	 */
	public MyBoard makeMove(int block, int position, char token) {
		String currentMove = "" + block + "/" + position;
		if (block == 1) {
			return new MyBoard(block1.move(position, token), block2, block3, block4, currentMove);
		} else if (block == 2) {
			return new MyBoard(block1, block2.move(position, token), block3, block4, currentMove);
		} else if (block == 3) {
			return new MyBoard(block1, block2, block3.move(position, token), block4, currentMove);
		} else {
			return new MyBoard(block1, block2, block3, block4.move(position, token), currentMove);
		}
	}
	
	/**
	 * Rotates a block of this board to the left or to the right.
	 * @param block
	 * @param direction
	 * @return a new board after rotation
	 */
	public MyBoard rotate(int block, char direction) {
		String currentMove = lastMove + " " + block + direction;
		if (block == 1 && direction == 'l') {
			return new MyBoard(block1.rotateLeft(), block2, block3, block4, currentMove);
		} else if (block == 1 && direction == 'r') {
			return new MyBoard(block1.rotateRight(), block2, block3, block4, currentMove);
		} else if (block == 2 && direction == 'l') {
			return new MyBoard(block1, block2.rotateLeft(), block3, block4, currentMove);
		} else if (block == 2 && direction == 'r') {
			return new MyBoard(block1, block2.rotateRight(), block3, block4, currentMove);
		} else if (block == 3 && direction == 'l') {
			return new MyBoard(block1, block2, block3.rotateLeft(), block4, currentMove);
		} else if (block == 3 && direction == 'r') {
			return new MyBoard(block1, block2, block3.rotateRight(), block4, currentMove);
		} else if (block == 4 && direction == 'l') {
			return new MyBoard(block1, block2, block3, block4.rotateLeft(), currentMove);
		} else {
			return new MyBoard(block1, block2, block3, block4.rotateRight(), currentMove);
		}
	}
	
	/**
	 * Whether a board has a winner(s).
	 * @return a token(s) of a winner(s)
	 */
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
	
	/**
	 * Prints a board.
	 */
	public void printBoard() {
		System.out.println("+---+---+");
		System.out.println("|" + block1.getValue().substring(0, 3) + "|" + block2.getValue().substring(0, 3) + "|");
		System.out.println("|" + block1.getValue().substring(3, 6) + "|" + block2.getValue().substring(3, 6) + "|");
		System.out.println("|" + block1.getValue().substring(6) + "|" + block2.getValue().substring(6) + "|");
		System.out.println("+---+---+");
		System.out.println("|" + block3.getValue().substring(0, 3) + "|" + block4.getValue().substring(0, 3) + "|");
		System.out.println("|" + block3.getValue().substring(3, 6) + "|" + block4.getValue().substring(3, 6) + "|");
		System.out.println("|" + block3.getValue().substring(6) + "|" + block4.getValue().substring(6) + "|");
		System.out.println("+---+---+");
	}
	
	@Override
    public boolean equals(final Object theOther) {        
        boolean returnValue = false;
        
        if (this == theOther) {
            returnValue = true;
        } else if (theOther != null && this.getClass() == theOther.getClass()) {
            final MyBoard otherBoard = (MyBoard) theOther;
            
            returnValue = (Objects.equals(block1, otherBoard.block1) && Objects.equals(block2, otherBoard.block2) && 
            		Objects.equals(block3, otherBoard.block3) && Objects.equals(block4, otherBoard.block4));
        } 
        
        return returnValue;
    } 
	
	/**
	 * Counts how many times the given sequences of tokens occur on the given board horizontally.
	 * @param b the board to check
	 * @param numberTokens in the given sequences.
	 * @param token of the sequences
	 * @return the number of occurrences.
	 */
	private int tokensInRow(MyBoard b, int numberTokens, char token) {
		int result = 0;
		result += tokensInRow(numberTokens, token, b.block1, b.block2);
		result += tokensInRow(numberTokens, token, b.block3, b.block4);
		return result;
	}
	
	private int tokensInRow(int numberTokens, char token, Block first, Block second) {
		int result = 0;		
		ArrayList<String> sequences = buildSequence(numberTokens, token);
		
		for (int i = 0; i < width / 2; i++) {
			StringBuilder sb = new StringBuilder();
			if (i == 2) {
				sb.append(first.getValue().substring(i * width / 2));
				sb.append(second.getValue().substring(i * width / 2));
			} else {
				sb.append(first.getValue().substring(i * width / 2, i * width / 2 + width / 2));			
				sb.append(second.getValue().substring(i * width / 2, i * width / 2 + width / 2));
			}
			
			for (String s: sequences) {
				if (sb.toString().contains(s)) {
					result++;
					break;
				}
			}
		}
		return result;
	}
	
	/**
	 * Builds a list of sequences to check on a board.
	 * @param numberTokens
	 * @param token
	 * @return
	 */
	private ArrayList<String> buildSequence(int numberTokens, char token) {
		ArrayList<String> s = new ArrayList<>();
		
		StringBuilder sequence = new StringBuilder();
		for (int i = 0; i < numberTokens; i++) {
			sequence.append(token);
		}
		
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		StringBuilder sb3 = new StringBuilder();
		if (numberTokens == 3) { 
			sb1.append(".."); // a sequence of tokens with an empty space in the beginning
			sb1.append(sequence); 	
			sb2.append(sequence); // a sequence of tokens with an empty space in the end	
			sb2.append("..");			
			s.add(sb1.toString());
			s.add(sb2.toString());
			
			// .www.
			sb3.append('.');
			sb3.append(sequence);
			sb3.append('.');
			s.add(sb3.toString());
			
		} else if (numberTokens == 4) {
			sb1.append("."); // .wwww
			sb1.append(sequence); 	
			sb2.append(sequence); // wwww.
			sb2.append(".");
			s.add(sb1.toString());
			s.add(sb2.toString());
			
			// w.www
			sb3.append(token);
			sb3.append('.');
			sb3.append(token);
			sb3.append(token);
			sb3.append(token);
			s.add(sb3.toString());
			
			// www.w
			sb3 = new StringBuilder();
			sb3.append(token);
			sb3.append(token);
			sb3.append(token);
			sb3.append('.');
			sb3.append(token);
			s.add(sb3.toString());	
			
			// ww.ww
			sb3 = new StringBuilder();
			sb3.append(token);
			sb3.append(token);
			sb3.append('.');
			sb3.append(token);
			sb3.append(token);
			s.add(sb3.toString());
			
		} else { // a sequence of 5 tokens without an empty space
			sb1 = sequence;
			s.add(sb1.toString());
		}
		
		return s;
	}
	
	/**
	 * Counts how many times the given sequences of tokens occur on the given board vertically.
	 * @param numberTokens
	 * @param token
	 * @return
	 */
	private int tokensInColumn(int numberTokens, char token) {
		MyBoard afterRightRotation = new MyBoard(block3.rotateRight(), block1.rotateRight(), block4.rotateRight(),
				block2.rotateRight(), lastMove);
		return tokensInRow(afterRightRotation, numberTokens, token);
	}
	
	/**
	 * Counts how many times the given sequences of tokens occur on diagonals of the given board.
	 * @param numberTokens
	 * @param token
	 * @return
	 */
	private int tokensInDiagonal(int numberTokens, char token) {
		int result = 0;
		ArrayList<String> sequences = buildSequence(numberTokens, token);
		ArrayList<String> allStrings = buildDiagonalStrings();
		
		for (String s: allStrings) {
			for (String s1: sequences) {
				if (s.contains(s1)) {
					result++;
					break;
				}
			}
		}
		return result;
	}
	
	/**
	 * Builds diagonal strings.
	 * @return
	 */
	private ArrayList<String> buildDiagonalStrings() {
		ArrayList<String> allStrings = new ArrayList<>();
		
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
		sb.append(buildString(block3, positions3));
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
		allStrings.add(sb.toString());
		
		return allStrings;
	}
	
	/**
	 * Builds a string using characters at given positions.
	 * @param block
	 * @param positions
	 * @return
	 */
	private String buildString(Block block, int[] positions) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < positions.length; i++) {
			sb.append(block.getValueAt(positions[i]));
		}
		return sb.toString();
	}

}
