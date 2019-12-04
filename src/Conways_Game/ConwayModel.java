package Conways_Game;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConwayModel {

	private boolean torus;
	private boolean play;
	private int survive_lower;
	private int survive_upper;
	private int birth_lower;
	private int birth_upper;
	private Date date;
	private long time;
	private String message;
	
	private List<ConwayObserver> observers;
	
	public ConwayModel() {
		torus = false;
		play = false;
		survive_lower = 2;
		survive_upper = 3;
		birth_lower = 3;
		birth_upper = 3;
		date = new Date();
		message = "Welcome to Conway's Game of Life!";
		observers = new ArrayList<ConwayObserver>();
	}
	
	public void reset() {
		torus = false;
		play = false;
		survive_lower = 2;
		survive_upper = 3;
		birth_lower = 3;
		birth_upper = 3;
		date = new Date();
		message = "reset";
		notifyObservers();
	}
	
	public void addObserver(ConwayObserver o) {
		observers.add(o);
		notifyObservers();
	}
	
	public void removeObserver(ConwayObserver o) {
		observers.remove(o);
	}
	
	private void notifyObservers() {
		for (ConwayObserver o : observers) {
			o.update(this,message);
		}
		message = "";
	}

	private boolean isTorus() {
		return torus;
	}

	public boolean isPlay() {
		return play;
	}

	private int getSurvive_lower() {
		return survive_lower;
	}

	private int getSurvive_upper() {
		return survive_upper;
	}

	private int getBirth_lower() {
		return birth_lower;
	}

	private int getBirth_upper() {
		return birth_upper;
	}

	public long getTime() {
		return time;
	}

	public void setTorus() {
		torus = !torus;
		
		if (torus) {
			message = "Torus mode is now on";
		} else {
			message = "Torus mode is now off";
		}
		
		notifyObservers();
	}

	public void setPlay(boolean play) {
		this.play = play;
		
		if (play) {
			message = "Playing";
		} else {
			message = "Paused";
		}
		
		notifyObservers();
	}

	private void setSurvive_lower(int survive_lower) {
		this.survive_lower = survive_lower;
		
		message = "The Lower Survive limit is now " + survive_lower;
		
		notifyObservers();
	}

	private void setSurvive_upper(int survive_upper) {
		this.survive_upper = survive_upper;
		
		message = "The Upper Survive limit is now " + survive_upper;
		
		notifyObservers();
	}

	private void setBirth_lower(int birth_lower) {
		this.birth_lower = birth_lower;
		
		message = "The Lower Birth limit is now " +  birth_lower;
		
		notifyObservers();
	}

	private void setBirth_upper(int birth_upper) {
		this.birth_upper = birth_upper;
		
		message = "The Upper Birth limit is now " + birth_upper;
		
		notifyObservers();
	}

	public void setTime() {;
		time = date.getTime();
	}
	
	public void lifeCycle(JSpotBoard board) {
		int[][] current = new int[board.getSpotWidth()][board.getSpotHeight()];
		for (int i = 0; i < board.getSpotWidth();i++) {
			for (int j = 0; j < board.getSpotHeight();j++) {
				current[i][j] = board.getSpotAt(i, j).isEmpty()? 0:1;
			}
		}
		
		if (!isTorus()) {
			for (int i = 0; i < board.getSpotWidth();i++) {
				for (int j = 0; j < board.getSpotHeight();j++) {
					int around = 0; //track number of lives squares surrounding it
					
					if (i > 0) {
						around += current[i-1][j];
					}
					
					if (i > 0 && j > 0) {
						around += current[i-1][j-1];
					}
					
					if (i > 0 && j < board.getSpotHeight()-1) {
						around += current[i-1][j+1];
					}
					
					if (j > 0) {
						around += current[i][j-1];
					}
					
					if (j > 0 && i < board.getSpotWidth()-1) {
						around += current[i+1][j-1];
					}
					
					if (i < board.getSpotWidth()-1) {
						around += current[i+1][j];
					}
					
					if (i < board.getSpotWidth()-1 && j < board.getSpotHeight()-1) {
						around += current[i+1][j+1];
					}
					
					if (j < board.getSpotHeight()-1) {
						around += current[i][j+1];
					}
					
					if (current[i][j] == 0) {
						if (around >= getBirth_lower() && around <= getBirth_upper()) {
							board.getSpotAt(i, j).toggleSpot();
						}
					} else {
						if (around < getSurvive_lower() || around > getSurvive_upper()) {
							board.getSpotAt(i, j).toggleSpot();
						}
					}
				}
			}
		}
		else {
			for (int i = 0; i < board.getSpotWidth();i++) {
				for (int j = 0; j < board.getSpotHeight();j++) {
					System.out.println("s");
					int around = 0; //track number of lives squares surrounding it
					int tempX = 0;
					int tempY = 0;
					
					if (i > 0) {
						around += current[i-1][j];
					} else {
						around += current[board.getSpotWidth()-1][j];
					}
					
					if (i > 0 && j > 0) {
						around += current[i-1][j-1];
					} else {
						if (i > 0)
							tempX = i-1;
						else
							tempX = board.getSpotWidth()-1;
						if (j > 0)
							tempY = j-1;
						else
							tempY = board.getSpotHeight()-1;
						around += current[tempX][tempY];
					}
					
					if (i > 0 && j < board.getSpotHeight()-1) {
						around += current[i-1][j+1];
					} else {
						if (i > 0)
							tempX = i-1;
						else
							tempX = board.getSpotWidth()-1;
						if (j < board.getSpotHeight()-1)
							tempY = j+1;
						else
							tempY = 0;
						around += current[tempX][tempY];
					}
					
					if (j > 0) {
						around += current[i][j-1];
					} 
					else {
						around += current[i][board.getSpotHeight()-1];
					}
					
					if (j > 0 && i < board.getSpotWidth()-1) {
						around += current[i+1][j-1];
					} else {
						if (j > 0)
							tempY = j-1;
						else
							tempY = board.getSpotHeight()-1;
						if (i < board.getSpotWidth()-1)
							tempX = i+1;
						else
							tempX = 0;
						around += current[tempX][tempY];
					}
					
					if (i < board.getSpotWidth()-1) {
						around += current[i+1][j];
					} else {
						around += current[0][j];
					}
					
					if (i < board.getSpotWidth()-1 && j < board.getSpotHeight()-1) {
						around += current[i+1][j+1];
					} else {
						if (i < board.getSpotWidth()-1)
							tempX = i+1;
						else
							tempX = 0;
						if (j < board.getSpotHeight()-1)
							tempY = j+1;
						else
							tempY = 0;
						around += current[tempX][tempY];
					}
					
					if (j < board.getSpotHeight()-1) {
						around += current[i][j+1];
					} else {
						around += current[i][0];
					}
					
					if (current[i][j] == 0) {
						if (around >= getBirth_lower() && around <= getBirth_upper()) {
							board.getSpotAt(i, j).toggleSpot();
						}
					} else {
						if (around < getSurvive_lower() || around > getSurvive_upper()) {
							board.getSpotAt(i, j).toggleSpot();
						}
					}
				}
			}
		}
	}
	
	public void increaseUpperSurvive() {
		if (survive_upper == 8) {
			message = "The Upper Limit of Survive can not be increased";
			notifyObservers();
		} else {
			setSurvive_upper(survive_upper + 1);
		}
	}
	
	public void decreaseUpperSurvive() {
		if (survive_upper == survive_lower) {
			message = "The Upper Limit of Survive can not be decreased";
			notifyObservers();
		} else {
			setSurvive_upper(survive_upper-1);
		}
	}
	
	public void increaseUpperBirth() {
		if (birth_upper == 8) {
			message = "The Upper Limit of Birth can not be increased";
			notifyObservers();
		} else {
			setBirth_upper(birth_upper + 1);
		}
	}
	
	public void decreaseUpperBirth() {
		if (birth_upper == birth_lower) {
			message = "The Upper Limit of Birth can not be decreased";
			notifyObservers();
		} else {
			setBirth_upper(birth_upper-1);
		}
	}
	
	public void increaseLowerSurvive() {
		if (survive_lower == survive_upper) {
			message = "The Lower Limit of Survive can not be increased";
			notifyObservers();
		} else {
			setSurvive_lower(survive_lower+1);
		}
	}
	
	public void decreaseLowerSurvive() {
		if (survive_lower == 1) {
			message = "The Lower Limit of Survive can not be decreased";
			notifyObservers();
		} else {
			setSurvive_lower(survive_lower - 1);
		}
	}
	
	public void increaseLowerBirth() {
		if (birth_lower == birth_upper) {
			message = "The Lower Limit of Birth can not be increased";
			notifyObservers();
		} else {
			setBirth_lower(birth_lower+1);
		}
	}
	
	public void decreaseLowerBirth() {
		if (birth_lower == 1) {
			message = "The Lower Limit of Birth can not be decreased";
			notifyObservers();
		} else {
			setBirth_lower(birth_lower - 1);
		}
	}
}
