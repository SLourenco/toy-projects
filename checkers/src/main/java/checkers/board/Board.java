package checkers.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import checkers.checkersGame.Player;
import checkers.checkersGame.utils.Pair;

public class Board {

	public static int upperLimit = 8;
	private static int downLimit = -1;
	static int PLAYER1_PIECE= 1;
	static int PLAYER2_PIECE = 2;
	private static int PLAYER1_PIECE_PLUS = 11;
	private static int PLAYER2_PIECE_PLUS = 22;
	int[][] board = new int[upperLimit][upperLimit];

	public Board()
	{
		// player 1
		board[0][0] = PLAYER1_PIECE;
		board[2][0] = PLAYER1_PIECE;
		board[4][0] = PLAYER1_PIECE;
		board[6][0] = PLAYER1_PIECE;
		board[1][1] = PLAYER1_PIECE;
		board[3][1] = PLAYER1_PIECE;
		board[5][1] = PLAYER1_PIECE;
		board[7][1] = PLAYER1_PIECE;
		board[0][2] = PLAYER1_PIECE;
		board[2][2] = PLAYER1_PIECE;
		board[4][2] = PLAYER1_PIECE;
		board[6][2] = PLAYER1_PIECE;

		// player 2
		board[1][7] = PLAYER2_PIECE;
		board[3][7] = PLAYER2_PIECE;
		board[5][7] = PLAYER2_PIECE;
		board[7][7] = PLAYER2_PIECE;
		board[0][6] = PLAYER2_PIECE;
		board[2][6] = PLAYER2_PIECE;
		board[4][6] = PLAYER2_PIECE;
		board[6][6] = PLAYER2_PIECE;
		board[1][5] = PLAYER2_PIECE;
		board[3][5] = PLAYER2_PIECE;
		board[5][5] = PLAYER2_PIECE;
		board[7][5] = PLAYER2_PIECE;
	}
	
	/**
	 * Private constructor that initializes at a specific state
	 * @param board state to start in
	 */
	public Board(int[][] board)
	{
		this.board = board;
	}
	
	/**
	 * @param player owner of pieces
	 * @return Pieces belonging to the player
	 */
	public List<Piece> getPieces(Player player)
	{
		List<Piece> pieces = new ArrayList<Piece>();
		int playerPiece = Player.PLAYER1.equals(player) ? PLAYER1_PIECE : PLAYER2_PIECE;
		int playerPiecePlus = Player.PLAYER1.equals(player) ? PLAYER1_PIECE_PLUS : PLAYER2_PIECE_PLUS;
		for(int x=downLimit+1; x<upperLimit; x++)
		{
			for(int y=downLimit+1;y<upperLimit;y++)
			{
				if(board[x][y] == playerPiece)
				{
					pieces.add(new Piece(x, y, player));
				}
				else if (board[x][y] == playerPiecePlus){
					pieces.add(new Piece(x,y,player,true));
				}
			}
		}
		return pieces;
	}
	
	/**
	 * @return Pieces in the game
	 */
	public List<Piece> getPieces()
	{
		List<Piece> pieces = new ArrayList<Piece>();
		for(int x=downLimit+1; x<upperLimit; x++)
		{
			for(int y=downLimit+1;y<upperLimit;y++)
			{
				if(board[x][y] == PLAYER1_PIECE)
				{
					pieces.add(new Piece(x, y, Player.PLAYER1));
				}
				else if(board[x][y] == PLAYER2_PIECE){
					pieces.add(new Piece(x, y, Player.PLAYER2));
				}
				else if (board[x][y] == PLAYER1_PIECE_PLUS){
					pieces.add(new Piece(x,y,Player.PLAYER1,true));
				}
				else if (board[x][y] == PLAYER2_PIECE_PLUS){
					pieces.add(new Piece(x,y,Player.PLAYER2,true));
				}
			}
		}
		return pieces;
	}
	
	/**
	 * @return current board state
	 */
	public int[][] getBoardState()
	{
		return getClone(board);
	}
	
