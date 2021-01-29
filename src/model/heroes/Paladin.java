package model.heroes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.NotEnoughManaException;
import exceptions.NotYourTurnException;
import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;
import model.cards.spells.*;

public class Paladin extends Hero {

	public Paladin() throws IOException, CloneNotSupportedException
	{
		super("Uther Lightbringer");
		buildDeck();
	}
	
	
	public void buildDeck() throws IOException, CloneNotSupportedException 
	{
		ArrayList<Minion>temp;
		temp = getNeutralMinions(getAllNeutralMinions("neutral_minions.csv") , 15);
		ArrayList<Card> ans = getDeck();
		for(int i=0;i<temp.size();i++)
			{
			ans.add(temp.get(i));
			((Minion)getDeck().get(i)).setListener(this);
			}
		for(int i=0;i<2;i++) {
			ans.add(new SealOfChampions());
			ans.add(new LevelUp());
		}
		ans.add(new Minion("Tirion Fordring", 4, Rarity.LEGENDARY , 6 , 6,true , true , false));
		((Minion)getDeck().get(19)).setListener(this);
		Collections.shuffle(ans);
		
	}

	

	public void useHeroPower() throws NotEnoughManaException,
	HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException,
	FullFieldException, CloneNotSupportedException
	{
		super.useHeroPower();
		Minion m = new Minion("Silver Hand Recruit", 1, Rarity.BASIC, 1, 1, false, false, false);
		m.setListener(this);
		if(getField().size()==7)
			throw new FullFieldException("you can't play a new minion");
		getField().add(m);
	}
	
	
}
