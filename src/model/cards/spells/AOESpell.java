package model.cards.spells;
import java.util.*;

import model.cards.minions.Minion; 

public interface AOESpell {
	public void performAction(ArrayList<Minion> oppField,ArrayList<Minion> curField);
}
