package controller;

import java.awt.event.*;
import java.io.IOException;

import model.cards.*;
import model.cards.spells.AOESpell;
import model.cards.spells.CurseOfWeakness;
import model.cards.spells.DivineSpirit;
import model.cards.spells.FieldSpell;
import model.cards.spells.Flamestrike;
import model.cards.spells.HeroTargetSpell;
import model.cards.spells.HolyNova;
import model.cards.spells.KillCommand;
import model.cards.spells.LeechingSpell;
import model.cards.spells.LevelUp;
import model.cards.spells.MinionTargetSpell;
import model.cards.spells.MultiShot;
import model.cards.spells.Polymorph;
import model.cards.spells.Pyroblast;
import model.cards.spells.SealOfChampions;
import model.cards.spells.ShadowWordDeath;
import model.cards.spells.SiphonSoul;
import model.cards.spells.Spell;
import model.cards.spells.TwistingNether;
import model.cards.minions.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import engine.*;
import exceptions.CannotAttackException;
import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughManaException;
import exceptions.NotSummonedException;
import exceptions.NotYourTurnException;
import exceptions.TauntBypassException;
import model.heroes.*;
//import sun.security.provider.DSAKeyPairGenerator.Current;
import view.*;

import java.awt.*;

public class Controller implements ActionListener, GameListener {

	private View view;
	private Game game;
	private JButton lastSelected;
	private Card lastCard;

