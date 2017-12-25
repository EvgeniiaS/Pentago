package model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a game board.
 * @author Evgeniia Shcherbinina
 *
 */
public class Board {
	
	private final static String emptyBlock = "........."; 
	
	/** The first block on the game board */
	private String block1; 
	/** The second block on the game board */
	private String block2;
	/** The third block on the game board */
	private String block3;
	/** The fourth block on the game block */
	private String block4;
	/** A utility value of this board */
	private int utility;
	/** The last move resulted in this board state */
	private String LastMove;


	/**
	 * Constructs a new Board object
	 * @param s1 Block 1
	 * @param s2 Block 2
	 * @param s3 Block 3
	 * @param s4 Block 4
	 * @param move The last move
	 */
	public Board(String s1, String s2, String s3, String s4, String move) {
		block1 = s1;
		block2 = s2;
		block3 = s3;
		block4 = s4;
		LastMove = move;
		utility = 0;
	}
	
	/**
	 * Constructs an empty Board object
	 */
	public Board() {
		this(emptyBlock, emptyBlock, emptyBlock, emptyBlock, "");
	}
	
	/**
	 * Returns the last move.
	 * @return the last move
	 */
	public String getLastMove() {
		return LastMove;
	}
	
	/**
	 * Returns a utility value.
	 * @return
	 */
	public int getUtility() {
		return utility;
	}
	
	/**
	 * Sets a utility value.
	 * @param value
	 */
	public void setUtility(int value) {
		utility = value;
	}
	
	/**
	 * Checks whether a move is legal
	 * @param blockNumber A block to move
	 * @param position An index to move
	 * @return True if the move is legal and false otherwise
	 */
	public boolean isLegalMove(int blockNumber, int position) {
		if (blockNumber == 1) {
			return block1.charAt(position - 1) == '.';
		} else if (blockNumber == 2) {
			return block2.charAt(position - 1) == '.';
		} else if (blockNumber == 3) {
			return block3.charAt(position - 1) == '.';
		} else {
			return block4.charAt(position - 1) == '.';
		}
	}
	
	/**
	 * Makes a move on the board.
	 * @param blockNumber
	 * @param position An index to make a move
	 * @param token A token to make a move
	 * @return a new Board that contains all moves
	 */
	public Board makeMove(int blockNumber, int position, char token) {
		if (blockNumber == 1) {
			block1 = changeBlock(block1, position, token);
		} else if (blockNumber == 2) {
			block2 = changeBlock(block2, position, token);
		} else if (blockNumber == 3) {
			block3 = changeBlock(block3, position, token);
		} else {
			block4 = changeBlock(block4, position, token);
		}
		return new Board(block1, block2, block3, block4, "" + blockNumber + "/" + position);
	}
	
