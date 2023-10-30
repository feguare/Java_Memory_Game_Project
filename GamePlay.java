import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class GamePlay {
    private static Player player1;
    private static Player player2;
	public static View view; // GUI instance
	public byte expectedInput = (byte)0; // value 1 when waiting for player 1 name, 2 when waiting for player 2 name, 3 when waiting for game mode, 4 when waiting for a card, 5 when round over, 0 when not expecting input
	private int roundCount = 0;
	private byte mode; // inform necessary methods of which mode is in play
	private boolean firstCardPicked = false; // keep track of which card pick it is
	private int firstCardIndex = 20; // intentionally initialised to impossible value
	// placeholders to allow successful game initialisation. Chosen mode defined in setGame
	static private AnimalCard animalCard = new AnimalCard();
	static private ShapeCard shapeCard = new ShapeCard();
	static private ColorCard colorCard = new ColorCard();
	static private NumberCard numberCard = new NumberCard();


    public GamePlay(Player player1, Player player2, View view) { // class constructor
        this.player1 = player1;
        this.player2 = player2;
		this.view = view;
        player1.setMyTurn(true);
		player2.setMyTurn(false);

		this.view.addButtonListeners(new listenerForNewGameButton(), new listenerForSubmitUIButton(), new listenerForCardButtons());
    }

	// METHODS

	// request name input from the user on the GUI
	public void requestNames() {
        view.setChatText('a', "Welcome to Match Madness!");
        view.setChatText('a', "To set player names please");
        view.setChatText('a', "enter each players name in");
        view.setChatText('a', "the textfield above & press");
        view.setChatText('a', "\"Submit\" after each entry.");
		expectedInput = (byte)1;
	}
	// request mode input from the user on the GUI
	private void requestMode() {
		// view.setChatText('r', "");
		view.setChatText('r', "Enter a number from 1-4 and click");
		view.setChatText('a', "\"Submit\" to select a mode:");
		view.setChatText('a', "1. Animals");
		view.setChatText('a', "2. Shapes");
        view.setChatText('a', "3. Colors");
        view.setChatText('a', "4. Numbers");
		expectedInput = (byte)3;
	}
	// to prompt players to select cards when necessary
	private void promptNextCardPick() {
		switch (mode) {
			case 1:
				if (animalCard.getAnimalCards().size() > 0) { // if there's still cards left, prompt for next card
					printPrompt();				
				}
				else endRound();
			break;
			case 2:
				if (shapeCard.getShapeCards().size() > 0) { // if there's still cards left, prompt for next card
					printPrompt();
				}
				else endRound();
			break;
			case 3:
				if (colorCard.getColorCards().size() > 0) { // if there's still cards left, prompt for next card
					printPrompt();				
				}
				else endRound();
			break;
			case 4:
				if (numberCard.getNumberCards().size() > 0) { // if there's still cards left, prompt for next card
					printPrompt();				
				}
				else endRound();
			break;
			default:
				throw new IllegalArgumentException("Invalid mode: " + mode);   	
		}
	}
	// to be called in each case of promptNextCardPick to complete the printing to GUI
	private void printPrompt() {
		if (!firstCardPicked) {	 // if awaiting a full pair of cards
			// prompt the next player to pick a first card
			if (player1.isMyTurn()) {
				view.setChatText('r', "Hey " + player1.getName() + ",");
				view.setChatText('a', "it's your turn! Pick a card!");
			} else { 
				view.setChatText('r', "Hey " + player2.getName() + ",");
				view.setChatText('a', "it's your turn! Pick a card!");
			}
		} else { // else prompt the player to pick a second card
			view.setChatText('a', "Pick the matching card!");
		}
	}
	// to control card flips, front and back end
	private void flipCard(int cardIndex) {
		if (firstCardPicked && firstCardIndex == cardIndex){ // if the user reselects the first card as the second card
			view.setChatText('a', "This card is already selected. Pick another!");
			return;
		} 
		switch (mode) {
			case 1:
				if (animalCard.findCardByPosition((byte)cardIndex) == null) { // if the card is gone
					view.setChatText('a', "This card is already matched. Pick another!");
					return;
				} else { // if the card selected is valid
					AnimalCard card;
					card = animalCard.findCardByPosition((byte)cardIndex); 
					card.flip((byte)cardIndex); // flip the card on the back end
					view.showCard(card.getId(), cardIndex); // flip the card on the front end
					if (!firstCardPicked) { // if this is the first card of the pair, update the variable
						firstCardIndex = cardIndex;
					} else {
						boolean isMatch = areCardsSame((byte)firstCardIndex, (byte)cardIndex, card); // check if cards match
					}
					firstCardPicked = !firstCardPicked; // flip the bool when a valid card is picked
					promptNextCardPick();
				}
			break;
			case 2:
				if (shapeCard.findCardByPosition((byte)cardIndex) == null) { // if the card is gone
					view.setChatText('a', "This card is already matched. Pick another!");
					return;
				} else { // if the card selected is valid
					ShapeCard card;
					card = shapeCard.findCardByPosition((byte)cardIndex); 
					card.flip((byte)cardIndex); // flip the card on the back end
					view.showCard(card.getId(), cardIndex); // flip the card on the front end
					if (!firstCardPicked) { // if this is the first card of the pair, update the variable
						firstCardIndex = cardIndex;
					} else {
						boolean isMatch = areCardsSame((byte)firstCardIndex, (byte)cardIndex, card); // check if cards match
					}
					firstCardPicked = !firstCardPicked; // flip the bool when a valid card is picked
					promptNextCardPick();
				}
			break;
			case 3:
				if (colorCard.findCardByPosition((byte)cardIndex) == null) { // if the card is gone
					view.setChatText('a', "This card is already matched. Pick another!");
					return;
				} else { // if the card selected is valid
					ColorCard card;
					card = colorCard.findCardByPosition((byte)cardIndex); 
					card.flip((byte)cardIndex); // flip the card on the back end
					view.showCard(card.getId(), cardIndex); // flip the card on the front end
					if (!firstCardPicked) { // if this is the first card of the pair, update the variable
						firstCardIndex = cardIndex;
					} else {
						boolean isMatch = areCardsSame((byte)firstCardIndex, (byte)cardIndex, card); // check if cards match
					}
					firstCardPicked = !firstCardPicked; // flip the bool when a valid card is picked
					promptNextCardPick();
				}
			break;
			case 4:
				if (numberCard.findCardByPosition((byte)cardIndex) == null) { // if the card is gone
					view.setChatText('a', "This card is already matched. Pick another!");
					return;
				} else { // if the card selected is valid
					NumberCard card;
					card = numberCard.findCardByPosition((byte)cardIndex); 
					card.flip((byte)cardIndex); // flip the card on the back end
					view.showCard(card.getId(), cardIndex); // flip the card on the front end
					if (!firstCardPicked) { // if this is the first card of the pair, update the variable
						firstCardIndex = cardIndex;
					} else {
						boolean isMatch = areCardsSame((byte)firstCardIndex, (byte)cardIndex, card); // check if cards match
					}
					firstCardPicked = !firstCardPicked; // flip the bool when a valid card is picked
					promptNextCardPick();
				}
			break;
			default:
				throw new IllegalArgumentException("Invalid mode: " + mode);   

		}
	}

	// Method to start the game, it accesses the global variable mode, in form of byte, and starts the game in the right mode
    private void setGame() {
		player1.resetNumberOfMatches();
		player2.resetNumberOfMatches();

		// initialise an list of cards to define the card at each position
		switch (mode) {
			case 1:
				animalCard.initializeGame();
				// this for shows what cards are in each positions, we have left this in for convenience in testing
				for (AnimalCard card : animalCard.getAnimalCards()) {
					System.out.println("Position: " + card.getPosition() + ", Type: " + card.getType());
				}
			break;
			case 2:
				shapeCard.initializeGame();
				// this for shows what cards are in each positions, we have left this in for convenience in testing
				for (ShapeCard card : shapeCard.getShapeCards()) {
					System.out.println("Position: " + card.getPosition() + ", Type: " + card.getType());
				}
			break;
			case 3:
				colorCard.initializeGame();
				// this for shows what cards are in each positions, we have left this in for convenience in testing
				for (ColorCard card : colorCard.getColorCards()) {
					System.out.println("Position: " + card.getPosition() + ", Type: " + card.getType());
				}
			break;
			case 4: 
				numberCard.initializeGame();
				// this for shows what cards are in each positions, we have left this in for convenience in testing
				for (NumberCard card : numberCard.getNumberCards()) {
					System.out.println("Position: " + card.getPosition() + ", Type: " + card.getType());
				}
			break;
			default:
				throw new IllegalArgumentException("Invalid mode: " + mode);   	
		}

		expectedInput = (byte)4; // expect cards next
		firstCardPicked = false; // new game just set so first card has not been picked
		promptNextCardPick(); // prompt player to pick a card
    }
	// process that a round is complete on the front and back end
    private void endRound() {
		// check who won
        if (player1.getNumberOfMatches() > player2.getNumberOfMatches()) {
            player1.hasWon();
        } else if (player1.getNumberOfMatches() < player2.getNumberOfMatches()) {
            player2.hasWon();
        } else {
            System.out.println("It's a tie");
			view.setChatText('r', "It's a tie!"); // print to gui chat
        }

        System.out.println(player1.getName() + " matches: " + player1.getNumberOfMatches() + ", rounds won: " + player1.getRoundCount());
        System.out.println(player2.getName() + " matches: " + player2.getNumberOfMatches() + ", rounds won: " + player2.getRoundCount());
		// print current scores to GUI
		view.setChatText('a', player1.getName() + " matches: " + player1.getNumberOfMatches() + ", rounds won: " + player1.getRoundCount());
		view.setChatText('a', player2.getName() + " matches: " + player2.getNumberOfMatches() + ", rounds won: " + player2.getRoundCount()); 
		view.setChatText('a', "Click any card to start a new round!");
		expectedInput = (byte)5; // expect a card button to trigger a new game next
    }

	// functions to be called by all four areCardsSame methods below
	private static void handleMatch(byte position1, byte position2) {
		// print to GUI chat
		view.setChatText('r', "It's a match!"); 
		// show cards as removed on GUI
		view.removeCard((int)position1); 
		view.removeCard((int)position2);

		// add match to score of scoring player, and set to give player another turn
		if (player1.isMyTurn()) {
			player1.incrementMatches();
			player1.setMyTurn(true);
			player2.setMyTurn(false);
			view.setChatText('a', player1.getName() + " it's your turn again!");
		} else {
			player2.incrementMatches();
			player2.setMyTurn(true);
			player1.setMyTurn(false);
			view.setChatText('a', player2.getName() + " it's your turn again!");
		}
		// update score display on GUI
		view.setScoreDisplay(player1.getName(), player2.getName(), player1.getNumberOfMatches(), player2.getNumberOfMatches()); // update scores on GUI

	}
	private static void handleMisMatch(byte position1, byte position2) {
		// switch turns
		player1.setMyTurn(!player1.isMyTurn());
		player2.setMyTurn(!player2.isMyTurn());
		// unflip cards on front end & print output to chat
		view.hideCard((int)position1);
		view.hideCard((int)position2);
		view.setChatText('a', "It's not a match, but nice try!");
	}

    // Method to check if the two cards flipped are a match, it find the cards by the position, compare the type and if its a match, removes the cards as a possible choice, 
    // increases the matches for the player and set the turns to make this player go again. If it's no match then changes the turn to the other player and flips the both cards back;
    private static boolean areCardsSame(byte position1, byte position2, AnimalCard card) { 
		AnimalCard card1 = animalCard.findCardByPosition(position1);
		AnimalCard card2 = animalCard.findCardByPosition(position2);
        if (card1.getType().equals(card2.getType())) { // if it's a match
			// remove matching cards from availableCards list
			animalCard.getAnimalCards().remove(card1); 
            animalCard.getAnimalCards().remove(card2);
			handleMatch(position1, position2);
			return true;
		}
		else{ // if its not a match
			// unflip cards on back end 
			card1.flip(position1);
			card2.flip(position2);
			handleMisMatch(position1, position2);
			return false;
		}
    }
    private static boolean areCardsSame(byte position1, byte position2, ShapeCard card) {
		ShapeCard card1 = shapeCard.findCardByPosition(position1);
		ShapeCard card2 = shapeCard.findCardByPosition(position2);
        if (card1.getType().equals(card2.getType())) { // if it's a match
			// remove matching cards from availableCards list
			shapeCard.getShapeCards().remove(card1); 
            shapeCard.getShapeCards().remove(card2);
			handleMatch(position1, position2);
			return true;
		}
		else{ // if its not a match
			// unflip cards on back end 
			card1.flip(position1);
			card2.flip(position2);
			handleMisMatch(position1, position2);
			return false;
		}
    }
    private static boolean areCardsSame(byte position1, byte position2, ColorCard card) {
		ColorCard card1 = colorCard.findCardByPosition(position1);
		ColorCard card2 = colorCard.findCardByPosition(position2);
		if (card1.getType().equals(card2.getType())) { // if it's a match
			// remove matching cards from availableCards list
			colorCard.getColorCards().remove(card1); 
            colorCard.getColorCards().remove(card2);
			handleMatch(position1, position2);
			return true;
		}
		else{ // if its not a match
			// unflip cards on back end 
			card1.flip(position1);
			card2.flip(position2);
			handleMisMatch(position1, position2);
			return false;
		}
    }
    private static boolean areCardsSame(byte position1, byte position2, NumberCard card) {
		NumberCard card1 = numberCard.findCardByPosition(position1);
		NumberCard card2 = numberCard.findCardByPosition(position2);
        if (card1.getType().equals(card2.getType())) { // if it's a match
			// remove matching cards from availableCards list
			numberCard.getNumberCards().remove(card1); 
            numberCard.getNumberCards().remove(card2);
			handleMatch(position1, position2);
			return true;
		}
		else{ // if its not a match
			// unflip cards on back end 
			card1.flip(position1);
			card2.flip(position2);
			handleMisMatch(position1, position2);
			return false;
		}
    }

	// when New Game button pressed, set up a new game
	class listenerForNewGameButton implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent click) {
			view.setNewGame(player1.getName(), player2.getName());
			roundCount = 0;
			view.setRoundDisplay(roundCount);
			player1.resetRoundCount();
			player2.resetRoundCount();
			requestNames(); // start a new game, from asking for new player names
		}
	}

	// when submit button pressed, read in user input from textfield
	class listenerForSubmitUIButton implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent click) {
			String input = view.getUserInput(); // get text from textfield
			int intInput = 0;
			view.clearUserInput(); // then clear the textfield

			if (expectedInput == (byte)1) {
				if(input.length() != 0) { // if there was no text in input, skip to keep generic name
					player1.setName(input);
				}
				expectedInput = (byte)2; // to expect player name 2 input
			}

			else if (expectedInput == (byte)2) {
				if(input.length() != 0) { // if there was no text in input, skip to keep generic name
					player2.setName(input);
				}
				expectedInput = (byte)0; // to expect no more input
				view.setScoreDisplay(player1.getName(), player2.getName(), (byte)0, (byte)0);
				requestMode();
			}

			else if (expectedInput == (byte)3) {				
				try { // convert input string into an int
					intInput = Integer.parseInt(input);
				} catch (NumberFormatException e) {
					System.out.println("Invalid integer input");
				}

				if (input.length() == 0 || (intInput < 0 || intInput > 4)) { // if input is invalid, request valid input
					view.setChatText('r', "Please enter a digit from 1-4 then click enter.");
				} else { // if the if statement is not true, then valid input was selected
					expectedInput = (byte)0; // to expect no more input
					view.setRoundDisplay(++roundCount); // Update round display to 1
					mode = (byte)intInput; // set global variable to track mode
					setGame(); // continue the game in the selected mode
				}
			}

			else view.setChatText('a', "No input is requred now");
		}
	}

	// responf to card button clicks accordingly
	class listenerForCardButtons implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent click) {
			if (expectedInput == (byte)4) { // only respsond if it's time to pick cards
				int cardIndex = view.getCardIndex((JButton)click.getSource()); // get the index of the button pressed
				flipCard(cardIndex);
			}
			else if (expectedInput == (byte)5) { // only set new game from card button click if its expected
				setGame();
				view.setNewGame(player1.getName(), player2.getName());
				view.setRoundDisplay(++roundCount);
				promptNextCardPick();
			}
		}
	}
}

