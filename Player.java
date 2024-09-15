
public class Player {
	private Hand playerHand;
	private int playerScore;
	
	public Player() {
		playerHand = new Hand();
		playerScore = 0;
	}
	public void newRound() {
		playerHand = new Hand();
	}
	public Hand getPlayerHand() {
		return playerHand;
	}
	public void setPlayerHand(Hand playerHand) {
		this.playerHand = playerHand;
	}
	public int getPlayerScore() {
		return playerScore;
	}
	public void setPlayerScore(int playerScore) {
		this.playerScore = playerScore;
	}
	
}
