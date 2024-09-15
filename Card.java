

public class Card{
	private int value;
	private boolean flipped;
	
	public Card(int v) {
		value = v;
		flipped = false;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean isFlipped() {
		return flipped;
	}

	public void setFlipped(boolean flipped) {
		this.flipped = flipped;
	}
	
	
}
