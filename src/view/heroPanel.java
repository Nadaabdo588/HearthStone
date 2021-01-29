package view;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import controller.Controller;
import model.cards.Card;
import model.cards.minions.Icehowl;
import model.cards.minions.Minion;
import model.cards.spells.Spell;
import model.heroes.Hero;
import model.heroes.*;

public class heroPanel extends JPanel {
	private Hero hero ;
	private JLabel HeroInfo;
	private JPanel HeroHand;
	private JPanel ActualHand;
	private JPanel HeroUse;
	private JButton HeroEndTurn,HeroHandButton,HeroPower ;
	private JButton back;
	private ArrayList<JButton> handButtons;
	private JLabel noOfHandCards;
    private JButton HeroPhoto;
    private JTextArea textArea;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextArea textArea3;
    private JScrollPane s;

	public JTextArea getTextArea() {
		return textArea;
	}

	public heroPanel(Hero hero) throws HeadlessException {
		
		setLayout(new GridLayout(1,3));
		this.hero=hero;
    
		HeroInfo =new JLabel();
	    textArea1=new JTextArea();
	    textArea2=new JTextArea();
	    textArea3=new JTextArea();

		HeroInfo.setIcon(new ImageIcon("visuals\\HeroInfo.png"));
		setHeroInfo();
		HeroInfo.setLayout(null);
		textArea1.setForeground(new Color(84,46,2));
		textArea1.setFont(new Font("Serif", Font.BOLD, 28));
		textArea1.setBounds(475,52,50,30);
		textArea1.setOpaque(false);
		textArea1.setEditable(false);
		HeroInfo.add(textArea1);
		
		
		textArea2.setForeground(new Color(84,46,2));
		textArea2.setFont(new Font("Serif", Font.BOLD, 28));
		
		if(hero.getCurrentManaCrystals()==10||hero.getTotalManaCrystals()==10)
			textArea2.setBounds(450,107,100,30);
		else
		    textArea2.setBounds(475,107,50,30);
		textArea2.setOpaque(false);
		textArea2.setEditable(false);
		HeroInfo.add(textArea2);
		
		
		textArea3.setForeground(new Color(84,46,2));
		textArea3.setFont(new Font("Serif", Font.BOLD, 28));
		textArea3.setBounds(480,163,50,30);
		textArea3.setOpaque(false);
		textArea3.setEditable(false);
		HeroInfo.add(textArea3);
		
		
		HeroHand =new JPanel();
		HeroUse  =new JPanel();
		HeroHand.setOpaque(false);
		HeroUse.setOpaque(false);
		HeroPower =new JButton();
		HeroPower.setActionCommand("Hero Power");
		HeroPower.setBorderPainted(false);
		HeroPower.setContentAreaFilled(false);
		HeroPower.setFocusPainted(false);
		HeroEndTurn=new JButton();
		HeroEndTurn.setBorderPainted(false);
		HeroEndTurn.setContentAreaFilled(false);
		HeroEndTurn.setFocusPainted(false);
		HeroEndTurn.setIcon(new ImageIcon("visuals\\EndTurn.png"));
		HeroEndTurn.setActionCommand("End Turn");
		HeroHandButton =new JButton("");
		HeroHandButton.setActionCommand("Hand");
		HeroHandButton.setBorderPainted(false);
		HeroHandButton.setContentAreaFilled(false);
		HeroHandButton.setFocusPainted(false);
		HeroHandButton.setLayout(new BorderLayout());
		textArea= new JTextArea();
		textArea.setEditable(false);
		textArea.setOpaque(false);
		textArea.setForeground(Color.WHITE);
		textArea.setBounds(new Rectangle(new Dimension(10, 10)));
		textArea.setFont(new Font("Serif", Font.ITALIC, 48));
		textArea.setText(""+hero.getHand().size());
		HeroHand.setLayout(new BorderLayout());
		HeroHand.add(HeroHandButton);
//		HeroHand.add(noOfHandCards,BorderLayout.SOUTH);
		add(HeroHand);
		
		
		HeroUse.setLayout(new GridLayout(1,3));
		HeroUse.add(HeroPower);
	    HeroPhoto=new JButton(hero.getName()) ;
		HeroPhoto.setActionCommand("Hero");
		HeroPhoto.setBorderPainted(false);
		HeroPhoto.setFocusPainted(false);
		HeroPhoto.setContentAreaFilled(false);
//		HeroPhoto.setVisible(false);
		if(hero instanceof Hunter)
		{
			HeroPhoto.setIcon(new ImageIcon("visuals\\HunterName.gif"));
			HeroPower.setIcon(new ImageIcon("visuals\\HunterPower.png"));
		}
		if(hero instanceof Mage)
		{
			HeroPhoto.setIcon(new ImageIcon("visuals\\MageName.gif"));
			HeroPower.setIcon(new ImageIcon("visuals\\MagePower.png"));
		}
		if(hero instanceof Priest)
		{
			HeroPhoto.setIcon(new ImageIcon("visuals\\PriestName.gif"));
			HeroPower.setIcon(new ImageIcon("visuals\\PriestPower.png"));
		}
		if(hero instanceof Paladin)
		{
			HeroPhoto.setIcon(new ImageIcon("visuals\\PaladinName.gif"));
			HeroPower.setIcon(new ImageIcon("visuals\\PaladinPower.png"));
		}
		if(hero instanceof Warlock)
		{
			HeroPhoto.setIcon(new ImageIcon("visuals\\WarlockName.gif"));
			HeroPower.setIcon(new ImageIcon("visuals\\WarlockPower.png"));
		}
		HeroUse.add(HeroPhoto);
		HeroUse.add(HeroEndTurn);
		add(HeroUse);
		add(HeroInfo);
		
		ActualHand=new JPanel();
		ActualHand.setOpaque(false);
		ActualHand.setLayout(new GridBagLayout());
//		ActualHand.setBackground(Color.MAGENTA);
		s=new JScrollPane();
		JViewport v=new JViewport();
		v.setOpaque(false);
		v.setView(ActualHand);
		s.setViewport(v);
		s.setOpaque(false);
		s.setBorder(null);
		s.createHorizontalScrollBar();
		//s.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		//s.getVerticalScrollBar().setPreferredSize(new Dimension (0,0));
		back=new JButton();
		back.setActionCommand("Back");
		back.setIcon(new ImageIcon("visuals\\Back.png"));
		back.setBorderPainted(false);
		back.setContentAreaFilled(false);
		back.setFocusPainted(false);
		ActualHand.add(back);
		handButtons = new ArrayList<JButton>();
		setbuttons();


		
	}
	
