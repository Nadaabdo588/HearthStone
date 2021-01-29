package model.cards.spells;
import exceptions.InvalidTargetException;
import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;

public class Polymorph extends Spell implements MinionTargetSpell{
	public Polymorph() {
		super("Polymorph",4,Rarity.BASIC);
	}
	public void performAction(Minion m) throws InvalidTargetException {
		
		m.setName("Sheep");
		m.setMaxHP(1);
		m.setCurrentHP(1);
		m.setAttack(1);
		m.setManaCost(1);
		m.setTaunt(false);
		m.setDivine(false);
		m.setSleeping(true);
		
		
	}
	public Card clone() throws CloneNotSupportedException {
		return new Polymorph();
	}

}