	/**
	 * Validates action
	 * @param board - current board/state
	 * @param move - action to validate
	 * @param piece - target piece for the action
	 * @param player - player to perform the action
	 * @return true if the action is valid
	 */
	public static boolean isValidAction(Board board, Action move, Piece piece, Player player)
	{
		List<Action> validActions = getValidActions(board, piece, player);
		if(validActions != null && validActions.contains(move))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * @param board - current board/state
	 * @param piece - target piece
	 * @param player - player to perform the actions
	 * @return valid actions
	 */
	public static List<Action> getValidActions(Board board, Piece piece, Player player)
	{
		if(piece != null && board != null && player != null)
		{
			if(downLimit < piece.getXCoord() && piece.getXCoord() < upperLimit && downLimit < piece.getYCoord() && piece.getYCoord() < upperLimit)
			{
				if(Player.PLAYER1.equals(player) && (PLAYER1_PIECE == board.board[piece.getXCoord()][piece.getYCoord()] || PLAYER1_PIECE_PLUS == board.board[piece.getXCoord()][piece.getYCoord()])
						|| Player.PLAYER2.equals(player) && (PLAYER2_PIECE == board.board[piece.getXCoord()][piece.getYCoord()] || PLAYER2_PIECE_PLUS == board.board[piece.getXCoord()][piece.getYCoord()]))
				{
					// piece belongs to player
					List<Action> validActions = new ArrayList<Action>();
					
					for(Action action : Action.values())
					{
						Pair<Integer,Integer> modifiers = Action.getModifiers(action);
						if(piece.getXCoord()+modifiers.first > downLimit && piece.getXCoord()+modifiers.first < upperLimit
						   && piece.getYCoord()+modifiers.second > downLimit && piece.getYCoord() + modifiers.second < upperLimit)
						{
							// does not fall off board
							if(piece.getCanGoBack() || (Player.PLAYER1.equals(player) && modifiers.second > 0
									|| Player.PLAYER2.equals(player) && modifiers.second < 0)){
								// is not going back - unless is permitted to
								if(action.isEatAction())
								{
									int otherPlayerPiece = Player.PLAYER1.equals(player) ? PLAYER2_PIECE : PLAYER1_PIECE;
									int otherPlayerPlusPiece = Player.PLAYER1.equals(player) ? PLAYER2_PIECE_PLUS : PLAYER1_PIECE_PLUS;
									Action correspondingMoveAction = action.getCorrespondingMoveAction();
									if(correspondingMoveAction != null)
									{
										Pair<Integer, Integer> altModifiers = Action.getModifiers(correspondingMoveAction);
										if(board.board[piece.getXCoord()+modifiers.first][piece.getYCoord()+modifiers.second] == 0
												&& (board.board[piece.getXCoord()+altModifiers.first][piece.getYCoord()+altModifiers.second] == otherPlayerPiece 
												|| board.board[piece.getXCoord()+altModifiers.first][piece.getYCoord()+altModifiers.second] == otherPlayerPlusPiece))
										{
											validActions.add(action);
										}
									}
								}
								else
								{
									if(board.board[piece.getXCoord()+modifiers.first][piece.getYCoord()+modifiers.second] == 0)
									{
										validActions.add(action);
									}
								}
								
							}
						}
					}
					return validActions;
				}
			}
		}
		return null;
	}
	
	/**
	 * @return winner. Null if the game is not over
	 */
	public Player getWinner()
	{
		boolean firstPlayerPieces = false;
		boolean secondPlayerPieces = false;
		
		for(int x=downLimit+1; x<upperLimit;x++)
		{
			for(int y=downLimit+1;y<upperLimit;y++)
			{
				if(PLAYER1_PIECE == board[x][y] || PLAYER1_PIECE_PLUS == board[x][y])
				{
					firstPlayerPieces = true;
				}
				else if(PLAYER2_PIECE == board[x][y] || PLAYER2_PIECE_PLUS == board[x][y])
				{
					secondPlayerPieces = true;
				}
				
				if(firstPlayerPieces && secondPlayerPieces)
				{
					break;
				}
			}
			if(firstPlayerPieces && secondPlayerPieces)
			{
				break;
			}
		}
		
		if(firstPlayerPieces && !secondPlayerPieces)
		{
			return Player.PLAYER1;
		}
		
		if(secondPlayerPieces && !firstPlayerPieces)
		{
			return Player.PLAYER2;
		}
		return null;
	}
	
	/**
	 * Clones a int[][] structure
	 * @param board int[][] to clone
	 * @return clone of board
	 */
	private int[][] getClone(int[][] board)
	{
		int[][] boardClone = new int[board.length][board[0].length];
		
		for(int x=0; x<board.length; x++){
			boardClone[x] = board[x].clone();
		}
		
		return boardClone;
	}
	/**
	 * @param move action to execute
	 * @param piece target piece for that action
	 * @param player player to perform the action
	 * @return resulting board after applying an action. Not the actual board
	 */
	public Board getActionResult(Action move, Piece piece, Player player)
	{
		int[][] boardClone = getClone(board);
		executeAction(move, piece, player);
		Board boardToReturn = new Board(board);
		this.board = boardClone;
		return boardToReturn;
	}
	
	/**
	 * Prints the current board to system out
	 */
	public void printBoard()
	{
		System.out.print("\n ****************************************** \n");
		
		for(int y=upperLimit-1; y>downLimit; y--)
		{
			System.out.print(y+"  ");
			for(int x=downLimit+1; x < upperLimit; x++)
			{
				System.out.print(board[x][y]);
				System.out.print(" ");
				if(String.valueOf(board[x][y]).length() == 1)
				{
					System.out.print(" ");
				}
			}
			System.out.println(" ");
		}
		System.out.print(" ");
		for(int x=downLimit+1; x < upperLimit; x++)
		{
			System.out.print("  "+x);
		}
		System.out.println();
	}
	
	/**
	 * Executes action on the current board
	 * @param move action to execute
	 * @param piece target piece for that action
	 * @param player player to perform the action
	 */
	public void executeAction(Action move, Piece piece, Player player)
	{
		if(isValidAction(this, move, piece, player))
		{
			Pair<Integer, Integer> modifiers = Action.getModifiers(move);
			board[piece.getXCoord()][piece.getYCoord()] = 0;
			board[piece.getXCoord()+modifiers.first][piece.getYCoord()+modifiers.second] = Player.PLAYER1.equals(player) ? PLAYER1_PIECE : PLAYER2_PIECE;
			if(move.isEatAction())
			{
				Pair<Integer, Integer> altModifiers = Action.getModifiers(move.getCorrespondingMoveAction());
				board[piece.getXCoord()+altModifiers.first][piece.getYCoord()+altModifiers.second] = 0;
			}
			if(Player.PLAYER1.equals(player) && piece.getYCoord()+modifiers.second == upperLimit-1
					|| Player.PLAYER2.equals(player) && piece.getYCoord()+modifiers.second == downLimit+1)
			{
				piece.setCanGoBack(true);
				board[piece.getXCoord()+modifiers.first][piece.getYCoord()+modifiers.second] = Player.PLAYER1.equals(player) ? PLAYER1_PIECE_PLUS : PLAYER2_PIECE_PLUS;
			}
		}
	}
}
