package checkers.board;

import checkers.checkersGame.Player;

/**
 * Represents a checkers piece. It is owner by a {@link Player}.
 */
public class Piece {
	private int x;
	private int y;
	private boolean canGoBack = false;
	private Player player; 
	
	/**
	 * Creates a new piece
	 * @param x - position in X
	 * @param y - position in Y
	 * @param player - owner
	 */
	Piece(int x, int y, Player player)
	{
		this.x = x;
		this.y = y;
		this.player = player;
	}
	
	/**
	 * Creates a new piece
	 * @param x - position in X
	 * @param y - position in Y
	 * @param canGoBack - boolean to determine if the piece can go in the opposite direction
	 * @param player - owner
	 */
	Piece(int x, int y, Player player, boolean canGoBack)
	{
		this.x = x;
		this.y = y;
		this.player = player;
		this.canGoBack = canGoBack;
	}
	
	/**
	 * @return X position of this piece
	 */
	public int getXCoord()
	{
		return this.x;
	}
	
	/**
	 * @return Y position of this piece
	 */
	public int getYCoord()
	{
		return this.y;
	}
	
	/**
	 * @return owner of this piece
	 */
	public Player getOwner()
	{
		return this.player;
	}

	/**
	 * Moves this piece to the new coordinates
	 * @param x - new X position
	 * @param y - new Y position
	 */
	protected void move(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Changes the capability of this piece to go back in the opposite direction in the game
	 * @param value T/F
	 */
	protected void setCanGoBack(boolean value){
		this.canGoBack = value;
	}
	
	/**
	 * @return if the piece can go in the opposite direction
	 */
	public boolean getCanGoBack(){
		return canGoBack;
	}
}
