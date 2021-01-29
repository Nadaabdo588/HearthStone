package model.heroes;

import java.util.*;

import engine.*;
import model.cards.*;
import model.cards.minions.*;
import model.cards.spells.*;
import exceptions.*;
import java.io.*;

public abstract class Hero implements MinionListener {
	private HeroListener listener;
	private ActionValidator validator;
	private String name;
	private int currentHP = 30; //cannot exceed 30.
	private boolean heroPowerUsed;
	private int totalManaCrystals; //cannot exceed 10.
	private int currentManaCrystals; // each turn starts with val of totalMCrys.
	private ArrayList<Card> deck; // Object should be of type "Card".
	private ArrayList<Minion> field;//Object should be of type "Minion".
	private ArrayList<Card> hand; // Object should be of type "Card".
	private int fatigueDamage;
	public Hero(String name)
	{
		this.name=name;
		deck = new ArrayList<Card>();
		field = new ArrayList<Minion>();
		hand = new ArrayList<Card>();
		fatigueDamage=0;
	}
	public void onMinionDeath(Minion m) {
		getField().remove(m);
	}
	public int getCurrentHP() {
		return currentHP;
	}
	public void setCurrentHP(int currentHP) {
		this.currentHP = Math.min(currentHP,30);
		if(this.currentHP<=0 && listener != null)
			listener.onHeroDeath();
	}
	public boolean isHeroPowerUsed() {
		return heroPowerUsed;
	}
	public void setHeroPowerUsed(boolean heroPowerUsed) {
		this.heroPowerUsed = heroPowerUsed;
	}
	public int getTotalManaCrystals() {
		return totalManaCrystals;
	}
	public void setTotalManaCrystals(int totalManaCrystals) {
		this.totalManaCrystals = Math.min(10, totalManaCrystals);
		this.setCurrentManaCrystals(totalManaCrystals);
	}
	public int getCurrentManaCrystals() {
		return currentManaCrystals;
	}
	public void setCurrentManaCrystals(int currentManaCrystals) {
		this.currentManaCrystals = Math.min(currentManaCrystals,10);
	}
	public String getName() {
		return name;
	}
	public ArrayList<Card> getDeck() {
		return deck;
	}
	public ArrayList<Minion> getField() {
		return field;
	}
	public ArrayList<Card> getHand() {
		return hand;
	}
	public void setValidator(ActionValidator validator) {
		this.validator = validator;
	}
	public HeroListener getListener() {
		return listener;
	}
	public void setListener(HeroListener listener) {
		this.listener = listener;
	}

