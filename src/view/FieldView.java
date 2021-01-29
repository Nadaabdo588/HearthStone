package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.*;

import controller.Controller;
import model.cards.minions.Minion;
import model.heroes.Hero;

public class FieldView extends JPanel {
	private Hero hero;
	private ArrayList<JButton> minions;

	public FieldView(Hero hero) {
		minions = new ArrayList<JButton>();
		this.hero = hero;
		setLayout(new GridBagLayout());
		setOpaque(false);
//		for(Minion m : hero.getField())
//			{
//				JButton b = new JButton(m.toString());
//				minions.add(b);
//				add(b);
//			}
	}

	public ArrayList<JButton> getMinions() {
		return minions;
	}

	public void updateField() {
		for (; !minions.isEmpty();) {
			minions.remove(0);
		}
		removeAll();
		for (Minion m : hero.getField()) {
			

			JButton B = new JButton();
			B.setIcon(getIcon(m));
			B.setLayout(null);
		
			JTextArea attack = new JTextArea(m.getAttack() + "");
			if (attack.getText().length() == 1)
				attack.setBounds(25, 212, 40, 40);
			else
				attack.setBounds(20, 212, 40, 40);

			attack.setEditable(false);
			attack.setOpaque(false);
			attack.setForeground(Color.WHITE);
			attack.setFont(new Font("Serif", Font.BOLD, 24));
			
			
			JTextArea hp = new JTextArea(m.getCurrentHP() + "");
			if (hp.getText().length() == 1)
				hp.setBounds(168, 212, 40, 40);
			else
				hp.setBounds(160, 212, 40, 40);

			hp.setEditable(false);
			hp.setOpaque(false);
			hp.setFont(new Font("Serif", Font.BOLD, 24));
			hp.setForeground(Color.WHITE);
			
			
			JTextArea mana = new JTextArea(m.getManaCost() + "");
			if (mana.getText().length() == 1)
				mana.setBounds(21, 21, 40, 40);
			else
				mana.setBounds(16, 21, 40, 40);
			mana.setEditable(false);
			mana.setOpaque(false);
			mana.setForeground(Color.WHITE);
			mana.setFont(new Font("Serif", Font.BOLD, 24));

			
			B.add(attack);
			B.add(hp);
			B.add(mana);
			B.setContentAreaFilled(false);
			B.setFocusPainted(false);
			B.setBorderPainted(false);
			B.setPreferredSize(new Dimension(200, 250));
			minions.add(B);
			add(B);
		}
		revalidate();
		repaint();
	}

	public void setMinioinsListener(Controller c) {
		for (JButton m : minions) {
			m.addActionListener(c);
		}
	}

	public ImageIcon getIcon(Minion m) {
		String s="";
		if(m.isDivine()&&m.isSleeping()) 
			s="visuals//" + m.getName() +" Divine Slp.gif";
		else if(m.isSleeping())
	    	s= "visuals//"+m.getName()+" Slp.gif";
		else if(m.isDivine())
			s="visuals//" + m.getName() +" Divine.png";
		else
			s="visuals//" + m.getName() +".png";


		return new ImageIcon(s);
	}

}
