import java.util.*;

// Abstract Card class
abstract class Card {
    boolean isFaceUp;
    byte position;
	String id;
    // Constructor
    Card(byte position) {
        this.position = position;
        this.isFaceUp = false;
        this.id = id;
    }
     
    Card(){
		this.position = position;
        this.isFaceUp = false;
        this.id = id;
	}

    // Abstract method for flipping the card
    public abstract void initializeGame();
    public abstract void flip(byte position);
    
    public boolean IsFacedUp(){
		return isFaceUp;
	}
	public byte getPosition() {
        return position;
    }
    public String getId() {
        return toString();
    }
}

// Subclass: AnimalCard
class AnimalCard extends Card {
	public enum AnimalType {
		MOUSE, PIG, CHICK, COW, LION, SHEEP, CAT, DOG, DUCK, FROG
	}
	private AnimalType type;
	//This is the list of cards that are in the game when we inicialize, they are stored here.
	private List<AnimalCard> animalCards;
    // Constructor
    AnimalCard(byte position) {
        super(position);
        this.id = id;
        this.animalCards = animalCards;
    }
    // Constructor 2
    //This second constructor is created because to call the methods in other classes i needed to instanciate a card object, where it wasnt in any position
    AnimalCard(){
		super();
		this.id = id;
        this.animalCards = animalCards;
	}
    
    @Override
    public String toString() {
        return type.name();
    }
	public AnimalType getType() {
        return type;
    }
    public void setAnimalCards(List<AnimalCard> animalCards){
		this.animalCards = animalCards;
    }
    public List<AnimalCard> getAnimalCards(){
		return animalCards;
	}
	// Method overloading
	public AnimalCard findCardByPosition(byte position) {
        for (AnimalCard card : animalCards) {
            if (card.getPosition() == position) {
                return card;
            }
        }
        return null; // If no matching card is found
    }
    // this creats the list of cards for the game, it is shuffled to make sure its all in random positions, here we use the first constructor with the positions and in the end set the list of cards to the class variable 
	@Override
    public void initializeGame() {
        List<AnimalType> availableTypes = Arrays.asList(AnimalType.values());
        Collections.shuffle(availableTypes);

        List<Byte> positions = new ArrayList<>();
        for (byte i = 0; i < 20; i++) {
            positions.add(i);
        }
        Collections.shuffle(positions);

        List<AnimalCard> animalCards = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            byte position = positions.get(i);

            AnimalCard card1 = new AnimalCard(position);
            card1.type = availableTypes.get(i);
            animalCards.add(card1);

            // Create the matching card
            AnimalCard card2 = new AnimalCard(positions.get(i + 10));
            card2.type = availableTypes.get(i);
            animalCards.add(card2);
        }
        setAnimalCards(animalCards);
    }
	@Override
    public void flip(byte position) {
		if(isFaceUp == false) {
            isFaceUp = !isFaceUp;
            // animalflipview();//calls the view function for the specific motion for flipping an animal card // *** EXPLAINED IN ReadMe ***
        } else{
            isFaceUp = false;
        }
    }
}

// Subclass: ShapeCard 
class ShapeCard extends Card {
	public enum ShapeType {
		CIRCLE, SQUARE, RECTANGLE, DIAMOND, RHOMBUS, TRIANGLE, PENTAGON, OVAL, STAR, CROSS
	}
	private ShapeType type;
	private List<ShapeCard> shapeCards;
    // Constructor
    ShapeCard(byte position) {
        super(position);
        this.id = id;
        this.shapeCards = shapeCards;
    }
     // Constructor 2
    ShapeCard(){
		super();
		this.id = id;
        this.shapeCards = shapeCards;
	}
    
    @Override
    public String toString() {
        return type.name();
    }
    public ShapeType getType() {
        return type;
    }
    
    public void setShapeCards(List<ShapeCard> shapeCards){
		this.shapeCards = shapeCards;
    }
    public List<ShapeCard> getShapeCards(){
		return shapeCards;
	}
	// Method overloading
	public ShapeCard findCardByPosition(byte position) {
        for (ShapeCard card : shapeCards) {
            if (card.getPosition() == position) {
                return card;
            }
        }
        return null; // If no matching card is found
    }
	
