package checkers.checkersPlayer;

import java.util.List;

import checkers.board.Action;
import checkers.board.Board;
import checkers.checkersGame.Player;
import checkers.checkersGame.utils.Pair;
import checkers.checkersGame.GamePlayer;
import checkers.board.Piece;

public class GreedyPlayer implements GamePlayer{
	
	@Override
	public Pair<Action, Piece> play(Board board, Player id) 
	{
		List<Piece> pieces = board.getPieces(id);
		Pair<Action, Piece> higherMove = new Pair<Action, Piece>(getHigherAction(Board.getValidActions(board, pieces.get(0), id)), pieces.get(0));
		int higherScore = scoreAction(higherMove.first) + scorePiece(pieces.get(0));
		for(Piece piece : pieces)
		{
			Action act = getHigherAction(Board.getValidActions(board, piece, id));
			int score = scoreAction(act)+scorePiece(piece);
			if(score > higherScore)
			{
				higherScore = score;
				higherMove = new Pair<Action, Piece>(act, piece);
			}
		}
		return higherMove;
	}

	@Override
	public String getPlayerName() {
		return "Greedy Player";
	}
	
	

	private Action getHigherAction(List<Action> actions)
	{
		if(actions != null && !actions.isEmpty())
		{
			Action higherAction = actions.get(0);
			int higherScore = scoreAction(higherAction);
			for(Action act : actions)
			{
				int score = scoreAction(act);
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
	
	private int scoreAction(Action action)
	{
		if(action == null)
		{
			return -500000;
		}
		switch (action) {
		case UP_LEFT:
			return 2;
		case UP_RIGHT:
			return 2;
		case DOWN_LEFT:
			return -10;
		case DOWN_RIGHT:
			return -10;
		case EAT_UP_LEFT:
			return 4;
		case EAT_UP_RIGHT:
			return 4;
		case EAT_DOWN_LEFT:
			return 3;
		case EAT_DOWN_RIGHT:
			return 3;
		default:
			return 0;
		}
	}
	private int scorePiece(Piece piece)
	{
		if(piece == null)
		{
			return -500000;
		}
		return piece.getXCoord();
	}
}
