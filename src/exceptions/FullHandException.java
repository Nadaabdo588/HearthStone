package exceptions;
import model.cards.*;

public class FullHandException extends HearthstoneException {
	private Card burned;
	public Card getBurned() {
		return burned;
	}
	public FullHandException(Card b)
	{
		super();
		burned=b;
	}
	public FullHandException(String s, Card b) {
		super(s);
		burned=b;
	}

}
