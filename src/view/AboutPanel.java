package view;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AboutPanel extends JPanel{
	private JButton back;
	public JButton getBack() {
		return back;
	}
	public AboutPanel()
	{
		setLayout(null);
		back=new JButton();
		setSize(1920,1080);
		back.setActionCommand("backStart");
		back.setBounds(1390, 900, 180, 70);
		back.setIcon(new ImageIcon("visuals\\Back1.png"));
		back.setContentAreaFilled(false);
		back.setFocusPainted(false);
		add(back);
	}
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Image i= new ImageIcon("visuals\\About.jpg").getImage();
		g.drawImage(i, 0, 0,1920,1080, this);
	}
    public static void main(String[] args) {
		JFrame j=new JFrame();
		j.add(new AboutPanel());
		j.setVisible(true);
		j.setSize(1920,1080);
	}
	

}