package org.projet.cypath.tools;

import org.projet.cypath.exceptions.OutOfBoardException;
import org.projet.cypath.players.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Player> listWinners;
    private List<Player> listOnGoing;



    private Board board;

    public Game(int numberOfPlayers) throws IOException, OutOfBoardException {
        this.board=new Board(numberOfPlayers);
        if (numberOfPlayers==2){
            Box boxPlayer1 = board.getBox(0, 4);
            Player player1 = new Player(1, "1", "#FF0000", boxPlayer1, board);
            Box boxPlayer2 = board.getBox(8, 4);
            Player player2 = new Player(2, "2", "#0000FF", boxPlayer2, board);
            listOnGoing = new ArrayList<>();
            listOnGoing.add(player1);
            listOnGoing.add(player2);
            listWinners = new ArrayList<>();
        }
        if (numberOfPlayers==3){
            Box boxPlayer1 = board.getBox(0, 4);
            Player player1 = new Player(1, "1", "#FF0000", boxPlayer1, board);
            Box boxPlayer2 = board.getBox(8, 4);
            Player player2 = new Player(2, "2", "#0000FF", boxPlayer2, board);
            Box boxPlayer3= board.getBox(4, 0);
            Player player3 = new Player(3, "3", "##FFFF00", boxPlayer3, board);
            listOnGoing = new ArrayList<>();
            listOnGoing.add(player1);
            listOnGoing.add(player2);
            listOnGoing.add(player3);
            listWinners = new ArrayList<>();
        }
        if (numberOfPlayers==4){
            Box boxPlayer1 = board.getBox(0, 4);
            Player player1 = new Player(1, "1", "#FF0000", boxPlayer1, board);
            Box boxPlayer2 = board.getBox(8, 4);
            Player player2 = new Player(2, "2", "#0000FF", boxPlayer2, board);
            Box boxPlayer3= board.getBox(4, 0);
            Player player3 = new Player(3, "3", "#FFFF00", boxPlayer3, board);
            Box boxPlayer4= board.getBox(4, 8);
            Player player4 = new Player(4, "4", "#008000", boxPlayer4, board);
            listOnGoing = new ArrayList<>();
            listOnGoing.add(player1);
            listOnGoing.add(player2);
            listOnGoing.add(player3);
            listOnGoing.add(player4);
            listWinners = new ArrayList<>();
        }
        else {
            throw new OutOfBoardException("The player number must be between 2 and 4.");
        }
    }
    //Getter

    /**
     * getter of board
     * @return the board of the game
     */
    public Board getBoard() {
        return board;
    }
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
        player.setVictory(true);
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
     * @throws IOException
     */
    public void save(Board board) throws IOException {
        ObjectOutputStream oOSboard = null;
        ObjectOutputStream oOSplayer = null;
        try {
            new File("src/main/save").mkdir();
            oOSplayer = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File("src/main/save/save.player"))));
            for(Player player:this.listWinners){
                oOSplayer.writeObject(player);
            }
            for(Player player:this.listOnGoing){
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
     * Get the save of your game using this method
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
                    this.board=boardSave;
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

    public void ifWinner(Player player){
        if(player.getVictoryBoxes().contains(player.getCurrentBox())){
            listOnGoing.remove(player);
            listWinners.add(player);
        }
    }

    public boolean isGameOver(){
        if(listOnGoing.size() == 1){
            listWinners.add(listOnGoing.get(0));
            listOnGoing.remove(0);
            return true;
        }
        return false;
    }

}
