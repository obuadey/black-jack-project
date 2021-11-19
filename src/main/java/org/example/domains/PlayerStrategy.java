package org.example.domains;

import org.example.domains.enums.Status;

import java.util.ArrayList;
import java.util.List;

public class PlayerStrategy {
    private List<Player> bustedPlayers = new ArrayList<>();

    public List<Player> getBustedPlayers() {
        return bustedPlayers;
    }

    public void defaultStrategy(Player player){
        if (player.getScore() < 17){
            player.setStatus(Status.HIT);
            System.out.println(player.getName() + " requests for hit" );
        }
        else if (player.getScore() >=17 && player.getScore() <= 21){
            player.setStatus(Status.STICK);
            System.out.println(player.getName() + " requests for stick" );
        }
        else {
            player.setStatus(Status.BUSTED);
            bustedPlayers.add(player);
            System.out.println(player.getName() + " has been busted" );
        }
    }

    public void alwaysStickStrategy(Player player) {
        player.setStatus(Status.STICK);
        if (player.getScore() > 21) {
            player.setStatus(Status.BUSTED);
            bustedPlayers.add(player);
            System.out.println(player.getName() + " has been busted");
        }
    }

    public void alwaysHitStrategy(Player player){
        if (player.getScore() <= 21){
            player.setStatus(Status.HIT);
            System.out.println(player.getName() + " requests for hit" );
        }
        else {
            player.setStatus(Status.BUSTED);
            bustedPlayers.add(player);
            System.out.println(player.getName() + " has been busted" );
        }
    }

    public void riskCalculatortrategy(Player player){

    }
}
