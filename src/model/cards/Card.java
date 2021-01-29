package model.cards;

public abstract class Card implements Cloneable 
{

	private int manaCost;
	private String name;
	private Rarity rarity;
	
	
	public Card(){}
	
	public Card(String name , int manaCost , Rarity rarity)
	{
		if(manaCost<0)manaCost=0;
		if(manaCost>10)manaCost=10;
		this.manaCost = manaCost;
		this.name = name;
		this.rarity = rarity;
	}
	
	public int getManaCost() {
		return manaCost;
	}


	public void setManaCost(int manaCost) {
		this.manaCost = (manaCost<0)?0:(manaCost>10)? 10:manaCost;
	}


	public String getName() {
		return name;
	}





	public void setName(String name) {
		this.name = name;
	}





	public Rarity getRarity() {
		return rarity;
	}
	
	public  Card clone() throws CloneNotSupportedException 
	{
		return (Card)super.clone(); 
	}
	
	public String toString()
	{
		return ""+ rarity; 
	}


}
