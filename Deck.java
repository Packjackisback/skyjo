import java.util.ArrayList;
import java.util.Random;
public class Deck {
	private static final ArrayList<Card> deck = new ArrayList<Card>();
	private static final ArrayList<Card> discard = new ArrayList<Card>();
   /*      -2:  5 cards
	*	   -1: 10 cards
 	*		0: 15 cards
 	*		1: 10 cards
	*		2: 10 cards
	*		3: 10 cards
	*		4: 10 cards
	*		5: 10 cards
	*		6: 10 cards
	*		7: 10 cards
	*		8: 10 cards
	*		9: 10 cards
	*	   10: 10 cards
	*	   11: 10 cards
	*	   12: 10 cards
	* 
	* 
	*      Should be fine static I guess 🤷‍♀️
	*/
	
	public Deck() {
		newDeck();
	}
	public static void newDeck() {
		for (int i = -2; i <= 12; i++) {
            int count = 10;
            if (i == 0) {
                count = 15;
            } else if (i == -2) {
                count = 5;
            }
            for (int j = 0; j < count; j++) {
                deck.add(new Card(i));
            }
        }
	}
	public static Card getRandom() {
		if(deck.size()<1) newDeck();
		Random rand = new Random();
		System.out.println("Cards left: " + deck.size());
        int randomIndex = rand.nextInt(deck.size());
        Card c = deck.get(randomIndex);
        deck.remove(randomIndex);
        return c;
	}
	public static void discard(Card c) {
		discard.add(c);
	}
}