	public JButton getHeroPhoto() {
		return HeroPhoto;
	}

	public JLabel getNoOfHandCards() {
		return noOfHandCards;
	}

	private void setbuttons()
	{
		for(Card c : hero.getHand())
			{
			    String s=c.toString();
			    if(c instanceof Minion )
			    	if(!((Minion)c).isSleeping())
			    		s+="<br/>CHARGE";
			    s+="</html>";
			    JButton b=new JButton(s);
			    b.setBorderPainted(false);
				handButtons.add(b);
				ActualHand.add(handButtons.get(handButtons.size()-1));
			}
	}
	
	public void setNoOfHandCards()
	{
		textArea.setText(""+hero.getHand().size());
		revalidate();
		repaint();
	}
	
//	public void updatePanel()
//	{
//		int i = handButtons.size();
//		while(handButtons.size()<hero.getHand().size())
//		{
//			JButton b = new JButton((hero.getHand().get(i)).toString());
//			handButtons.add(b);
//			ActualHand.add(b);
//			i++;
//		}
//	}
//	
	public ArrayList<JButton> getHandButtons() {
		return handButtons;
	}
	public Hero getHero() {
		return hero;
	}
	public JLabel getHeroInfo() {
		return HeroInfo;
	}
	public JPanel getHeroHand() {
		return HeroHand;
	}
	public JPanel getActualHand() {
		return ActualHand;
	}
	public JPanel getHeroUse() {
		return HeroUse;
	}
	public JButton getHeroEndTurn() {
		return HeroEndTurn;
	}
	public JButton getHeroHandButton() {
		return HeroHandButton;
	}
	public JButton getHeroPower() {
		return HeroPower;
	}
	public JButton getBack() {
		return back;
	}
	public void setHeroInfo()
	{
		String s="";
		textArea1.setText(hero.getCurrentHP()+"");
	    textArea2.setText(hero.getCurrentManaCrystals()+"/"+hero.getTotalManaCrystals()+"");
	    if(hero.getCurrentManaCrystals()==10||hero.getTotalManaCrystals()==10) {
	    	
	    	textArea2.setForeground(new Color(84,46,2));
			textArea2.setFont(new Font("Serif", Font.BOLD, 24));
			textArea2.setBounds(458,107,102,30);}
		else
		    textArea2.setBounds(475,107,50,30);
		textArea3.setText(hero.getDeck().size()+"");
		HeroInfo.revalidate();
		HeroInfo.repaint();
		
	}
	public void updateHand()
	{
		removeAll();
		add(s);
		revalidate();
		repaint();
	}
	public void updateBack()
	{
		removeAll();
		add(getHeroHand());
		add(getHeroUse());
		add(getHeroInfo());
		getHeroPower().setVisible(true);
		getHeroEndTurn().setVisible(true);

		revalidate();
		repaint();
	}
	public void updateHandButtons()
	{
		setHeroInfo();
		setNoOfHandCards();
		getActualHand().removeAll();
		getActualHand().add(getBack());
		while(!handButtons.isEmpty())
		{
			handButtons.remove(0);
		}
		for(Card c : hero.getHand())
		{
			JButton B = new JButton();
			B.setPreferredSize(new Dimension(180,250));
			B.setIcon(getIcon(c));
			B.setContentAreaFilled(false);
			B.setFocusPainted(false);
			B.setBorderPainted(false);
			handButtons.add(B);
			getActualHand().add(B);
		}
		revalidate();
		repaint();
	}
	
	public void setButtonsListener(Controller c)
	{
		for(JButton b : handButtons)
		{
			if(b.getActionListeners().length==0)
				b.addActionListener(c);
		}
	}
//	public static void main(String[] args) throws HeadlessException, IOException, CloneNotSupportedException
//	{
//		JFrame j = new JFrame();
//		j.setExtendedState(j.MAXIMIZED_BOTH);
//		j.setBackground(Color.green);
//		JSplitPane s= new JSplitPane();
//		s.setOpaque(false);
//		s.setLeftComponent(new heroPanel(new Hunter()));
////		j.add(new BattleGround());
//		JPanel p = new JPanel();
//		s.setRightComponent(p);
//		j.setDefaultCloseOperation(j.EXIT_ON_CLOSE);
//		j.setVisible(true);
//	}
	public ImageIcon getIcon (Card c) {
		String s="visuals\\";
		if(c instanceof Minion)
		{
			s+=c.getName()+" Hand";
		}
		if (c instanceof Spell)
		{
			if(c.getName().equals("Shadow Word: Death"))
				s+="Shadow Word Death";
			else
			s+=c.getName();
		}
		s+=".png";
		return new ImageIcon (s);
	}

		  
	
}
