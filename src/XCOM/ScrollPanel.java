package XCOM;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.Scrollable;

/**
 * This is just used to lock up the expansion of the Map
 * (It prevents it from creating space in between MapCells)
 * @author Stefan
 *
 */
public class ScrollPanel extends JPanel implements Scrollable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ScrollPanel(GridLayout gridLayout) {
		super(gridLayout);
	}

	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return super.getPreferredSize();
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle arg0, int arg1, int arg2) {
		return 12;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		return false;
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle arg0, int arg1, int arg2) {
		return 12;
	}
}
