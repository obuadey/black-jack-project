package org.example;


import org.example.domains.Game;

import java.util.Arrays;

public class MainApp {
    public static void main(String... args)  {
        Game game = new Game(args);
//        game.promptUser();
        Arrays.stream(args).forEach(System.out::println);
    }

}

