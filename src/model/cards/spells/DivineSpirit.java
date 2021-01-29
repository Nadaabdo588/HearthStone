package model.cards.spells;
import exceptions.InvalidTargetException;
import model.cards.*;
import model.cards.minions.Minion;

public class DivineSpirit extends Spell implements MinionTargetSpell
{
	public DivineSpirit()
	{
		super("Divine Spirit", 3, Rarity.BASIC);
	}

	public void performAction(Minion m) throws InvalidTargetException {
		m.setMaxHP(2*m.getMaxHP());
		m.setCurrentHP(m.getCurrentHP()*2);
	}

	public Card clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return new DivineSpirit();
	}
}
