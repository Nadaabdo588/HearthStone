package model.cards.spells;

import java.util.ArrayList;

import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;

public class HolyNova extends Spell implements AOESpell{
   public HolyNova() 
   {
	   super("Holy Nova",5,Rarity.BASIC);
   }

public void performAction(ArrayList<Minion> oppField, ArrayList<Minion> curField) {
	for(int i=0;i<oppField.size();i++)
	{   
		Minion m=oppField.get(i);
		if(m.isDivine())m.setDivine(false);
		else m.setCurrentHP(m.getCurrentHP()-2);
		if(m.getCurrentHP()<=0)i--;
	}
	for(Minion m : curField )
	{
		m.setCurrentHP(m.getCurrentHP()+2);
	}
	//Minoins may die 
}

public Card clone() throws CloneNotSupportedException {
	// TODO Auto-generated method stub
	return new HolyNova();
}
}
