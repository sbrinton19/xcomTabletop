package XCOM;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This GUI is the Map Editor. It will hopefully be augmented with sandbox functionality for simulation and DM aid, eventually...
 * @author Stefan
 * 
 */
public class MapGUI extends GUITemplate implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9104342953268029248L;

	private int rowCount = 20;
	private int columnCount = 20;
	private int elevationCount = 1;
	
	
	private JPanel sidebar;
	private ScrollPanel mapContainer;
	private JScrollPane mapPane;
	private ArrayList<ArrayList<ArrayList<MapCell>>> mapGraphics;
	private JButton addRow;
	private JButton removeRow;
	private JButton addColumn;
	private JButton removeColumn;
	private JButton addElevation;
	private JButton removeElevation;
	private JButton upElevation;
	private JButton downElevation;
	
	private JLabel top;
	private JLabel bottom;
	private JLabel left;
	private JLabel right;
	private JLabel content;
	private JComboBox<String> topCell;
	private JComboBox<String> bottomCell;
	private JComboBox<String> leftCell;
	private JComboBox<String> rightCell;
	private JComboBox<String> contentCell;
	private JLabel labelElevation;
	
	
	private MapCell editCell;
	private int currentElevation = 0;
	//Tracks changes for save on close
	private boolean changes = false;
	public MapGUI(){
		//Setup GUI defaults
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("X-Com Admin Tool - Map Editor");
		maps.setEnabled(false);
		
		
		//Setup Content
		
		//SIDEBAR
		sidebar = new JPanel(new GridBagLayout());
		addRow = new JButton("Add Row");
		addRow.addActionListener(this);
		addRow.setToolTipText("Adds a new row to the bottom edge of the map");
		removeRow = new JButton("Remove Row");
		removeRow.addActionListener(this);
		removeRow.setToolTipText("Removes a row from the bottom edge of the map");
		addColumn = new JButton("Add Column");
		addColumn.addActionListener(this);
		addColumn.setToolTipText("Adds a new column to the right edge of the map");
		removeColumn = new JButton("Remove Column");
		removeColumn.addActionListener(this);
		removeColumn.setToolTipText("Removes a column from the right edge of the map");
		addElevation = new JButton("Add Layer");
		addElevation.addActionListener(this);
		addElevation.setToolTipText("Adds a new layer of elevation to the top of the map");
		removeElevation = new JButton("Remove Layer");
		removeElevation.addActionListener(this);
		removeElevation.setToolTipText("Removes top layer of elevation from the map");
		upElevation = new JButton("Edit Up a Layer");
		upElevation.addActionListener(this);
		upElevation.setToolTipText("Shifts the view up one layer of elevation");
		downElevation = new JButton("Edit Down a Layer");
		downElevation.addActionListener(this);
		downElevation.setToolTipText("Shifts the view down one layer of elevation");
		labelElevation = new JLabel("Current Elevation: 1");
		/*
		 * Half Cover & Step Up
		 * indicates that a cover element is a transition up a half unit vertically
		 * and can be climbed (mantle/mounted) as a free action
		 */
		String[] cover = { "No Cover", "Half Cover", "Full Cover", "Half Cover & Step Up" };
		
		//Cell editing items
		topCell = new JComboBox<String>(cover);
		topCell.addActionListener(this);
		topCell.setToolTipText("Sets the cover on a tile's top");
		top = new JLabel("Set Cover for the Top of selected tile");
		bottomCell = new JComboBox<String>(cover);
		bottomCell.addActionListener(this);
		bottomCell.setToolTipText("Sets the cover on a tile's bottom");
		bottom = new JLabel("Set Cover for the Bottom of selected tile");
		leftCell = new JComboBox<String>(cover);
		leftCell.addActionListener(this);
		leftCell.setToolTipText("Sets the cover on a tile's left");
		left = new JLabel("Set Cover for the Left of selected tile");
		rightCell = new JComboBox<String>(cover);
		rightCell.addActionListener(this);
		rightCell.setToolTipText("Sets the cover on a tile's right");
		right = new JLabel("Set Cover for the Right of selected tile");
		
		/*
		 * Destructible cover cannot be entered until it is destroyed 
		 */
		String[] contents = { "Empty", "Not Enterable", "Destructible cover", "Aireal Units only", "Climbable item", "Incline" };
		contentCell = new JComboBox<String>(contents);
		contentCell.addActionListener(this);
		contentCell.setToolTipText("Sets the contents of the selected tile");
		content = new JLabel("Set Content for the selected tile");
		
		//MAPVIEW
		mapContainer = new ScrollPanel(new GridLayout(rowCount, columnCount));
		mapGraphics = new ArrayList<ArrayList<ArrayList<MapCell>>>();
		for(int i = 0; i < rowCount; i++){
			ArrayList<ArrayList<MapCell>> temp = new ArrayList<ArrayList<MapCell>>(); 
			for(int j = 0; j < columnCount; j++){
				ArrayList<MapCell> temp2 = new ArrayList<MapCell>();
				for(int k = 0; k < elevationCount; k++){
					MapCell adder = new MapCell(0,0);
					adder.addMouseListener(this);
					temp2.add(adder);
					mapContainer.add(adder);
				}
				temp.add(temp2);
			}
			mapGraphics.add(temp);
		}
				
		mapPane = new JScrollPane(mapContainer);
		mapPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mapPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		//Setup Layout
		GridBagConstraints c = new GridBagConstraints();
		
		//SIDEBAR contents
		c.weightx = 1;
		c.weighty = .2;
		sidebar.add(addRow, c);
		c.gridy = 1;
		sidebar.add(removeRow, c);
		c.gridy = 2;
		sidebar.add(addColumn,c);
		c.gridy = 3;
		sidebar.add(removeColumn,c);
		c.gridy = 4;
		sidebar.add(addElevation,c);
		c.gridy = 5;
		sidebar.add(removeElevation,c);
		c.gridx = 1;
		sidebar.add(downElevation,c);
		c.gridy = 4;
		sidebar.add(upElevation, c);
		c.gridy = 3;
		sidebar.add(labelElevation,c);
		c.weightx = 1;
		c.weighty = .2;
		c.gridy = 6;
		c.gridx = 0;
		sidebar.add(top,c);
		c.gridx = 1;
		sidebar.add(topCell,c);
		c.gridy= 7;
		sidebar.add(bottomCell,c);
		c.gridx = 0;
		sidebar.add(bottom,c);
		c.gridy = 8;
		sidebar.add(left,c);
		c.gridx = 1;
		sidebar.add(leftCell,c);
		c.gridy= 9;
		sidebar.add(rightCell,c);
		c.gridx = 0;
		sidebar.add(right,c);
		c.gridy = 10;
		sidebar.add(content, c);
		c.gridx = 1;
		sidebar.add(contentCell,c);
		
		
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
		add(mapPane, c);
		
		
		
		
		setVisible(true);
		pack();
	}
	
	@Override
	protected void save(ObjectOutputStream out) throws IOException {
		out.writeInt(rowCount);
		out.writeInt(columnCount);
		out.writeInt(elevationCount);
		for(int i = 0; i < rowCount; i++)
			for(int j = 0; j < columnCount; j++)
				for(int k = 0; k < elevationCount; k++){
					byte[] writing = {mapGraphics.get(i).get(j).get(k).cover,mapGraphics.get(i).get(j).get(k).contents};
					out.write(writing);
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
		rowCount = in.readInt();
		columnCount = in.readInt();
		elevationCount = in.readInt();
		currentElevation = 0;
		labelElevation.setText("Current Elevation: 1");
		mapContainer.removeAll();
		mapGraphics.clear();
		for(int i = 0; i < rowCount; i++){
			ArrayList<ArrayList<MapCell>> temp = new ArrayList<ArrayList<MapCell>>(); 
			for(int j = 0; j < columnCount; j++){
				ArrayList<MapCell> temp2 = new ArrayList<MapCell>();
				for(int k = 0; k < elevationCount; k++){
					MapCell adder = new MapCell(0,0);
					adder.addMouseListener(this);
					adder.cover = in.readByte();
					adder.contents = in.readByte();
					temp2.add(adder);
					if(k == 0)
						mapContainer.add(adder);
				}
				temp.add(temp2);
			}
			mapGraphics.add(temp);
		}
		
		
		mapContainer.revalidate();
		mapContainer.repaint();
		changes = false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void subActionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == addRow){
			//Adding a row requires that the GridLayout be extended and the graphics and references are updated
			GridLayout mapLayout = (GridLayout) mapContainer.getLayout();
			mapLayout.setRows(mapLayout.getRows()+1);
			ArrayList<ArrayList<MapCell>> temp = new ArrayList<ArrayList<MapCell>>(); 
			for(int j = 0; j < columnCount; j++){
				ArrayList<MapCell> temp2 = new ArrayList<MapCell>();
				for(int k = 0; k < elevationCount; k++){
					MapCell adder = new MapCell(0,0);
					adder.addMouseListener(this);
					temp2.add(adder);
					if(k == currentElevation)
						mapContainer.add(adder);
				}
				temp.add(temp2);
			}
			mapGraphics.add(temp);
			mapContainer.revalidate();
			rowCount++;
			changes =true;
		}
		else if(arg0.getSource() == removeRow && rowCount > 1){
			//Removing a row requires the graphics to be updated prior to the reference to the graphic elements' removal
			int ret = JOptionPane.showConfirmDialog(this, "Are you sure you want to remove this row?");
			if(ret != 0)
				return;
			GridLayout mapLayout = (GridLayout) mapContainer.getLayout();
			mapLayout.setRows(mapLayout.getRows()-1);
			rowCount--;
			for(int i = 0; i < mapGraphics.get(rowCount).size(); i++)
				mapContainer.remove(mapGraphics.get(rowCount).get(i).get(currentElevation));
			mapGraphics.remove(rowCount);
			
			mapContainer.revalidate();
			changes =true;
		}
		else if(arg0.getSource() == addColumn){
			//Adding a column requires that the GridLayout be extended and the graphics and references are updated
			GridLayout mapLayout = (GridLayout) mapContainer.getLayout();
			mapLayout.setColumns(mapLayout.getColumns()+1);
			for(int i = 0; i < mapGraphics.size();i++){
				ArrayList<MapCell> columnAdder = new ArrayList<MapCell>();
				for(int j = 0; j< elevationCount; j++){
					MapCell adder = new MapCell(0,0);
					adder.addMouseListener(this);
					columnAdder.add(adder);
					if(j == currentElevation)
						mapContainer.add(adder,columnCount*(i+1)+i);
				}
				
				mapGraphics.get(i).add(columnAdder);
				//Every added cell must be at the end of the row, this bumps up the subsequent insertion by 1
				
			}
			mapContainer.revalidate();
			columnCount++;
			changes =true;
		}
		else if(arg0.getSource() == removeColumn && columnCount > 1){
			//Removing a column requires the graphics to be updated prior to the reference to the graphic elements' removal
			int ret = JOptionPane.showConfirmDialog(this, "Are you sure you want to remove this column?");
			if(ret != 0)
				return;
			GridLayout mapLayout = (GridLayout) mapContainer.getLayout();
			mapLayout.setColumns(mapLayout.getColumns()-1);
			columnCount--;
			for(int i = 0; i < rowCount; i++){
				mapContainer.remove(mapGraphics.get(i).get(columnCount).get(currentElevation));
				mapGraphics.get(i).remove(columnCount);
			}
			mapContainer.revalidate();
			changes = true;
		}
		else if(arg0.getSource() == addElevation){
			for(int i = 0; i< mapGraphics.size(); i++)
				for(int j = 0; j < mapGraphics.get(i).size();j++){
					MapCell adder = new MapCell(0,0);
					adder.addMouseListener(this);
					mapGraphics.get(i).get(j).add(adder);
				}
			elevationCount++;
			changes = true;
		}
		else if(arg0.getSource() == removeElevation && elevationCount > 1){
			//Removing a column requires the graphics to be updated prior to the reference to the graphic elements' removal
			int ret = JOptionPane.showConfirmDialog(this, "Are you sure you want to remove this elevation?");
			if(ret != 0)
				return;
			if(--elevationCount == currentElevation){
				labelElevation.setText("Current Elevation: " + currentElevation--);
				mapContainer.removeAll();
			}
			for(int i = 0; i< mapGraphics.size(); i++)
				for(int j = 0; j < mapGraphics.get(i).size();j++){
					mapGraphics.get(i).get(j).remove(elevationCount);
					if(currentElevation == elevationCount-1)
						mapContainer.add(mapGraphics.get(i).get(j).get(currentElevation));
				}
			mapContainer.revalidate();
			mapContainer.repaint();
			changes = true;
		}
		else if(arg0.getSource() == upElevation && currentElevation < elevationCount -1){
			mapContainer.removeAll();
			labelElevation.setText("Current Elevation: " + (++currentElevation + 1));
			for(int i = 0; i< mapGraphics.size(); i++)
				for(int j = 0; j < mapGraphics.get(i).size();j++)
					mapContainer.add(mapGraphics.get(i).get(j).get(currentElevation));
			if(editCell != null)
				editCell.selected = false;
			editCell = null;
			mapContainer.revalidate();
			mapContainer.repaint();
		}
		else if(arg0.getSource() == downElevation && currentElevation > 0){
			mapContainer.removeAll();
			labelElevation.setText("Current Elevation: " + currentElevation--);
			for(int i = 0; i< mapGraphics.size(); i++)
				for(int j = 0; j < mapGraphics.get(i).size();j++)
					mapContainer.add(mapGraphics.get(i).get(j).get(currentElevation));
			if(editCell != null)
				editCell.selected = false;
			editCell = null;
			mapContainer.revalidate();
			mapContainer.repaint();
		}
		else if(arg0.getSource() == topCell && editCell != null){
			byte cover = (byte) ((JComboBox)arg0.getSource()).getSelectedIndex();
			editCell.setTop(cover);
			changes = true;
		}
		else if(arg0.getSource() == bottomCell && editCell != null){
			byte cover = (byte) ((JComboBox)arg0.getSource()).getSelectedIndex();
			editCell.setBottom(cover);
			changes = true;
		}
		else if(arg0.getSource() == leftCell && editCell != null){
			byte cover = (byte) ((JComboBox)arg0.getSource()).getSelectedIndex();
			editCell.setLeft(cover);
			changes = true;
		}
		else if(arg0.getSource() == rightCell && editCell != null){
			byte cover = (byte) ((JComboBox)arg0.getSource()).getSelectedIndex();
			editCell.setRight(cover);
			changes = true;
		}
		else if(arg0.getSource() == contentCell && editCell != null){
			byte content = (byte) ((JComboBox)arg0.getSource()).getSelectedIndex();
			editCell.setContent(content);
			changes = true;
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getSource() instanceof MapCell){
			if(editCell != null){
				editCell.selected = false;
				editCell.repaint();
			}
			//If our source is a MapCell we should open the editing menu for it and have it refer to that item
			editCell = (MapCell) arg0.getSource();
			editCell.selected = true;
			editCell.repaint();
			boolean temp = changes;
					
			topCell.setSelectedIndex((editCell.cover & 0xC0) >>> 6);
			bottomCell.setSelectedIndex((editCell.cover & 0x30) >>> 4);
			leftCell.setSelectedIndex((editCell.cover & 0x0C) >>> 2);
			rightCell.setSelectedIndex(editCell.cover & 0x03);
			contentCell.setSelectedIndex(editCell.contents);
			changes = temp; //The setSelected Invoke actionListeners that changes to true despite nothing actually happening
			//So we have to reload its old value
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
				
	}

	@Override
	protected String getExtension() {
		return ".xmap";
	}

	@Override
	protected FileFilter getFilter() {
		return new FileNameExtensionFilter("XCOM Map", "xmap");
	}

	@Override
	public void dispose() {
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
