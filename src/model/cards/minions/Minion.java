package model.cards.minions;

import exceptions.InvalidTargetException;
import model.cards.*;
import model.heroes.Hero;

public class Minion extends Card implements Cloneable
{
	private int attack;
	private int maxHP;
	private int currentHP;
	private boolean taunt;
	private boolean divine ;
	private boolean sleeping ;
	private boolean attacked;
	private MinionListener listener;
	
	public void setListener(MinionListener listener) {
		this.listener = listener;
	}
	public Minion() {} 
	public Minion(String name,int manaCost,Rarity rarity,int attack,int maxHP,boolean taunt ,boolean divine , boolean charge) 
	{
		super(name ,manaCost,rarity);
		this.attack=Math.max(attack,0);
		this.maxHP=maxHP;
		this.currentHP=maxHP;
		this.taunt=taunt;
		this.divine=divine;
		this.sleeping=!charge;
	}
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = Math.max(attack,0);
	}
	public int getMaxHP() {
		return maxHP;
	}
	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}
	public int getCurrentHP() {
		return currentHP;
	}
	public void setCurrentHP(int currentHP) {
		this.currentHP = Math.min(currentHP,maxHP);
		if(this.currentHP<=0 && listener != null) {
			listener.onMinionDeath(this);
		}
	}
	public boolean isTaunt() {
		return taunt;
	}
	public void setTaunt(boolean taunt) {
		this.taunt = taunt;
	}
	public boolean isDivine() {
		return divine;
	}
	public void setDivine(boolean divine) {
		this.divine = divine;
	}
	public boolean isSleeping() {
		return sleeping;
	}
	public void setSleeping(boolean sleeping) {
		this.sleeping = sleeping;
	}
	public boolean isAttacked() {
		return attacked;
	}
	public void setAttacked(boolean attacked) {
		this.attacked = attacked;
	}
	public Minion clone() throws CloneNotSupportedException 
	{
		return (Minion) super.clone();
	}
	public String toString()
	{
	    String s =super.toString();
	    if(taunt)
	    {
	    	s+="\n"+"TAUNT ";
	    }
	    if(divine)
	    {
	    	if(taunt)
	    	s+=" , DIVINE";
	    	else
	    		s+="\n"+"DIVINE";
	    }
	    return s;
	}
	
	public void attack(Minion target)
	{
		setAttacked(true);
		target.setCurrentHP((target.isDivine())?target.getCurrentHP():(target.getCurrentHP()-this.attack));
		if(this.attack>0)
			target.setDivine(false);	
		this.setCurrentHP((this.isDivine())?this.getCurrentHP():(this.getCurrentHP()-target.attack));
		if(target.attack>0)
			this.setDivine(false);	
	}
	public void attack(Hero target) throws InvalidTargetException
	{
		if (this.getName().equals("Icehowl"))
		{
		   throw new InvalidTargetException (" An Icehowl can not attack heroes ! " ); 
		  	
		}
		
		else 
		{
			target.setCurrentHP(target.getCurrentHP()-this.attack);
			attacked=true;
		}
	}
	
	
	

}
