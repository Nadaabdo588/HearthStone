package model.cards.spells;
import exceptions.InvalidTargetException;
import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;

public class SealOfChampions extends Spell implements MinionTargetSpell {
	public SealOfChampions() {
		super("Seal of Champions",3,Rarity.COMMON);
	}

	public void performAction(Minion m) throws InvalidTargetException {
		m.setDivine(true);
		m.setAttack(m.getAttack()+3);
	}
	public Card clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return new SealOfChampions();
	}

}
