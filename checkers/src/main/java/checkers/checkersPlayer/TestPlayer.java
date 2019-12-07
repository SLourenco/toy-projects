package checkers.checkersPlayer;

import java.util.List;

import checkers.board.Action;
import checkers.board.Board;
import checkers.checkersGame.Player;
import checkers.checkersGame.utils.Pair;
import checkers.checkersGame.GamePlayer;
import checkers.board.Piece;

public class TestPlayer implements GamePlayer{

	
	@Override
	public Pair<Action, Piece> play(Board board, Player id) 
	{
		List<Piece> pieces = board.getPieces(id);
		List<Action> actions = null;
		int pieceSelected = 0;
		while(actions == null || actions.isEmpty())
		{
			actions = Board.getValidActions(board, pieces.get(pieceSelected), id);
			pieceSelected++;
			if(pieces.size() <= pieceSelected)
			{
				break;
			}
		}
		
		if(actions != null && !actions.isEmpty())
		{
			return new Pair<Action, Piece>(actions.get(0), pieces.get(pieceSelected-1));
		}
		return null;
	}

	@Override
	public String getPlayerName() {
		return "Test Player";
	}
	
}
