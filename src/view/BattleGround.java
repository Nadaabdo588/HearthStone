package view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.*;

import model.heroes.Hero;
import model.heroes.Hunter;
import model.heroes.Mage;
import model.heroes.Paladin;
import model.heroes.Priest;
import model.heroes.Warlock;

public class BattleGround extends JPanel
{
	private heroPanel firstHero, secondHero,cur,opp;
	private FieldView firstHeroField, secondHeroField;
	public BattleGround(Hero firstHero , Hero secondHero,Hero current,Hero opponent)
	{

		int w = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int h = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setSize(1920,1080);
		setLayout(new GridLayout(4, 1));
		this.firstHero = new heroPanel(firstHero);
		this.secondHero = new heroPanel(secondHero);
		this.firstHeroField = new FieldView(firstHero);
		this.secondHeroField = new FieldView(secondHero);
		this.secondHero.getHeroHandButton().setVisible(false);
		this.firstHero.getHeroHandButton().setIcon(new ImageIcon("visuals\\HandUptest.png"));
		this.secondHero.getHeroHandButton().setIcon(new ImageIcon("visuals\\HandDowntest.png"));
		this.firstHero.getHeroHand().add(this.firstHero.getTextArea(),BorderLayout.WEST);
		this.secondHero.getHeroHand().add(this.secondHero.getTextArea(),BorderLayout.WEST);
		this.firstHero.setOpaque(false);
		this.secondHero.setOpaque(false);
		this.firstHeroField.setOpaque(false);
		this.secondHeroField.setOpaque(false);
		
		
		if(firstHero==current)
		{
			this.cur=this.firstHero;
			this.opp=this.secondHero;
		}
		else
		{
			this.opp=this.firstHero;
			this.cur=this.secondHero;
		}
		updatePanel();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stud
		Image i= (new ImageIcon("visuals//Test.jpg")).getImage();
		super.paintComponent(g);
		int w = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int h = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		g.drawImage(i, 0,0 , 1920, 1080, this);
		
	}

	public void updateField()
	{
		firstHeroField.updateField();
		secondHeroField.updateField();
	}
	public heroPanel getFirstHero() {
		return firstHero;
	}


	public void setFirstHero(heroPanel firstHero) {
		this.firstHero = firstHero;
	}


	public heroPanel getSecondHero() {
		return secondHero;
	}


	public void setSecondHero(heroPanel secondHero) {
		this.secondHero = secondHero;
	}


	public heroPanel getCur() {
		return cur;
	}


	public void setCur(heroPanel cur) {
		this.cur = cur;
	}


	public heroPanel getOpp() {
		return opp;
	}


	public void setOpp(heroPanel opp) {
		this.opp = opp;
	}


	public FieldView getFirstHeroField() {
		return firstHeroField;
	}


	public void setFirstHeroField(FieldView firstHeroField) {
		this.firstHeroField = firstHeroField;
	}


	public FieldView getSecondHeroField() {
		return secondHeroField;
	}


	public void setSecondHeroField(FieldView secondHeroField) {
		this.secondHeroField = secondHeroField;
	}


	public void endTurn()
	{
		if(firstHero==cur)
		{
			opp=firstHero;
			cur=secondHero;
			secondHeroField.updateField();
		}
		else
		{
			cur=firstHero;
			opp=secondHero;
			firstHeroField.updateField();
		}
		cur.updateHandButtons();
		updatePanel();
	}
	public void updateBattleGround()
	{
		removeAll();
		add(secondHero);
		add(secondHeroField);
		add(firstHeroField);
		add(firstHero);
		revalidate();
		repaint();
	}
	public void updateBattleGroundBack()
	{
		cur.updateBack();
//		updatePanel();
	}
	public void updateBattleGroundHand()
	{
		cur.updateHand();
//		updatePanel();
	}
	public void updatePanel()
	{
		
		updateBattleGround();
		opp.getHeroEndTurn().setVisible(false);
		opp.getHeroPower().setVisible(false);
		opp.getHeroHandButton().setVisible(false);
		cur.getHeroEndTurn().setVisible(true);
		cur.getHeroPower().setVisible(true);
		cur.getHeroHandButton().setVisible(true);
		repaint();
		revalidate();
		
	}

	public static void main(String[] args) 
	{
		
		
	}
	

}
