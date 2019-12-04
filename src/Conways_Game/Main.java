package Conways_Game;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		ConwayModel model = new ConwayModel();
		ConwayView view = new ConwayView();
		ConwayController controller = new ConwayController(model,view);
		
		JFrame main_frame = new JFrame();
		main_frame.setTitle("Conway's Game of Life");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		main_frame.setContentPane(view);
		
		main_frame.pack();
		main_frame.setVisible(true);

	}

}
