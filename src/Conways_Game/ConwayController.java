package Conways_Game;

public class ConwayController implements ConwayObserver, ConwayViewListener {
	
	ConwayModel model;
	ConwayView view;
	
	private boolean play;
	private int wait_time = 1000;
	
	public ConwayController(ConwayModel m, ConwayView v) {
		model = m;
		view = v;
		
		view.addConwayViewListener(this);
		
		model.addObserver(this);
	}

	@Override
	public void handleConwayViewEvent(ConwayViewEvent e) {
		if (e.isSimpleEvent()) {
			SimpleEvent event = (SimpleEvent) e;
			
			if (event.getString().equals("Clear")){
				view.reset();
				model.reset();
			} else if (event.getString().equals("Random")) {
				view.random();
			} else if (event.getString().equals("Advance")) {
				model.lifeCycle(view.getBoard());
			} else if (event.getString().equals("Increase Upper Survive")) {
				model.increaseUpperSurvive();
			} else if (event.getString().equals("Increase Lower Survive")) {
				model.increaseLowerSurvive();
			} else if (event.getString().equals("Increase Upper Birth")) {
				model.increaseUpperBirth();
			} else if (event.getString().equals("Increase Lower Birth")) {
				model.increaseLowerBirth();
			} else if (event.getString().equals("Decrease Upper Survive")) {
				model.decreaseUpperSurvive();
			} else if (event.getString().equals("Decrease Lower Survive")) {
				model.decreaseLowerSurvive();
			} else if (event.getString().equals("Decrease Upper Birth")) {
				model.decreaseUpperBirth();
			} else if (event.getString().equals("Decrease Lower Birth")) {
				model.decreaseLowerBirth();
			} else if (event.getString().equals("Play")) {
				play = true;
				view.appendToTape("Playing");
				life();
			} else if (event.getString().equals("Pause")) {
				play = false;
				view.appendToTape("Paused");
			} else if (event.getString().equals("Torus")) {
				model.setTorus();
			} else if (event.getString().equals("Decrease Iteration time")) {
				if (wait_time > 10) 
					wait_time -= 10;
				view.appendToTape("Time Between Iterations is now " + wait_time + "milliseconds");
			}
		}
		else if (e.isBoardSizeEvent()) {
			BoardSizeEvent event = (BoardSizeEvent) e;
			
			if (event.getType() == 'w') {
				view.appendToTape("The new Width of the Board is " + event.getValue());
				view.setBoard(event.getValue(), view.getBoard().getSpotHeight());

			} else if (event.getType() == 'h') {
				view.appendToTape("The new Height of the Board is " + event.getValue());
				view.setBoard(view.getBoard().getSpotWidth(),event.getValue());
			}
		}
		else if (e.isTimeEvent()) {
			TimeEvent event = (TimeEvent) e;
			wait_time = event.getValue();
			view.appendToTape("The new time between iterations is " + wait_time + " milliseconds");
		}
		
	}

	@Override
	public void update(ConwayModel con, String m) {
		view.appendToTape(m);
	}
	
	public void life() {
		if (play) {
			model.lifeCycle(view.getBoard());
			new Thread(new Runnable() {
				public void run() {
					try {
						Thread.sleep(wait_time);
					} catch (InterruptedException e) {
					}
					life();
				}
			}).start();
		}
	}
}
