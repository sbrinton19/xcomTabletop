package XCOM;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PlayerGUI extends GUITemplate implements MouseListener {

	private static final long serialVersionUID = -3481903976077321282L;

	private JPanel sidebar;
	
	private JButton addHuman;
	private JButton deleteHuman;
	private JButton saveHuman;
	private JLabel humansLabel;
	private JComboBox<String> humans;
	
	private ScrollPanel humanForm;
	private JScrollPane humanPane;
	private JLabel nameLabel;
	private TextField name;
	private JLabel classLabel;
	private JComboBox<String> className;
	private JLabel sexLabel;
	private JRadioButton male;
	private JRadioButton female;
	private ButtonGroup sex;
	private JLabel ownerLabel;
	private TextField owner;
	private JLabel nationLabel;
	private JComboBox<String> nations;
	private JLabel healthLabel;
	private TextField health;
	private JLabel aimLabel;
	private TextField aim;
	private JLabel willLabel;
	private TextField will;
	private JLabel movementLabel;
	private TextField movement;
	private JLabel rankLabel;
	private JLabel abilitiesLabel;
	private JLabel medals;
	private JLabel modLabel;
	private JRadioButton mech;
	private JRadioButton gene;
	private JRadioButton norm;
	private ButtonGroup modState;
	private JLabel psiLabel;
	private JRadioButton psi;
	private JRadioButton notPsi;
	private ButtonGroup psion;
	private JLabel expLabel;
	private TextField exp;
	
	public PlayerGUI(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("X-Com Admin Tool - Human Editor");
		playerAdmin.setEnabled(false);
		
		//SIDEBAR
		sidebar = new JPanel(new GridBagLayout());
		addHuman = new JButton("Add Human");
		addHuman.addActionListener(this);
		addHuman.setToolTipText("Opens human form with a blank human");
		deleteHuman = new JButton("Delete Human");
		deleteHuman.addActionListener(this);
		deleteHuman.setToolTipText("Deletes currently selected human");
		saveHuman = new JButton("Save Human");
		saveHuman.addActionListener(this);
		saveHuman.setToolTipText("Saves currently selected human");
		humansLabel = new JLabel("Human Select");
		humans = new JComboBox<String>();
		humans.addActionListener(this);
		
		//HUMAN FORM
		humanForm = new ScrollPanel(new GridBagLayout());
		nameLabel = new JLabel("Name: ");
		name = new TextField("");
		classLabel = new JLabel("Class: ");
		className = new JComboBox<String>(Human.classes);
		className.addActionListener(this);
		sexLabel = new JLabel("Sex: ");
		male = new JRadioButton("Male");
		male.addActionListener(this);
		female = new JRadioButton("Female");
		female.addActionListener(this);
		sex = new ButtonGroup();
		sex.add(male);
		sex.add(female);
		ownerLabel = new JLabel("Owner: ");
		owner = new TextField("");
		nationLabel = new JLabel("Nationality: ");
		nations = new JComboBox<String>(Human.nationalities);
		nations.addActionListener(this);
		healthLabel = new JLabel("Health: ");
		health = new TextField("");
		aimLabel = new JLabel("Aim: ");
		aim = new TextField("");
		willLabel = new JLabel("Will: ");
		will = new TextField("");
		movementLabel = new JLabel("Movement: ");
		movement = new TextField("");
		rankLabel = new JLabel("Rank: ");
		abilitiesLabel = new JLabel("Abilities: ");
		abilitiesLabel.addMouseListener(this);
		medals = new JLabel("Medals: ");
		medals.addMouseListener(this);
		modLabel = new JLabel("Soldier Modified: ");
		mech = new JRadioButton("MEC Trooper");
		gene = new JRadioButton("Gene Mod Trooper");
		norm = new JRadioButton("Unmodified Human");
		modState = new ButtonGroup();
		modState.add(mech);
		modState.add(gene);
		modState.add(norm);
		psiLabel = new JLabel("Soldier is: ");
		psi = new JRadioButton("Psionically Gifted");
		notPsi = new JRadioButton("Not Psionically Gifted");
		psion = new ButtonGroup();
		psion.add(psi);
		psion.add(notPsi);
		expLabel = new JLabel("XP Total: ");
		exp = new TextField("");
		//Construct Human Form
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1;
		c.weighty = .1;
		c.insets = new Insets(5,5,5,5);
		c.fill = GridBagConstraints.BOTH;
		humanForm.add(nameLabel, c);
		c.gridx = 1;
		humanForm.add(name,c);
		c.gridx = 2; 
		humanForm.add(ownerLabel,c);
		c.gridx = 3;
		humanForm.add(owner, c);
		c.gridy = 1;
		humanForm.add(nations,c);
		c.gridx = 2;
		humanForm.add(nationLabel,c);
		c.gridx = 1;
		humanForm.add(className,c);
		c.gridx = 0;
		humanForm.add(classLabel,c);
		c.gridy = 2;
		humanForm.add(rankLabel,c);
		c.gridx = 1;
		humanForm.add(sexLabel, c);
		c.gridx =2;
		humanForm.add(male, c);
		c.gridx = 3;
		humanForm.add(female, c);
		c.gridy = 3;
		humanForm.add(aim, c);
		c.gridx = 2;
		humanForm.add(aimLabel,c );
		c.gridx = 1;
		humanForm.add(health,c);
		c.gridx = 0;
		humanForm.add(healthLabel,c);
		c.gridy = 4;
		humanForm.add(willLabel,c);
		c.gridx= 1;
		humanForm.add(will,c);
		c.gridx = 2;
		humanForm.add(movementLabel,c);
		c.gridx = 3;
		humanForm.add(movement,c);
		c.gridy = 5;
		c.gridx = 0;
		c.gridwidth = 4;
		humanForm.add(abilitiesLabel, c);
		c.gridy = 6;
		humanForm.add(medals,c);
		c.gridwidth = 1;
		c.gridy = 7;
		humanForm.add(modLabel,c);
		c.gridx = 1;
		humanForm.add(mech,c);
		c.gridx = 2;
		humanForm.add(gene,c);
		c.gridx = 3;
		humanForm.add(norm, c);
		c.gridy = 8;
		c.gridx = 0;
		humanForm.add(psiLabel, c);
		c.gridx = 1;
		humanForm.add(psi,c);
		c.gridx = 2;
		humanForm.add(notPsi,c);
		c.gridx = 0;
		c.gridy = 9;
		humanForm.add(expLabel,c);
		c.gridx = 1;
		humanForm.add(exp, c);
		
		humanPane = new JScrollPane(humanForm);
		humanPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		humanPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		//Construct SIDEBAR
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = .2;
		c.weighty = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		sidebar.add(addHuman,c );
		c.gridy = 1;
		sidebar.add(saveHuman,c );
		c.gridy=2;
		sidebar.add(deleteHuman,c);
		c.gridy=3;
		sidebar.add(humansLabel,c);
		c.gridy=4;
		sidebar.add(humans, c);
		//Add Sidebar and pane to mainbody
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = .2;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5,5,5,0);
		add(sidebar,c);
		c.weightx = .8;
		c.gridx = 1;
		c.insets = new Insets(5,5,0,5);
		add(humanPane, c);
		
		setVisible(true);
		pack();
	}
	
	@Override
	protected void save(ObjectOutputStream out) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void load(ObjectInputStream in) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void subActionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected String getExtension() {
		return ".xplay";
	}

	@Override
	protected FileFilter getFilter() {
		return new FileNameExtensionFilter("XCOM Player", "xplay");
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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

}
