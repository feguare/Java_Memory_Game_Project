import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.geom.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;



public class View extends JFrame {

    // declare and initialise components
    private JLabel title = new JLabel("Match Madness!!", JLabel.LEFT); // Game Title
    private JButton cardPosition[] = new JButton[20]; // array of buttons representing cards
    private JButton newGameButton = new JButton("New Game"); // button to start a new game
    private JButton submitUIButton = new JButton("Submit"); // button to submit user input
    private JTextField userInput = new JTextField(20); // field for user to input text
    private JLabel chatBox = new JLabel(" ", JLabel.CENTER); // label to display intructions to users               
    private JLabel roundDisplay = new JLabel(" ", JLabel.CENTER); // label to display current round        
    private JLabel scoreDisplay = new JLabel(" ", JLabel.CENTER); // label to diplay current scores

    View() { // constructor

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // end program when window is closed

        // panel to organise GUI layout
        JPanel cardPanel = new JPanel();
        JPanel titlePanel = new JPanel();
        JPanel topSidePanel = new JPanel();
        JPanel middleSidePanel = new JPanel();
        JPanel bottomSidePanel = new JPanel();

        // format components where necasary
        title.setFont(new Font("Arial", Font.PLAIN, 140));
        title.setForeground(new Color(0,100,0)); // dark green title
        chatBox.setOpaque(true);
        chatBox.setBackground(Color.WHITE);
        scoreDisplay.setFont(new Font("Arial", Font.PLAIN, 30));
        roundDisplay.setFont(new Font("Arial", Font.PLAIN, 40));
        newGameButton.setBackground(new Color(225,225,0));
        submitUIButton.setBackground(new Color(209,237,202));
        for (int i = 0; i < cardPosition.length; i++) {
            cardPosition[i] = new JButton();
            cardPosition[i].setBackground(new Color(0,225,0)); // set all cards green initially 
            cardPanel.add(cardPosition[i]);
        }
        
        // add components to GUI Panels
        topSidePanel.add(newGameButton);
        middleSidePanel.add(userInput);
        middleSidePanel.add(submitUIButton);
        bottomSidePanel.add(chatBox);
        bottomSidePanel.add(roundDisplay);
        bottomSidePanel.add(scoreDisplay);
        titlePanel.add(title);


        // format the GUI Panels
        cardPanel.setLayout(new GridLayout(4,5,10,10));
        cardPanel.setBounds(50, 200, 700, 550);

        topSidePanel.setLayout(new GridLayout());
        topSidePanel.setBounds(800, 200, 200, 90);

        middleSidePanel.setLayout(new GridLayout(1,0));
        middleSidePanel.setBounds(800, 300, 200, 50);

        bottomSidePanel.setLayout(new GridLayout(0,1,10,10));
        bottomSidePanel.setBounds(800, 360, 200, 390);

        // add GUI Panels to the frame to be displayed
        this.add(topSidePanel);
        this.add(middleSidePanel);
        this.add(bottomSidePanel);
        this.add(cardPanel);
        this.add(titlePanel);
    }
    
    // METHODS

    public void setNewGame(String name1, String name2) { // set up GUI for new game
        setScoreDisplay(name1, name2, (byte)0, (byte)0); // set the scores to 0
        setChatText('r', ""); // clear the chat box
        for (int i = 0; i < cardPosition.length; i++) {
            hideCard(i); // hide all cards
        }
    }

    public String getUserInput() { // to retrieve player names
        return userInput.getText();
    }

    public void clearUserInput() {  // to clear textField when submit is pressed
        userInput.setText(""); // clear text from the user input box
    }

    public String getChatText() { // to retrieve text currently displayed in chatbox
        return chatBox.getText();
    }

    public void setChatText(char action, String text) { // to update chatBox text
        if (action == 'a') { // use action key 'a' to append the message
            chatBox.setText("<html>\t" + getChatText() + "<br>\t" + text + "<html>");
        }
        if (action == 'r') { // use action key 'r' to replace the message
            chatBox.setText(" ");                  
            setChatText('a', text);
        }
    }

    public void setScoreDisplay(String name1, String name2, byte score1, byte score2) { // display the current number of cards matched by each player
        scoreDisplay.setText("<html>Score<br>" + name1 + "\t:\t" + score1 + "<br>" + name2 + "\t:\t" + score2 + "<html>");
    }

    public void setRoundDisplay(int round) { // display the current number of cards matched by each player
        roundDisplay.setText("Round\t:\t"+ round);
    }

    public void addButtonListeners(ActionListener listenerForNewGameButton, ActionListener listenerForSubmitUIButton, ActionListener listenerForCardButtons) {
        newGameButton.addActionListener(listenerForNewGameButton);
        submitUIButton.addActionListener(listenerForSubmitUIButton);
        for (int i = 0; i < cardPosition.length; i++) {
            cardPosition[i].addActionListener(listenerForCardButtons);
        }
    }

    public void showCard(String cardName, int cardIndex) { // to flip card at cardIndex position
        cardPosition[cardIndex].setBackground(new Color(225,225,225)); // set background to white to view images more clearly
        cardPosition[cardIndex].setIcon(new ImageIcon("Sprites/" + cardName + ".png")); // add the corresponging image icon to the button
    }

    public void hideCard(int cardIndex) { // to  un-flipp card if it was not correctly matched
        cardPosition[cardIndex].setBackground(new Color(0,225,0)); // original green as still available
        cardPosition[cardIndex].setIcon(new ImageIcon("")); // clear icon
    }

    public void removeCard(int cardIndex) { // to be called when a card is correctly matched, and no longer availabel
        cardPosition[cardIndex].setBackground(new Color(0,100,0)); // dark green as no longer available
        cardPosition[cardIndex].setIcon(new ImageIcon("")); // clear icon
    }

    public int getCardIndex(JButton clickedButton) { // to return the cardIndex of the given button
        int i;
        for (i = 0; i < cardPosition.length; i++) {
            if (clickedButton == cardPosition[i]) break;
        }
        return i;
    }
}