package model.heroes;

import java.awt.Polygon;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.NotEnoughManaException;
import exceptions.NotYourTurnException;
import exceptions.TauntBypassException;
import model.cards.*;
import model.cards.minions.*;
import model.cards.spells.*;

public class Mage extends Hero {


	public Mage() throws IOException, CloneNotSupportedException
	{
		super("Jaina Proudmoore");
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
			ans.add(new Polymorph());
			ans.add(new Flamestrike());
			ans.add(new Pyroblast());
		}
		ans.add(new Minion("Kalycgos", 10, Rarity.LEGENDARY , 4 , 12,false , false , false));
		((Minion)getDeck().get(19)).setListener(this);
		Collections.shuffle(ans);
		
	}
	
	
	public void useHeroPower(Hero target) throws NotEnoughManaException,
	HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException,
	FullFieldException, CloneNotSupportedException
	{
		super.useHeroPower();
		target.setCurrentHP(target.getCurrentHP()-1);
	}
	
	public void useHeroPower(Minion target) throws NotEnoughManaException,
	HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException,
	FullFieldException, CloneNotSupportedException
	{
		super.useHeroPower();
		
		if(target.isDivine())
			target.setDivine(false);
		else
			target.setCurrentHP(target.getCurrentHP()-1);
	}// what if there is a taunt minion with the opponent
	

	
	
}
