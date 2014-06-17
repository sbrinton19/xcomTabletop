package XCOM;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * @author Stefan
 * Serves as a general template of forms throughout the project
 * Mostly to save time on declaring the menubar
 */
public abstract class FormTemplate extends JFrame{
	private static final long serialVersionUID = 1L;
	protected JMenuBar bar;

	protected JMenu file;
	protected JMenuItem load;
	protected JMenuItem save;
	protected JMenuItem exit;

	protected JMenu views;
	protected JMenuItem main;
	protected JMenuItem maps;
	protected JMenuItem playerAdmin;
	protected JMenuItem xcomAdmin;
	
	protected FormTemplate(){
		//File Menu
		load = new JMenuItem("Load");
		load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,ActionEvent.ALT_MASK));
		save = new JMenuItem("Save");
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.ALT_MASK));
		exit = new JMenuItem("Exit");
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.ALT_MASK));
		file = new JMenu("File");
		file.setMnemonic('F');
		file.add(save);
		file.add(load);
		file.add(exit);
		
		//View Menu
		main = new JMenuItem("Main");
		main.setToolTipText("Open Main");
		main.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.ALT_MASK));
		maps = new JMenuItem("Maps");
		maps.setToolTipText("Open Map Editor/Use Window");
		maps.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,ActionEvent.ALT_MASK));
		playerAdmin = new JMenuItem("Player Admin");
		playerAdmin.setToolTipText("Open Administration for Player Characters");
		playerAdmin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,ActionEvent.ALT_MASK));
		xcomAdmin = new JMenuItem("X-COM Admin");
		xcomAdmin.setToolTipText("Open Administration for X-Com to help players");
		xcomAdmin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.ALT_MASK));
		views = new JMenu("Views");
		views.setMnemonic('V');
		views.add(main);
		views.add(maps);
		views.add(playerAdmin);
		views.add(xcomAdmin);
		
		bar = new JMenuBar();
		bar.add(file);
		bar.add(views);
		setJMenuBar(bar);
		
		setLayout(new GridBagLayout());
	}
	
}
