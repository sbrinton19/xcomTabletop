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
	
	private MapCell editCell;
	private int currentElevation = 0;
	
	public MapGUI(){
		//Setup GUI defaults
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("X-Com Admin Tool - Map Editor");
		maps.setEnabled(false);
		
		
		//Setup Content
		
		//SIDEBAR
		sidebar = new JPanel(new GridBagLayout());
		addRow = new JButton("Add new row to the map");
		addRow.addActionListener(this);
		addRow.setToolTipText("Adds a new row to the bottom edge of the map");
		removeRow = new JButton("Remove a row from the map");
		removeRow.addActionListener(this);
		removeRow.setToolTipText("Removes a row from the bottom edge of the map");
		addColumn = new JButton("Add new column to the map");
		addColumn.addActionListener(this);
		addColumn.setToolTipText("Adds a new column to the right edge of the map");
		removeColumn = new JButton("Remove a column from the map");
		removeColumn.addActionListener(this);
		removeColumn.setToolTipText("Removes a column from the right edge of the map");
		
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
		String[] contents = { "Empty", "Not Enterable", "Destructible cover", "Aireal Units only" };
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
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void load(ObjectInputStream in) throws IOException {
		// TODO Auto-generated method stub
		
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
		}
		else if(arg0.getSource() == topCell){
			byte cover = (byte) ((JComboBox)arg0.getSource()).getSelectedIndex();
			editCell.setTop(cover);
		}
		else if(arg0.getSource() == bottomCell){
			byte cover = (byte) ((JComboBox)arg0.getSource()).getSelectedIndex();
			editCell.setBottom(cover);
		}
		else if(arg0.getSource() == leftCell){
			byte cover = (byte) ((JComboBox)arg0.getSource()).getSelectedIndex();
			editCell.setLeft(cover);
		}
		else if(arg0.getSource() == rightCell){
			byte cover = (byte) ((JComboBox)arg0.getSource()).getSelectedIndex();
			editCell.setRight(cover);
		}
		else if(arg0.getSource() == contentCell){
			byte content = (byte) ((JComboBox)arg0.getSource()).getSelectedIndex();
			editCell.setContent(content);
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
			topCell.setSelectedIndex((editCell.cover & 0xC0) >>> 6);
			bottomCell.setSelectedIndex((editCell.cover & 0x30) >>> 4);
			leftCell.setSelectedIndex((editCell.cover & 0x0C) >>> 2);
			rightCell.setSelectedIndex(editCell.cover & 0x03);
			contentCell.setSelectedIndex(editCell.contents);
			if(!sidebar.isAncestorOf(top)){
				GridBagConstraints c = new GridBagConstraints();
				c.weightx = 1;
				c.weighty = .2;
				c.gridy = 4;
				sidebar.add(top,c);
				c.gridx = 1;
				sidebar.add(topCell,c);
				c.gridy= 5;
				sidebar.add(bottomCell,c);
				c.gridx = 0;
				sidebar.add(bottom,c);
				c.gridy = 6;
				sidebar.add(left,c);
				c.gridx = 1;
				sidebar.add(leftCell,c);
				c.gridy= 7;
				sidebar.add(rightCell,c);
				c.gridx = 0;
				sidebar.add(right,c);
				c.gridy = 8;
				sidebar.add(content, c);
				c.gridx = 1;
				sidebar.add(contentCell,c);
				sidebar.revalidate();
			}
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

}
