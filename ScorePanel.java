import javax.swing.*;
import java.awt.*;

public class ScorePanel {
    private JPanel scorePanel;
    private int numPlayers;

    public ScorePanel(int numPlayers) {
        this.numPlayers = numPlayers;
        scorePanel = new JPanel(new GridLayout(numPlayers, 2));

        // Add player names and scores to the panel
        for (int i = 0; i < numPlayers; i++) {
            JLabel playerLabel = new JLabel("Player " + (i + 1));
            JLabel scoreLabel = new JLabel(": 0"); // Initialize score to 0
            scorePanel.add(playerLabel);
            scorePanel.add(scoreLabel);
        }
    }

    // Method to update a player's score
    public void updateScore(int playerIndex, int newScore) {
        Component[] components = scorePanel.getComponents();
        JLabel scoreLabel = (JLabel) components[playerIndex * 2 + 1]; // Access the score label
        scoreLabel.setText(": " + String.valueOf(newScore));
    }

    // Get the score panel
    public JPanel getScorePanel() {
        return scorePanel;
    }
}