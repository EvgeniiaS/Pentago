package model;

import java.util.Scanner;

/**
 * Represents Pentago game.
 * @author Evgeniia Shcherbinina
 *
 */
public class Game {	
	
	/** 
	 * A game board 
	 */
	private MyBoard board;
	/**
	 * A human player.
	 */
	private Player human;
	/**
	 * An AI player.
	 */
	private AIPlayer ai;
	/**
	 * The current player: 1 - a human, 2 - AI.
	 */
	private int currentPlayer;

	/**
	 * Constructs a new game.
	 * @param token of a human player
	 * @param minOrMax Is AI Min or Max player
	 */
	public Game(char humanToken, String theName, char AIToken, boolean isMax, int theCurrentPlayer) {
		board = new MyBoard();
		human = new Player(humanToken, theName);
		ai = new AIPlayer(AIToken, isMax);
		currentPlayer = theCurrentPlayer;
	}
	
	/**
	 * Starts the game.
	 * @param in Scanner
	 */
	public void start(Scanner in) {
		do {			
			printGame();
			if (currentPlayer == 1) {
				System.out.println("Your next move: ");
				String nextMove = in.nextLine();
				while (!checkMove(nextMove)) {
					System.out.println("Enter the valid move: ");
					nextMove = in.nextLine();
				}
				
				int block = Integer.parseInt(nextMove.substring(0, 1));
				int position = Integer.parseInt(nextMove.substring(2, 3));
				int blockRotate = Integer.parseInt(nextMove.substring(4, 5));
				char direction = nextMove.toLowerCase().charAt(5);				
				board = human.move(board, block, position, blockRotate, direction);
			} else {
				System.out.println("Alie is thinking...\n");
				board = ai.AImove(board);;
			}
			currentPlayer = changeCurrentPlayer(currentPlayer);
		} while (!isOver());
		
		System.out.println();
		System.out.println("-------------------");
		System.out.println("-------------------");
		System.out.println("Final state");		
		
		printGame();
		System.out.println("A winner is " + getWinner().toUpperCase());		
	}
	
	/**
	 * Alters the current player.
	 * @param currenPlayer
	 * @return a player for the next move.
	 */
	public int changeCurrentPlayer(int currenPlayer) {
		return currentPlayer == 1 ? 2 : 1;
	}
	
	/**
	 * Checks whether an entered move contains legal information.
	 * @param move
	 * @return
	 */
	private boolean checkMove(String move) {
		if (move.length() < 6) {
			return false;
		}
		int block = Integer.parseInt(move.substring(0, 1));
		int position = Integer.parseInt(move.substring(2, 3));
		int blockRotate = Integer.parseInt(move.substring(4, 5));
		char direction = move.toLowerCase().charAt(5);
		
		return (block >= 1 && block <= 4) && (position >=1 && position <= 9) && 
				(blockRotate >= 1 && blockRotate <= 4 ) && (direction == 'l' || direction == 'r');
	}

	/**
	 * Checks whether a  game is over
	 * @return
	 */
	public boolean isOver() {
		return !board.checkWin().isEmpty();
	}
	
	/**
	 * Returns a token of a winner(s)
	 * @return
	 */
	public String getWinner() {
		return board.checkWin();
	}
	
	/**
	 * Prints the current game state.
	 */
	public void printGame() {		
		System.out.println("Player 1: " + human.getName() + " (" + Character.toUpperCase(human.getToken()) + ")");
		System.out.println("Player 2: " + ai.getName() + " (" + Character.toUpperCase(ai.getToken()) + ")");
		
		if (currentPlayer == 1) {
			System.out.println("Player to move next: 1");
		} else {			
			System.out.println("Player to move next: 2");
		}		
		board.printBoard();
	}

}