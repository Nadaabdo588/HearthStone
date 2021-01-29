package model.cards.spells;
import exceptions.InvalidTargetException;
import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;

public class ShadowWordDeath extends Spell implements MinionTargetSpell {
	public ShadowWordDeath() {
		super("Shadow Word: Death",3,Rarity.BASIC);
	}
	public void performAction(Minion m) throws InvalidTargetException {
		if(m.getAttack()>=5)m.setCurrentHP(0);//dead minoin
		else
		{
			throw new InvalidTargetException("Can not attack Minion with attack < 5");
		}
		
	}
	public Card clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return new ShadowWordDeath();
	}

}
