package model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a game Pentago.
 * @author Evgeniia Shcherbinina
 *
 */
public class Game {
	
	/**
	 * A depth for MinMax algorithm.
	 */
	private final static int gameTreeDepth = 3;
	
	/** A game board */
	private Board myBoard;
	/** A token of a human player */
	private char humanToken;
	/** A token of AI player */
	private char AIToken;
	/** All moves made during the game */
	private StringBuilder allMoves;
	/** Whether AI is a Max or Min player */
	private String AIMinOrMax;
	
	// to count the number of nodes expanded
	int nodeCount = 0;

	/**
	 * Constructs a new game.
	 * @param token of a human player
	 * @param minOrMax Is AI Min or Max player
	 */
	public Game(char token, String minOrMax) {
		myBoard = new Board();
		humanToken = token;
		allMoves = new StringBuilder();
		AIMinOrMax = minOrMax;
		setAIToken();
	}
	
	public Game(Board b){
		this('b', "min");
		myBoard = b;
		
	}
	
	/**
	 * Sets a token of AI.
	 */
	private void setAIToken() {
		if (humanToken == 'w') {
			AIToken = 'b';
		} else {
			AIToken = 'w';
		}
	}
	
	/**
	 * Makes a move in the game.
	 * @param move An input from a player
	 * @param token A token for a move
	 */
	public void move(String move, char token) {
		// game statistics
		allMoves.append(move.toUpperCase());
		allMoves.append("\n");
		
		int block = Integer.parseInt(move.substring(0, 1));
		int position = Integer.parseInt(move.substring(2, 3));
		int blockRotate = Integer.parseInt(move.substring(4, 5));
		char direction = move.charAt(5);

		if (myBoard.isLegalMove(block, position)) {
			myBoard = myBoard.makeMove(block, position, token);
			if (myBoard.checkWin().isEmpty()) {
				myBoard = myBoard.rotate(blockRotate, direction);					
			} 
		} else { // a move is illegal
			System.out.println("Enter a legal move: ");
			Scanner in = new Scanner(System.in);
			String newMove = in.nextLine();
			move(newMove, token);
			in.close();
		} 
	}
	
	/**
	 * A move of AI.
	 */
	public void AIMove() {		
		GameTreeNode root = new GameTreeNode(myBoard.makeCopy(), null);
		buildGameTree(root, 0, AIToken);
		
		char maxPlayerToken = findMaxPlayerToken();
		
		// find a utility for the root using MinMax algorithm
		int utility = minMax(root, AIMinOrMax, maxPlayerToken);
		
		// find an optimal move from the game tree
		String move = findOptimalMove(root, utility);
		move(move, AIToken);
	}
	
	/**
	 * Find an optimal move in a game tree.
	 * @param root of the game tree
	 * @param utility returned by the MinMax algorithm
	 * @return a string representation of the optimal move
	 */
	private String findOptimalMove(GameTreeNode root, int utility) {
		List<GameTreeNode> children = root.getChildren();
		for (int i = 0; i < children.size(); i++) {
			if (children.get(i).getBoard().getUtility() == utility) {
				return children.get(i).getBoard().getLastMove();
			}
		}
		return children.get(0).getBoard().getLastMove();
	}
	
	/**
	 * Builds a complete game tree.
	 * @param currentNode a node to start building a tree
	 * @param depth the current depth of the tree
	 * @param token to add on the board
	 */
	private void buildGameTree(GameTreeNode currentNode, int depth, char token) {
		
		Board currentBoard = currentNode.getBoard();
		if (depth < gameTreeDepth) {
			
			for (int i = 1; i <= 4; i++) { // a block number
				for (int j = 1; j <= 9; j++) { // position
					
					if (currentBoard.isLegalMove(i, j)) {
						Board copyBoard = currentBoard.makeCopy();
						Board b = copyBoard.makeMove(i, j, token);
						
						if (b.checkWin().isEmpty()) { // no a winner
						
							for (int k = 1; k <= 4; k++) { // a block number for rotation							
							
								Board copy = b.makeCopy();
								Board bLeft = copy.rotate(k, 'l');
								
								GameTreeNode addedChild = currentNode.addChild(bLeft);
								//if(depth == 1) nodeCount++;
							
								if (bLeft.checkWin().isEmpty()) {
									buildGameTree(addedChild, depth + 1, changeToken(token));
								}
								
								copy = b.makeCopy();
								Board bRight = copy.rotate(k, 'r');
								addedChild = currentNode.addChild(bRight);
								//if(depth == 1) nodeCount++;
								
								if (bRight.checkWin().isEmpty()) {
									buildGameTree(addedChild, depth + 1, changeToken(token));
								}
							}
						} else {
							currentNode.addChild(b);
							//if(depth == 1) nodeCount++;
						}
					}					
				}
			}			
		}
	}
	
	/**
	 * The MinMax algorithm
	 * @param root a node for which a decision is made
	 * @param minMax which player makes a decision at this level
	 * @param maxPlayerToken a token of a Max player
	 * @return a utility value 
	 */
	private int minMax(GameTreeNode root, String minMax, char maxPlayerToken) {
		int bestUtility = 0;
		
		if (root.hasChild()) {
			List<GameTreeNode> children = root.getChildren();
			
			if (minMax.equals("max")) { // a max player
				bestUtility = -10000;
				for (int i = 0; i < children.size(); i++) {
			
					int utility = minMax(children.get(i), "min", maxPlayerToken);
				
					if (utility > bestUtility) {
						bestUtility = utility;
					}
				}
				root.getBoard().setUtility(bestUtility);
				
			} else { // a min player
				bestUtility = 10000;
				for (int i = 0; i < children.size(); i++) {
					int utility = minMax(children.get(i), "max", maxPlayerToken);
					if (utility < bestUtility) {
						bestUtility = utility;
					}
				}
				root.getBoard().setUtility(bestUtility);
			}
			
		} else { // this is a leave node
			bestUtility =  root.getBoard().findUtility(maxPlayerToken);
		}
		return bestUtility;
	}
	
