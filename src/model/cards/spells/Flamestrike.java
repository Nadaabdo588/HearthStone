package model.cards.spells;

import java.util.ArrayList;

import model.cards.*;
import model.cards.minions.*;

public class Flamestrike extends Spell implements AOESpell
{
	public Flamestrike()
	{
		super("Flamestrike", 7, Rarity.BASIC);
	}

	public void performAction(ArrayList<Minion> oppField, ArrayList<Minion> curField) {
		for (int i=0;i<oppField.size();i++) 
		{ 
			Minion m=oppField.get(i);
			if(m.isDivine())m.setDivine(false);
			else m.setCurrentHP(m.getCurrentHP()-4);
			if(m.getCurrentHP()<=0)
				i--;
		}
		//Do not forget dead minoins
	}

	public Card clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return new Flamestrike() ;
	}
}
