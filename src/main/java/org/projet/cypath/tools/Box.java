package org.projet.cypath.tools;

public class Box {
    private final int x;
    private final int y;

    @Override
    public String toString() {
        return "Box{" +
                "x=" + x +
                ", y=" + y +
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

    public Box(int x, int y) {
        this.x = x;
        this.y = y;
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

    public boolean isHasPlayer() {
        return player;
    }

    public void setHasPlayer(boolean hasPlayer) {
        this.player = hasPlayer;
    }
}
