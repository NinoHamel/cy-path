package org.projet.cypath.tools;

import org.projet.cypath.players.Player;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Player> listWinners;
    private List<Player> listOnGoing;
    
    public Game(Player player1, Player player2){
        listOnGoing = new ArrayList<>();
        listOnGoing.add(player1);
        listOnGoing.add(player2);
        listWinners = new ArrayList<>();
    }

    public Game(Player player1, Player player2, Player player3){
        this(player1,player2);
        listOnGoing.add(player3);
    }

    public Game(Player player1, Player player2, Player player3, Player player4){
        this(player1,player2,player3);
        listOnGoing.add(player4);
    }

    //Getter
    /**
     * getter of the list of winners
     *
     * @return list of winners from first to last
     */
    public List<Player> getListWinners() {
        return listWinners;
    }
    /**
     * getter of the list of players
     *
     * @return list of players who are still playing
     */
    public List<Player> getListOnGoing() {
        return listOnGoing;
    }


    /**
     *add the winner in listWinners and remove the player from listOnGoing
     * @param player is for the player who won
     */
    public void addPlayerListWinners(Player player) {
        listWinners.add(player);
        removePlayerListOnGoing(player);
    }
    /**
     * remove the player from listOnGoing
     * @param player
     */
    public void removePlayerListOnGoing(Player player) {
        listOnGoing.remove(player);
    }

    public static void combat(List<Player> listOnGoing){
        while (listOnGoing.size()>1){
            //TODO
        }
    }

}
