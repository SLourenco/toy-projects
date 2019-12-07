package checkers.checkersGame;

import checkers.board.Action;
import checkers.board.Board;
import checkers.board.Piece;
import checkers.checkersGame.utils.Pair;

public interface GamePlayer {

	public Pair<Action, Piece> play(Board board, Player id);
	
	/**
	 * Returns player name to be printed if this player wins (Not player 1 or player 2)
	 */
	public String getPlayerName();
	
}
