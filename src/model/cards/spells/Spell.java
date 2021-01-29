package model.cards.spells;
import model.cards.*;
public abstract class Spell extends Card
{
	public Spell()
	{
		
	}
	public Spell(String name , int manaCost, Rarity rarity)
	{
		super(name, manaCost,rarity);
	}
	
	public String toString()
	{
		return super.toString() + "</html>";
	}
}
