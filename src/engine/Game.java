package engine;
import java.io.IOException;

import exceptions.CannotAttackException;
import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughManaException;
import exceptions.NotSummonedException;
import exceptions.NotYourTurnException;
import exceptions.TauntBypassException;
import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;
import model.heroes.*;
public class Game implements HeroListener , ActionValidator
{
	private Hero firstHero, secondHero, currentHero, opponent;
	private GameListener listener;
	
	public void setListener(GameListener listener) {
		this.listener = listener;
	}

	public Game(Hero p1 , Hero p2) throws FullHandException, CloneNotSupportedException
	{
		firstHero = p1;
		secondHero = p2;
		
		firstHero.setListener(this);
		firstHero.setValidator(this);
		secondHero.setListener(this);
		secondHero.setValidator(this);
		
		int rand = (int)(Math.random()*2);
		if(rand==1)
		{
			currentHero = firstHero;
			opponent = secondHero;
		}
		else
		{
			currentHero= secondHero;
			opponent = firstHero;
		}
		currentHero.setTotalManaCrystals(1);
		for(int i=0;i<3;i++)
		{
			currentHero.drawCard();
			opponent.drawCard();
		}
		opponent.drawCard();
	}

	public Hero getCurrentHero() {
		return currentHero;
	}

	public Hero getOpponent() {
		return opponent;
	}

	@Override
	public void onHeroDeath() {
		if(listener != null)
			listener.onGameOver();		
	}

	public GameListener getListener() {
		return listener;
	}

	@Override
	public void damageOpponent(int amount) {
		opponent.setCurrentHP(opponent.getCurrentHP()-amount);
	}

	@Override
	public void endTurn() throws FullHandException, CloneNotSupportedException {
		if(currentHero==firstHero)
		{
			currentHero=secondHero;
			opponent=firstHero;
			
		}
		else 
		{
			currentHero=firstHero;
			opponent=secondHero;	
		}
		currentHero.setTotalManaCrystals(currentHero.getTotalManaCrystals()+1);
		currentHero.setHeroPowerUsed(false);
		for(Minion m : currentHero.getField())
		{
				m.setSleeping(false);
				m.setAttacked(false);
		}
		currentHero.drawCard();
		
	}

	@Override
	public void validateTurn(Hero user) throws NotYourTurnException {
		if(user!=currentHero)
		{
			throw new NotYourTurnException("It is not your turn!");
		}
	}

	@Override
	public void validateAttack(Minion attacker, Minion target)
			throws CannotAttackException, NotSummonedException, TauntBypassException, InvalidTargetException {

		if(!currentHero.getField().contains(attacker))
		{
			throw new NotSummonedException("This minion is not in your field!");
		}
	
		if(currentHero.getField().contains(target))
		{
			throw new InvalidTargetException("Friendly Fire");
		}
		
		if(attacker.isAttacked())
		{
			throw new CannotAttackException("This minion has already attacked!");
		}
		if(attacker.getAttack()==0)
		{
			throw new CannotAttackException("This minion has 0 attack!");
		}
		if(attacker.isSleeping())
		{
			throw new CannotAttackException("ZzZzZzZzZz!");
		}
		
		if(!(opponent.getField().contains(target)))
			throw new NotSummonedException("The tagret you are trying to attack is an illusion");
		
		if(!target.isTaunt())
		{
			for(Minion m : opponent.getField())
			{
				if(m.isTaunt())
				{
					throw new TauntBypassException("You should Kill the taunt minion first");
				}
			}
		}
		attacker.setListener(currentHero);
		target.setListener(opponent);
	}

	@Override
	public void validateAttack(Minion attacker, Hero target)
			throws CannotAttackException, NotSummonedException, TauntBypassException, InvalidTargetException {
		if(!currentHero.getField().contains(attacker))
		{
			throw new NotSummonedException("This minion is not in your field!");
		}
		if(currentHero==target)
		{
			throw new InvalidTargetException("You cannot attack yourself!!!!!");
		}
		
		if(attacker.isAttacked())
		{
			throw new CannotAttackException("This minion has already attacked!");
		}
		if(attacker.getAttack()==0)
		{
			throw new CannotAttackException("This minions has 0 attack!");
		}
		if(attacker.isSleeping())
		{
			throw new CannotAttackException("ZzZzZzZzZz!");
		}

		for(Minion m : opponent.getField())
			{
				if(m.isTaunt())
				{
					throw new TauntBypassException("You should Kill the taunt minion first");
				}
			}
	}

	@Override
	public void validateManaCost(Card card) throws NotEnoughManaException {
		if(currentHero.getCurrentManaCrystals()<card.getManaCost())
		{
			throw new NotEnoughManaException("Not enough MANA CRYSTALS to perform the action!");
		}
	}

	@Override
	public void validatePlayingMinion(Minion minion) throws FullFieldException {
		if(currentHero.getField().size()>=7)
		{
			throw new FullFieldException("You should be satisfied with your 7 minions");
		}
		
	}

	@Override
	public void validateUsingHeroPower(Hero hero) throws NotEnoughManaException, HeroPowerAlreadyUsedException {
		if(currentHero.isHeroPowerUsed())
		{
			throw new HeroPowerAlreadyUsedException("can't you wait for your next turn?");
		}
		if(currentHero.getCurrentManaCrystals()<2)
		{
			throw new NotEnoughManaException("Not enough MANA CRYSTALS to perform the action!");
		}
		
	}
	
}
