package checkers.board;

import checkers.checkersGame.utils.Pair;

/**
 *  set of possible actions
 */
public enum Action {
	UP_LEFT(-1,+1,"UP+LEFT"),
	UP_RIGHT(+1,+1,"UP+RIGHT"),
	DOWN_LEFT(-1,-1,"DOWN+LEFT"),
	DOWN_RIGHT(+1,-1,"DOWN+RIGHT"),
	EAT_UP_LEFT(-2,+2,"UP+LEFT+EAT"),
	EAT_UP_RIGHT(+2,+2,"UP+RIGHT+EAT"),
	EAT_DOWN_LEFT(-2,-2,"DOWN+LEFT+EAT"),
	EAT_DOWN_RIGHT(+2,-2,"DOWN+RIGHT+EAT");
	
	int x;
	int y;
	String desc;
	
	private Action(int x, int y, String desc)
	{
		this.x = x;
		this.y = y;
		this.desc = desc;
	}
	
	/**
	 * @param action 
	 * @return modifiers to the piece
	 */
	protected static Pair<Integer, Integer> getModifiers(Action action)
	{
		return new Pair<Integer, Integer>(action.x, action.y);
	}
	
	/**
	 * @param action
	 * @return true if the action eliminates another players piece
	 */
	public boolean isEatAction()
	{
		switch (this) {
		case EAT_UP_LEFT:
			return true;
		case EAT_UP_RIGHT:
			return true;
		case EAT_DOWN_LEFT:
			return true;
		case EAT_DOWN_RIGHT:
			return true;
		default:
			return false;
		}
	}
	
	/**
	 * @param action
	 * @return equivalent move action if there was no enemy piece to eat
	 */
	protected Action getCorrespondingMoveAction()
	{
		switch (this) {
		case EAT_UP_LEFT:
			return UP_LEFT;
		case EAT_UP_RIGHT:
			return UP_RIGHT;
		case EAT_DOWN_LEFT:
			return DOWN_LEFT;
		case EAT_DOWN_RIGHT:
			return DOWN_RIGHT;
		default:
			return null;
		}
	}
	
	/**
	 * @return action name
	 */
	public String getName()
	{
		return desc;
	}
}
