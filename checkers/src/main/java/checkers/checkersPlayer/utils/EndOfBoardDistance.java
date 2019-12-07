package checkers.checkersPlayer.utils;

import java.util.List;

import checkers.board.Board;
import checkers.board.Piece;
import checkers.checkersGame.Player;
import checkers.checkersPlayer.Heuristic;

public class EndOfBoardDistance implements Heuristic{

	@Override
	public int score(Board board, Player player) {
		List<Piece> pieces = board.getPieces(player);
		List<Piece> otherPlayerPieces = board.getPieces(Player.PLAYER1.equals(player) ? Player.PLAYER1 : Player.PLAYER2);
		int sum = 0;
		int plusPieces = countPlusPieces(pieces);
		int enemyPlusPieces = countPlusPieces(otherPlayerPieces);
		if(Player.PLAYER1.equals(player)){
			for(Piece piece:pieces){
				sum += piece.getYCoord();
			}
		}
		else
		{
			for(Piece piece:pieces){
				sum += Math.abs(Board.upperLimit - piece.getYCoord());
			}
		}
		return sum*(2*(pieces.size()-plusPieces+(plusPieces*2))-(otherPlayerPieces.size()-enemyPlusPieces+(enemyPlusPieces*2)));
	}

	private int countPlusPieces(List<Piece> pieces){
		int sum = 0;
		for(Piece piece : pieces){
			if(piece.getCanGoBack())
			{
				sum ++;
			}
		}
		return sum;
	}
}
