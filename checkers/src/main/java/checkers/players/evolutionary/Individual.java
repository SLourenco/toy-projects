package checkers.players.evolutionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import checkers.board.Action;
import checkers.board.Board;
import checkers.board.Piece;
import checkers.checkersGame.Player;
import checkers.checkersGame.utils.Pair;

class Individual {

	private Map<Board, Pair<Action, Piece>> actionsMap = new HashMap<>();
	private static final int initialBoardStatesGenerated = 10;
	
	/**
	 * Creates a random individual
	 */
	Individual(){
		
		for(int z=0; z<initialBoardStatesGenerated;z++){
			int[][] board = new int[8][8];
			Random rnd = new Random();
			// generate random state
			for(int x=0; x<Board.upperLimit; x++){
				for(int y=0; y<Board.upperLimit; y++){
					board[x][y] = rnd.nextInt(3);
				}
			}
			Board b = new Board(board);
			List<Piece> pieces = b.getPieces();
			for(Piece piece : pieces)
			{
				List<Action> actionsForPiece = new ArrayList<Action>();
				List<Action> tempActions = Board.getValidActions(b, piece, Player.PLAYER1);
				if(tempActions != null){
					actionsForPiece.addAll(tempActions);
				}
				tempActions = Board.getValidActions(b, piece, Player.PLAYER2);
				if(tempActions != null){
					actionsForPiece.addAll(tempActions);
				}
				if(actionsForPiece != null && !actionsForPiece.isEmpty()){
					// chooses random action
					Action choosenAction = actionsForPiece.get(rnd.nextInt(actionsForPiece.size()));
					actionsMap.put(b, new Pair<Action, Piece>(choosenAction, piece));
				}
			}
		}
		
	}
	/**
	 * @param board current state
	 * @param player player to play
	 * @return action for this player with this state
	 */
	Pair<Action, Piece> getAction(Board board, Player player){
		Pair<Action, Piece> result = actionsMap.get(board);
		
		if(result != null && player.equals(result.second.getOwner()))
		{
			return result; // state exists
		}
		
		// Compute closest state
		Iterator<Board> it = actionsMap.keySet().iterator();
		Board closestBoard = null;
		int numberOfEquals = -1;
		while(it.hasNext()){
			Board b = it.next();
			int diff = scoreDifference(board.getBoardState(), b.getBoardState());
			if(diff > numberOfEquals)
			{
				closestBoard = b;
				numberOfEquals = diff;
			}
		}
		
		result = actionsMap.get(closestBoard); // closest state
		if(Board.isValidAction(board, result.first, result.second, player)){
			return result; // closest state has valid action
		}
		
		Random rnd = new Random();
		List<Piece> pieces = board.getPieces(player);
		Piece piece = null;
		List<Action> actions= null;
		while(actions == null || actions.isEmpty())
		{
			piece = pieces.get(rnd.nextInt(pieces.size()));
			
			actions= Board.getValidActions(board, piece, player);
		}
		int b = actions.size();
		System.out.println(b);
		int a = rnd.nextInt(b);
		System.out.println(a);
		return new Pair<Action, Piece>(actions.get(a), piece); // random action for random piece, since nothing was predicted
	}
	
	private int scoreDifference(int[][] b1, int[][] b2){
		int score = 0;
		for(int x = 0; x<Board.upperLimit; x++){
			for(int y = 0; y<Board.upperLimit; y++){
				score += Math.abs(b1[x][y] - b2[x][y]);
			}
		}
		return score;
	}
	
	/**
	 * @return fitness value of individual
	 */
	int computeIndividualFitness(){
		Iterator<Board> it = actionsMap.keySet().iterator();
		Board currBoard;
		int totalValue = 0;
		while(it.hasNext()){
			currBoard = it.next();
			totalValue += computeBoardValue(currBoard, Player.PLAYER1) + computeBoardValue(currBoard, Player.PLAYER2);
		}
		return totalValue;
	}
	
	/**
	 * @return value of board
	 */
	private int computeBoardValue(Board board, Player player){
		return board.getPieces(player).size(); // - board.getPieces(Player.PLAYER1.equals(player) ? Player.PLAYER2 : Player.PLAYER1).size();
	}
	
	/**
	 * Reproduces individuals
	 * @param ind1
	 * @param ind2
	 * @return
	 */
	static Individual reproduce(Individual ind1, Individual ind2){
		Individual nextGen = new Individual(); // genetic mutations
		nextGen.actionsMap.putAll(ind1.actionsMap); // crossover
		nextGen.actionsMap.putAll(ind2.actionsMap); // crossover
		return nextGen;
	}
}
