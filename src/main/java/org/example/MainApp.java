package org.example;


import org.example.domains.Game;

public class MainApp {
    public static void main(String... args)  {
<<<<<<< Updated upstream
        Game game = new Game();

=======
        Game game = new Game(args);
        game.processCommandArgs();
        game.createDeck();
>>>>>>> Stashed changes
    }

}

