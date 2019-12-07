package checkers.checkersGame;

import java.util.ArrayList;
import java.util.List;

import checkers.board.Action;
import checkers.board.Board;
import checkers.board.Piece;
import checkers.checkersGame.utils.Pair;
import checkers.checkersPlayer.HeuristicPlayer;
import checkers.players.evolutionary.EvolutionaryPlayer;

public class Game {
	private GamePlayer player1;
	private GamePlayer player2;
	
	public boolean playCheckersGame(List<GamePlayer> players)
	{
		initializePlayers(players);
		Board board = new Board();
		int test = 0;
//		while(board.getWinner() != null)
		while(test < 100)
		{
			if(board.getWinner() == null)
			{
				System.out.println("Turn: "+test);
				System.out.println("Player 1");
				executePlay(player1, Player.PLAYER1, board);
				board.printBoard();
				if(board.getWinner() == null)
				{
					System.out.println("Player 2");
					executePlay(player2, Player.PLAYER2, board);
				}
				else
				{
					System.out.println("Winner: "+board.getWinner());
					break;
				}
			}
			else
			{
				System.out.println("Winner: "+board.getWinner());
				board.printBoard();
				break;
			}
			test++;
		}
		
		
		return false;
	}
	
	private void initializePlayers(List<GamePlayer> players)
	{
		this.player1 = players.get(0);
		this.player2 = players.get(1);
	}
	
	private void executePlay(GamePlayer player, Player id, Board board)
	{
		Pair<Action, Piece> move = null;
		move = player.play(board, id);
		if(move != null && move.first != null && move.second != null)
		{
			System.out.println("Player: "+player.getPlayerName()+", Action: "+move.first.getName()+", Piece: "+move.second.getXCoord()+" "+move.second.getYCoord());
			board.executeAction(move.first, move.second, id);
		}
		else
		{
			System.out.println("No more moves for player "+player.getPlayerName());
			System.exit(1);
		}
	}
	
	public static void main(String[] args)
	{
		Game game = new Game();
		System.out.println("Created Game");
		List<GamePlayer> players = new ArrayList<GamePlayer>();
		System.out.println("Adding Players");
//		players.add(new GreedyPlayer()); // must be player 1
//		players.add(new TestPlayer());
		players.add(new HeuristicPlayer());
		players.add(new EvolutionaryPlayer(1000));
		System.out.println("Starting Game");
		game.playCheckersGame(players);
	}
}
