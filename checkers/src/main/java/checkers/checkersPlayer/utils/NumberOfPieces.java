package checkers.checkersPlayer.utils;

import checkers.board.Board;
import checkers.checkersGame.Player;
import checkers.checkersPlayer.Heuristic;

public class NumberOfPieces implements Heuristic{

	@Override
	public int score(Board board, Player player) {
		return board.getPieces(player).size();
	}

}
