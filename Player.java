class Player {
    private String name;
    private byte numberOfMatches; // count matches made in current round
    private boolean myTurn;
    private int roundCount; // count rounds won

    public Player(String name) {
        this.name = name;
        this.numberOfMatches = 0;
        this.myTurn = false;
        this.roundCount = 0;
        
    }

    public String getName() {
        return name;
    }
    
    public void setName(String newName) {
        this.name = newName;
    }

    public byte getNumberOfMatches() {
        return numberOfMatches;
    }

    public void resetNumberOfMatches() {
        numberOfMatches = 0;
    }

    public void incrementMatches() {
        numberOfMatches++;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }
     public void hasWon(){
        System.out.println("Congratulations " + this.getName() + ", you've won this round");
        roundCount++;
    }
    public int getRoundCount() {
        return roundCount;
    }
    public void resetRoundCount() {
        roundCount = 0;
    }
}
