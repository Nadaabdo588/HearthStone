package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;




public class StartPage extends JPanel {
	private JButton startButton;
	private JButton about;
	private JButton credits;
	private JButton quit;

	public JButton getAbout() {
		return about;
	}
	public JButton getCredits() {
		return credits;
	}
	public JButton getQuit() {
		return quit;
	}
	private Image image;
	public StartPage()
	{
		image=new ImageIcon("visuals\\start.png").getImage();
		setLayout(null);
		int w = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int h = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setSize(w,h);
		setBackground(new Color(57,38,19));
		startButton =new JButton();
	    startButton.setActionCommand("Start");
		startButton.setIcon(new ImageIcon("visuals\\Play.png"));
		startButton.setBounds(651,660, 618, 62);
		//startButton.setBackground(new Color());
	    about =new JButton ();
		about.setActionCommand("about");
		about.setBounds(651, 737, 618, 62);
		about.setIcon(new ImageIcon("visuals\\About.png"));
	    credits=new JButton();
		credits.setActionCommand("credits");
		credits.setBounds(651, 814, 618, 62);
		credits.setIcon(new ImageIcon("visuals\\Credits.png"));
		quit =new JButton ();
		quit.setActionCommand("quit");
		quit.setBounds(651, 891, 618, 62);
		quit.setIcon(new ImageIcon("visuals\\Quit.png"));
		
        startButton.setContentAreaFilled(false);
        startButton.setFocusPainted(false);
        about.setContentAreaFilled(false);
        about.setFocusPainted(false); 
        credits.setContentAreaFilled(false);
        credits.setFocusPainted(false);
        quit.setContentAreaFilled(false);
        quit.setFocusPainted(false);
       // startButton.setBorderPainted(false);
		add(startButton);
		add(about);
		add(credits);
		add(quit);
	
	}
	public JButton getStartButton() {
		return startButton;
	}
	public Image getImage() {
		return image;
	}
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(image, 0, 0,1920,1080, this);
	}

}
