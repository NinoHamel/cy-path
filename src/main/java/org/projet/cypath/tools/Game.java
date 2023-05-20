package org.projet.cypath.tools;

import org.projet.cypath.players.Player;

import java.io.*;
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

    /**
     * Save your game using this method
     * @param board is the board of the game
     * @param playerList is a list of players
     * @throws IOException
     */
    public void save(Board board,List<Player> playerList) throws IOException {
        ObjectOutputStream oOSboard = null;
        ObjectOutputStream oOSplayer = null;
        try {
            new File("src/main/save").mkdir();
            oOSplayer = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File("src/main/save/save.player"))));
            for(Player player:playerList){
                oOSplayer.writeObject(player);
            }
            oOSboard = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File("src/main/save/save.board"))));
            oOSboard.writeObject(board);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (oOSplayer != null) {
                oOSplayer.close();
            }
            if (oOSboard != null) {
                oOSboard.close();
            }
        }
    }

    /**
     * Use the method to save your game in the files src/main/save/save.player and src/main/save/save.board
     * @param board is the board of the game
     * @param listWinners is the list of winners
     * @param listOnGoing is the list of players who are still playing
     * @throws IOException
     */
    public void save(Board board,List<Player> listWinners,List<Player> listOnGoing) throws IOException {
        ObjectOutputStream oOSboard = null;
        ObjectOutputStream oOSplayer = null;
        try {
            new File("src/main/save").mkdir();
            oOSplayer = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File("src/main/save/save.player"))));
            for(Player player:listWinners){
                oOSplayer.writeObject(player);
            }
            for(Player player:listOnGoing){
                oOSplayer.writeObject(player);
            }
            oOSboard = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File("src/main/save/save.board"))));
            oOSboard.writeObject(board);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (oOSplayer != null) {
                oOSplayer.close();
            }
            if (oOSboard != null) {
                oOSboard.close();
            }
        }
    }
    /**
     * Get yhe save of your game using this method
     * @throws IOException
     */
    public void getSave() throws IOException {
        ObjectInputStream oISboard = null;
        ObjectInputStream oISplayer = null;
        try {
            oISboard = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File("src/main/save/save.board"))));
            oISplayer = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File("src/main/save/save.player"))));
            while (true) {
                try {
                    Player player = (Player) oISplayer.readObject();
                    if(player.isVictory()==false){
                        listOnGoing.add(player);
                    }
                    else {
                        listWinners.add(player);
                    }
                } catch (EOFException e) {
                    // La fin du fichier a été atteinte
                    break;
                }
            }
            while (true) {
                try {
                    Board boardSave=(Board) oISboard.readObject();
                    System.out.println(boardSave.displayBoard());
                } catch (EOFException e) {
                    // La fin du fichier a été atteinte
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (oISboard != null) {
                oISboard.close();
            }
            if (oISplayer != null) {
                oISplayer.close();
            }
        }
    }

}
