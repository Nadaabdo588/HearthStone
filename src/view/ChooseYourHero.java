package view;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import javax.tools.Tool;
import model.heroes.Hero;
public class ChooseYourHero extends JPanel implements MouseListener , ActionListener{

	private JLabel Hero1;
	private	JLabel Hero2;
	private JButton hunter;
	private JButton mage;
	private JButton priest;
	private JButton warlock;
	private JButton paladin;
	private JButton Go;
	private JComponent jc;
	public ChooseYourHero() throws IOException {
		setLayout(new BorderLayout());
		JSplitPane s = new JSplitPane();
		JPanel rPanel = new JPanel();
		JPanel lPanel = new JPanel();
		JPanel Upper = new JPanel();
		JPanel Mid = new JPanel();
		JPanel Lower = new JPanel();
		rPanel.setOpaque(false);
		lPanel.setOpaque(false);
		Upper.setOpaque(false);
		Mid.setOpaque(false);
		Lower.setOpaque(false);
		rPanel.setLayout(new GridLayout(3,1));
		Mid.setLayout(new GridLayout(1,5));
		 hunter=new JButton("Hunter") ;
		hunter.addMouseListener(this);
		Mid.add(hunter);
		 mage=new JButton("Mage") ;
		mage.addMouseListener(this);
		Mid.add(mage);
		 priest=new JButton("Priest") ;
		priest.addMouseListener(this);
		Mid.add(priest);
		 warlock=new JButton("Warlock") ;
		warlock.addMouseListener(this);
		Mid.add(warlock);
		 paladin=new JButton("Paladin") ;
		paladin.addMouseListener(this);
		Mid.add(paladin);
		Lower.setLayout(new GridBagLayout());
		Go = new JButton();
		Go.setActionCommand("Go to Battle!");
		Go.setIcon(new ImageIcon("visuals\\GoToBattle.png"));
		Go.addActionListener(this);
		Go.setContentAreaFilled(false);
		Go.setBorderPainted(false);
		Go.setFocusPainted(false);
		Lower.add(Go);
		Lower.setOpaque(false);
		Upper.setLayout(new GridBagLayout());
//		Upper.add(new JLabel("Choose Your Hero"));
		JPanel Player1 = new JPanel();
		JPanel Player2 = new JPanel();
		Player1.setOpaque(false);
		Player2.setOpaque(false);
		Player1.setLayout(new BorderLayout());
//		Player1.add(new JLabel("Player 1"),BorderLayout.NORTH);
		Hero1=new JLabel();
		Hero1.setOpaque(false);
		//Hero1.setIcon(new ImageIcon("visuals\\Unknown1.jpg"));
		Hero1.addMouseListener(this);
		Player1.add(Hero1);
		Player2.setLayout(new BorderLayout());
//		Player2.add(new JLabel("Player 2"),BorderLayout.NORTH);
		Hero2=new JLabel();
		Hero2.setOpaque(false);
		//Hero2.setIcon(new ImageIcon("visuals\\Unknown1.jpg"));
		Hero2.addMouseListener(this);
		Player2.add(Hero2);
		lPanel.setLayout(new GridLayout(2,1));
		lPanel.add(Player1);
		lPanel.add(Player2);
		rPanel.add(Upper);
		rPanel.add(Mid);
		rPanel.add(Lower);
		lPanel.setPreferredSize(new Dimension(300,500));
		s.setRightComponent(lPanel);
		s.setLeftComponent(rPanel);
		hunter.setIcon(new ImageIcon("visuals\\hunter.gif"));
		paladin.setIcon(new ImageIcon("visuals\\paladin.gif"));
		mage.setIcon(new ImageIcon("visuals\\mage.gif"));
		warlock.setIcon(new ImageIcon("visuals\\warlock.gif"));
		priest.setIcon(new ImageIcon("visuals\\priest.gif"));
		hunter.setBorderPainted(false);
		hunter.setFocusPainted(false);
		hunter.setContentAreaFilled(false);
		paladin.setBorderPainted(false);
		paladin.setFocusPainted(false);
		paladin.setContentAreaFilled(false);
		mage.setBorderPainted(false);
		mage.setFocusPainted(false);
		mage.setContentAreaFilled(false);
		warlock.setBorderPainted(false);
		warlock.setFocusPainted(false);
		warlock.setContentAreaFilled(false);
		priest.setBorderPainted(false);
		priest.setFocusPainted(false);
		priest.setContentAreaFilled(false);
		hunter.setTransferHandler(new TransferHandler("icon"));
		mage.setTransferHandler(new TransferHandler("icon"));
		priest.setTransferHandler(new TransferHandler("icon"));
		warlock.setTransferHandler(new TransferHandler("icon"));
		paladin.setTransferHandler(new TransferHandler("icon"));
		Hero1.setTransferHandler(new TransferHandler("icon"));
		Hero2.setTransferHandler(new TransferHandler("icon"));
		JPanel upper1JPanel = new JPanel(); 
		add(lPanel,BorderLayout.EAST);
		add(rPanel);
	}

	public JLabel getHero1() {
		return Hero1;
	}

	public JLabel getHero2() {
		return Hero2;
	}

	public JButton getGo() {
		return Go;
	}

	public ChooseYourHero(LayoutManager layout) {
		super(layout);
	}

	public ChooseYourHero(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public ChooseYourHero(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}
	public static void main(String[] args) throws IOException {
		JFrame j = new JFrame();
		j.add(new ChooseYourHero());
		j.setSize(500,500);
		j.setDefaultCloseOperation(j.EXIT_ON_CLOSE);
		j.setVisible(true);
	}
	public void paintComponent(Graphics g)
	{
		
		Image img = new ImageIcon("visuals\\chooseurhero.jpg").getImage();
		int w = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int h = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		super.paintComponent(g);
		g.drawImage(img,0,0,w,h,this);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		jc = (JComponent)e.getSource();
		if(true)
		{
			TransferHandler th = jc.getTransferHandler();
			JButton [] arr = {paladin,hunter,mage,warlock,priest};
			th.exportAsDrag(jc, e, TransferHandler.COPY);
			
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	
	}

	@Override
	public void mouseEntered(MouseEvent e) {
//		hunter.setIcon(new ImageIcon("visuals\\rexxar.gif"));
//		paladin.setIcon(new ImageIcon("visuals\\paladin.gif"));
//		mage.setIcon(new ImageIcon("visuals\\mage.gif"));
//		warlock.setIcon(new ImageIcon("visuals\\warlock.gif"));
//		priest.setIcon(new ImageIcon("visuals\\priest.png"));
//		this.revalidate();
//		this.repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		hunter.setIcon(new ImageIcon("visuals\\hunter.gif"));
		paladin.setIcon(new ImageIcon("visuals\\paladin.gif"));
		mage.setIcon(new ImageIcon("visuals\\mage.gif"));
		warlock.setIcon(new ImageIcon("visuals\\warlock.gif"));
		priest.setIcon(new ImageIcon("visuals\\priest.gif"));
		this.revalidate();
		this.repaint();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
	}
}
