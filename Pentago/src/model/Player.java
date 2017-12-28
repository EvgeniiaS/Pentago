package model;

public class Player {
	
	protected char token;

	public Player(char theToken, boolean theIsMax) {
		token = theToken;		
	}

	public MyBoard move(MyBoard board, int block, int position, char direction) {
		MyBoard newBoard = board.makeMove(block, position, token);
		if (newBoard.checkWin().isEmpty()) {
			newBoard = newBoard.rotate(block, direction);
		}
		return newBoard;
	}

}