    @Override
    public void initializeGame() {
        List<ShapeType> availableTypes = Arrays.asList(ShapeType.values());
        Collections.shuffle(availableTypes);

        List<Byte> positions = new ArrayList<>();
        for (byte i = 0; i < 20; i++) {
            positions.add(i);
        }
        Collections.shuffle(positions);

        List<ShapeCard> shapeCards = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            byte position = positions.get(i);

            ShapeCard card1 = new ShapeCard(position);
            card1.type = availableTypes.get(i);
            shapeCards.add(card1);

            // Create the matching card
            ShapeCard card2 = new ShapeCard(positions.get(i + 10));
            card2.type = availableTypes.get(i);
            shapeCards.add(card2);
        }
        setShapeCards(shapeCards);
    }
	@Override
    public void flip(byte position) {
		if(isFaceUp == false){
            isFaceUp = !isFaceUp;
            //shapeflipview();//calls the view function for the specific motion for flipping an animal card
        } else{
            isFaceUp = false;
        }
    }
}

class ColorCard extends Card {
	public enum ColorType {
		RED, YELLOW, GREEN, BLUE, ORANGE, PURPLE, PINK, GREY, DARKBLUE, DARKGREEN
	}
	private ColorType type;
	private List<ColorCard> colorCards;
    // Constructor
    ColorCard(byte position) {
        super(position);
        this.id = id;
        this.colorCards = colorCards;
    }
     // Constructor 2
    ColorCard(){
		super();
		this.id = id;
        this.colorCards = colorCards;
	}
    
    @Override
    public String toString() {
        return type.name();
    }
    
    public ColorType getType() {
        return type;
    }
   
    public void setColorCards(List<ColorCard> colorCards){
		this.colorCards = colorCards;
    }
    public List<ColorCard> getColorCards(){
		return colorCards;
	}
	// Method overloading
	public ColorCard findCardByPosition(byte position) {
        for (ColorCard card : colorCards) {
            if (card.getPosition() == position) {
                return card;
            }
        }
        return null; // If no matching card is found
    }
	
    
    @Override
    public void initializeGame() {
    List<ColorType> availableTypes = Arrays.asList(ColorType.values());
    Collections.shuffle(availableTypes);

    List<Byte> positions = new ArrayList<>();
    for (byte i = 0; i < 20; i++) {
        positions.add(i);
    }
    Collections.shuffle(positions);

    List<ColorCard> colorCards = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
        byte position = positions.get(i);

        ColorCard card1 = new ColorCard(position);
        card1.type = availableTypes.get(i);
        colorCards.add(card1);

        // Create the matching card
        ColorCard card2 = new ColorCard(positions.get(i + 10));
        card2.type = availableTypes.get(i);
        colorCards.add(card2);
    }
    setColorCards(colorCards);;
}
	@Override
    public void flip(byte position) {
		if(isFaceUp == false){
            isFaceUp = !isFaceUp;
            //colorflipview();//calls the view function for the specific motion for flipping an animal card
        } else{
            isFaceUp = false;
        }
    }
}

class NumberCard extends Card {
	public enum NumberType {
		ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE
	}
	private NumberType type;
	private List<NumberCard> numberCards;
	
    // Constructor
    NumberCard(byte position) {
        super(position);
        this.id = id;
    }
     // Constructor 2
    NumberCard(){
		super();
		this.id = id;
        this.numberCards = numberCards;
	}
    
    @Override
    public String toString() {
        return type.name();
    }
    
    public NumberType getType() {
        return type;
    }
   
    public void setNumberCards(List<NumberCard> numberCards){
		this.numberCards = numberCards;
    }
    public List<NumberCard> getNumberCards(){
		return numberCards;
	}
	// Method overloading
	public NumberCard findCardByPosition(byte position) {
        for (NumberCard card : numberCards) {
            if (card.getPosition() == position) {
                return card;
            }
        }
        return null; // If no matching card is found
    }
	
    public void initializeGame() {
    List<NumberType> availableTypes = Arrays.asList(NumberType.values());
    Collections.shuffle(availableTypes);

    List<Byte> positions = new ArrayList<>();
    for (byte i = 0; i < 20; i++) {
        positions.add(i);
    }
    Collections.shuffle(positions);

    List<NumberCard> numberCards = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
        byte position = positions.get(i);

        NumberCard card1 = new NumberCard(position);
        card1.type = availableTypes.get(i);
        numberCards.add(card1);

        // Create the matching card
        NumberCard card2 = new NumberCard(positions.get(i + 10));
        card2.type = availableTypes.get(i);
        numberCards.add(card2);
    }
    setNumberCards(numberCards);
}
	@Override
    public void flip(byte position) {
		if(isFaceUp == false){
		isFaceUp = !isFaceUp;
        //numberflipview();//calls the view function for the specific motion for flipping an animal card
        } else{
            isFaceUp = false;
        } 
    }
}