	/**
	 * Inserts a new token on a block.
	 * @param s A block to modify
	 * @param position An index to insert a new token.
	 * @param token A token to insert
	 * @return A block after insertion
	 */
	private String changeBlock(String s, int position, char token) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			if (i == position - 1) {
				sb.append(token);
			} else {
				sb.append(s.charAt(i));
			}
		}
		return sb.toString();
	}
	
	/**
	 * Returns a copy of this board
	 * @return
	 */
	public Board makeCopy() {
		return new Board(block1, block2, block3, block4, LastMove);
	}
	
	/**
	 * Rotates a block 90 degree
	 * @param blockNumber
	 * @param direction 
	 * @return A new modified board 
	 */
	public Board rotate(int blockNumber, char direction) {
		if (blockNumber == 1 && direction == 'l') {
			block1 = rotateLeft(block1);
		} else if (blockNumber == 1 && direction =='r') {
			block1 = rotateRight(block1);
		} else if (blockNumber == 2 && direction == 'l') {
			block2 = rotateLeft(block2);
		} else if (blockNumber == 2 && direction == 'r') {
			block2 = rotateRight(block2);
		} else if (blockNumber == 3 && direction == 'l') {
			block3 = rotateLeft(block3);
		} else if (blockNumber == 3 && direction == 'r') {
			block3 = rotateRight(block3);
		} else if (blockNumber == 4 && direction == 'l') {
			block4 = rotateLeft(block4);
		} else if (blockNumber == 4 && direction == 'r') {
			block4 = rotateRight(block4); 
		}
		return new Board(block1, block2, block3, block4, LastMove + " " + blockNumber + direction);
	}
	
	/**
	 * Rotates a block to the left.
	 * @param s A block
	 * @return A block after rotation
	 */
	private String rotateLeft(String s) {
		StringBuilder sb = new StringBuilder();
		for (int i = 2; i <= 8; i += 3) {
			sb.append(s.charAt(i));
		}
		for (int i = 1; i <= 7; i += 3) {
			sb.append(s.charAt(i));
		}
		for (int i = 0; i <= 6; i += 3) {
			sb.append(s.charAt(i));
		}
		return sb.toString();
	}
	
	/**
	 * Rotates a block to the right
	 * @param s A block
	 * @return A block after totation
	 */
	private String rotateRight(String s) {
		StringBuilder sb = new StringBuilder();
		for (int i = 6; i >= 0; i -= 3) {
			sb.append(s.charAt(i));
		}
		for (int i = 7; i >= 1; i -= 3) {
			sb.append(s.charAt(i));
		}
		for (int i = 8; i >= 2; i -= 3) {
			sb.append(s.charAt(i));
		}
		return sb.toString();
	}
	
	/**
	 * Prints a board.
	 */
	public void printBoard(FileWriter fw) {
		System.out.println("+---+---+");
		System.out.println("|" + block1.substring(0, 3) + "|" + block2.substring(0, 3) + "|");
		System.out.println("|" + block1.substring(3, 6) + "|" + block2.substring(3, 6) + "|");
		System.out.println("|" + block1.substring(6) + "|" + block2.substring(6) + "|");
		System.out.println("+---+---+");
		System.out.println("|" + block3.substring(0, 3) + "|" + block4.substring(0, 3) + "|");
		System.out.println("|" + block3.substring(3, 6) + "|" + block4.substring(3, 6) + "|");
		System.out.println("|" + block3.substring(6) + "|" + block4.substring(6) + "|");
		System.out.println("+---+---+");
		
		try {
			fw.write("+---+---+\n");
			fw.write("|" + block1.substring(0, 3) + "|" + block2.substring(0, 3) + "|\n" );
			fw.write("|" + block1.substring(3, 6) + "|" + block2.substring(3, 6) + "|\n");
			fw.write("|" + block1.substring(6) + "|" + block2.substring(6) + "|\n");
			fw.write("+---+---+\n");
			fw.write("|" + block3.substring(0, 3) + "|" + block4.substring(0, 3) + "|\n");
			fw.write("|" + block3.substring(3, 6) + "|" + block4.substring(3, 6) + "|\n");
			fw.write("|" + block3.substring(6) + "|" + block4.substring(6) + "|\n");
			fw.write("+---+---+\n");
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Calculates the utility of this board; intended for objects that are leaves in a game tree.
	 * 100 points for 5 tokens in a row, 15 points for 4 tokens in a row.
	 * @param maxToken A token of Max player
	 * @return the utility value
	 */
	public int findUtility(char maxToken) {
		
		// check horizontals
		int w3 = tokensInRow(this, 'w', 3);
		int b3 = tokensInRow(this, 'b', 3);
		int w4 = tokensInRow(this, 'w', 4);
		int b4 = tokensInRow(this, 'b', 4);
		int w5 = tokensInRow(this, 'w', 5);
		int b5 = tokensInRow(this, 'b', 5);
		
		utility += calculateUtility(maxToken, w3, b3, w4, b4, w5, b5);
		
		// check verticals
		Board b = this.makeCopy();
		b.rotate(1, 'r');
		b.rotate(2, 'r');
		b.rotate(3, 'r');
		b.rotate(4, 'r');
		b = new Board(b.block3, b.block1, b.block4, b.block2, "");
		
		w3 = tokensInRow(b, 'w', 3);
		b3 = tokensInRow(b, 'b', 3);
		w4 = tokensInRow(b, 'w', 4);
		b4 = tokensInRow(b, 'b', 4);
		w5 = tokensInRow(b, 'w', 5);
		b5 = tokensInRow(b, 'b', 5);
		
		utility += calculateUtility(maxToken, w3, b3, w4, b4, w5, b5);
		
		// check diagonals
		List<String> allStrings = buildDiagonalStrings();
		b3 = checkDiagonal(allStrings, 'w', 3);
		w3 = checkDiagonal(allStrings, 'b', 3);
		w4 = checkDiagonal(allStrings, 'w', 4);
		b4 = checkDiagonal(allStrings, 'b', 4);
		w5 = checkDiagonal(allStrings, 'w', 5);
		b5 = checkDiagonal(allStrings, 'b', 5);
		
		utility += calculateUtility(maxToken, w3, b3, w4, b4, w5, b5);
		
		return utility;
	}
	
	/**
	 * Provides calculations for utility
	 * @param maxToken A token of the Max player
	 * @param w4 The number of 4 'w's in a row
	 * @param b4 The number of 4 'b's in a row
	 * @param w5 The number of 5 'w's in a row
	 * @param b5 The number of 5 'b's in a row
	 * @return calculated utility
	 */
	private int calculateUtility(char maxToken, int w3, int b3, int w4, int b4, int w5, int b5) {
		if (maxToken == 'w') {
			return w3 * 15 + w4 * 30 + w5 * 100 - b3 * 15 - b4 * 30 - b5 * 100;
		} else {
			return b3 * 15 + b4 * 30 + b5 * 100 - w3 * 15 - w4 * 30 - w5 * 100;
		}
	}
	
	/**
	 * The number of times a given sequence of tokens appears in diagonal rows
	 * @param allStrings A list of all diagonal strings
	 * @param token A token to look for
	 * @param numberTokens The number of tokens in a row to look for
	 * @return how many times a sequence of  tokens appears in diagonal rows
	 */
	private int checkDiagonal(List<String> allStrings, char token, int numberTokens) {
		int result = 0;
		StringBuilder sequence = new StringBuilder();
		for (int i = 0; i < numberTokens; i++) { // build a sequence of tokens
			sequence.append(token);
		}
		
		StringBuilder sb1 = new StringBuilder(); // a sequence of tokens with an empty space in the beginning
		sb1.append('.');
		sb1.append(sequence);
		
		StringBuilder sb2 = new StringBuilder(); // a sequence of tokens with an empty space in the end		
		sb2.append(sequence);
		sb2.append('.');
		
		if(numberTokens == 5) { // a sequence of 5 tokens without an empty space
			sb1 = sequence;
			sb2 = sequence;
		}
		
		for (int i = 0; i < allStrings.size(); i++) { // check diagonals whether they contain the given sequence
			String current = allStrings.get(i);
			if(current.contains(sb1) || current.contains(sb2)) {
				result++;
			}
		}
		
		return result;
	}
	
	/**
	 * Builds a new string 
	 * @param source 
	 * @param index An array of indices 
	 * @return A new string
	 */
	private String buildString(String source, int[] index) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < index.length; i++) {
			sb.append(source.charAt(index[i]));
		}
		return sb.toString();
	}
	
	/**
	 * Returns a list of diagonal strings of the length 3, 4, 5, 6
	 * @return
	 */
	private List<String> buildDiagonalStrings() {
		List<String> allStrings = new LinkedList<>();
		
		int[] index = {0, 4, 8};
		allStrings.add(buildString(block2, index));
		
		StringBuilder sb = new StringBuilder();	
		sb.append(block1.charAt(6));
		sb.append(block3.charAt(1));
		sb.append(block3.charAt(5));
		sb.append(block4.charAt(6));
		allStrings.add(sb.toString());
		
		int[] index1 = {3, 7};
		StringBuilder sb1 = new StringBuilder();
		sb1.append(buildString(block1, index1));
		sb1.append(block3.charAt(2));
		sb1.append(buildString(block4, index1));
		allStrings.add(sb1.toString());
		
		StringBuilder sb2 = new StringBuilder();
		sb2.append(buildString(block1, index));
		sb2.append(buildString(block4, index));
		allStrings.add(sb2.toString());
		
		int[] index3 = {1, 5};
		StringBuilder sb3 = new StringBuilder();
		sb3.append(buildString(block1, index3));
		sb3.append(block2.charAt(6));
		sb3.append(buildString(block4, index3));
		allStrings.add(sb3.toString());
		
		StringBuilder sb4 = new StringBuilder();
		sb4.append(block1.charAt(2));
		sb4.append(block2.charAt(3));
		sb4.append(block2.charAt(7));
		sb4.append(block4.charAt(2));
		allStrings.add(sb4.toString());
		
		allStrings.add(buildString(block2, index));
		
		int[] index4 = {2, 4, 6};
		allStrings.add(buildString(block1, index4));
		
		StringBuilder sb5 = new StringBuilder();
		sb5.append(block3.charAt(0));
		sb5.append(block1.charAt(7));
		sb5.append(block1.charAt(5));
		sb5.append(block2.charAt(0));
		allStrings.add(sb5.toString());
		
		int[] index5 = {3, 1};
		StringBuilder sb6 = new StringBuilder();
		sb6.append(buildString(block3, index5));
		sb6.append(block1.charAt(8));
		sb6.append(buildString(block2, index5));
		allStrings.add(sb6.toString());
		
		int[] index6 = {6, 4, 2};
		StringBuilder sb7 = new StringBuilder();
		sb7.append(buildString(block3, index6));
		sb7.append(buildString(block2, index6));
		allStrings.add(sb7.toString());
		
		int[] index7 = {7, 5};
		StringBuilder sb8 = new StringBuilder();
		sb8.append(buildString(block3, index7));
		sb8.append(block4.charAt(0));
		sb8.append(buildString(block2, index7));
		allStrings.add(sb8.toString());
		
		StringBuilder sb9 = new StringBuilder();
		sb9.append(block3.charAt(8));
		sb9.append(block4.charAt(3));
		sb9.append(block4.charAt(1));
		sb9.append(block2.charAt(8));
		allStrings.add(sb9.toString());
		
		allStrings.add(buildString(block4, index6));		
		
		return allStrings;
	}

	/**
	 * Finds how many times a sequence of tokens appear in all horizontal rows.
	 * @param b A board to look for a sequence appearance 
	 * @param token A token to look for
	 * @param numberTokens The number of tokens to look for
	 * @return how many times a sequence appeared in horizontal rows
	 */
	private int tokensInRow(Board b, char token, int numberTokens) {
		int result = 0;
		
		StringBuilder sequence = new StringBuilder();
		for (int i = 0; i < numberTokens; i++) {
			sequence.append(token);
		}
		
		StringBuilder sb1 = new StringBuilder(); // a sequence of tokens with an empty space in the beginning
		sb1.append('.');
		sb1.append(sequence);
		
		StringBuilder sb2 = new StringBuilder(); // a sequence of tokens with an empty space in the end		
		sb2.append(sequence);
		sb2.append('.');
		
		if(numberTokens == 5) { // a sequence of 5 tokens without an empty space
			sb1 = sequence;
			sb2 = sequence;
		}
		
		for (int i = 0; i < 3; i++) {
			StringBuilder sb = new StringBuilder();
			if (i == 2) {
				sb.append(b.block1.substring(i * 3));
				sb.append(b.block2.substring(i * 3));
			} else {
				sb.append(b.block1.substring(i * 3, i + 3));			
				sb.append(b.block2.substring(i * 3, i + 3));
			}
			if (sb.toString().contains(sb1) || sb.toString().contains(sb2)) {
				result++;
			}
		}
		
		for (int i = 0; i < 3; i++) {
			StringBuilder sb = new StringBuilder();
			if (i == 2) {
				sb.append(b.block3.substring(i * 3));
				sb.append(b.block4.substring(i * 3));
			} else {
				sb.append(b.block3.substring(i * 3, i + 3));
				sb.append(b.block4.substring(i * 3, i + 3));
			}
			if (sb.toString().contains(sb1) || sb.toString().contains(sb2)) {
				result++;
			}
		}
		
		return result;
	}	
	
	/**
	 * Checks a win.
	 * @return A string with won token(s) or an empty string if there is no a winner
	 */
	public String checkWin() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(checkHorizontal());
		sb.append(checkVertical());
		sb.append(checkDiagonal());
		boolean isW = false;
		boolean isB = false;
		for (int i = 0; i < sb.length(); i++) {
			if (sb.charAt(i) == 'w') {
				isW = true;
			} else if (sb.charAt(i) == 'b') {
				isB = true;	
			}
		}
		
		if (isW && isB) {
			return "wb";
		} else if (isW && !isB) {
			return "w";
		} else if (!isW && isB) {
			return "b";
		} else {
			return "";
		}
	
	}
	
	/**
	 * Checks whether there is 5 tokens in a row horizontally.
	 * @return a string with a winner token or an empty string
	 */
	private String checkHorizontal() {	
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 3; i++) {
			char c = block1.charAt(i * 3 + 1);
			if (c != '.' && c == block1.charAt(i * 3 + 2) && c == block2.charAt(i * 3) &&
					c == block2.charAt(i * 3 + 1) && (c == block1.charAt(i) || c == block2.charAt(i * 3 + 2))) {
				
				sb.append(c);
			}
		}
		
		for (int i = 0; i < 3; i++) {
			char c = block3.charAt(i * 3 + 1);
			if (c != '.' && c == block3.charAt(i * 3 + 2) && c == block4.charAt(i * 3) &&
					c == block4.charAt(i * 3 + 1) && (c == block3.charAt(i) || c == block4.charAt(i * 3 + 2))) {
				sb.append(c);
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * Checks whether there is 5 tokens in a row vertically.
	 * @return a string with a winner token or an empty string
	 */
	private String checkVertical() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 3; i++) {
			char c = block1.charAt(i + 3);
			if (c != '.' && c == block1.charAt(i + 6) && c == block3.charAt(i) &&
					c == block3.charAt(i + 3) && (c == block1.charAt(i) || c == block3.charAt(i + 6))) {
				sb.append(c);
			}
		}
		
		for (int i = 0; i < 3; i++) {
			char c = block2.charAt(i + 3);
			if (c != '.' && c == block2.charAt(i + 6) && c == block4.charAt(i) &&
					c == block4.charAt(i + 3) && (c == block2.charAt(i) || c == block4.charAt(i + 6))) {
				sb.append(c);
			}
		}
		
		return sb.toString();
	}
	
	
	/**
	 * Checks diagonals for a winner.
	 * @return a string with a winner token or an empty string
	 */
	private String checkDiagonal() {
		StringBuilder sb = new StringBuilder();
		if (block1.charAt(4) != '.' && block1.charAt(4) == block1.charAt(8) && block1.charAt(4) == block4.charAt(0) &&
				block1.charAt(4) == block4.charAt(4) &&
				(block1.charAt(4) == block1.charAt(0) || block1.charAt(4) == block4.charAt(8))) {
			sb.append(block1.charAt(4));
		} 
		if (block1.charAt(1) != '.' && block1.charAt(1) == block1.charAt(5) && block1.charAt(1) == block2.charAt(6) &&
				   block1.charAt(1) == block4.charAt(1) && block1.charAt(1) == block4.charAt(5)) {
			sb.append(block1.charAt(1));
		}
		if (block1.charAt(3) != '.' && block1.charAt(3) == block1.charAt(7) && block1.charAt(3) == block3.charAt(2) &&
				   block1.charAt(3) == block4.charAt(3) && block1.charAt(3) == block4.charAt(7)) {
			sb.append(block1.charAt(3));
		}
		if (block2.charAt(4) != '.' && block2.charAt(4) == block2.charAt(6) && block2.charAt(4) == block3.charAt(2) &&
				block2.charAt(4) == block3.charAt(4) &&
				(block2.charAt(4) == block2.charAt(2) || block2.charAt(4) == block3.charAt(6))) {
			sb.append(block2.charAt(4));
		} 
		if (block2.charAt(1) != '.' && block2.charAt(1) == block2.charAt(3) && block2.charAt(1) == block1.charAt(8) &&
				   block2.charAt(1) == block3.charAt(1) && block2.charAt(1) == block3.charAt(3)) {
			sb.append(block2.charAt(1));
		}
		if (block2.charAt(5) != '.' && block2.charAt(5) == block2.charAt(7) && block2.charAt(5) == block4.charAt(0) &&
				   block2.charAt(5) == block3.charAt(5) && block2.charAt(5) == block3.charAt(7)) {
			sb.append(block2.charAt(5));
		}
		return sb.toString();
	}

}