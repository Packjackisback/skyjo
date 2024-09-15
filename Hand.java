import java.util.ArrayList;
public class Hand {
	ArrayList<ArrayList<Card>> hand = new ArrayList<ArrayList<Card>>();
	
	public Hand(ArrayList<ArrayList<Card>> c) {
		hand = c;
		hand.add(new ArrayList<Card>());
		hand.add(new ArrayList<Card>());
		hand.add(new ArrayList<Card>());
		hand.add(new ArrayList<Card>());
	}
	public Hand() {
		for(int i = 0;i<4;i++) {
			hand.add(new ArrayList<Card>());
			for(int j = 0;j<3;j++) {
				hand.get(i).add(new Card(0));
			}
		}
		for (int i = 0; i < hand.size(); i++)
		{
		    for (int j = 0; j < hand.get(i).size(); j++)
		    {
		        hand.get(i).set(j, Deck.getRandom());
		        hand.get(i).get(j).setFlipped(false); //just in case
		    } 
		}
	}
	public ArrayList<ArrayList<Card>> getDeck() {
		return hand;
	}
	
	public Card getCard(int x, int y) {
		return(hand.get(x).get(y));
	}

	public void changeCard(int x, int y, int val) {
		hand.get(x).get(y).setValue(val);
	}
	
	public void flipCard(int x, int y) {
		hand.get(x).get(y).setFlipped(true);
	}
	
	public void shrinkArray(int n, boolean vertical) {
		if(vertical) {
			for(int i = 0;i<hand.size();i++) {
				hand.get(i).remove(n);
				
			}
			return;
		}
		hand.remove(n);
	}
	public boolean isFinished() {
		for (int i = 0; i < hand.size(); i++)
		{
		    for (int j = 0; j < hand.get(i).size(); j++)
		    {
		        if(hand.get(i).get(j).isFlipped()==false) return false;
		    } 
		}		
		return true;
	
	
	}
	public ArrayList<ArrayList<Card>> getHand() {
		return hand;
	}
	public void setHand(ArrayList<ArrayList<Card>> hand) {
		this.hand = hand;
	}
	
}
