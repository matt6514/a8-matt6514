package Conways_Game;

abstract public class ConwayViewEvent {
	
	public boolean isSimpleEvent() {
		return false;
	}
	
	public boolean isBoardSizeEvent() {
		return false;
	}
	
	public boolean isTimeEvent() {
		return false;
	}
}

class SimpleEvent extends ConwayViewEvent{
	private String command;
	
	public SimpleEvent(String s) {
		command = s;
	}
	
	public String getString() {
		return command;
	}
	
	public boolean isSimpleEvent() {
		return true;
	}
}

class BoardSizeEvent extends ConwayViewEvent{
	private char type;
	private int newValue;
	
	public BoardSizeEvent(char c, int val) {
		type = c;
		newValue = val;
	}
	
	public char getType() {
		return type;
	}
	
	public int getValue() {
		return newValue;
	}
	
	public boolean isBoardSizeEvent() {
		return true;
	}
}

class TimeEvent extends ConwayViewEvent{
	private int newValue;
	
	public TimeEvent(int num) {
		newValue = num;
	}
	
	public int getValue() {
		return newValue;
	}
	
	public boolean isTimeEvent() {
		return true;
	}
}
