package XCOM;

/**
 * @author Stefan
 * Core serves as the implementation class
 * The class initializes the ScanGUI to start the application and implements
 * all top-level functions as static methods
 * Tabletop X-COM Assistance Tool
 */
public class Core {
	
	//Debugging flag
	public static final boolean DEBUG = false;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ScanGUI();
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
		new MapGUI();
	}
	
}
