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
import model.cards.spells.DivineSpirit;
import model.cards.spells.HolyNova;
import model.cards.spells.LevelUp;
import model.cards.spells.SealOfChampions;
import model.cards.spells.ShadowWordDeath;

public class Priest extends Hero {

	public Priest() throws IOException, CloneNotSupportedException 
	{
		super("Anduin Wrynn");
		buildDeck();
	}
	
	

	public void buildDeck() throws IOException, CloneNotSupportedException 
	{
		ArrayList<Minion>temp;
		temp = getNeutralMinions(getAllNeutralMinions("neutral_minions.csv") , 13);
		ArrayList<Card> ans = getDeck();
		for(int i=0;i<temp.size();i++)
			{
			ans.add(temp.get(i));
			((Minion)getDeck().get(i)).setListener(this);
			}
		for(int i=0;i<2;i++) {
			ans.add(new DivineSpirit());
			ans.add(new HolyNova());
			ans.add(new ShadowWordDeath());
		}
		ans.add(new Minion("Prophet Velen", 7, Rarity.LEGENDARY , 7 , 7,false , false, false));
		((Minion)getDeck().get(19)).setListener(this);
		Collections.shuffle(ans);
		
	}
    private int findProphetVelen()
    {
    	for(Minion m :getField() )
    	{
    		if(m.getName().equals("Prophet Velen")) {
    			return 8;
    		}
    		
    	}
    	return 2;
    }

	public void useHeroPower(Hero target) throws NotEnoughManaException,
	HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException,
	FullFieldException, CloneNotSupportedException
	{
		super.useHeroPower();
		target.setCurrentHP(target.getCurrentHP()+findProphetVelen());
	}
	public void useHeroPower(Minion target) throws NotEnoughManaException,
	HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException,
	FullFieldException, CloneNotSupportedException
	{
		super.useHeroPower();
		target.setCurrentHP(target.getCurrentHP()+findProphetVelen());
	}
}
