package org.projet.cypath.tools;

import javafx.print.PageLayout;
import org.projet.cypath.players.Player;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        try {

            Board board = new Board(2);
            Player player1 = new Player(1, "Test1", "Red", board.getBox(0,4), board);
            Player player2 = new Player(2, "Test2", "Blue", board.getBox(8,4), board);
            List<Player> players = new ArrayList<>();
            players.add(player1);
            players.add(player2);
            board.setBottomWall(board.getBox(0, 4));
            board.setRightWall(board.getBox(0, 5));
            board.setRightWall(board.getBox(0, 3));
            System.out.println(board.displayBoard());
            System.out.println(board.hasPathAll(players));
            //board.setBottomWall();
        }catch (Exception e){
            System.out.println(e);
        }
    }

}
