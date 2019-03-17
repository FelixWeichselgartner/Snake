/**
 * Represents one tile of the board.
 */
public class Node {
    /**
     * constants for the different directions.
     */
    public static final char up = 'u', down = 'd', left = 'l', right = 'r', noDirection = 'n';

    /**
     * attributes for the type and the current direction.
     */
    private char type, direction;

    /**
     * attributes used for emulating a linked list in C.
     * instead of pointers to the next body part these are coordinates.
     */
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
