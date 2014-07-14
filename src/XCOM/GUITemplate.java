package XCOM;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

/**
 * Serves as a general template of GUIs throughout the project
 * Mostly to save time on declaring the menubar
 * @author Stefan
 * 
 */
public abstract class GUITemplate extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	protected JMenuBar bar;

	protected JMenu file;
	protected JMenuItem load;
	protected JMenuItem save;
	protected JMenuItem exit;

	protected JMenu views;
	protected JMenuItem scan;
	protected JMenuItem maps;
	protected JMenuItem playerAdmin;
	protected JMenuItem xcomAdmin;
	protected final JFileChooser fileChooser = new JFileChooser();
	protected String fileExt = "";
	
	
	protected GUITemplate(){
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
		load.addActionListener(this);
		save.addActionListener(this);
		exit.addActionListener(this);
		
		
		//View Menu
		scan = new JMenuItem("Scan");
		scan.setToolTipText("Open Scanning GUI");
		scan.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.ALT_MASK));
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
		views.add(scan);
		views.add(maps);
		views.add(playerAdmin);
		views.add(xcomAdmin);
		scan.addActionListener(this);
		maps.addActionListener(this);
		playerAdmin.addActionListener(this);
		xcomAdmin.addActionListener(this);
		
		bar = new JMenuBar();
		bar.add(file);
		bar.add(views);
		setJMenuBar(bar);
		
		setLayout(new GridBagLayout());
		
		fileChooser.setFileFilter(getFilter());
	}
	
	
	/**
	 * Implements the code for a save excluding what is actually being saved
	 * it calls the virtual (abstract) method save to allow subclasses to specify what they save
	 */
	protected void saveDialog(){
		int ret = fileChooser.showSaveDialog(this);
		if(ret == JFileChooser.APPROVE_OPTION){
			File file = fileChooser.getSelectedFile();
			if(!file.getPath().toLowerCase().endsWith(getExtension())){
				file = new File(file.getPath()+ getExtension());
			}
			if(!file.exists())
				try {
					file.createNewFile();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(this, "Saving failed due to: " + e.getMessage() + " during file creation");
					return;
				}
			FileOutputStream output = null;
			try {
				output = new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(this, "Congratulations! The file you have asked to save to doesn't exist."
						+ "Despite that the file should have just been created if didn't already exist or told you an error occurred and returned");
				return;
			}
			ObjectOutputStream out = null;
			try {
				out = new ObjectOutputStream(output);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "The output stream failed to be created due to: " + e.getMessage());
				try {
					output.close();
				} catch (IOException e1) {
					//I don't even want to get here
				}
				return;
			}
			try {
				//Save Objects related to the page's function
				save(out);
				out.close();
				output.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Saving the information to the file failed due to: " + e.getMessage());
			}
		}
	}
	
	/**
	 * Implements the code for a load excluding what is actually being loaded
	 * it calls the virtual (abstract) method load to allow subclasses to specify what they load
	 */
	protected void loadDialog(){
		int ret = fileChooser.showOpenDialog(this);
		if(ret == JFileChooser.APPROVE_OPTION){
			File file = fileChooser.getSelectedFile();
			if(!file.exists()){
				JOptionPane.showMessageDialog(this, "You can't open a file that doesn't exist");
				return;
			}
			FileInputStream input = null;
			try {
				input = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(this, "How did you open a file that doesn't exist? I just told you it didn't and returned!");
				return;
			}
			ObjectInputStream in = null;
			try {
				in = new ObjectInputStream(input);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "The input stream failed to be created due to: " + e.getMessage());
				try {
					input.close();
				} catch (IOException e1) {
					//I don't even want to get here
				}
				return;
			}
			try {
				load(in);
				in.close();
				input.close();
				
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Loading the information from the file failed due to: " + e.getMessage());
			}
		}
	}
	
	@Override
	/**
	 * Implements all shared menuItems
	 * has a fall through to the function subActionPerformed
	 * to allow subclasses to implement their actions
	 */
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == exit){
			dispose();
		}
		else if(arg0.getSource() == load){
			loadDialog();
		}
		else if(arg0.getSource() == save){
			saveDialog();
		}
		else if(arg0.getSource() == playerAdmin){
			Core.makePlayer();
		}
		else if (arg0.getSource() == xcomAdmin){
			
		}
		else if(arg0.getSource() == maps){
			Core.makeMap();
		}
		else if(arg0.getSource() == scan){
			Core.makeScan();
		}
		else{
 			subActionPerformed(arg0);
		}
		
	}
		
	/**
	 * Abstract method used to allow subclasses to define what information is saved to file
	 * @param out
	 * The stream used to write to the selected file
	 * @throws IOException
	 * This is surrounded by a try catch in GUITemplate so it will be handled up a level
	 */
	abstract protected void save(ObjectOutputStream out) throws IOException;
	
	/**
	 * Abstract method used to allow subclasses to define what information is loaded from file
	 * @param in
	 * The stream used to read from the selected file
	 * @throws IOException
	 * This is surrounded by a try catch in GUITemplate so it will be handled up a level
	 */
	abstract protected void load(ObjectInputStream in) throws IOException;
		
	/**
	 * Abstract method allowing fall through to subclasses' actionPerformed methods
	 * @param arg0
	 * The Action Event
	 */
	abstract protected void subActionPerformed(ActionEvent arg0);
	
	/**
	 * This method is an abstract overrideable for 
	 * forms to indicate what extension they save as
	 * @return
	 * The file extension type this form saves
	 */
	abstract protected String getExtension();
	/**
	 * This method is an abstract overrideable for 
	 * forms to indicate what extension they load
	 * @return
	 * A filter to let this form only load the correct
	 * file extension
	 */
	abstract protected FileFilter getFilter();
	
	
}
