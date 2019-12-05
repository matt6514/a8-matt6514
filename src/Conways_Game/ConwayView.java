package Conways_Game;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ConwayView extends JPanel implements ActionListener, SpotListener, ChangeListener {
	
	private JScrollPane scroll_pane;
	private JPanel sub_panel; //holds button panel and scroll pane;
	private JTextArea tape;
	private JSpotBoard board;
	private JPanel button_panel;
	private List<ConwayViewListener> listeners;
	private JSlider scrollWidth;
	private JSlider scrollHeight;
	private JSlider scrollIteration;
	
	public ConwayView() {
		setLayout(new BorderLayout());
		
		tape = new JTextArea(2,50);
		tape.setEditable(false);
		scroll_pane = new JScrollPane(tape);
		add(scroll_pane,BorderLayout.NORTH);
		
		board = new JSpotBoard(50,50);
		board.addSpotListener(this);
		add(board,BorderLayout.CENTER);
		
		button_panel = new JPanel();
		button_panel.setLayout(new GridLayout(3,5));
		
		sub_panel = new JPanel();
		sub_panel.setLayout(new BorderLayout());
		
		//top row
		button_panel.add(new JButton("Clear"));
		button_panel.add(new JButton("Random"));
		button_panel.add(new JButton("Advance"));
		button_panel.add(new JButton("Play"));
		button_panel.add(new JButton("Pause"));
		
		//seconf row
		button_panel.add(new JButton("Increase Lower Survive"));
		button_panel.add(new JButton("Increase Upper Survive"));
		button_panel.add(new JButton("Increase Lower Birth"));
		button_panel.add(new JButton("Increase Upper Birth"));
		button_panel.add(new JButton("Torus"));
		
		//third row
		button_panel.add(new JButton("Decrease Lower Survive"));
		button_panel.add(new JButton("Decrease Upper Survive"));
		button_panel.add(new JButton("Decrease Lower Birth"));
		button_panel.add(new JButton("Decrease Upper Birth"));
		//button_panel.add(new JButton("Decrease Iteration time"));
		
		sub_panel.add(button_panel,BorderLayout.CENTER);
		
		JPanel scrollPanel = new JPanel();
		JPanel scrollWest = new JPanel();
		JPanel scrollCenter = new JPanel();
		JPanel scrollEast = new JPanel();
		
		//create scroller for width
		JLabel scrollWidthLabel = new JLabel();
		scrollWidthLabel.setText("Width");
		
		scrollWest.add(scrollWidthLabel,BorderLayout.NORTH);
		
		scrollWidth = new JSlider(JSlider.HORIZONTAL,10,500,50);
		scrollWidth.setMajorTickSpacing(100);
		scrollWidth.setMinorTickSpacing(10);
		scrollWidth.setPaintTicks(true);
		scrollWidth.setPaintLabels(true);
		scrollWidth.setSnapToTicks(true);
		scrollWidth.addChangeListener(this);
		
		scrollWest.add(scrollWidth, BorderLayout.CENTER);
		
		//create scroller for height
		JLabel scrollHeightLabel = new JLabel();
		scrollHeightLabel.setText("Height");
		
		scrollCenter.add(scrollHeightLabel,BorderLayout.NORTH);
		
		scrollHeight = new JSlider(JSlider.HORIZONTAL,10,500,50);
		scrollHeight.setMajorTickSpacing(100);
		scrollHeight.setMinorTickSpacing(10);
		scrollHeight.setPaintTicks(true);
		scrollHeight.setPaintLabels(true);
		scrollHeight.setSnapToTicks(true);
		scrollHeight.addChangeListener(this);
		
		scrollCenter.add(scrollHeight,BorderLayout.CENTER);
		
		//create scroller for time between iterations
		JLabel scrollIterationLabel =  new JLabel();
		scrollIterationLabel.setText("Time Between Iterations(Miliseconds)");
		
		scrollEast.add(scrollIterationLabel,BorderLayout.NORTH);
		
		scrollIteration = new JSlider(JSlider.HORIZONTAL,10,2000,2000);
		scrollIteration.setMajorTickSpacing(500);
		scrollIteration.setMinorTickSpacing(50);
		scrollIteration.setPaintTicks(true);
		scrollIteration.setPaintLabels(true);
		scrollIteration.setSnapToTicks(true);
		scrollIteration.addChangeListener(this);
		
		scrollEast.add(scrollIteration,BorderLayout.CENTER);
		
		scrollPanel.add(scrollWest,BorderLayout.WEST);
		scrollPanel.add(scrollCenter,BorderLayout.CENTER);
		scrollPanel.add(scrollEast,BorderLayout.EAST);
		
		sub_panel.add(scrollPanel,BorderLayout.SOUTH);
		
		add(sub_panel,BorderLayout.SOUTH);
		
		
		for (Component c: button_panel.getComponents()) {
			JButton b = (JButton) c;
			b.addActionListener(this);
		}
		
		listeners = new ArrayList<ConwayViewListener>();
		
		this.setFocusable(true);
		this.grabFocus();
	}
	
	public void appendToTape(String s) {
		if (s.equals("")) {
			return;
		}
		tape.append(s + "\n");
		JScrollBar vertical = scroll_pane.getVerticalScrollBar();
		vertical.setValue( vertical.getMaximum() );
	}

	@Override
	public void spotClicked(Spot spot) {
		spot.toggleSpot();
	}

	@Override
	public void spotEntered(Spot spot) {
		spot.highlightSpot();

	}

	@Override
	public void spotExited(Spot spot) {
		spot.unhighlightSpot();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		String string = button.getText();
		
		dispatchEventByString(string);

	}
	
	public void reset() {
		for (int i = 0; i < board.getSpotWidth();i++) {
			for (int j = 0; j < board.getSpotHeight();j++) {
				board.getSpotAt(i, j).clearSpot();
			}
		}
	}
	
	public void random() {
		reset();
		for (int i = 0; i < board.getSpotWidth();i++) {
			for (int j =0; j < board.getSpotHeight();j++) {
				if (Math.random() > 0.5) {
					board.getSpotAt(i, j).toggleSpot();
				}
			}
		}
		appendToTape("Randomized");
	}
	
	private void dispatchEventByString(String s) {
		fireEvent(new SimpleEvent(s));
	}
	
	public void addConwayViewListener(ConwayViewListener l) {
		listeners.add(l);
	}

	public void removeConwayViewListener(ConwayViewListener l) {
		listeners.remove(l);
	}
	
	public void fireEvent(ConwayViewEvent e) {
		for (ConwayViewListener l : listeners) {
			l.handleConwayViewEvent(e);
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider slider = (JSlider) e.getSource();
		if (slider.getValueIsAdjusting()) {
			if (slider == scrollWidth)
				fireEvent(new BoardSizeEvent('w',slider.getValue()));
			else if (slider == scrollHeight) 
				fireEvent(new BoardSizeEvent('h',slider.getValue()));
			else if (slider == scrollIteration)
				fireEvent(new TimeEvent(slider.getValue()));
		}
	}
	
	public JSpotBoard getBoard() {
		return board;
	}
	
	public void setBoard(int w, int h) {
		removeAll();
		repaint();
		revalidate();
		
		add(scroll_pane,BorderLayout.NORTH);
		add(sub_panel,BorderLayout.SOUTH);
		board = new JSpotBoard(w,h);
		board.addSpotListener(this);
		add(board,BorderLayout.CENTER);
		
		repaint();
		revalidate();
	}
}
