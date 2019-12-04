package Conways_Game;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

/*
 * JSpotBoard is a user interface component that implements SpotBoard.
 * 
 * Spot width and spot height are specified to the constructor. 
 * 
 * By default, the spots on the spot board are set up with a checker board pattern
 * for background colors and yellow highlighting.
 * 
 * Uses SpotBoardIterator to implement Iterable<Spot>
 * 
 */

public class JSpotBoard extends JPanel implements SpotBoard {

	private static final int DEFAULT_SCREEN_WIDTH = 600;
	private static final int DEFAULT_SCREEN_HEIGHT = 600;
	private static final Color DEFAULT_BACKGROUND_LIGHT = Color.WHITE;
	private static final Color DEFAULT_BACKGROUND_DARK = Color.WHITE;
	private static final Color DEFAULT_SPOT_COLOR = Color.BLACK;
	private static final Color DEFAULT_HIGHLIGHT_COLOR = Color.YELLOW;

	private Spot[][] _spots;
	private int w, h;
	
	public JSpotBoard(int width, int height) {
		if (width < 1 || height < 1 || width > 500 || height > 500) {
			throw new IllegalArgumentException("Illegal spot board geometry");
		}
		
		w = width;
		h = height;
		setLayout(new GridLayout(height, width));
		_spots = new Spot[width][height];
		
		Dimension preferred_size = new Dimension(DEFAULT_SCREEN_WIDTH/width, DEFAULT_SCREEN_HEIGHT/height);
		
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				Color bg = ((x+y)%2 == 0) ? DEFAULT_BACKGROUND_LIGHT : DEFAULT_BACKGROUND_DARK;
				_spots[x][y] = new JSpot(bg, DEFAULT_SPOT_COLOR, DEFAULT_HIGHLIGHT_COLOR, this, x, y);
				((JSpot)_spots[x][y]).setPreferredSize(preferred_size);
				add(((JSpot) _spots[x][y]));
			}			
		}
	}

	// Getters for SpotWidth and SpotHeight properties
	
	public void newWidth(int width) {
		if (width != getSpotWidth()) {
			w = width;
			setLayout(new GridLayout(h,w));
			_spots = new Spot[w][h];
			
			Dimension preferred_size = new Dimension(DEFAULT_SCREEN_WIDTH/w, DEFAULT_SCREEN_HEIGHT/h);
			
			for (int y=0; y<h; y++) {
				for (int x=0; x<w; x++) {
					Color bg = ((x+y)%2 == 0) ? DEFAULT_BACKGROUND_LIGHT : DEFAULT_BACKGROUND_DARK;
					_spots[x][y] = new JSpot(bg, DEFAULT_SPOT_COLOR, DEFAULT_HIGHLIGHT_COLOR, this, x, y);
					try {
						((JSpot)_spots[x][y]).setPreferredSize(preferred_size);
					} catch(NullPointerException e) {
						System.out.println(e.getMessage());
					}
					add(((JSpot) _spots[x][y]));
				}			
			}
		}
	}
	
	@Override
	public int getSpotWidth() {
		return _spots.length;
	}
	
	@Override
	public int getSpotHeight() {
		return _spots[0].length;
	}

	// Lookup method for Spot at position (x,y)
	
	@Override
	public Spot getSpotAt(int x, int y) {
		if (x < 0 || x >= getSpotWidth() || y < 0 || y >= getSpotHeight()) {
			throw new IllegalArgumentException("Illegal spot coordinates" + x + "," + y);
		}
		
		return _spots[x][y];
	}
	
	// Convenience methods for (de)registering spot listeners.
	
	@Override
	public void addSpotListener(SpotListener spot_listener) {
		for (int x=0; x<getSpotWidth(); x++) {
			for (int y=0; y<getSpotHeight(); y++) {
				_spots[x][y].addSpotListener(spot_listener);
			}
		}
	}
	
	@Override
	public void removeSpotListener(SpotListener spot_listener) {
		for (int x=0; x<getSpotWidth(); x++) {
			for (int y=0; y<getSpotHeight(); y++) {
				_spots[x][y].removeSpotListener(spot_listener);
			}
		}
	}

	@Override
	public Iterator<Spot> iterator() {
		return new SpotBoardIterator(this);
	}
}
