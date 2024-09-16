import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
public class GraphicsAIO {
    private static JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private JPanel playerHandPanel;
    private JPanel otherHandPanel; // Panel 2
    // for dealer's hand
    private JPanel scorePanel;
    private JPanel deckPanel; // Panel for the deck
    private JPanel currentCardPanel;
    private Card topCard;
    private Card discard;
    private ScorePanel scoreboard;
    private JPanel startScreenPanel;
    private JTextField numPlayersField;
    private JTextField targetScoreField;
    private JButton startButton;
    private JButton currentCardButton;
    private final JButton[][] playerHandButtons = new JButton[4][3];// Array to hold player's hand buttons
    private JButton drawButton; // Button to draw a card from the deck
    private JButton discardButton;
    private ImageIcon[] handSnapshots;
    private static final Deck deck = new Deck();
    Player[] players;
    private final ImageIcon[] icons;
    private int currentPlayer = 0;
    private final boolean buttonPressed = false;
    private Card drawnCard = null; // Track the drawn card for swapping
    private boolean flipTime = false;
    private JPanel logo;
    private ImageIcon logoIcon;
    JPanel panelWithBackground;
    public GraphicsAIO() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        prepareGUI();
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println("Failed to initialize LaF: " + ex.getMessage());
        }
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
        logo = new JPanel();
        logo.setLayout(new FlowLayout());
        logo.add(new JLabel(logoIcon));
        logo.setPreferredSize(new Dimension(200, 200));
        BufferedImage image = null;
        try {image = ImageIO.read(new File("images/logo.jpeg")); System.out.println("file read I hope");}
        catch(Exception e) {
            System.out.println("oopsie woopsies I made a fucky wucky");
        }
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight
                = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        TiledBackgroundPanel panel = new TiledBackgroundPanel(image);
        panel.setPreferredSize(new Dimension(mainFrame.getWidth(), mainFrame.getHeight()));

        startScreenPanel = new JPanel();
        startScreenPanel.setLayout(new GridLayout(3, 2));

        JLabel numPlayersLabel = new JLabel("Number of Players:");
        numPlayersField = new JTextField();


        startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame();
                initializeGame();
            }
        });

        startScreenPanel.add(numPlayersLabel);
        startScreenPanel.add(numPlayersField);

        startScreenPanel.add(new JLabel()); // Empty label for spacing
        startScreenPanel.add(startButton);

        mainFrame.add(startScreenPanel);
        mainFrame.setSize(300, 400);
        //mainFrame.add(panel, gbc);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void startGame() {
        // Retrieve values from text fields
        int numPlayers = Integer.parseInt(numPlayersField.getText());

        System.out.println("START GAME HAS BEEN RUN");
        players = new Player[numPlayers];
        for (int i = 0; i < players.length; i++) players[i] = new Player();
        scoreboard = new ScorePanel(numPlayers);

        // Remove the start screen and show the main game screen
        mainFrame.remove(startScreenPanel);
        prepareGUI();
        otherHandPanel = new JPanel();
        otherHandPanel.setLayout(new GridLayout(players.length - 1, 1));
        mainFrame.add(otherHandPanel);
        otherHandPanel.setPreferredSize(new Dimension(300, 400)); // Adjust dimensions as needed
        BufferedImage image = null;
        try {image = ImageIO.read(new File("images/logo.jpeg")); System.out.println("file read I hope");}
        catch(Exception e) {
            System.out.println("oopsie woopsies I made a fucky wucky");
        }
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight
                = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        GridBagConstraints gbcScoreboard = new GridBagConstraints();
        gbcScoreboard.gridx = 0; // Start at the first column
        gbcScoreboard.gridy = GridBagConstraints.RELATIVE; // Place below the previous component
        gbcScoreboard.gridwidth = GridBagConstraints.REMAINDER; // Span across all columns
        gbcScoreboard.fill = GridBagConstraints.HORIZONTAL; // Fill the entire width
        gbcScoreboard.anchor = GridBagConstraints.PAGE_END;
        gbcScoreboard.weighty = 1.0; // Give the panel weight to push other components up

        TiledBackgroundPanel panel = new TiledBackgroundPanel(image);
        panel.setPreferredSize(new Dimension(mainFrame.getWidth(), mainFrame.getHeight()));
        mainFrame.add(panel, gbc);
        scoreboard.getScorePanel().setVisible(true);
        mainFrame.add(scoreboard.getScorePanel(), gbcScoreboard);
        mainFrame.setComponentZOrder(scoreboard.getScorePanel(), mainFrame.getComponentCount());
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void prepareGUI() {

        mainFrame = new JFrame("Skyjo");
        mainFrame.setSize(1620, 1080);
        mainFrame.setLayout(new GridBagLayout());

        // ... rest of prepareGUI logic (window listener, labels, etc.)

        playerHandPanel = new JPanel();
        playerHandPanel.setLayout(new GridLayout(4, 3));
        mainFrame.add(playerHandPanel);




        currentCardPanel = new JPanel();
        currentCardPanel.setLayout(new FlowLayout());
        mainFrame.add(currentCardPanel);

        deckPanel = new JPanel();
        deckPanel.setLayout(new GridLayout(1,2));
//        deckPanel.setLayout(new FlowLayout());
        deckPanel.setPreferredSize(new Dimension(100, 100));
        mainFrame.add(deckPanel);
        mainFrame.setSize(800, 600);
        mainFrame.setVisible(true);

        mainFrame.setVisible(true);
    }

    private static ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = GraphicsAIO.class.getResource(path);
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
        for(Player p : players) {
            flipTwoRandomElements(p.getPlayerHand().getHand());
        }
        renderHand(players[currentPlayer].getPlayerHand().getHand(), playerHandPanel, playerHandButtons);
        renderOtherHands();
        /*TODO Add scoring
                How should I do that
                Score after every round
                I need to check if the array is all flipped
                if it is I need to somehow remind it to call roundend next time
        *

        */
    }
    public boolean isArrayFlipped(ArrayList<ArrayList<Card>> cardArrayList) {
        for (ArrayList<Card> row : cardArrayList) {
            for (Card card : row) {
                if (!card.isFlipped()) {
                    return false;
                }
            }
        }
        return true;
    }
    public static void flipTwoRandomElements(ArrayList<ArrayList<Card>> twoDArray) {

        Random random = new Random();

        // Calculate the total number of elements
        int totalElements = 0;
        for (ArrayList<Card> innerList : twoDArray) {
            totalElements += innerList.size();
        }

        // Generate two random indices within the total number of elements
        int randomIndex1 = random.nextInt(totalElements);
        int randomIndex2 = random.nextInt(totalElements);

        // Find the corresponding row and column indices for each random index
        int row1 = 0, col1 = 0, row2 = 0, col2 = 0;
        int count = 0;
        for (int i = 0; i < twoDArray.size(); i++) {
            for (int j = 0; j < twoDArray.get(i).size(); j++) {
                if (count == randomIndex1) {
                    row1 = i;
                    col1 = j;
                } else if (count == randomIndex2) {
                    row2 = i;
                    col2 = j;
                }
                count++;
            }
        }

        // Print the picked elements
        twoDArray.get(row1).get(col1).setFlipped(true);
        twoDArray.get(row2).get(col2).setFlipped(true);
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
    private static int score(ArrayList<ArrayList<Card>> hand) {
        int score = 0;
        for(ArrayList<Card> c : hand) {
            for(Card cu : c) {
                score+=cu.getValue();
            }
        }
        return score;
    }
    private void roundOver() {
        int[] highest = {currentPlayer, score(players[currentPlayer].getPlayerHand().getHand())};
        for (int i =0;i<players.length;i++) {
            Player p=players[i];
            if(score(p.getPlayerHand().getHand())>highest[1]) {
                highest[0] = i;
                highest[1] = score(p.getPlayerHand().getHand());
            }
            p.setPlayerScore(p.getPlayerScore()+score(p.getPlayerHand().getHand()));
            scoreboard.updateScore(i, p.getPlayerScore());
            if (p.getPlayerScore() >= 100)
                System.exit(0); // Replace with game over

        }
        if(currentPlayer==highest[1]) {
            players[currentPlayer].setPlayerScore(players[currentPlayer].getPlayerScore()+highest[1]);
        }
        //now redo deck and player hands
        Deck.newDeck();
        for(Player cP : players) {
            cP.newRound();
            flipTwoRandomElements(cP.getPlayerHand().getHand());
        }
        renderHand(players[currentPlayer].getPlayerHand().getHand(), playerHandPanel, playerHandButtons);
    }
    private void nextPlayer() {
        if(currentPlayer!=players.length-1) {
            currentPlayer += 1;
        }
        else currentPlayer=0;
        System.out.println(currentPlayer);
        renderHand(players[currentPlayer].getPlayerHand().getHand(), playerHandPanel, playerHandButtons);
        renderOtherHands();
    }
    private void handleCardClick(int row, int col, int playerIndex) {
        Card cardInHand = players[playerIndex].getPlayerHand().getHand().get(row).get(col);
        System.out.println("Flip time is " + flipTime);
        if (flipTime) {
            if (!players[playerIndex].getPlayerHand().getHand().get(row).get(col).isFlipped()) {
                players[playerIndex].getPlayerHand().getHand().get(row).get(col).setFlipped(true);
                flipTime = false;
                renderHand(players[playerIndex].getPlayerHand().getHand(), playerHandPanel, playerHandButtons);
                deckPanel.setVisible(true);
                currentCardPanel.setVisible(true);
                deckPanel.repaint();

            }
            nextPlayer();
        }
        else if (drawnCard != null) {
            // Swap the drawn card with the selected card
            swapCards(cardInHand, row, col);
            nextPlayer();
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

    public void hasIdenticalElements(ArrayList<ArrayList<Card>> matrix) {
        // Create a copy of the matrix to avoid ConcurrentModificationException
        ArrayList<ArrayList<Card>> copy = new ArrayList<>(matrix);

        // Check rows
        for (int row = 0; row < copy.size(); row++) {
            int firstValue = copy.get(row).getFirst().getValue();
            boolean stop = true;
            for (int i = 1; i < copy.get(row).size(); i++) {
                if (copy.get(row).get(i).getValue() != firstValue || !copy.get(row).get(i).isFlipped()) {
                    // Remove the offending row from the original matri
                    stop = false;
                    break;
                }
            }
            if(stop) matrix.remove(row);
        }

        // Check columns
        for (int col = 0; col < matrix.getFirst().size(); col++) {
            int firstValue = matrix.getFirst().get(col).getValue();
            boolean stop = true;
            for (int row = 1; row < matrix.size(); row++) {
                if (matrix.get(row).get(col).getValue() != firstValue || !copy.get(row).get(col).isFlipped()) {
                    // Remove the offending row from the original matrix
                    stop = false;

                    break;
                }

            }
            copy = new ArrayList<ArrayList<Card>>(matrix);
            if(stop) {
                for(int i = 0; i<copy.size(); i++) {
                    matrix.get(i).remove(col);
                }
            }
        }

    }
    private void renderHand(ArrayList<ArrayList<Card>> hand, JPanel handPanel, JButton[][] handButtons) {
        handPanel.removeAll();
        hasIdenticalElements(hand);
        if(isArrayFlipped(hand)) {
            roundOver();
            return;
        }
        for (int i = 0; i < hand.size(); i++) {
            ArrayList<Card> row = hand.get(i);
            for (int j = 0; j < row.size(); j++) {
                Card card = row.get(j);
                ImageIcon cardIcon = icons[0];
                if (card.isFlipped()) {
                    cardIcon = icons[card.getValue() + 3];
                }

                handButtons[i][j].setIcon(cardIcon);
                // Update listener to reference current player
                int finalI = i;
                int finalJ = j;
                for(ActionListener a : handButtons[i][j].getActionListeners()) {
                    handButtons[i][j].removeActionListener(a);
                }
                handButtons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleCardClick(finalI, finalJ, currentPlayer); // Pass currentPlayer
                    }
                });
                handPanel.add(handButtons[i][j]);
            }
        }
        handPanel.revalidate();
        handPanel.repaint();
    }
    private void renderOtherHands() {
        // Iterate through existing panels and remove those that don't belong to the current player
        for (Component component : otherHandPanel.getComponents()) {
            if (component instanceof JPanel panel) {
                Integer playerIndex = (Integer) panel.getClientProperty("playerIndex");
                if (playerIndex != null && playerIndex == currentPlayer) {
                    otherHandPanel.remove(panel);
                }
            }
        }

        // Ensure the otherHandPanel is visible and has a suitable layout
        otherHandPanel.setVisible(true);
        otherHandPanel.setLayout(new GridLayout(players.length - 1, 1));
        otherHandPanel.setPreferredSize(new Dimension(400, 200));
        // Add panels for other players (excluding the current player)
        for (int i = 0; i < players.length; i++) {
            if (i != currentPlayer) {
                JPanel handPanel = new JPanel();
                handPanel.setLayout(new GridLayout(4, 3));
                handPanel.putClientProperty("playerIndex", i);

                // Add cards to the panel
                ArrayList<ArrayList<Card>> hand = players[i].getPlayerHand().getHand();
                for (ArrayList<Card> row : hand) {
                    for (Card card : row) {
                        ImageIcon cardIcon = icons[0];
                        if (card.isFlipped()) {
                            cardIcon = icons[card.getValue() + 3];
                        }
                        JLabel label = new JLabel();
                        label.setIcon(cardIcon);
                        handPanel.add(label);
                    }
                }

                // Add the panel to the otherHandPanel
                otherHandPanel.add(handPanel);
            }
        }

        // Revalidate and repaint the panel to ensure updates are reflected
        otherHandPanel.revalidate();
        otherHandPanel.repaint();
    }





}