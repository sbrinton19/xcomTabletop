package XCOM;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * ScanGUI is the UI housing the scanning actions
 * and the home GUI for the application
 * @author Stefan
 */
public class ScanGUI extends GUITemplate implements MouseListener {

	private static final long serialVersionUID = 1L;

	private JButton scan;
	private JLabel fullDate;
	private JLabel day;
	private JLabel alien;
	private JTextField alienEntry;
	private JLabel exalt;
	private JTextField exaltEntry;
	private JPopupMenu dayPop;
	private JMenuItem setDay;
	private Calendar currentDate;
	private int AACount = 0;
	private int XACount = 0;
	private int lastAlien = 0;
	private int lastExalt = 0;
	
	public ScanGUI() {
		//Initialize Calendar
		currentDate = Calendar.getInstance();
		currentDate.setTimeInMillis(0);

		//Set up menu and Frame options
		addMouseListener(this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("X-Com Admin Tool");
		main.setEnabled(false);
		
		//Setup contents
		scan = new JButton("Scan for Activity");
		scan.addActionListener(this);
		dayPop = new JPopupMenu();
		setDay = new JMenuItem("Set Day");
		setDay.addActionListener(this);
		dayPop.add(setDay);
		day = new JLabel("Day: --");
		day.addMouseListener(this);
		alien = new JLabel("Alien Roll: ");
		exalt = new JLabel("EXALT Roll: ");
		fullDate = new JLabel("");
		alienEntry = new JTextField("Used to enter dice roll for Alien Attack");
		alienEntry.addActionListener(this);
		alienEntry.setVisible(false);

		exaltEntry = new JTextField("Used to enter dice roll for EXALT Attack");
		exaltEntry.addActionListener(this);
		exaltEntry.setVisible(false);

		//Setup Layout
		GridBagConstraints c = new GridBagConstraints();

		
		c.gridy = 2;
		c.weightx = .4;
		c.weighty = .4;
		c.insets = new Insets(5, 5, 5, 5);
		add(alien, c);

		c.gridy = 1;
		add(exalt, c);

		c.gridx = 1;
		add(exaltEntry, c);

		c.gridy = 2;
		add(alienEntry, c);
		
		c.gridy = 1;
		c.anchor = GridBagConstraints.LINE_END;
		add(day, c);
		
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;
		add(fullDate, c);
		
		
		c.gridx = 3;
		c.gridy = 3;
		c.gridheight = 2;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.BOTH;
		add(scan, c);

		setVisible(true);
		pack();
	}

	@Override
	public void subActionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == scan && !alienEntry.isVisible() && !exaltEntry.isVisible()){
			//Scan was clicked, advance day by 1 
			//and calculate chance for alien attack
			//(EXALT attack is calculated after alien attack is checked)
			if(currentDate.getTimeInMillis() != 0){
				//Don't try to scan if the time is 0 millis b/c the user hasn't given a start date yet
				currentDate.add(Calendar.DAY_OF_MONTH, 1);
				setDate();
				//This is the equation for calculating the chance of an alien attack
				int alienProb = (int) Math.round(5*currentDate.get(Calendar.DAY_OF_MONTH) + 2*Math.log(lastAlien)-28*AACount);
				/* It currently averages slightly less than 5 attacks/month
				  Here is a quick synopsis of what each term does for the equation
				  
				  The first term increases the probability of an attack as the month wears on

				  The second term uses a log to make the first few days after an attack
				  very unlikely to have another attack, but progressive makes attacks more likely
				   
				  The third term acts as a limiter on the total number of attacks per month a larger negative
				  means less attacks & smaller means more
				*/
				alien.setText("Alien Roll: " + alienProb);
				//If we in debug mode we auto-generate numbers and compare
				//Setting debug mode on also functions as a way for a DM
				//to not roll the attacks rolls and let them happen automatically
				if(!Core.DEBUG){
					alienEntry.setVisible(true);
					alienEntry.requestFocus();
					pack();
				}
				else{
					Random rand = new Random(System.currentTimeMillis());
					int ran = 100-rand.nextInt(100);
					if(ran < alienProb){
						AACount++;
						JOptionPane.showMessageDialog(this, "Aliens Attacked!", "Alien Attack", JOptionPane.WARNING_MESSAGE);
						lastAlien = -1;
					}
					lastAlien++;
					
					int exaltProb = 0;
					if(lastAlien < 2)
						exaltProb = currentDate.get(Calendar.DAY_OF_MONTH) + 100*(lastAlien -1) - 2*(15 -lastExalt) - 200*XACount;
					else
						exaltProb =  2*currentDate.get(Calendar.DAY_OF_MONTH) + 2*(9-lastExalt) - 78*XACount;
					ran = 100-rand.nextInt(100);
					if(ran < exaltProb){
						XACount++;
						JOptionPane.showMessageDialog(this, "EXALT sabotaged X-Com's efforts!", "EXALT Attack", JOptionPane.WARNING_MESSAGE);
						lastExalt = -1;
					}
					lastExalt++;
				}
			}
		}
		else if(arg0.getSource() == alienEntry){
			//When we hit enter in the alienEntry box
			
			//Recalculating is faster than parsing
			int prob = (int) Math.round(5*currentDate.get(Calendar.DAY_OF_MONTH) + 2*Math.log(lastAlien)-28*AACount);
			int entered = 0;
			try{
				entered = Integer.parseInt(alienEntry.getText().trim());
			}catch(Exception e){
				JOptionPane.showMessageDialog(this, "Don't be a dick you know to enter a number", "Invalid Entry", JOptionPane.ERROR_MESSAGE);
			}
			if(entered < prob){
				//Rolled a value less than chance means an attack occurs
				AACount++;
				JOptionPane.showMessageDialog(this, "Aliens Attacked!", "Alien Attack", JOptionPane.WARNING_MESSAGE);
				lastAlien = -1;
				//set to negative one so it increments to 0
			}
			lastAlien++;
			alien.setText("Alien Roll: ");
			alienEntry.setVisible(false);
			alienEntry.setText("         ");
			//Calculate EXALT and Display
			int exaltProb;
			/*
			 * To formulae are used in these cases
			 * Immediately following an attack a formula that renders an immediate attack impossible
			 * And very slim chance of an attack a day after
			 * 
			 * The second is the normal chance and has the same structure as the alien attack chance
			 */
			if(lastAlien < 2)
				exaltProb = currentDate.get(Calendar.DAY_OF_MONTH) + 100*(lastAlien -1) - 2*(15 -lastExalt) - 200*XACount;
			else
				exaltProb =  2*currentDate.get(Calendar.DAY_OF_MONTH) - 2*(9-lastExalt) - 78*XACount;
			exalt.setText("EXALT Roll: " + exaltProb);
			exaltEntry.setVisible(true);
			exaltEntry.requestFocus();
			save.setEnabled(false);
			load.setEnabled(false);
			pack();
		}
		else if(arg0.getSource() == exaltEntry){
			//Hit enter for Exalt
			int exaltProb;
			if(lastAlien < 2)
				exaltProb = currentDate.get(Calendar.DAY_OF_MONTH) + 100*(lastAlien -1) - 2*(15 -lastExalt) - 200*XACount;
			else
				exaltProb =  2*currentDate.get(Calendar.DAY_OF_MONTH) - 2*(9-lastExalt) - 78*XACount;
			
			int entered = 0;
			try{
				entered = Integer.parseInt(exaltEntry.getText().trim());
			}catch(Exception e){
				JOptionPane.showMessageDialog(this, "Don't be a dick you know to enter a number", "Invalid Entry", JOptionPane.ERROR_MESSAGE);
			}
			if(entered < exaltProb){
				//Rolled a value less than chance means an attack occurs
				XACount++;
				JOptionPane.showMessageDialog(this, "EXALT sabotaged X-Com's efforts!", "EXALT Attack", JOptionPane.WARNING_MESSAGE);
				lastExalt = -1;
				//set to negative one so it increments to 0
			}
			lastExalt++;
			exalt.setText("EXALT Roll: ");
			exaltEntry.setVisible(false);
			exaltEntry.setText("       ");
			scan.requestFocus();
			save.setEnabled(true);
			load.setEnabled(true);
		}
		else if(arg0.getSource() == setDay){
			//Manually Set the date for scanning: Also allows manual time skips if your campaign calls for it
			int curYear = -1;
			while(curYear < 2001){
				String year = (String) JOptionPane.showInputDialog(this, "Enter a valid year", "Year Entry", JOptionPane.PLAIN_MESSAGE, null, null, 2015);
				try{
					curYear = Integer.parseInt(year);
				}catch(Exception e){
					year = (String) JOptionPane.showInputDialog(this, "Enter a valid year", "Year Entry", JOptionPane.PLAIN_MESSAGE, null, null, 2015);
				}
				
			}
			Object[] options = {"January", "February","March","April","May","June","July","August","September","October","November","December"};
			String month = (String) JOptionPane.showInputDialog(this, "Enter current month", "Month Entry", JOptionPane.PLAIN_MESSAGE, null, options, "March");
			int MONTH_LENGTH = 0;
			int i = 0;
			for(i = 0; i < 12; i++)
				if(month.equals(options[i])){
					if(i == 1 && curYear % 4 == 0 && curYear % 400 != 0)
						MONTH_LENGTH = 29;
					else if(i == 1)
						MONTH_LENGTH = 28;
					else if(i < 7 && i % 2 == 0)
						MONTH_LENGTH = 31;
					else if(i < 7 && i % 2 == 1)
						MONTH_LENGTH = 30;
					else if( i % 2 == 0)
						MONTH_LENGTH = 30;
					else
						MONTH_LENGTH = 31;
					break;
				}
			
			int dayOfMonth = 0;
			while(dayOfMonth < 1 || dayOfMonth > MONTH_LENGTH){
				String day = (String) JOptionPane.showInputDialog(this, "Enter current day of the month", "Day Entry", JOptionPane.PLAIN_MESSAGE, null, null, 1);
				try{
					dayOfMonth = Integer.parseInt(day);
				}catch(Exception e){
					day = (String) JOptionPane.showInputDialog(this, "Enter current day of the month", "Day Entry", JOptionPane.PLAIN_MESSAGE, null, null, 1);
				}
			}
			//Finish Handling time setup
			
			
			currentDate.set(curYear, i, dayOfMonth);
			
			setDate();
			pack();
		}
		
	}

	/**
	 * This method updates all
	 * fields reliant on the
	 * currentDate object as such
	 * it should be called after any changes to it
	 */
	private void setDate(){
		if(currentDate.getTimeInMillis() == 0){
			fullDate.setText("");
			day.setText("Day: --");
			return;
		}
		Date temp = currentDate.getTime();
		SimpleDateFormat format = new SimpleDateFormat("MMMM d',' yyyy");
		fullDate.setText("Date: " + format.format(temp));
		day.setText("Day: " + currentDate.get(Calendar.DAY_OF_MONTH));
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		//Ctrl+right click brings up day edit
		if(arg0.getSource() == day && arg0.isControlDown() && SwingUtilities.isRightMouseButton(arg0)){
			dayPop.show(day, arg0.getX(), arg0.getY());
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void save(ObjectOutputStream out) throws IOException {
		out.writeInt(AACount);
		out.writeInt(lastAlien);
		out.writeInt(XACount);
		out.writeInt(lastExalt);
		out.writeLong(currentDate.getTimeInMillis());
	}

	@Override
	protected void load(ObjectInputStream in) throws IOException
	{
		AACount = in.readInt();
		lastAlien = in.readInt();
		XACount = in.readInt();
		lastExalt = in.readInt();
		currentDate.setTimeInMillis(in.readLong());
		setDate();
		pack();
	}

	@Override
	protected String getExtension() {
		return ".xdate";
	}

	@Override
	protected FileFilter getFilter() {
		return new FileNameExtensionFilter("XCOM Date", "xdate");
	}
	
}