	public final static ArrayList<Minion> getAllNeutralMinions(String filePath) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String inp = "";
		ArrayList<Minion> l = new ArrayList<Minion>();
		while ((inp = br.readLine()) != null)
		{
			String[] temp = inp.split(",");
			if(temp[0].equals("Icehowl"))
			{
				l.add(new Icehowl());continue;
			}
			Rarity r=Rarity.RARE;
			switch(temp[2].charAt(0))
			{
			case 'b' : r = Rarity.BASIC;break;
			case 'c' : r = Rarity.COMMON;break;
			case 'r' : r = Rarity.RARE;break;
			case 'e' : r = Rarity.EPIC;break;
			case 'l' : r = Rarity.LEGENDARY;break;		
			}
			l.add(new Minion(temp[0],Integer.parseInt(temp[1]), r,Integer.parseInt(temp[3]),Integer.parseInt(temp[4]),temp[5].equals("TRUE"),
					temp[6].equals("TRUE"),temp[7].equals("TRUE")));
		}
		return l;
	}
	
	public final static ArrayList<Minion> getNeutralMinions(ArrayList<Minion> minions,int count) throws CloneNotSupportedException
	{
		ArrayList <Minion> ans = new ArrayList<Minion>();
		int[] duplicates = new int [minions.size()];
		while(ans.size()<count)
		{
			int ind = (int)(Math.random()*minions.size());
			if((duplicates[ind]<2 && minions.get(ind).getRarity() != Rarity.LEGENDARY)|| (duplicates[ind]<1 && minions.get(ind).getRarity() == Rarity.LEGENDARY)) {
				if(minions.get(ind).getName().equals("icehowl"))
				{
					ans.add(new Icehowl());
				}
				else
				{
					ans.add(minions.get(ind).clone());
				}
				duplicates[ind]++;
			}
		}
		return ans;
	}
	
	public abstract void buildDeck() throws IOException, CloneNotSupportedException ;
	/******************/
	public void useHeroPower() throws NotEnoughManaException,
	HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException,
	FullFieldException, CloneNotSupportedException
	{
		if(validator!=null)
		{
			validator.validateTurn(this);
			validator.validateUsingHeroPower(this);
		}
		setCurrentManaCrystals(getCurrentManaCrystals()-2);
		heroPowerUsed = true;
	}
	
	
	public void playMinion(Minion m) throws NotYourTurnException,
	NotEnoughManaException, FullFieldException
	{
		if(validator!=null)
		{
			validator.validateTurn(this);
			validator.validateManaCost(m);
			validator.validatePlayingMinion(m);
		}
		field.add(m);
		hand.remove(m);
		setCurrentManaCrystals(getCurrentManaCrystals()- m.getManaCost());
		m.setListener(this);
	}
	

	public void attackWithMinion(Minion attacker, Minion target) throws
	CannotAttackException, NotYourTurnException, TauntBypassException,
	InvalidTargetException, NotSummonedException
	{   
		if(validator != null)
		{
			validator.validateTurn(this);
			validator.validateAttack(attacker, target);
		}
		attacker.attack(target);	
	}
	

	public void attackWithMinion(Minion attacker, Hero target) throws
	CannotAttackException, NotYourTurnException, TauntBypassException,
	InvalidTargetException, NotSummonedException
	{   
		if(validator != null)
		{
			validator.validateTurn(this);
			validator.validateAttack(attacker, target);
		}
		attacker.attack(target);
	}
	
	// we have to handle manacost of each spell
	public void castSpell(FieldSpell s) throws NotYourTurnException,NotEnoughManaException
	{
		if(validator != null)
		{	
			validator.validateTurn(this);
			validator.validateManaCost((Card)s);
		}
		if(findKalycgos())
			((Spell)s).setManaCost(((Spell)s).getManaCost()-4);	
		this.setCurrentManaCrystals(this.getCurrentManaCrystals()-((Spell)s).getManaCost());
		s.performAction(field);
		hand.remove((Card)s);
		
	}

	public void castSpell(AOESpell s, ArrayList<Minion>oppField) throws NotYourTurnException, NotEnoughManaException
	{
		if(validator != null)
		{	
			validator.validateTurn(this);
			validator.validateManaCost((Card)s);
		}
		if(findKalycgos())
			((Spell)s).setManaCost(((Spell)s).getManaCost()-4);
		this.setCurrentManaCrystals(this.getCurrentManaCrystals()-((Spell)s).getManaCost());
		s.performAction(oppField, field);
		hand.remove((Card)s);
	}
	
	public void castSpell(MinionTargetSpell s, Minion m) throws NotYourTurnException,NotEnoughManaException, InvalidTargetException
	{
		if(validator != null)
		{	
			validator.validateTurn(this);
			validator.validateManaCost((Card)s);
		}
		if(findKalycgos())
			((Spell)s).setManaCost(((Spell)s).getManaCost()-4);
		s.performAction(m);
		this.setCurrentManaCrystals(this.getCurrentManaCrystals()-((Spell)s).getManaCost());
		hand.remove((Card)s);
	}
	
	public void castSpell(HeroTargetSpell s, Hero h) throws NotYourTurnException,NotEnoughManaException
	{
		if(validator != null)
		{	
			validator.validateTurn(this);
			validator.validateManaCost((Card)s);
		}
		if(findKalycgos())
			((Spell)s).setManaCost(((Spell)s).getManaCost()-4);
		s.performAction(h);
		this.setCurrentManaCrystals(this.getCurrentManaCrystals()-((Spell)s).getManaCost());
		hand.remove((Card)s);
	}
	
	public void castSpell(LeechingSpell s, Minion m) throws NotYourTurnException,NotEnoughManaException
	{
		if(validator != null)
		{	
			validator.validateTurn(this);
			validator.validateManaCost((Card)s);
		}
		if(findKalycgos())
			((Spell)s).setManaCost(((Spell)s).getManaCost()-4);
		this.setCurrentManaCrystals(this.getCurrentManaCrystals()-((Spell)s).getManaCost());
		setCurrentHP(currentHP+s.performAction(m));
		hand.remove((Card)s);
	}

	
	public void endTurn() throws FullHandException, CloneNotSupportedException
	{
		if(listener != null)
			listener.endTurn();
	}

	public Card drawCard() throws FullHandException, CloneNotSupportedException 
	{
		if(deck.size()==0)
		{
			setCurrentHP(getCurrentHP()-fatigueDamage);
			fatigueDamage++;
			return null;
		}
		Card c = deck.remove(0);
		if(deck.isEmpty())
			fatigueDamage = 1;
		if(hand.size()==10)
		{
			throw new FullHandException("your hand is full and you can't draw a card",c);
		}
		hand.add(c);
		if(hand.size()<10)
		{
			for(Minion m : field)
			{
				if(m.getName().equals("Chromaggus")) 
				{
					hand.add(c.clone());
				}
			}
		}
		return c;
		
	}
	private boolean findKalycgos () {
		for (Minion m : field)
		{
			if(m.getName().equals("Kalycgos")) {
				return true;
			}
		}
		return false;
		
	}
	// we should check if we can add another operation in drawCard 

}
