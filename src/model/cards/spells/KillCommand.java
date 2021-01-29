package model.cards.spells;

import exceptions.InvalidTargetException;
import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;
import model.heroes.Hero;

public class KillCommand extends Spell implements MinionTargetSpell,HeroTargetSpell{
   public KillCommand() 
   {
	   super("Kill Command",3,Rarity.COMMON);
   }

public void performAction(Hero h) {
	h.setCurrentHP(h.getCurrentHP()-3);
}

public void performAction(Minion h) throws InvalidTargetException {
	if(h.isDivine()) h.setDivine(false);
	else h.setCurrentHP(h.getCurrentHP()-5);
}

public Card clone() throws CloneNotSupportedException {
	// TODO Auto-generated method stub
	return new KillCommand();
}
}
