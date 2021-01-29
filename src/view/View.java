package view;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;


public class View extends JFrame
{
	private JPanel status;
	private Clip clip;
	
	public Clip getClip() {
		return clip;
	}
	public View() throws LineUnavailableException, UnsupportedAudioFileException, IOException
	{
		super("Heartstone");
		status = new StartPage();
		add(status);
		int w = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int h = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setExtendedState(this.MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		play("sounds\\pull-up-a-chair.wav");
	}
	public void setStatus(JPanel status) {
		remove(this.status);
		this.status = status;
		add(status);
		revalidate();
		repaint();
	}
	public JPanel getStatus() {
		return status;
	}
//	public static void main(String[] args) {
//		new View();
//	}
	public void endTurn()
	{
		((BattleGround)status).endTurn();
	}
	public void playSound(String path) {
	    try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
	        Clip clip1 = AudioSystem.getClip();
	        clip1.open(audioInputStream);
	        clip1.start();
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}
	public  void play(String path) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
	    clip = AudioSystem.getClip();
	    // getAudioInputStream() also accepts a File or InputStream
	    AudioInputStream ais = AudioSystem.getAudioInputStream( new File(path).getAbsoluteFile() );
	    clip.open(ais);
	    clip.loop(Clip.LOOP_CONTINUOUSLY);
	    SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	            // A GUI element to prevent the Clip's daemon Thread 
	            // from terminating at the end of the main()
	           // JOptionPane.showMessageDialog(null, "Close to exit!");
	        }
	    });
	    }
	
		  

}
