import java.util.*;

public class Main {
    public static void main(String[] args) {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        View gameInterface = new View();
        GamePlay game = new GamePlay(player1, player2, gameInterface);
        game.requestNames();
        gameInterface.setVisible(true);
    }
}
