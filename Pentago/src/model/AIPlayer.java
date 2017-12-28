package model;

public class AIPlayer extends Player {
	
	private static final int gameTreeDepth = 3;
	
	private boolean isMax;

	public AIPlayer(char theToken, boolean theIsMax) {
		super(theToken, theIsMax);
		isMax = theIsMax;
	}

	public void AImove(MyBoard board, int block, int position, char direction) {
		GameTreeNode root = new GameTreeNode(board, null);
		
		char maxPlayerToken = findMaxPlayerToken();
		nodeCount = 0; // counts the number of nodes expanded
		
		// find a utility for the root using MinMax algorithm
		int utility = alphaBetaPruning(root, 0, AIToken, AIMinOrMax, maxPlayerToken, -10000, 10000);
		
		// find an optimal move from the game tree
		String move = findOptimalMove(root, utility);
		move(move, AIToken);	
	}
	
	private char maxPlayerToken() {
		if ((isMax && token == 'w') || (!isMax && token == 'b')) {
			return 'w';
		} else {
			return 'b';
		}
	}
	
	private int alphaBetaPruning(GameTreeNode root, int depth, char token, String minOrMax, char maxPlayerToken, int alpha, int beta) {
		int bestUtility = 0;
		int utility;

		MyBoard currentBoard = root.getBoard();
		if (depth >= gameTreeDepth) {
			if (minOrMax.equals("max")) {
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

				if (!b.checkWin().isEmpty()) { // a winner, no need to rotate
					if (minOrMax.equals("max")) {
						return b.calculateUtility(maxPlayerToken);
					} else { // a Min player
						return b.calculateUtility(maxPlayerToken);
					}
				}
		
				for (int k = 1; k <= 4; k++) { // a block number for rotation	

					if(alpha < beta) {
		
						MyBoard bLeft = b.rotate(k, 'l'); // a child board with left rotation									
						GameTreeNode addedChild = root.addChild(bLeft);
						if (!bLeft.checkWin().isEmpty()) { // a winner after left rotation, don't expand further
							utility = bLeft.calculateUtility(maxPlayerToken);
						} else { // no a winner, expand further
							utility = alphaBetaPruning(addedChild, depth + 1, changeToken(token), changeMinMax(minOrMax),
									maxPlayerToken, alpha, beta);
						}

						if (minOrMax.equals("max")) {
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
							utility = alphaBetaPruning(addedChild, depth + 1, changeToken(token), changeMinMax(minOrMax),
									maxPlayerToken, alpha, beta);
						}
	
						if (minOrMax.equals("max")) {
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

}
