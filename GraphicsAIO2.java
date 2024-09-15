import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class GraphicsAIO2 {
    private static JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private JPanel playerHandPanel;
    private JPanel dealerHandPanel; // Panel for dealer's hand
    private JPanel deckPanel; // Panel for the deck
    private JPanel currentCardPanel;
    private Card topCard;
    private Card discard;

    private JPanel startScreenPanel;
    private JTextField numPlayersField;
    private JTextField targetScoreField;
    private JButton startButton;
    private JButton currentCardButton;
    private final JButton[][] playerHandButtons = new JButton[4][3];// Array to hold player's hand buttons
    private JButton drawButton; // Button to draw a card from the deck
    private JButton discardButton;

    private final Deck deck = new Deck();
    Player[] players;
    private final ImageIcon[] icons;
    private int currentPlayer = 0;
    private final boolean buttonPressed = false;
    private Card drawnCard = null; // Track the drawn card for swapping
    private boolean flipTime = false;
    public GraphicsAIO2() {
        prepareGUI();
        icons = new ImageIcon[16];
        icons[0] = createImageIcon("/images/back.png", "Java");
        icons[1] = createImageIcon("/images/cardn2.png", "Java");
        icons[2] = createImageIcon("/images/cardn1.png", "Java");
        icons[3] = createImageIcon("/images/card0.png", "Java");
        icons[4] = createImageIcon("/images/card1.png", "Java");
        icons[5] = createImageIcon("/images/card2.png", "Java");
        icons[6] = createImageIcon("/images/card3.png", "Java");
        icons[7] = createImageIcon("/images/card4.png", "Java");
        icons[8] = createImageIcon("/images/card5.png", "Java");
        icons[9] = createImageIcon("/images/card6.png", "Java");
        icons[10] = createImageIcon("/images/card7.png", "Java");
        icons[11] = createImageIcon("/images/card8.png", "Java");
        icons[12] = createImageIcon("/images/card9.png", "Java");
        icons[13] = createImageIcon("/images/card10.png", "Java");
        icons[14] = createImageIcon("/images/card11.png", "Java");
        icons[15] = createImageIcon("/images/card12.png", "Java");
        showStartScreen();
        for (int g = 0; g < playerHandButtons.length; g++) {
            for (int i = 0; i < playerHandButtons[g].length; i++) {
                playerHandButtons[g][i] = new JButton();
            }
        }
    }

    private void showStartScreen() {
        startScreenPanel = new JPanel();
        startScreenPanel.setLayout(new GridLayout(3, 2));

        JLabel numPlayersLabel = new JLabel("Number of Players:");
        numPlayersField = new JTextField();
        JLabel targetScoreLabel = new JLabel("Target Score:");
        targetScoreField = new JTextField();

        startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame();
                initializeGame();
            }
        });

        startScreenPanel.add(numPlayersLabel);
        startScreenPanel.add(numPlayersField);
        startScreenPanel.add(targetScoreLabel);
        startScreenPanel.add(targetScoreField);
        startScreenPanel.add(new JLabel()); // Empty label for spacing
        startScreenPanel.add(startButton);

        mainFrame.add(startScreenPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void startGame() {
        // Retrieve values from text fields
        int numPlayers = Integer.parseInt(numPlayersField.getText());
        int targetScore = Integer.parseInt(targetScoreField.getText());

        players = new Player[numPlayers];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player();
        }
        topCard = deck.getRandom();
        // Remove the start screen and show the main game screen
        mainFrame.remove(startScreenPanel);
        prepareGUI();
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Skyjo");
        mainFrame.setSize(1620, 1080);
        mainFrame.setLayout(new GridLayout(4, 1));

        // ... rest of prepareGUI logic (window listener, labels, etc.)

        playerHandPanel = new JPanel();
        playerHandPanel.setLayout(new GridLayout(4, 3));
        mainFrame.add(playerHandPanel);

        dealerHandPanel = new JPanel();
        dealerHandPanel.setLayout(new FlowLayout());
        mainFrame.add(dealerHandPanel);

        currentCardPanel = new JPanel();
        currentCardPanel.setLayout(new FlowLayout());
        mainFrame.add(currentCardPanel);

        deckPanel = new JPanel();
        deckPanel.setLayout(new FlowLayout());
        mainFrame.add(deckPanel);

        mainFrame.setVisible(true);
    }

    private static ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = GraphicsAIO2.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private void initializeGame() {

        // Create draw button
        drawButton = new JButton("Draw Card", icons[0]);
        drawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Draw a card from the deck
                drawCard();
            }
        });
        if (discard != null) {
            discardButton = new JButton("Draw Card from the discard", icons[discard.getValue()]);
            discardButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    drawDiscard();
                }
            });
        }
        if (discard == null) {
            discardButton = new JButton("Draw Card from the discard");
            discardButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    drawDiscard();
                }
            });
        }
        deckPanel.add(discardButton);
        deckPanel.add(drawButton);
        System.out.println("This is being run first");
        mainLoop();
    }
    private void nextPlayer() {
        if(currentPlayer<players.length-1) {
            currentPlayer++;
        }
        else currentPlayer=0;
    }
    private void drawCard() {

        if(drawnCard==null) {
            drawnCard = Deck.getRandom(); // Now using drawnCard
            drawnCard.setFlipped(true);
            currentCardButton = new JButton();
            ImageIcon cardIcon = icons[drawnCard.getValue() + 3];

            currentCardButton.setIcon(cardIcon);

            // Add an action listener to each button
            currentCardButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Drawn card: " + drawnCard.getValue());
                }
            });

            currentCardPanel.removeAll();
            currentCardPanel.add(currentCardButton);
            currentCardPanel.revalidate();
            currentCardPanel.repaint();
        }
    }

    private void drawDiscard() {
        System.out.println(discard);
        if(discard!=null&&drawnCard==null) {
            drawnCard = discard;
            drawnCard.setFlipped(true);
            currentCardButton = new JButton();
            ImageIcon cardIcon = icons[discard.getValue() + 3];
            currentCardButton.setIcon(cardIcon);

            // Add an action listener to each button
            currentCardButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Drawn card: " + discard.getValue());
                    
                }
            });

            currentCardPanel.removeAll();
            currentCardPanel.add(currentCardButton);
            currentCardPanel.revalidate();
            currentCardPanel.repaint();


        }
        else if(drawnCard!=null) {
        	discard = drawnCard;
        	
        	System.out.println("Discarding");
        	currentCardPanel.removeAll();
            drawnCard=null;
            if (discardButton == null) {
                discardButton = new JButton("Discard Pile", icons[discard.getValue()+3]);
                discardButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    	System.out.println(drawnCard);
                        drawDiscard();
                    }
                });
                deckPanel.add(discardButton);
            } else {
                discardButton.setIcon(icons[discard.getValue()+3]);
            }
            deckPanel.setVisible(false);
            currentCardPanel.setVisible(false);
        	flipTime=true;
        	System.out.print(flipTime);
        	currentCardPanel.revalidate();
        	currentCardPanel.repaint();
        	deckPanel.repaint();
        	
        }
    }
    private int score(ArrayList<ArrayList<Card>> hand) {
        int score = 0;
        for(ArrayList<Card> c : hand) {
            for(Card cu : c) {
                score+=cu.getValue();
            }
        }
        return score;
    }
    private void mainLoop() {
        for (Player p : players) {
            if (p.getPlayerScore() >= 100)
                System.exit(0); // Replace with game over
        }

        renderHand(players[currentPlayer].getPlayerHand().getHand(), playerHandPanel, playerHandButtons);
    }

    private void handleCardClick(int row, int col) {
        Card cardInHand = players[currentPlayer].getPlayerHand().getHand().get(row).get(col);
        System.out.println("Flip time is " + flipTime);
        if (flipTime) {
            if (!players[currentPlayer].getPlayerHand().getHand().get(row).get(col).isFlipped()) {
                players[currentPlayer].getPlayerHand().getHand().get(row).get(col).setFlipped(true);
                flipTime = false;
                renderHand(players[currentPlayer].getPlayerHand().getHand(), playerHandPanel, playerHandButtons);
                deckPanel.setVisible(true);
                currentCardPanel.setVisible(true);
                deckPanel.repaint();
                
            }

        }
        else if (drawnCard != null) {
            // Swap the drawn card with the selected card
            swapCards(cardInHand, row, col);
        } else {
            System.out.println("No card has been drawn.");
        }
    }
    
    private void swapCards(Card cardInHand, int row, int col) {
        // Discard the card from hand
        discard = cardInHand;

        // Place the drawn card in the selected slot
        players[currentPlayer].getPlayerHand().getHand().get(row).set(col, drawnCard);

        // Clear the drawnCard and update the hand
        drawnCard = null;
        currentCardPanel.removeAll();
        currentCardPanel.repaint();

        // Update visual representation
        renderHand(players[currentPlayer].getPlayerHand().getHand(), playerHandPanel, playerHandButtons);

        // Update discard pile if needed
        if (discardButton == null) {
            discardButton = new JButton("Discard Pile", icons[discard.getValue()+3]);
            discardButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	System.out.println(drawnCard);
                    drawDiscard();
                }
            });
            deckPanel.add(discardButton);
        } else {
            discardButton.setIcon(icons[discard.getValue()+3]);
        }
        deckPanel.revalidate();
        deckPanel.repaint();
    }

    private boolean canPlaceCard(Card card, int row, int col) {
        return true; // Placeholder
    }
    public static boolean hasIdenticalElements(ArrayList<ArrayList<Card>> matrix) {
        // Check rows
        for (ArrayList<Card> row : matrix) {
            int firstValue = row.get(0).getValue();
            for (int i = 1; i < row.size(); i++) {
                if (row.get(i).getValue() != firstValue) {
                    return false;
                }
            }
        }

        // Check columns
     // Check columns
        for (int col = 0; col < matrix.get(0).size(); col++) {
            int firstValue = matrix.get(0).get(col).getValue();
            for (int row = 1; row < matrix.size(); row++) {
                if (matrix.get(row).get(col).getValue() != firstValue) {
                    return false;
                }
            }
        }

        return true;
    }
    private void renderHand(ArrayList<ArrayList<Card>> hand, JPanel handPanel, JButton[][] handButtons) {
        handPanel.removeAll();

        for (int i = 0; i < hand.size(); i++) {
            ArrayList<Card> row = hand.get(i);
            final int finalI = i;

	        for (int j = 0; j < row.size(); j++) {
	        	final int finalJ = j; // Create a final copy of i
	            Card card = row.get(j);
                ImageIcon cardIcon = icons[0];
                if(card.isFlipped()) {
                     cardIcon = icons[card.getValue() + 3];
                }
                System.out.println("Has identical elements: "+hasIdenticalElements(hand));
                if(hasIdenticalElements(hand)) System.exit(0);
	            handButtons[i][j].setIcon(cardIcon);

	            // Add an action listener to each button
	            handButtons[i][j].addActionListener(new ActionListener() {
	                @Override
	                public void actionPerformed(ActionEvent e) {
                        //when each card is clicked call handleCardClick for it's spot
	                    handleCardClick(finalI, finalJ);
	                }
	            });

	            handPanel.add(handButtons[i][j]);
	        }
	    }

	    handPanel.revalidate();
	    handPanel.repaint();
	}
    
    private void pickCard() {
    	
    }
    public static void extRepaint() {
        mainFrame.repaint();
    }
}