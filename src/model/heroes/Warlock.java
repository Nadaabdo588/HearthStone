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
import model.cards.minions.Icehowl;
import model.cards.minions.Minion;
import model.cards.spells.CurseOfWeakness;
import model.cards.spells.DivineSpirit;
import model.cards.spells.HolyNova;
import model.cards.spells.KillCommand;
import model.cards.spells.MinionTargetSpell;
import model.cards.spells.Polymorph;
import model.cards.spells.Pyroblast;
import model.cards.spells.SealOfChampions;
import model.cards.spells.ShadowWordDeath;
import model.cards.spells.SiphonSoul;
import model.cards.spells.Spell;
import model.cards.spells.TwistingNether;

public class Warlock extends Hero {

	public Warlock() throws IOException, CloneNotSupportedException 
	{
		super("Gul'dan");
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
			ans.add(new CurseOfWeakness());
			ans.add(new SiphonSoul());
			ans.add(new TwistingNether());
		}
		ans.add(new Minion("Wilfred Fizzlebang", 6, Rarity.LEGENDARY , 4 , 4,false , false, false));
		((Minion)getDeck().get(19)).setListener(this);
		Collections.shuffle(ans);
	}
	
	public void useHeroPower() throws NotEnoughManaException,
	HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException,
	FullFieldException, CloneNotSupportedException
	{   
		boolean flag=(getHand().size()<9);
		super.useHeroPower();
		Card c = super.drawCard();
		setCurrentHP(getCurrentHP()-2);
		if(c instanceof Minion&& findWilfredFizzlebang ())
		{
			getHand().get((getHand().size())-1).setManaCost(0);
			if(findChromaggus () && flag ) {
				getHand().get(getHand().size()-2).setManaCost(0);
			}
		}
	}
	private boolean findChromaggus()
	{
		for( Minion m : getField()) {
			if(m.getName().equals("Chromaggus"))
				return true ;
		}
		return false;
	}
	private boolean findWilfredFizzlebang()
	{
		for(Minion m : getField()) {
			if(m.getName().equals("Wilfred Fizzlebang"))
				return true;
		}
		return false;
	}
	
}
