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
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	private ArrayList<Human> humansList = new ArrayList<Human>();
	
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
	private JLabel expAddFieldLabel;
	private TextField exp;

	private boolean changes;
	private int humeIndex = 0;
	
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
		deleteHuman.setEnabled(false);
		saveHuman = new JButton("Save Human");
		saveHuman.addActionListener(this);
		saveHuman.setToolTipText("Saves currently selected human");
		saveHuman.setEnabled(false);
		humansLabel = new JLabel("Human Select");
		humans = new JComboBox<String>();
		humans.addItem("<New Human>");
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
		expAddFieldLabel = new JLabel("Add XP to Total");
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
		humanForm.add(expAddFieldLabel,c);
		c.gridx = 2;
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
		out.writeInt(humansList.size());
		for(int i = 0; i < humansList.size();i++){
			Human human = humansList.get(i);
			out.writeInt(human.name.length());
			out.writeChars(human.name);
			out.writeInt(human.owner.length());
			out.writeChars(human.owner);
			out.writeInt(human.xp);
			out.writeInt(human.aim);
			out.writeInt(human.will);
			byte[] temp = {human.health, human.movement, human.charClass, human.nationality };
			out.write(temp);
			out.write(human.abilityList);
			out.write(human.medalList);
			out.writeBoolean(human.mec);
			out.writeBoolean(human.gene);
			out.writeBoolean(human.psi);
			out.writeBoolean(human.sex);
		}
		changes = false;
	}

	@Override
	protected void load(ObjectInputStream in) throws IOException {
		if(changes){
			int ret = JOptionPane.showConfirmDialog(this, "Do you want to save the changes to this item before loading?");
			if(ret == 2)
				return;
			if(ret == 0)
				saveDialog();
		}
		int count = in.readInt();
		humansList.clear();
		
		humans.removeAllItems();
		humans.addItem("<New Human>");
		humans.setSelectedIndex(0);
		for(int i = 0; i < count; i++){
			Human construct = new Human();
			int nameLength = in.readInt();
			StringBuilder build = new StringBuilder();
			for(int j = 0; j < nameLength; j++)
				build.append(in.readChar());
			construct.name = build.toString();
			int ownerLength = in.readInt();
			build = new StringBuilder();
			for(int j = 0; j < ownerLength; j++)
				build.append(in.readChar());
			construct.owner = build.toString();
			construct.xp = in.readInt();
			construct.aim = in.readInt();
			construct.will = in.readInt();
			construct.health = in.readByte();
			construct.movement = in.readByte();
			construct.charClass = in.readByte();
			construct.nationality = in.readByte();
			in.read(construct.abilityList);
			in.read(construct.medalList);
			construct.mec = in.readBoolean();
			construct.gene = in.readBoolean();
			construct.psi = in.readBoolean();
			construct.sex = in.readBoolean();
			humansList.add(construct);
			humans.addItem(construct.name);
		}
	}

	@Override
	protected void subActionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == saveHuman){
			StringBuilder sb = new StringBuilder("");
			if(health.getText().trim().equals("")){
				sb.append("Health is a required field\n");
			}
			else{
				try{
					Integer.parseInt(health.getText());
				}
				catch(NumberFormatException e){
					sb.append("Health must be an integer\n");
				}
			}
			if(aim.getText().trim().equals("")){
				sb.append("Aim is a required field\n");
			}
			else{
				try{
					Integer.parseInt(aim.getText());
				}
				catch(NumberFormatException e){
					sb.append("Aim must be an integer\n");
				}
			}
			if(will.getText().trim().equals("")){
				sb.append("Will is a required field\n");
			}
			else{
				try{
					Integer.parseInt(will.getText());
				}
				catch(NumberFormatException e){
					sb.append("Will must be an integer\n");
				}
			}
			if(movement.getText().trim().equals("")){
				sb.append("Movement is a required field\n");
			}
			else{
				try{
					Integer.parseInt(movement.getText());
				}
				catch(NumberFormatException e){
					sb.append("Movement must be an integer\n");
				}
			}
			if(!mech.isSelected() && !gene.isSelected() && !norm.isSelected()){
				sb.append("Modification is a required field\n");
			}
			if(!psi.isSelected() && !notPsi.isSelected()){
				sb.append("Psionic Status is a required field\n");
			}
			if(exp.getText().trim().equals("")){
				sb.append("Experience is a required field (use 0)\n");
			}
			else{
				try{
					Integer.parseInt(movement.getText());
				}
				catch(NumberFormatException e){
					sb.append("Experience must be an integer\n");
				}
			}
			if(!sb.toString().equals("")){
				//We have errors
				JOptionPane.showMessageDialog(this, sb.toString(),"Cannot Save", JOptionPane.WARNING_MESSAGE);
				return;
			}
			//We have no errors time to save a human
			Human modify = humansList.get(humeIndex);
			modify.xp += Integer.parseInt(exp.getText());
			modify.aim = Integer.parseInt(aim.getText());
			modify.health = Byte.parseByte(health.getText());
			modify.will = Integer.parseInt(will.getText());
			modify.charClass = (byte) className.getSelectedIndex();
			modify.movement = Byte.parseByte(movement.getText());
			modify.mec = mech.isSelected();
			modify.gene = gene.isSelected();
			modify.psi = psi.isSelected();
			humansList.remove(humeIndex);
			humansList.add(humeIndex, modify);
			changes = true;
			humans.setSelectedItem(modify.name);
		}
		else if(arg0.getSource() == addHuman){
			StringBuilder sb = new StringBuilder("");
			if(name.getText().trim().equals("")){
				sb.append("Name is a required field\n");
			}
			if(owner.getText().trim().equals("")){
				sb.append("Owner is a required field\n");
			}
			if(!male.isSelected() && !female.isSelected()){
				sb.append("Sex is a required field\n");
			}
			if(health.getText().trim().equals("")){
				sb.append("Health is a required field\n");
			}
			else{
				try{
					Integer.parseInt(health.getText());
				}
				catch(NumberFormatException e){
					sb.append("Health must be an integer\n");
				}
			}
			if(aim.getText().trim().equals("")){
				sb.append("Aim is a required field\n");
			}
			else{
				try{
					Integer.parseInt(aim.getText());
				}
				catch(NumberFormatException e){
					sb.append("Aim must be an integer\n");
				}
			}
			if(will.getText().trim().equals("")){
				sb.append("Will is a required field\n");
			}
			else{
				try{
					Integer.parseInt(will.getText());
				}
				catch(NumberFormatException e){
					sb.append("Will must be an integer\n");
				}
			}
			if(movement.getText().trim().equals("")){
				sb.append("Movement is a required field\n");
			}
			else{
				try{
					Integer.parseInt(movement.getText());
				}
				catch(NumberFormatException e){
					sb.append("Movement must be an integer\n");
				}
			}
			if(!mech.isSelected() && !gene.isSelected() && !norm.isSelected()){
				sb.append("Modification is a required field\n");
			}
			if(!psi.isSelected() && !notPsi.isSelected()){
				sb.append("Psionic Status is a required field\n");
			}
			if(exp.getText().trim().equals("")){
				sb.append("Experience is a required field (use 0)\n");
			}
			else{
				try{
					Integer.parseInt(movement.getText());
				}
				catch(NumberFormatException e){
					sb.append("Experience must be an integer\n");
				}
			}
			if(!sb.toString().equals("")){
				//We have errors
				JOptionPane.showMessageDialog(this, sb.toString(),"Cannot Create", JOptionPane.WARNING_MESSAGE);
				return;
			}
			//We have no errors time to make a human
			Human construction = new Human();
			construction.name = name.getText();
			construction.owner = owner.getText();
			construction.xp = Integer.parseInt(exp.getText());
			construction.aim = Integer.parseInt(aim.getText());
			construction.will = Integer.parseInt(will.getText());
			construction.health = Byte.parseByte(health.getText());
			construction.movement = Byte.parseByte(movement.getText());
			construction.charClass = (byte) className.getSelectedIndex();
			construction.nationality = (byte) nations.getSelectedIndex();
			construction.mec = mech.isSelected();
			construction.gene = gene.isSelected();
			construction.psi = psi.isSelected();
			construction.sex = male.isSelected();
			humansList.add(construction);
			saveHuman.setEnabled(true);
			deleteHuman.setEnabled(true);
			addHuman.setEnabled(false);
			humans.addItem(construction.name);
			humans.setSelectedItem(construction.name);
			changes = true;
			expLabel.setText("XP Total: " + construction.xp);
			rankLabel.setText("Rank: " + construction.getRank());
		}
		else if(arg0.getSource() == deleteHuman){
			int ret = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this human?");
			if(ret != 0)
				return;
			humansList.remove(humeIndex);
			humans.removeItemAt(humeIndex+1);
			humans.setSelectedIndex(0);
		}
		else if(arg0.getSource() == humans){
			
			if(humans.getSelectedItem() == null){
				
			}
			else if(((String)humans.getSelectedItem()).equals("<New Human>")){
				//Adding a new Human
				saveHuman.setEnabled(false);
				deleteHuman.setEnabled(false);
				addHuman.setEnabled(true);
				name.setText("");
				name.setEnabled(true);
				className.setSelectedIndex(0);
				className.setEnabled(true);
				male.setEnabled(true);
				male.setSelected(false);
				
				female.setEnabled(true);
				female.setSelected(false);
				owner.setText("");
				owner.setEnabled(true);
				nations.setSelectedIndex(0);
				nations.setEnabled(true);
				health.setText("");
				aim.setText("");
				will.setText("");
				movement.setText("");
				mech.setEnabled(true);
				mech.setSelected(false);
				gene.setEnabled(true);
				gene.setSelected(false);
				norm.setEnabled(true);
				norm.setSelected(false);
				
				psi.setSelected(false);
				notPsi.setSelected(false);
				exp.setText("");
				rankLabel.setText("Rank: ");
				abilitiesLabel.setText("Abilities: ");
				medals.setText("Medals: ");
				expLabel.setText("XP Total: ");
				humeIndex = 0;
			}
			else{
				//Editing an existing human
				saveHuman.setEnabled(true);
				deleteHuman.setEnabled(true);
				addHuman.setEnabled(false);
				humeIndex = 0;
				for(int i = 0; i < humansList.size(); i++){
					if(humansList.get(i).name.equals(humans.getSelectedItem())){
						humeIndex = i;
						break;
					}
				}
				Human chosen = humansList.get(humeIndex);
				name.setText(chosen.name);
				name.setEnabled(false);
				className.setSelectedIndex(chosen.charClass);
				if(chosen.xp >= 90)
					className.setEnabled(false);
				else
					className.setEnabled(true);
				male.setSelected(chosen.sex);
				male.setEnabled(false);
				female.setSelected(!chosen.sex);
				male.setEnabled(false);
				female.setEnabled(false);
				owner.setText(chosen.owner);
				owner.setEnabled(false);
				nations.setSelectedIndex(chosen.nationality);
				nations.setEnabled(false);
				health.setText(String.valueOf(chosen.health));
				aim.setText(String.valueOf(chosen.aim));
				will.setText(String.valueOf(chosen.will));
				movement.setText(String.valueOf(chosen.movement));
				mech.setSelected(chosen.mec);
				gene.setSelected(chosen.gene);
				norm.setSelected(!chosen.mec && ! chosen.gene);
				if(!norm.isSelected()){
					mech.setEnabled(false);
					gene.setEnabled(false);
					norm.setEnabled(false);
				}
				else{
					mech.setEnabled(true);
					gene.setEnabled(true);
					norm.setEnabled(true);
				}
				psi.setSelected(chosen.psi);
				notPsi.setSelected(!chosen.psi);
				exp.setText("");
				rankLabel.setText("Rank: " + chosen.getRank());
				abilitiesLabel.setText("<html>Abilities: " + chosen.getAbilitiesString());
				medals.setText("Medals: " + chosen.getMedalsString());
				expLabel.setText("XP Total: " + chosen.xp);
			}
		}
		
		
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
		if(arg0.getClickCount() == 2){
			if(arg0.getSource() == abilitiesLabel && !name.isEnabled()){
				//Edit abilities list
				
				Human chosen = humansList.get(humeIndex);
				String[] choices = chosen.getPossibleAbilities();
				String ability = (String) JOptionPane.showInputDialog(this, "Chose an abilty to add or remove", "Ability Add/Remove", JOptionPane.PLAIN_MESSAGE, null, choices, choices[0]);
				if(ability == null)
					return;
				if(chosen.hasAbility(ability)){
					chosen.removeAbility(ability);
				}
				else{
					chosen.addAbility(ability);
				}
				String temp = "<html>Abilities: " + chosen.getAbilitiesString();
				abilitiesLabel.setText(temp);
			}
			else if(arg0.getSource() == medals && !name.isEnabled()){
				Human chosen = humansList.get(humeIndex);
				String medal = (String) JOptionPane.showInputDialog(this, "Chose an abilty to add or remove", "Ability Add/Remove", JOptionPane.PLAIN_MESSAGE, null, Human.medals, Human.medals[0]);
				if(medal == null)
					return;
				if(chosen.hasMedal(medal)){
					chosen.removeMedal(medal);
				}
				else{
					chosen.addMedal(medal);
				}
				medals.setText("Medals: " + chosen.getMedalsString());
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

	@Override
	public void dispose(){
		if(!changes){
			Core.guis.remove(this);
			super.dispose();
			return;
		}
		int ret = JOptionPane.showConfirmDialog(this, "Do you want to save the changes to this item?");
		if(ret == 1){
			Core.guis.remove(this);
			super.dispose();
			return;
		}
		if(ret == 2)
			return;
		saveDialog();
		Core.guis.remove(this);
		super.dispose();
	}
	
}
