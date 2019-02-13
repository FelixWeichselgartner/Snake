

public class Node {
    public static final char up = 'u';
    public static final char down = 'd';
    public static final char left = 'l';
    public static final char right = 'r';
    public static final char noDirection = 'n';

    private char type;
    private char direction;

    private int thisX, thisY;
    private int nextX, nextY;

    public int getNextX() {
        return nextX;
    }

    public void setNextX(int nextX) {
        this.nextX = nextX;
    }

    public int getNextY() {
        return nextY;
    }

    public void setNextY(int nextY) {
        this.nextY = nextY;
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public int getThisX() {
        return thisX;
    }

    public void setThisX(int thisX) {
        this.thisX = thisX;
    }

    public int getThisY() {
        return thisY;
    }

    public void setThisY(int thisY) {
        this.thisY = thisY;
    }

    Node() {
        this.type = Snake.empty;
        this.direction = noDirection;
    }

    Node(char type, char direction) {
        this.type = type;
        this.direction = direction;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }
}
