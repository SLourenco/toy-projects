package checkers.checkersPlayer;

import java.util.ArrayList;
import java.util.List;

import checkers.board.Action;
import checkers.board.Board;
import checkers.checkersGame.GamePlayer;
import checkers.board.Piece;
import checkers.checkersGame.Player;
import checkers.checkersGame.utils.Pair;
import checkers.checkersPlayer.utils.EndOfBoardDistance;
import checkers.checkersPlayer.utils.NumberOfPieces;

public class HeuristicPlayer implements GamePlayer{

	@Override
	public Pair<Action, Piece> play(Board board, Player id) {
		List<Piece> pieces = board.getPieces(id);
		Pair<Action, Piece> higherMove = new Pair<Action, Piece>(getHigherAction(Board.getValidActions(board, pieces.get(0), id),pieces.get(0),board), pieces.get(0));
		int higherScore = scoreAction(higherMove.first,pieces.get(0), board);
		for(Piece piece : pieces)
		{
			Action act = getHigherAction(Board.getValidActions(board, piece, id),piece,board);
			int score = scoreAction(act,piece, board);
			if(score > higherScore)
			{
				higherScore = score;
				higherMove = new Pair<Action, Piece>(act, piece);
			}
		}
		return higherMove;
	}

	private Action getHigherAction(List<Action> actions, Piece piece, Board board)
	{
		if(actions != null && !actions.isEmpty())
		{
			Action higherAction = actions.get(0);
			int higherScore = scoreAction(higherAction,piece,board);
			for(Action act : actions)
			{
				int score = scoreAction(act,piece,board);
				if( score > higherScore)
				{
					higherScore = score;
					higherAction = act;
				}
			}
			return higherAction;
		}
		return null;
	}
	
	private int scoreAction(Action action, Piece piece, Board board)
	{
		if(action == null)
		{
			return -500000;
		}
		Board resultingBoard = board.getActionResult(action, piece, piece.getOwner());
		return scoreBoard(resultingBoard, piece.getOwner());
	}
	
	private int scoreBoard(Board board, Player player)
	{
		List<Heuristic> heuristics = new ArrayList<Heuristic>();
		heuristics.add(new NumberOfPieces());
		heuristics.add(new EndOfBoardDistance());
		
		int maxScore = heuristics.get(0).score(board,player);
		int currScore;
		for(Heuristic heuristic : heuristics){
			currScore = heuristic.score(board,player);
			if(currScore > maxScore)
			{
				maxScore = currScore;
			}
		}
		
		return maxScore;
	}
	
	@Override
	public String getPlayerName() {
		return "Heuristic Player";
	}
}
