package model;

import java.util.List;

/**
 * Represents AI player.
 * @author Evgeniia Shcherbinina
 *
 */
public class AIPlayer extends Player {
	
	/**
	 * The max depth of the game tree.
	 */
	private static final int gameTreeDepth = 3;
	
	/**
	 * Whether an AI player is a max player.
	 */
	private boolean isMax;

	/**
	 * A constructor.
	 * @param theToken of AI player
	 * @param theIsMax Whether AI player is a max player.
	 */
	public AIPlayer(char theToken, boolean theIsMax) {
		super(theToken, "Alie");
		isMax = theIsMax;
	}

	/**
	 * AI move using min-max algorithm with alpha-beta pruning.
	 * @param board
	 * @return a new board after the move
	 */
	public MyBoard AImove(MyBoard board) {
		GameTreeNode root = new GameTreeNode(board, null);
		
		// find a utility for the root using MinMax algorithm
		int utility = alphaBetaPruning(root, 0, getToken(), isMax, maxPlayerToken(), Integer.MIN_VALUE, Integer.MAX_VALUE);
		
		// find an optimal move from the game tree
		String move = findOptimalMove(root, utility);
		
		int block = Integer.parseInt(move.substring(0, 1));
		int position = Integer.parseInt(move.substring(2, 3));
		int blockRotate = Integer.parseInt(move.substring(4, 5));
		char direction = move.charAt(5);
		
		return move(board, block, position, blockRotate, direction);	
	}
	
	/**
	 * Finds a child of the given root with the optimal utility.
	 * @param root
	 * @param utility
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
	 * Returns a token of a max player.
	 * @return
	 */
	private char maxPlayerToken() {
		if ((isMax && getToken() == 'w') || (!isMax && getToken() == 'b')) {
			return 'w';
		} else {
			return 'b';
		}
	}
	
	/**
	 * Returns the optimal utility of the given root using min-max algorithm with alpha-beta pruning.
	 * @param root the current node
	 * @param depth of the root node
	 * @param token of the current player
	 * @param isMax whether the current player is the max player
	 * @param maxPlayerToken
	 * @param alpha
	 * @param beta
	 * @return the optimal utility of the given node
	 */
	private int alphaBetaPruning(GameTreeNode root, int depth, char token, boolean isMax, char maxPlayerToken, int alpha, int beta) {
		int bestUtility = 0;
		int utility;

		MyBoard currentBoard = root.getBoard();
		if (depth >= gameTreeDepth) {
			if (isMax) {
				return Math.max(alpha, currentBoard.calculateUtility(maxPlayerToken));
			} else { // AI is a Min player
				return Math.min(beta, currentBoard.calculateUtility(maxPlayerToken));
			}
		}


		for (int i = 1; i <= 4; i++) { // a block number
			for (int j = 1; j <= 9; j++) { // position

				if (!currentBoard.isLegalMove(i, j)) { // skip this position
					continue;
				}
	 
				MyBoard b = currentBoard.makeMove(i, j, token); // a child board without rotation
		
				for (int k = 1; k <= 4; k++) { // a block number for rotation	

					if(alpha < beta) {
		
						MyBoard bLeft = b.rotate(k, 'l'); // a child board with left rotation									
						GameTreeNode addedChild = root.addChild(bLeft);
						if (!bLeft.checkWin().isEmpty()) { // a winner after left rotation, don't expand further
							utility = bLeft.calculateUtility(maxPlayerToken);
						} else { // no a winner, expand further
							utility = alphaBetaPruning(addedChild, depth + 1, changeToken(token), changeMinMax(isMax),
									maxPlayerToken, alpha, beta);
						}

						if (isMax) {
							alpha = Math.max(alpha, utility);
							bestUtility = Math.max(alpha, utility);
						} else {
							beta = Math.min(beta, utility);
							bestUtility = Math.min(beta, utility);
						}
					}
			
					if (alpha < beta) {								
						MyBoard bRight = b.rotate(k, 'r'); // a child board with right rotation
						GameTreeNode addedChild = root.addChild(bRight);
												
						if (!bRight.checkWin().isEmpty()) { // a winner after left rotation, don't expand further
							utility = bRight.calculateUtility(maxPlayerToken);
						} else { // no a winner, expand further
							utility = alphaBetaPruning(addedChild, depth + 1, changeToken(token), changeMinMax(isMax),
									maxPlayerToken, alpha, beta);
						}
	
						if (isMax) {
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
	
//	private int helperPruning(MyBoard b, char direction, GameTreeNode root, char token, boolean isMax,
//			char maxPlayerToken, int alpha, int beta, int depth, int blockNumber) {
//		int utility = 0;
//		MyBoard bRight = b.rotate(blockNumber, direction); // a child board with right rotation
//		GameTreeNode addedChild = root.addChild(bRight);
//								
//		if (!bRight.checkWin().isEmpty()) { // a winner after left rotation, don't expand further
//			utility = bRight.calculateUtility(maxPlayerToken);
//		} else { // no a winner, expand further
//			utility = alphaBetaPruning(addedChild, depth + 1, changeToken(token), changeMinMax(isMax),
//					maxPlayerToken, alpha, beta);
//		}
//
//		if (isMax) {
//			alpha = Math.max(alpha, utility);
//			return Math.max(alpha, utility);
//		} else {
//			beta = Math.min(beta, utility);
//			return Math.min(beta, utility);
//		}
//	}
	
	/**
	 * Changes a token for the next move.
	 * @param token to change
	 * @return a token for the next move
	 */
	private char changeToken(char token) {
		return token == 'w'? 'b' : 'w'; 
	}
	
	/**
	 * Changes which player (Min or Max) will make a decision at the next level.
	 * @param minMax the current decision maker
	 * @return
	 */
	private boolean changeMinMax(boolean isMax) {
		return !isMax;
	}

}