	public Controller() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		view = new View();
		StartPage start=(StartPage) view.getStatus();
		start.getStartButton().addActionListener(this);
		start.getAbout().addActionListener(this);
		start.getCredits().addActionListener(this);
		start.getQuit().addActionListener(this);
		
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Start")) {
			ChooseYourHero c = null;
			try {
				c = new ChooseYourHero();
				c.getGo().addActionListener(this);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(view, e1.getMessage());
			}	
			view.setStatus(c);
		}else if (e.getActionCommand().equals("about"))
		{
			AboutPanel about=new AboutPanel();
			about.getBack().addActionListener(this);
			view.setStatus(about);
			
		}
		else if (e.getActionCommand().equals("credits"))
		{
			Credits credits=new Credits();
			credits.getBack().addActionListener(this);
			view.setStatus(credits);			
		}
		else if(e.getActionCommand().equals("quit"))
		{
			System.exit(0);
		}
		else if (e.getActionCommand().equals("backStart"))
		{
			StartPage start=new StartPage();
			start.getStartButton().addActionListener(this);
			start.getAbout().addActionListener(this);
			start.getCredits().addActionListener(this);
			start.getQuit().addActionListener(this);
			view.setStatus(start);
		}
		else if (e.getActionCommand().equals("Go to Battle!")) {
			ChooseYourHero ch = ((ChooseYourHero) view.getStatus());
			String s1="",s2="";
			if(ch.getHero1().getIcon()!=null)
			    s1 = (String) ch.getHero1().getIcon().toString();
			if(ch.getHero2().getIcon()!=null)
			    s2 = (String) ch.getHero2().getIcon().toString();
			Hero H1 = null;
			try {
				H1 = thatHero(s1);
			} catch (IOException | CloneNotSupportedException e2) {
				JOptionPane.showMessageDialog(view, e2.getMessage());
		
			}
			Hero H2 = null;
			try {
				H2 = thatHero(s2);
			} catch (IOException | CloneNotSupportedException e2) {
				JOptionPane.showMessageDialog(view, e2.getMessage());
			}
			if (H1 == null && H2 == null) {
				JOptionPane.showMessageDialog(view, "Drag the two Preferred Heroes to the EMPTY places!");
				return;
			}
			else if (H1 == null || H2 == null) {
				JOptionPane.showMessageDialog(view, "You MUST choose another Hero");
				return;
			}
			try {
				game = new Game(H1, H2);
				game.setListener(this);
			} catch (FullHandException | CloneNotSupportedException e1) {
				JOptionPane.showMessageDialog(view, e1.getMessage());
			}
			BattleGround g = new BattleGround(H2, H1, game.getCurrentHero(), game.getOpponent());
			if (g.getFirstHero().getHeroHandButton().getActionListeners().length == 0)
				g.getFirstHero().getHeroHandButton().addActionListener(this);
			if (g.getFirstHero().getBack().getActionListeners().length == 0)
				g.getFirstHero().getBack().addActionListener(this);
			if (g.getSecondHero().getHeroHandButton().getActionListeners().length == 0)
				g.getSecondHero().getHeroHandButton().addActionListener(this);
			if (g.getSecondHero().getBack().getActionListeners().length == 0)
				g.getSecondHero().getBack().addActionListener(this);
			if (g.getFirstHero().getHeroHandButton().getActionListeners().length == 0)
				g.getCur().setButtonsListener(this);
			if (g.getFirstHero().getHeroHandButton().getActionListeners().length == 0)
				g.getOpp().setButtonsListener(this);
			if (g.getFirstHero().getHeroEndTurn().getActionListeners().length == 0)
				g.getFirstHero().getHeroEndTurn().addActionListener(this);
			if (g.getSecondHero().getHeroEndTurn().getActionListeners().length == 0)
				g.getSecondHero().getHeroEndTurn().addActionListener(this);
			if (g.getFirstHero().getHeroPower().getActionListeners().length == 0)
				g.getFirstHero().getHeroPower().addActionListener(this);
			if (g.getSecondHero().getHeroPower().getActionListeners().length == 0)
				g.getSecondHero().getHeroPower().addActionListener(this);
			if (g.getCur().getHeroPhoto().getActionListeners().length == 0)
				g.getCur().getHeroPhoto().addActionListener(this);
			if (g.getOpp().getHeroPhoto().getActionListeners().length == 0)
				g.getOpp().getHeroPhoto().addActionListener(this);
			view.setStatus(g);
			// System.out.println(H1.getName()+" "+H2.getName());
		} else if (e.getActionCommand().equals("Hand")) {
			deselect();
			BattleGround bg = (BattleGround) view.getStatus();
			bg.getCur().updateHandButtons();
			bg.updateBattleGroundHand();
			bg.getCur().setButtonsListener(this);
		} else if (e.getActionCommand().equals("Back")) {
			BattleGround bg = (BattleGround) view.getStatus();
			bg.updateBattleGroundBack();
		} else if (view.getStatus() instanceof BattleGround
				&& ((BattleGround) view.getStatus()).getCur().getHandButtons().contains((JButton) e.getSource())) {
			deselect();
			BattleGround bg = (BattleGround) view.getStatus();
			int i = ((BattleGround) view.getStatus()).getCur().getHandButtons().indexOf((JButton) e.getSource());
			Card c = game.getCurrentHero().getHand().get(i);
			if (c instanceof Minion || c instanceof Icehowl) {
				try {
					game.getCurrentHero().playMinion((Minion) c);
					deselect();

				} catch (NotYourTurnException | NotEnoughManaException | FullFieldException e1) {
					JOptionPane.showMessageDialog(view, e1.getMessage());
					return;
				}

			} else {
				if (c instanceof CurseOfWeakness) {
					try {
						game.getCurrentHero().castSpell((AOESpell) c, game.getOpponent().getField());
					} catch (NotYourTurnException | NotEnoughManaException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(view, e1.getMessage());
					}
					deselect();
				} else if (c instanceof Flamestrike) {
					try {
						game.getCurrentHero().castSpell((AOESpell) c, game.getOpponent().getField());
					} catch (NotYourTurnException | NotEnoughManaException e1) {
						JOptionPane.showMessageDialog(view, e1.getMessage());
					}
					deselect();

				} else if (c instanceof HolyNova) {
					try {
						game.getCurrentHero().castSpell((AOESpell) c, game.getOpponent().getField());
					} catch (NotYourTurnException | NotEnoughManaException e1) {
						JOptionPane.showMessageDialog(view, e1.getMessage());
					}
					deselect();

				} else if (c instanceof LevelUp) {
					try {
						game.getCurrentHero().castSpell((FieldSpell) c);
					} catch (NotYourTurnException | NotEnoughManaException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(view, e1.getMessage());
					}
					deselect();

				} else if (c instanceof MultiShot) {
					try {
						game.getCurrentHero().castSpell((AOESpell) c, game.getOpponent().getField());
					} catch (NotYourTurnException | NotEnoughManaException e1) {
						JOptionPane.showMessageDialog(view, e1.getMessage());
					}
					deselect();

				} else if (c instanceof TwistingNether) {
					try {
						game.getCurrentHero().castSpell((AOESpell) c, game.getOpponent().getField());
					} catch (NotYourTurnException | NotEnoughManaException e1) {
						JOptionPane.showMessageDialog(view, e1.getMessage());
					}
					deselect();

				} else if (c instanceof DivineSpirit) {
					select((JButton) e.getSource(), c);

				} else if (c instanceof KillCommand) {
					select((JButton) e.getSource(), c);

				} else if (c instanceof Polymorph) {
					select((JButton) e.getSource(), c);

				} else if (c instanceof Pyroblast) {
					select((JButton) e.getSource(), c);

				} else if (c instanceof SealOfChampions) {
					select((JButton) e.getSource(), c);

				} else if (c instanceof ShadowWordDeath) {
					select((JButton) e.getSource(), c);
				} else if (c instanceof SiphonSoul) {
					select((JButton) e.getSource(), c);

				}
			}
			bg.getCur().updateHandButtons();
			bg.updateBattleGroundHand();
			bg.updateField();
			bg.getCur().setButtonsListener(this);
			bg.getFirstHeroField().setMinioinsListener(this);
			bg.getSecondHeroField().setMinioinsListener(this);
		} else if (e.getActionCommand().equals("End Turn")) {
			try {
				game.endTurn();
			} catch (FullHandException | CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				if (e1 instanceof FullHandException) {
					ImageIcon icon =getIcon(((FullHandException) e1).getBurned());
					JOptionPane.showMessageDialog(view, e1.getMessage(), "Burnt Card ",JOptionPane.INFORMATION_MESSAGE ,icon);
				}else 
				JOptionPane.showMessageDialog(view, e1.getMessage());	
				}
			deselect();
			
			if(view.getStatus() instanceof BattleGround) {
			view.endTurn();
			BattleGround bg = (BattleGround) view.getStatus();
			bg.getCur().setButtonsListener(this);
			bg.getFirstHeroField().setMinioinsListener(this);
			bg.getSecondHeroField().setMinioinsListener(this);
			}
		} else if (e.getActionCommand().equals("Hero Power")) {
			BattleGround bg = (BattleGround) view.getStatus();
			if (game.getCurrentHero() instanceof Hunter) {
				try {
					game.getCurrentHero().useHeroPower();
					bg.getCur().setHeroInfo();
					bg.getOpp().setHeroInfo();
					deselect();

				} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
						| FullHandException | FullFieldException | CloneNotSupportedException e1) {
					if (e1 instanceof FullHandException) {
						ImageIcon icon =getIcon(((FullHandException) e1).getBurned());
						JOptionPane.showMessageDialog(view, e1.getMessage(), "Burnt Card ",JOptionPane.INFORMATION_MESSAGE ,icon);
					}else 
					JOptionPane.showMessageDialog(view, e1.getMessage());	
					}
			} else if (game.getCurrentHero() instanceof Paladin) {
				try {
					game.getCurrentHero().useHeroPower();
					bg.updateField();
					bg.getFirstHeroField().setMinioinsListener(this);
					bg.getSecondHeroField().setMinioinsListener(this);
					bg.getCur().setHeroInfo();
					deselect();

				} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
						| FullHandException | FullFieldException | CloneNotSupportedException e1) {
					if (e1 instanceof FullHandException) {
						ImageIcon icon =getIcon(((FullHandException) e1).getBurned());
						JOptionPane.showMessageDialog(view, e1.getMessage(), "Burnt Card ",JOptionPane.INFORMATION_MESSAGE ,icon);
					}else 
					JOptionPane.showMessageDialog(view, e1.getMessage());	
					}
			} else if (game.getCurrentHero() instanceof Warlock) {
				try {
					game.getCurrentHero().useHeroPower();
					bg.getCur().setHeroInfo();
					bg.getCur().updateHandButtons();
					bg.getCur().setButtonsListener(this);
					bg.getCur().updateHand();
					bg.getCur().setNoOfHandCards();
					deselect();

				} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
						| FullHandException | FullFieldException | CloneNotSupportedException e1) {
					if (e1 instanceof FullHandException) {
						ImageIcon icon =getIcon(((FullHandException) e1).getBurned());
						JOptionPane.showMessageDialog(view, e1.getMessage(), "Burnt Card ",JOptionPane.INFORMATION_MESSAGE ,icon);
					}else 
					JOptionPane.showMessageDialog(view, e1.getMessage());	
					}
			} else if (game.getCurrentHero() instanceof Mage || game.getCurrentHero() instanceof Priest) {
				deselect();
				lastSelected = (JButton) e.getSource();
				lastSelected.setBorderPainted(true);
			}

		}

		else if (e.getActionCommand().equals("Hero")) {
			BattleGround bg = (BattleGround) view.getStatus();
			JButton b = (JButton) e.getSource();
			if (lastSelected != null) {
				if (bg.getCur().getHeroPhoto() == b)
					attack(game.getCurrentHero());
				else
					attack(game.getOpponent());
			}
			deselect();
		} else if (view.getStatus() instanceof BattleGround) {
			BattleGround bg = (BattleGround) view.getStatus();
			JButton b = (JButton) e.getSource();
			if (lastSelected == null) {

				if (bg.getFirstHeroField().getMinions().contains(b)) {
					int i = bg.getFirstHeroField().getMinions().indexOf(b);
					if (bg.getFirstHero() == bg.getCur())
						select(b, game.getCurrentHero().getField().get(i));
					else

						select(b, game.getOpponent().getField().get(i));

				}
				if (bg.getSecondHeroField().getMinions().contains(b)) {
					int i = bg.getSecondHeroField().getMinions().indexOf(b);
					if (bg.getSecondHero() == bg.getCur())
						select(b, game.getCurrentHero().getField().get(i));
					else
						select(b, game.getOpponent().getField().get(i));
				}
			} else {
				Minion m = null;

				if (bg.getFirstHeroField().getMinions().contains(b)) {

					int i = bg.getFirstHeroField().getMinions().indexOf(b);

					if (bg.getFirstHero() == bg.getCur()) {
						m = game.getCurrentHero().getField().get(i);
					} else {
						m = game.getOpponent().getField().get(i);
					}

				} else if (bg.getSecondHeroField().getMinions().contains(b)) {

					int i = bg.getSecondHeroField().getMinions().indexOf(b);

					if (bg.getSecondHero() == bg.getCur()) {
						m = game.getCurrentHero().getField().get(i);
					} else {
						m = game.getOpponent().getField().get(i);
					}

				}
				if (m != null) {
					attack(m);

				}
				deselect();

			}

		}

		view.revalidate();
		view.repaint();

	}

	private Hero thatHero(String s) throws IOException, CloneNotSupportedException {
		if (s.contains("hunter"))
			return new Hunter();
		if (s.contains("mage"))
			return new Mage();
		if (s.contains("paladin"))
			return new Paladin();
		if (s.contains("priest"))
			return new Priest();
		if (s.contains("warlock"))
			return new Warlock();
		return null;

	}

	public void deselect() {
		if (lastSelected != null)
			lastSelected.setBorderPainted(false);
		lastSelected = null;
		lastCard = null;
	}

	public void select(JButton b, Card c) {
		lastSelected = b;
		lastSelected.setBorderPainted(true);
		lastCard = c;
	}

	public void attack(Minion m) {
		String [] s= new String[]{"Explode sound effect.wav","Broken glass sound effect (high quality).wav",
				"Knife Slice Into Flesh Sound Effect.wav","Knife Stab Sound Effect (original).wav",
				"Roblox Rocket Explosion Sound.wav"};
		int i =(int) (Math.random()*5);
		if (lastSelected.getActionCommand().equals("Hero Power")) {
			if (game.getCurrentHero() instanceof Mage) {
				Mage mage = (Mage) game.getCurrentHero();
				try {
					mage.useHeroPower(m);
				} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
						| FullHandException | FullFieldException | CloneNotSupportedException e) {
					deselect();
					if (e instanceof FullHandException) {
						ImageIcon icon = getIcon(((FullHandException) e).getBurned());
						JOptionPane.showMessageDialog(view, e.getMessage(), "Burnt Card ",
								JOptionPane.INFORMATION_MESSAGE, icon);
					} else
						JOptionPane.showMessageDialog(view, e.getMessage());
				}
			} else {
				Priest p = (Priest) game.getCurrentHero();
				try {
					p.useHeroPower(m);
				} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
						| FullHandException | FullFieldException | CloneNotSupportedException e) {
					deselect();
					if (e instanceof FullHandException) {
						ImageIcon icon = getIcon(((FullHandException) e).getBurned());
						JOptionPane.showMessageDialog(view, e.getMessage(), "Burnt Card ",
								JOptionPane.INFORMATION_MESSAGE, icon);
					} else
						JOptionPane.showMessageDialog(view, e.getMessage());
				}
			}

		}
		if (lastCard instanceof Minion || lastCard instanceof Icehowl) {
			try {
				if(lastCard==m)
					deselect();
				else {
				    game.getCurrentHero().attackWithMinion((Minion) lastCard, m);
					view.playSound("sounds\\"+s[i]);
				}
			} catch (CannotAttackException | NotYourTurnException | TauntBypassException | InvalidTargetException
					| NotSummonedException e) {
				deselect();
				JOptionPane.showMessageDialog(view, e.getMessage());
			}
		}
		if (lastCard instanceof DivineSpirit) {
			try {
				game.getCurrentHero().castSpell((MinionTargetSpell) lastCard, m);
			} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e) {
				deselect();
				JOptionPane.showMessageDialog(view, e.getMessage());
			}
		}
		if (lastCard instanceof KillCommand) {
			try {
				game.getCurrentHero().castSpell((MinionTargetSpell) lastCard, m);
			} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e) {
				deselect();
				JOptionPane.showMessageDialog(view, e.getMessage());
			}
		}
		if (lastCard instanceof Polymorph) {
			try {
				game.getCurrentHero().castSpell((MinionTargetSpell) lastCard, m);
			} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e) {
				deselect();
				JOptionPane.showMessageDialog(view, e.getMessage());
			}
		}
		if (lastCard instanceof Pyroblast) {
			try {
				game.getCurrentHero().castSpell((MinionTargetSpell) lastCard, m);
			} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e) {
				deselect();
				JOptionPane.showMessageDialog(view, e.getMessage());
			}
		}
		if (lastCard instanceof SealOfChampions) {
			try {
				game.getCurrentHero().castSpell((MinionTargetSpell) lastCard, m);
			} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e) {
				deselect();
				JOptionPane.showMessageDialog(view, e.getMessage());
			}
		}
		if (lastCard instanceof ShadowWordDeath) {
			try {
				game.getCurrentHero().castSpell((MinionTargetSpell) lastCard, m);
			} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e) {
				deselect();
				JOptionPane.showMessageDialog(view, e.getMessage());
			}
		}
		if (lastCard instanceof SiphonSoul) {
			try {
				game.getCurrentHero().castSpell((LeechingSpell) lastCard, m);
			} catch (NotYourTurnException | NotEnoughManaException e) {
				deselect();
				JOptionPane.showMessageDialog(view, e.getMessage());
			}
		}
		if(view.getStatus() instanceof BattleGround ) {
		BattleGround bg = (BattleGround) view.getStatus();
		bg.getCur().updateHandButtons();
		bg.updateField();
		bg.getCur().setHeroInfo();
		bg.getOpp().setHeroInfo();
		bg.getCur().setButtonsListener(this);
		bg.getFirstHeroField().setMinioinsListener(this);
		bg.getSecondHeroField().setMinioinsListener(this);
		bg.getCur().setNoOfHandCards();
		bg.updatePanel();}

	}

	public void attack(Hero m) {
		String [] s= new String[]{"Explode sound effect.wav","Broken glass sound effect (high quality).wav",
				"Knife Slice Into Flesh Sound Effect.wav","Knife Stab Sound Effect (original).wav",
				"Roblox Rocket Explosion Sound.wav"};
		int i =(int) (Math.random()*5);
		if (lastSelected.getActionCommand().equals("Hero Power")) {
			if (game.getCurrentHero() instanceof Mage) {
				Mage mage = (Mage) game.getCurrentHero();
				try {
					mage.useHeroPower(m);
				} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
						| FullHandException | FullFieldException | CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					if (e instanceof FullHandException) {
						ImageIcon icon = getIcon(((FullHandException) e).getBurned());
						JOptionPane.showMessageDialog(view, e.getMessage(), "Burnt Card ",
								JOptionPane.INFORMATION_MESSAGE, icon);
					} else
						JOptionPane.showMessageDialog(view, e.getMessage());
				}
			} else {
				Priest p = (Priest) game.getCurrentHero();
				try {
					p.useHeroPower(m);
				} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
						| FullHandException | FullFieldException | CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					if (e instanceof FullHandException) {
						ImageIcon icon = getIcon(((FullHandException) e).getBurned());
						JOptionPane.showMessageDialog(view, e.getMessage(), "Burnt Card ",
								JOptionPane.INFORMATION_MESSAGE, icon);
					} else
						JOptionPane.showMessageDialog(view, e.getMessage());
				}
			}

		} else if (lastCard instanceof Minion || lastCard instanceof Icehowl)
			try {
				
				game.getCurrentHero().attackWithMinion((Minion) lastCard, m);
				view.playSound("sounds\\"+s[i]);
			} catch (CannotAttackException | NotYourTurnException | TauntBypassException | InvalidTargetException
					| NotSummonedException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(view, e1.getMessage());
			}
		if (lastCard instanceof KillCommand) {
			try {
				game.getCurrentHero().castSpell((HeroTargetSpell) lastCard, m);
			} catch (NotYourTurnException | NotEnoughManaException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(view, e.getMessage());
			}
		}
		if (lastCard instanceof Pyroblast) {
			try {
				game.getCurrentHero().castSpell((HeroTargetSpell) lastCard, m);
			} catch (NotYourTurnException | NotEnoughManaException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(view, e.getMessage());
			}
		}
		if(view.getStatus() instanceof BattleGround ) {
		BattleGround bg = (BattleGround) view.getStatus();
		bg.getCur().updateHandButtons();
		bg.getCur().setHeroInfo();
		bg.getOpp().setHeroInfo();
		bg.getCur().setButtonsListener(this);
		bg.getCur().setNoOfHandCards();
		bg.updatePanel();
		}


	}

	public ImageIcon getIcon(Card c) {
		String s = "visuals\\";
		if (c instanceof Minion) {
			s += c.getName() + " Hand";
		}
		if (c instanceof Spell) {
			if (c.getName().equals("Shadow Word: Death"))
				s += "Shadow Word Death";
			else
				s += c.getName();
		}
		s += ".png";
		return new ImageIcon(s);
	}

	public ImageIcon getWinner(Hero h) {
		ImageIcon i = null;
		if (h instanceof Paladin)
			i = new ImageIcon("visuals//PaladinVictory.gif");
		if (h instanceof Mage)
			i = new ImageIcon("visuals//MageVictory.gif");
		if (h instanceof Priest)
			i = new ImageIcon("visuals//PriestVictory.gif");
		if (h instanceof Hunter)
			i = new ImageIcon("visuals//HunterVictory.gif");
		if (h instanceof Warlock)
			i = new ImageIcon("visuals//WarlockVictory.gif");
		return i;

	}

	public static void main(String[] args) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		Controller c = new Controller();
	}

	@Override
	public void onGameOver() {		
		try {
			view.getClip().stop();
			view.play("sounds\\CoffinDance.wav");
		} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s ="";
		Hero h=null;
		BattleGround bg=(BattleGround)view.getStatus();
        if(bg.getFirstHero().getHero().getCurrentHP()<=0) {
        	s="SECOND HERO WON !"; 
        	h=bg.getSecondHero().getHero();
        } else {
        	s="FIRST HERO WON !";
           h=bg.getFirstHero().getHero();
        }
        
		JOptionPane.showMessageDialog(view, "", " WOOOHHHOOO ! "+s, JOptionPane.INFORMATION_MESSAGE,
				getWinner(h));
		view.getClip().stop();
		
		try {
			view.play("sounds\\pull-up-a-chair.wav");
		} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StartPage start=new StartPage();
		view.setStatus(start);
		start.getStartButton().addActionListener(this);
		start.getAbout().addActionListener(this);
		start.getCredits().addActionListener(this);
		start.getQuit().addActionListener(this);
		
		
	}
}
