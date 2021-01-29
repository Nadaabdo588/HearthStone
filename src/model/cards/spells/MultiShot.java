package model.cards.spells;
import java.util.ArrayList;

import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;

public class MultiShot extends Spell implements AOESpell {
      public MultiShot() {
    	  super("Multi-Shot",4,Rarity.BASIC);
      }

	public void performAction(ArrayList<Minion> oppField, ArrayList<Minion> curField) {
		if(oppField.size()!=0)
		{
			if(oppField.size()==1)
			{
				if(oppField.get(0).isDivine())oppField.get(0).setDivine(false);
				else oppField.get(0).setCurrentHP(oppField.get(0).getCurrentHP()-3);
			}
			else
			{
				int i = (int)(Math.random()*oppField.size());
				Minion m=oppField.get(i);
				if(oppField.get(i).isDivine())oppField.get(i).setDivine(false);
				else oppField.get(i).setCurrentHP(oppField.get(i).getCurrentHP()-3);
				
				int j= (int)(Math.random()*oppField.size());
				if(m.getCurrentHP()>0) {
					while(i==j)
					{
						 j = (int)(Math.random()*oppField.size());
					}
				}
				
				if(oppField.get(j).isDivine())oppField.get(j).setDivine(false);
				else oppField.get(j).setCurrentHP(oppField.get(j).getCurrentHP()-3);
			}
			//minions can die
		}
		
	}

	public Card clone() throws CloneNotSupportedException {
		
		return new MultiShot();
	}
}