	/**
	 * AI move using alpha-beta pruning
	 */
	public void AIMovePruning() {
		GameTreeNode root = new GameTreeNode(myBoard.makeCopy(), null);
		
		char maxPlayerToken = findMaxPlayerToken();
		nodeCount = 0; // counts the number of nodes expanded
		
		// find a utility for the root using MinMax algorithm
		int utility = alphaBetaPruning(root, 0, AIToken, AIMinOrMax, maxPlayerToken, -10000, 10000);
		
		// find an optimal move from the game tree
		String move = findOptimalMove(root, utility);
		move(move, AIToken);		
	}
	
	/**
	 * Returns an optimal utility value using Alfa-Beta pruning.
	 * @param root A node for which a utility is found
	 * @param depth A depth of the current node
	 * @param token A token to move next
	 * @param minMax Which player makes a decision at this depth
	 * @param maxPlayerToken A token of Max player
	 * @param alpha 
	 * @param beta
	 * @return the best utility found for this node
	 */
	private int alphaBetaPruning(GameTreeNode root, int depth, char token, String minMax,
								char maxPlayerToken, int alpha, int beta) {
		int bestUtility = 0;
		int utility;
		
		Board currentBoard = root.getBoard();
		if (depth >= gameTreeDepth) {
			if (minMax.equals("max")) {
				return Math.max(alpha, currentBoard.findUtility(maxPlayerToken));
			} else { // a Min player
				return Math.min(beta, currentBoard.findUtility(maxPlayerToken));
			}
		}
		
				
		for (int i = 1; i <= 4; i++) { // a block number
			for (int j = 1; j <= 9; j++) { // position
					
				if (!currentBoard.isLegalMove(i, j)) { // skip this position
					continue;
				}
						 
				Board copyBoard = currentBoard.makeCopy();
				Board b = copyBoard.makeMove(i, j, token); // a child board without rotation
				
				if (!b.checkWin().isEmpty()) { // a winner, no need to rotate
					if (minMax.equals("max")) {
						return b.findUtility(maxPlayerToken);
					} else { // a Min player
						return b.findUtility(maxPlayerToken);
					}
				}
							
				for (int k = 1; k <= 4; k++) { // a block number for rotation	
					
					Board copy = b.makeCopy();
					if(alpha < beta) {
							
						Board bLeft = copy.rotate(k, 'l'); // a child board with left rotation									
						GameTreeNode addedChild = root.addChild(bLeft);
						if (!bLeft.checkWin().isEmpty()) { // a winner after left rotation, don't expand further
							utility = bLeft.findUtility(maxPlayerToken);
						} else { // no a winner, expand further
							if(depth == 2) nodeCount++;
							utility = alphaBetaPruning(addedChild, depth + 1, changeToken(token), changeMinMax(minMax),
														maxPlayerToken, alpha, beta);
						}
					
						if (minMax.equals("max")) {
							alpha = Math.max(alpha, utility);
							bestUtility = Math.max(alpha, utility);
						} else {
							beta = Math.min(beta, utility);
							bestUtility = Math.min(beta, utility);
						}
					}
								
					if (alpha < beta) {								
						copy = b.makeCopy();
						Board bRight = copy.rotate(k, 'r'); // a child board with right rotation
						GameTreeNode addedChild = root.addChild(bRight);
																	
						if (!bRight.checkWin().isEmpty()) { // a winner after left rotation, don't expand further
							utility = bRight.findUtility(maxPlayerToken);
						} else { // no a winner, expand further
							if(depth == 3) nodeCount++;
							utility = alphaBetaPruning(addedChild, depth + 1, changeToken(token), changeMinMax(minMax),
														maxPlayerToken, alpha, beta);
						}
						
						if (minMax.equals("max")) {
							alpha = Math.max(alpha, utility);
							bestUtility = Math.max(alpha, utility);
						} else {
							beta = Math.min(beta, utility);
							bestUtility = Math.min(beta, utility);
						}
					} 
				}					
			}
					
		} 
		root.getBoard().setUtility(bestUtility);
		return bestUtility;
	}
	
	/**
	 * Changes a token for the next move.
	 * @param token to change
	 * @return a token for the next move
	 */
	private char changeToken(char token) {
		if (token == 'w') {
			return 'b';
		} else {
			return 'w';
		}
	}
	
	/**
	 * Changes which player (Min or Max) will make a decision at the next level.
	 * @param minMax the current decision maker
	 * @return
	 */
	private String changeMinMax(String minMax) {
		if (minMax.equals("min")) {
			return "max";
		} else {
			return "min";
		}
	}
	

	/**
	 * Checks whether a  game is over
	 * @return
	 */
	public boolean isOver() {
		String result = myBoard.checkWin();
		if (!result.isEmpty()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns a token of a winner(s)
	 * @return
	 */
	public String getWinner() {
		return myBoard.checkWin();
	}
	
	/**
	 * Prints the current game state.
	 */
	public void printGame(FileWriter fw) {
		myBoard.printBoard(fw);
		System.out.println("List of moves made (from first to last):\n" + allMoves.toString());
		//System.out.println(nodeCount);
		
		try {
			fw.write(allMoves.toString() + "\n");
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns a token of Max player.
	 * @return
	 */
	private char findMaxPlayerToken() {
		char maxPlayerToken = humanToken;
		if (AIMinOrMax.equals("max")) {
			maxPlayerToken = AIToken;
		} return maxPlayerToken;
	}

}