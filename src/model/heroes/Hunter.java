package model.heroes;

import java.io.*;
import java.util.*;

import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.NotEnoughManaException;
import exceptions.NotYourTurnException;
import model.cards.*;
import model.cards.minions.*;
import model.cards.spells.KillCommand;
import model.cards.spells.MultiShot;

public class Hunter extends Hero {

	public Hunter() throws IOException, CloneNotSupportedException
	{
		super("Rexxar");
		buildDeck();
	}
    
	
	public void buildDeck() throws IOException, CloneNotSupportedException 
	{
		ArrayList<Minion>l= getAllNeutralMinions("neutral_minions.csv");
		ArrayList<Minion>temp = getNeutralMinions(l , 15);
		for(int i=0;i<15;i++) {
			getDeck().add(temp.get(i));
			((Minion)getDeck().get(i)).setListener(this);
		}
		for(int i=0;i<2;i++) {
			getDeck().add(new KillCommand());
			getDeck().add(new MultiShot());

		}
		getDeck().add(new Minion("King Krush", 9, Rarity.LEGENDARY , 8 , 8,false , false , true));
		((Minion)getDeck().get(19)).setListener(this);
		Collections.shuffle(getDeck());
		
	}
	
	public void useHeroPower() throws NotEnoughManaException,
	HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException,
	FullFieldException, CloneNotSupportedException
	{
		super.useHeroPower();
		if(getListener()!=null)
			getListener().damageOpponent(2);
	}
	

	
}
