package model.cards.spells;

import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;

public class SiphonSoul extends Spell implements LeechingSpell {
	public SiphonSoul() {
		super("Siphon Soul",6,Rarity.RARE);
	}
	public int performAction(Minion m) {
		m.setCurrentHP(0); //dead minion
		return 3;//should be added to hero's health;
	}
	public Card clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return new SiphonSoul ();
	}

}
