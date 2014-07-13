package XCOM;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
/**
 * MapCells are the Individual tiles on the map
 * These cells allow some flexibility, but are intended to be used as is
 * They also store the state information that is saved and loaded for maps
 * @author Stefan
 *
 */
public class MapCell extends JPanel{
	
	private static final long serialVersionUID = 1L;
	//The cover byte is segmented into 4 2-bit segments
	//Each pair of bits corresponds to a different cover element
	//if a given cover byte is aabbccdd
	//aa is the cover for the top of the cell
	//bb is the cover for the bottom
	//cc is the cover for the left
	//dd is the cover for the right
	
	public byte cover;
	public byte contents;
	public boolean selected;
	private final int HEIGHT = 51, WIDTH = 51;
	/**
	 * The constructor for a MapCell
	 * The x and y parameters are for
	 * anyone who wants the map cells to explicitly state their position
	 * I have not implemented this behavior
	 * @param x
	 * @param y
	 */
	public MapCell(int x, int y){
		selected = false;
		cover = 0;
		contents = 0;
		//This Doesn't matter because it will ALWAYS be inside a scrollpane
		//So this method doesn't hurt portability
		setPreferredSize(new Dimension(HEIGHT,WIDTH));
	}
	
	@Override
	public void paint(Graphics ge){
		super.paint(ge);
		Graphics2D g = (Graphics2D) ge;
        g.setStroke(new BasicStroke(3));
        //THESE VALUES MUST BE INCLUDED AS LITERALS
        //Not doing so causes java to upcast everything into integers
        //and the corresponding right shifts can fail
        //Whoever fucking decided that bitwise operators should upcast
        //operands to ints deserves to be hurt
        byte top =  (byte) ((cover & 0xC0) >>> 6);
        byte bottom = (byte) ((cover & 0x30) >>> 4);
        byte left = (byte) ((cover & 0x0C) >>> 2);
        byte right = (byte) (cover & 0x03);
        setColor(g, left);
		g.drawLine(1, 1, 1, HEIGHT-1);
		setColor(g, top);
		g.drawLine(1, 1, WIDTH-1, 1);
		setColor(g, right);
		g.drawLine(WIDTH-2, 0, WIDTH-2, HEIGHT-1);
		setColor(g, bottom);
		g.drawLine(3, HEIGHT-2, WIDTH-1, HEIGHT-2);
		Color color = Color.LIGHT_GRAY;
		if(selected)
			color = Color.cyan;
		else if(contents == 1)
			color = Color.black;
		else if(contents == 2)
			color = Color.red;
		else if (contents == 3)
			color = Color.blue;
		g.setStroke(new BasicStroke(1));
		g.setColor(color);
		g.fillRect(3, 3, HEIGHT-6, WIDTH-6);
	}
		
	public void setColor(Graphics g, byte cover){
		//No Cover
		if(cover == 0)
			g.setColor(Color.black);
		//Half Cover
		else if(cover == 1)
			g.setColor(new Color(0xD1D119));
		//Full Cover
		else if(cover == 2)
			g.setColor(new Color(0x00CC00));
		//Half Cover Step Up
		else if(cover == 3)
			g.setColor(new Color(0x0066FF));
	}

	public void setTop(byte cover) {
		this.cover = (byte) (this.cover & ~0xC0);
		this.cover = (byte) (this.cover | (cover<<6));
		repaint();
	}
	
	public void setBottom(byte cover) {
		this.cover = (byte) (this.cover & ~0x30);
		this.cover = (byte) (this.cover | (cover<<4));
		repaint();
	}
	
	public void setLeft(byte cover) {
		this.cover = (byte) (this.cover & ~0x0C);
		this.cover = (byte) (this.cover | (cover<<2));
		repaint();
	}
	
	public void setRight(byte cover) {
		this.cover = (byte) (this.cover & ~0x03);
		this.cover = (byte) (this.cover | cover);
		repaint();
	}

	public void setContent(byte content){
		this.contents = content;
		repaint();
	}
	
}
