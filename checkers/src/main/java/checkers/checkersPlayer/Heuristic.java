package checkers.checkersPlayer;

import checkers.board.Board;
import checkers.checkersGame.Player;

public interface Heuristic {

	public int score(Board board, Player player);
}
