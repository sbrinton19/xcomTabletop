package XCOM;

import java.util.Vector;

/**
 * Core serves as the implementation class
 * The class initializes the ScanGUI to start the application and implements
 * all top-level functions as static methods
 * Tabletop X-COM Assistance Tool
 * @author Stefan
 * 
 */
public class Core {
	
	public static Vector<GUITemplate> guis;  
	
	//Debugging flag
	public static final boolean DEBUG = false;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		guis = new Vector<GUITemplate>();
		guis.add(new ScanGUI());
	}

	/*
	 * All GUIs are made through core
	 * this is to keep the GUI template separated from the other classes
	 * to prevent high intertwining between it and its subclasses.
	 */
	
	
	/**
	 * Initializes a new MapGUI
	 */
	public static void makeMap(){
		for(GUITemplate temp: guis ){
			if(temp instanceof MapGUI){
				temp.toFront();
				return;
			}
		}
		guis.add(new MapGUI());
	}

	public static void makeScan() {
		for(GUITemplate temp: guis ){
			if(temp instanceof ScanGUI){
				temp.toFront();
				return;
			}
		}
		guis.add(new ScanGUI());
	}
	
	public static void makePlayer(){
		for(GUITemplate temp: guis ){
			if(temp instanceof PlayerGUI){
				temp.toFront();
				return;
			}
		}
		guis.add(new PlayerGUI());
	}
	
}
