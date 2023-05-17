package org.projet.cypath.tools;

import javafx.geometry.Pos;
import org.projet.cypath.exceptions.OutOfBoardException;

public class Box {

    Position position;
    int x= position.getX();
    int y= position.getY();

    @Override
    public String toString() {
        return "Box{" +
                "position=" + position +
                ", x="+ x +
                ", y="+ y +
                ", leftWall=" + leftWall +
                ", rightWall=" + rightWall +
                ", topWall=" + topWall +
                ", bottomWall=" + bottomWall +
                '}';
    }

    private boolean leftWall;
    private boolean rightWall;
    private boolean topWall;
    private boolean bottomWall;
    private boolean player;

    public Box(Position position) {
        this.position = position;
    }
    public Box(int x, int y) throws OutOfBoardException {
        this.position=new Position(x,y);
    }

    public boolean hasLeftWall() {
        return leftWall;
    }

    public void setLeftWall(boolean leftWall) {
        this.leftWall = leftWall;
    }

    public boolean hasRightWall() {
        return rightWall;
    }

    public void setRightWall(boolean rightWall) {
        this.rightWall = rightWall;
    }

    public boolean hasTopWall() {
        return topWall;
    }

    public void setTopWall(boolean topWall) {
        this.topWall = topWall;
    }

    public boolean hasBottomWall() {
        return bottomWall;
    }

    public void setBottomWall(boolean bottomWall) {
        this.bottomWall = bottomWall;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position getPosition() {
        return position;
    }

    public boolean hasPlayer() {
        return player;
    }

    public void setHasPlayer(boolean hasPlayer) {
        this.player = hasPlayer;
    }
}
