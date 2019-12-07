package checkers.checkersGame.utils;

/**
 * Class that olds two objects
 * @param <T> first object class
 * @param <F> second object class
 */
public class Pair<T,F> 
{
	public T first;
	public F second;
	
	public Pair(T f, F s)
	{
		this.first = f;
		this.second = s;
	}
}
