package org.projet.cypath.tools;

import org.projet.cypath.exceptions.InvalidWallException;
import org.projet.cypath.exceptions.OutOfBoardException;
import org.projet.cypath.players.Player;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Player> listWinners;
    private List<Player> listOnGoing;
    private String uniqueId=generateUniqueId();



    private Board board;

    public Game(int numberOfPlayers) throws IOException, OutOfBoardException {
        this.board=new Board(numberOfPlayers);
        if (numberOfPlayers==2){
            Box boxPlayer1 = board.getBox(0, 4);
            Player player1 = new Player(1, ": ONE", "#FF0000", boxPlayer1, board);
            Box boxPlayer2 = board.getBox(8, 4);
            Player player2 = new Player(2, ": TWO", "#0000FF", boxPlayer2, board);
            listOnGoing = new ArrayList<>();
            listOnGoing.add(player1);
            listOnGoing.add(player2);
            listWinners = new ArrayList<>();
        }
        else if (numberOfPlayers==3){
            Box boxPlayer1 = board.getBox(0, 4);
            Player player1 = new Player(1, ": ONE", "#FF0000", boxPlayer1, board);
            Box boxPlayer2 = board.getBox(8, 4);
            Player player2 = new Player(2, ": TWO", "#0000FF", boxPlayer2, board);
            Box boxPlayer3= board.getBox(4, 0);
            Player player3 = new Player(3, ": THREE", "#FFFF00", boxPlayer3, board);
            listOnGoing = new ArrayList<>();
            listOnGoing.add(player1);
            listOnGoing.add(player2);
            listOnGoing.add(player3);
            listWinners = new ArrayList<>();
        }
        else if (numberOfPlayers==4){
            Box boxPlayer1 = board.getBox(0, 4);
            Player player1 = new Player(1, ": ONE", "#FF0000", boxPlayer1, board);
            Box boxPlayer2 = board.getBox(8, 4);
            Player player2 = new Player(2, ": TWO", "#0000FF", boxPlayer2, board);
            Box boxPlayer3= board.getBox(4, 0);
            Player player3 = new Player(3, ": THREE", "#FFFF00", boxPlayer3, board);
            Box boxPlayer4= board.getBox(4, 8);
            Player player4 = new Player(4, ": FOUR", "#008000", boxPlayer4, board);
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
     * gette of the uniqueId
     * @return the uniqueId
     */
    public String getUniqueId() {
        return uniqueId;
    }
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
     * sette of the unique id
     * @param uniqueId the new uniqueId of the parameter
     */
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
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
            new File("src/save").mkdir();
            oOSplayer = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File("src/save/save.player"))));
            for(Player player:playerList){
                oOSplayer.writeObject(player);
            }
            oOSboard = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File("src/save/save.board"))));
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
     * Use the method to save your game in the files src/save/save.player and src/save/save.board
     * @throws IOException
     */
    public void save() throws IOException {
        Board board=this.getBoard();
        ObjectOutputStream oOSboard = null;
        ObjectOutputStream oOSplayer = null;
        try {
            String saveFolderPath = "src/save/" + this.uniqueId;

            Files.createDirectories(Paths.get(saveFolderPath)); // Crée le répertoire de sauvegarde

            // Sauvegarde des joueurs
            oOSplayer = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(saveFolderPath + "/save.player")));
            for (Player player : this.listWinners) {
                oOSplayer.writeObject(player);
            }
            for (Player player : this.listOnGoing) {
                oOSplayer.writeObject(player);
            }

            // Sauvegarde du plateau de jeu
            oOSboard = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(saveFolderPath + "/save.board")));
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
     * Use this method to create a unique id
     * @return
     */
    private String generateUniqueId() {
        String saveFolderPath = "src/save";
        File saveFolder = new File(saveFolderPath);
        int saveCount = 1; // Valeur par défaut si aucun fichier de sauvegarde n'existe

        if (saveFolder.exists() && saveFolder.isDirectory()) {
            File[] saves = saveFolder.listFiles();
            if (saves != null) {
                saveCount = saves.length + 1;
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime now = LocalDateTime.now();
        return String.valueOf(saveCount) + "_" + now.format(formatter);
    }

    /**
     * Get the save of your game using this method
     * Using this method, listWinners,listOngoing and board are going to be initialized if possible according to the save, if it is not possible, nothing will happen.
     * @throws IOException
     */
    public void getSave(String saveId) throws IOException, OutOfBoardException {
        ObjectInputStream oISboard = null;
        ObjectInputStream oISplayer = null;
        //uniqueId=saveId;
        listWinners=new ArrayList<>();
        listOnGoing=new ArrayList<>();
        try {
            //String saveFolderPath = saveId;

            // Récupération des joueurs
            oISplayer = new ObjectInputStream(new BufferedInputStream(new FileInputStream(saveId + "/save.player")));
            while (true) {
                try {
                    Player player = (Player) oISplayer.readObject();
                    if (player.isVictory()) {
                        listWinners.add(player);
                    } else {
                        listOnGoing.add(player);
                    }
                } catch (EOFException e) {
                    // La fin du fichier a été atteinte
                    break;
                }
            }

            // Récupération du plateau de jeu
            oISboard = new ObjectInputStream(new BufferedInputStream(new FileInputStream(saveId + "/save.board")));
            this.board = (Board) oISboard.readObject();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (oISplayer != null) {
                oISplayer.close();
            }
            if (oISboard != null) {
                oISboard.close();
            }
        }
        for(Player player:listOnGoing){
            Box boxPlayer=player.getCurrentBox();
            int colPlayer=boxPlayer.getColumn();
            int rowPlayer=boxPlayer.getRow();
            this.board.getBox(rowPlayer,colPlayer).setHasPlayer(false);
            System.out.println("ok: "+player);
            player.setCurrentBox(this.getBoard().getBox(rowPlayer,colPlayer));
            this.board.getBox(rowPlayer,colPlayer).setHasPlayer(true);
        }
    }

    /**
     * Use the method to delete every single saves from your file using {@link #deleteSave(File)}
     */
    public void deleteAllSaves() {
        String saveFolderPath = "src/save";
        File saveFolder = new File(saveFolderPath);

        if (saveFolder.exists() && saveFolder.isDirectory()) {
            File[] saves = saveFolder.listFiles();
            if (saves != null) {
                for (File save : saves) {
                    deleteSave(save);
                }
            }
        }
    }

    /**
     * Use this method to delete a save from its id using {@link #deleteSave(File)}
     * @param saveId
     */
    public void deleteSaveById(String saveId) {
        String saveFolderPath = "src/save/" + saveId;
        File saveFolder = new File(saveFolderPath);

        if (saveFolder.exists() && saveFolder.isDirectory()) {
            deleteSave(saveFolder);
        }
    }
    /**
     * Delete every file from a directory or a file recursively
     * @param file a directory or a file
     */
    private void deleteSave(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File child : files) {
                    deleteSave(child);
                }
            }
        }
        file.delete();
        // Supprime le répertoire parent uniquement s'il est vide
        if (file.getParentFile() != null && file.getParentFile().isDirectory() && file.getParentFile().listFiles().length == 0) {
            file.getParentFile().delete();
        }
    }
    /**
     * Method to remove a player from listOnGoing and add him to listWinners
     * @param player
     */
    public void ifWinner(Player player){
        if(player.getVictoryBoxes().contains(player.getCurrentBox())){
            listOnGoing.remove(player);
            listWinners.add(player);
        }
    }

    /**
     * Check if the game is over and change the player lists in this case
     * @return true or false if the game is over
     */
    public boolean isGameOver(){
        if(listOnGoing.size() == 1){
            listWinners.add(listOnGoing.get(0));
            listOnGoing.remove(0);
            return true;
        }
        return false;
    }
    /**
     *
     * @param currentRow row of the fist wall
     * @param currentCol column of the first wall
     * @param  orientation is the orientation of the box, 0 for bottom and 1 for right
     * @return boolean if there is a path or not
     * @throws OutOfBoardException
     */
    public Boolean hasPath(int currentRow,int currentCol,int orientation) throws OutOfBoardException {
        if (orientation == 0) {
            this.getBoard().setBottomWall(currentRow,currentCol,true);
            for (Player player : listOnGoing) {
                if (!board.hasPath(player)) {
                    board.setBottomWall(currentRow,currentCol,false);
                    return false;
                }
            };
            board.setBottomWall(currentRow,currentCol,false);
        }
        else {
            board.setRightWall(currentRow, currentCol,true);
            for (Player player : listOnGoing) {
                if (!board.hasPath(player)) {
                    board.setRightWall(currentRow,currentCol,false);
                    return false;
                }
            }
            board.setRightWall(currentRow,currentCol,false);
        }
        return true;
    }

}
