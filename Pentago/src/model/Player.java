package model;

/**
 * Represents a player of the game.
 * @author Evgeniia Shcherbinina
 *
 */
public class Player {
	
	/**
	 * A token of this player.
	 */
	private char token;
	/**
	 * A name of this player.
	 */
	private String name;

	/**
	 * A constructor.
	 * @param theToken
	 * @param theName
	 */
	public Player(char theToken, String theName) {
		token = theToken;
		name = theName;
	}

	/**
	 * Places a token and rotates one block.
	 * @param board to make a move on
	 * @param block to place a token
	 * @param position to place a token
	 * @param blockForRotation
	 * @param direction of the rotation
	 * @return a new board after the current move
	 */
	public MyBoard move(MyBoard board, int block, int position, int blockForRotation, char direction) {
		MyBoard newBoard = board.makeMove(block, position, token);
		return newBoard.rotate(blockForRotation, direction);
	}
	
	/**
	 * Returns a token of this player.
	 * @return
	 */
	public char getToken() {
		return token;
	}
	
	/**
	 * Returns a name of this player.
	 * @return
	 */
	public String getName() {
		return name;
	}

}
