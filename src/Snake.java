import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * Main class
 */
public class Snake extends JPanel {
    /**
     * variables for the different types a Node can have.
     */
    public static final char empty = 'e', body = 'b', head = 'h', apple = 'a', object = 'o';

    /**
     * true if an is on board, false if not.
     */
    private boolean appleAvailable;

    /**
     * amount of Nodes in x and y direction.
     */
    private final int width = 15, height = 15;

    /**
     * amount of pixels for a tile.
     */
    private int pixels = 20;

    /**
     * position of the head of the snake.
     */
    private int headPositionX, headPositionY;

    /**
     * the actual game board.
     */
    private Node[][] board = new Node[width][height];

    /**
     * This is to avoid while snaking right you can go left and eat yourself.
     */
    private char lastDirection;

    private int getPixels() {
        return pixels;
    }

    /**
     * initialises the board with empty nodes.
     */
    private void initBoard() {
        for (int i = 0; i < width; i++) {
            for (int k = 0; k < height; k++) {
                board[i][k] = new Node();
            }
        }
    }

    /**
     * sets the parameters for a node.
     *
     * @param x         x position of the node.
     * @param y         y position of the node.
     * @param px        x position of the next node (for snake).
     * @param py        y position of the next node (for snake).
     * @param type      type of the node.
     * @param direction direction of the node.
     */
    private void setNodeParameters(int x, int y, int px, int py, char type, char direction) {
        board[y][x].setType(type);
        board[y][x].setDirection(direction);
        board[y][x].setNextX(px);
        board[y][x].setNextY(py);
    }

    /**
     * sets parameters for a snake head.
     *
     * @param x  x position of snake head.
     * @param y  y position of snake head.
     * @param px x position of the next node (for snake).
     * @param py y position of the next node (for snake).
     */
    private void setHead(int x, int y, int px, int py) {
        setNodeParameters(x, y, px, py, head, Node.right);
    }

    /**
     * sets parameters for a snake body.
     *
     * @param x  x position of snake head.
     * @param y  y position of snake head.
     * @param px x position of the next node (for snake).
     * @param py y position of the next node (for snake).
     */
    private void setBody(int x, int y, int px, int py) {
        setNodeParameters(x, y, px, py, body, Node.right);
    }

    /**
     * sets parameters for an apple.
     *
     * @param x x position of apple.
     * @param y y position of apple.
     */
    private void setApple(int x, int y) {
        setNodeParameters(x, y, -1, -1, apple, Node.noDirection);
    }

    /**
     * generates an apple at a random position with an empty node.
     */
    private void generateApple() {
        Random randomNumber = new Random();
        int x, y;
        do {
            x = randomNumber.nextInt(width - 1);
            y = randomNumber.nextInt(height - 1);
        } while (board[y][x].getType() != empty);
        setApple(x, y);
        this.appleAvailable = true;
    }

    /**
     * prints all parts of a snake to the console.
     */
    private void printSnakeToConsole() {
        int x = headPositionX, y = headPositionY;
        int tempx, tempy;
        System.out.println("Snake To Console: ");
        while (x != -1 || y != -1) {
            System.out.println("x = " + x + " y = " + y);
            System.out.println("direction: " + board[y][x].getDirection());
            tempx = board[y][x].getNextX();
            tempy = board[y][x].getNextY();
            x = tempx;
            y = tempy;
        }
        System.out.println("");
    }

    /**
     * initialises the snake with head and 3 body parts.
     */
    private void initSnake() {
        int headX = width / 2, headY = height / 3;
        this.headPositionX = headX;
        this.headPositionY = headY;
        setHead(headX, headY, headX - 1, headY);
        for (int i = 1; i < 4; i++) {
            if (i != 3) {
                setBody(headX - i, headY, headX - i - 1, headY);
            } else {
                setBody(headX - i, headY, -1, -1);
            }
        }
        //printSnakeToConsole();
    }

    /**
     * sets walls on the outside of the board.
     */
    private void setWalls() {
        for (int i = 0; i < width; i++) {
            setNodeParameters(i, 0, -1, -1, object, Node.noDirection);
            setNodeParameters(i, height - 1, -1, -1, object, Node.noDirection);
        }
        for (int k = 1; k < height - 1; k++) {
            setNodeParameters(0, k, -1, -1, object, Node.noDirection);
            setNodeParameters(width - 1, k, -1, -1, object, Node.noDirection);
        }
    }

    /**
     * constructor.
     * initialises the board, snake, walls and generates an apple.
     */
    Snake() {
        this.appleAvailable = false;
        initBoard();
        initSnake();
        setWalls();
        generateApple();
    }

    /**
     * appends a new node to the snake.
     */
    private boolean appendNode() {
        int x = headPositionX, y = headPositionY;
        int tempx = board[y][x].getNextX(), tempy = board[y][x].getNextY();
        while (tempx != -1 || tempy != -1) {
            //System.out.println("x = " + x + " y = " + y);
            //System.out.println("direction: " + board[y][x].getDirection());
            tempx = board[y][x].getNextX();
            tempy = board[y][x].getNextY();
            if (tempx != -1 && tempy != -1) {
                x = tempx;
                y = tempy;
            }
        }

        int newX = x, newY = y;

        System.out.println("position of the new node is: " + newX + ", " + newY);

        boolean appending = true;
        int oldX = newX, oldY = newY;
        char switchDirection = board[y][x].getDirection();
        short counter = 0;

        while (appending) {
            switch (switchDirection) {
                case Node.noDirection:
                    break;
                case Node.right:
                    newX--;
                    break;
                case Node.left:
                    newX++;
                    break;
                case Node.up:
                    newY++;
                    break;
                case Node.down:
                    newY--;
                    break;
            }

            if (board[newY][newX].getType() != empty) {
                newX = oldX;
                newY = oldY;

                switch (switchDirection) {
                    case Node.right:
                        switchDirection = Node.down;
                    case Node.down:
                        switchDirection = Node.left;
                    case Node.left:
                        switchDirection = Node.up;
                    case Node.up:
                        switchDirection = Node.right;
                }
            }
            if (counter > 4) {
                //game over because there is no place to set the tail.
                System.out.println("[GameOver] counter in append node quit the game");
                return false;
            } else {
                appending = false;
            }
            counter++;
        }

        //System.out.println("new body position: " + x + " " + y + " " + newX + " " + newY);

        setBody(newX, newY, -1, -1);
        board[y][x].setNextX(newX);
        board[y][x].setNextY(newY);
        board[newY][newX].setDirection(board[y][x].getDirection());

        //System.out.println("");

        return true;
    }

    /**
     * checks if head will collide with another node.
     *
     * @param x x coordinate of the node to check
     * @param y y coordinate of the node to check
     * @return true if no collision, false if apple or no collision
     */
    private boolean collision(int x, int y) {
        char type = board[y][x].getType();
        if (type == apple) {
            if (!appendNode()) {
                return true;
            }
            this.appleAvailable = false;
        } else if (type != empty) {
            return true;
        }
        return false;
    }

    /**
     * corrects the coordinates to the next part of the snake.
     *
     * @param x  the new x coordinate of the next node.
     * @param y  the new y coordinate of the next node.
     * @param nx the x coordinate of the node to adjust.
     * @param ny the y coordinate of the node to adjust.
     */
    private void correctNext(int x, int y, int nx, int ny) {
        board[ny][nx].setNextX(x);
        board[ny][nx].setNextY(y);
    }

    /**
     * moves a node to its direction
     *
     * @param x     x coordinate of the node to move.
     * @param y     y coordinate of the node to move.
     * @param tempx x of next node -> if -1 end of snake.
     * @param tempy y of next node -> if -1 end of snake.
     * @return false if not possible to move a node, true if everything fine.
     */
    private boolean moveNode(int x, int y, int tempx, int tempy) {
        boolean flag = false;
        int newX = -1, newY = -1;
        if (x == headPositionX && y == headPositionY) {
            flag = true;
        }
        //System.out.println("switch direction: " + board[y][x].getDirection() + "bei x = " + x + " y = " + y);
        switch (board[y][x].getDirection()) {
            case Node.noDirection:
                //System.out.println("didn't move");
                System.out.println("[GameOver] GameOver due to number 0 (no direction)");
                return false;
            case Node.right:
                newX = x + 1;
                newY = y;
                if (collision(x + 1, y)) {
                    System.out.println("[GameOver] GameOver due to number 1 (right)");
                    return false;
                }
                if (flag) {
                    this.headPositionX++;
                }
                //System.out.println("moved right");
                break;
            case Node.left:
                newX = x - 1;
                newY = y;
                if (collision(x - 1, y)) {
                    System.out.println("[GameOver] GameOver due to number 2 (left)");
                    return false;
                }
                if (flag) {
                    this.headPositionX--;
                }
                //System.out.println("moved left");
                break;
            case Node.up:
                newX = x;
                newY = y - 1;
                if (collision(x, y - 1)) {
                    System.out.println("[GameOver] GameOver due to number 3 (up)");
                    return false;
                }
                if (flag) {
                    this.headPositionY--;
                }
                //System.out.println("moved up");
                break;
            case Node.down:
                newX = x;
                newY = y + 1;
                if (collision(x, y + 1)) {
                    System.out.println("[GameOver] GameOver due to number 4 (down)");
                    return false;
                }
                if (flag) {
                    this.headPositionY++;
                }
                //System.out.println("moved down");
                break;
        }
        if (newX != -1 && newY != -1) {
            board[newY][newX] = board[y][x];
            //System.out.println("tempxx = " + tempx + " tempyy = " + tempy);
            if (tempx == -1 && tempy == -1) {
                correctNext(-1, -1, newX, newY);
                //System.out.println("corrected: " + newX + " " + newY + " " + -1 + " " + -1);
            } else {
                correctNext(x, y, newX, newY);
                //System.out.println("corrected: " + newX + " " + newY + " " + x + " " + y);
            }
        }
        board[y][x] = new Node();
        return true;
    }

    /**
     * gives command to moves all nodes to their direction
     *
     * @return false if not possible to move a node, true if everything fine.
     */
    public boolean moveDirection() {
        int x = this.headPositionX, y = this.headPositionY;
        int tempx = -1, tempy = -1, count = 0;
        while (x != -1 && y != -1) {
            tempx = board[y][x].getNextX();
            tempy = board[y][x].getNextY();
            //System.out.println("moved!");
            //System.out.println("x: " + x + " y: " + y);
            //System.out.println("tempx = " + tempx + " tempy = " + tempy);

            if (!moveNode(x, y, tempx, tempy) && x == headPositionX && y == headPositionY) {
                System.out.println("moved node false");
                return false;
            }
            x = tempx;
            y = tempy;

            /*
            count++;
            if (count == 10) {
                System.out.println("count 10 error");
                return false;
            }
            */
        }

        x = this.headPositionX;
        y = this.headPositionY;
        char tempDirection = Node.noDirection;
        char setDirection = board[y][x].getDirection();
        while (x != -1 && y != -1) {
            tempx = board[y][x].getNextX();
            tempy = board[y][x].getNextY();
            if (tempx != -1 && tempy != -1) {
                tempDirection = board[tempy][tempx].getDirection();
                //System.out.println("x = " + x + " y = " + y + " tempx = " + tempx + " tempy = " + tempy);
                //System.out.println("direction: " + board[y][x].getDirection() + " next direction: " + board[tempy][tempy].getDirection());
                board[tempy][tempx].setDirection(setDirection);
            }
            setDirection = tempDirection;
            x = tempx;
            y = tempy;
        }

        //System.out.println("count = " + count + "\n");

        //printSnakeToConsole();

        return true;
    }

    /**
     * paints the board with snake, apple and objects.
     *
     * @param g
     */
    public void paint(Graphics g) {
        for (int i = 0; i < width; i++) {
            for (int k = 0; k < height; k++) {
                switch (board[k][i].getType()) {
                    case empty:
                        g.setColor(Color.WHITE);
                        break;
                    case head:
                        g.setColor(Color.BLUE);
                        break;
                    case body:
                        g.setColor(Color.GREEN);
                        break;
                    case apple:
                        g.setColor(Color.RED);
                        break;
                    case object:
                        g.setColor(Color.BLACK);
                        break;
                }
                if (board[k][i].getType() == apple) {
                    g.setColor(Color.RED);
                }
                g.fillRect(i * pixels, k * pixels, pixels, pixels);
            }
        }

    }

    /**
     * updates the window
     *
     * @param jframe the frame to repaint.
     */
    private void updateWindow(JFrame jframe) {
        jframe.getContentPane().validate();
        jframe.repaint();
    }

    public void gameloop(JFrame frame) {
        updateWindow(frame);

        //add key listener
        frame.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent ke) {
                char key = ke.getKeyChar();
                System.out.println(key);
                switch (key) {
                    case 'a':
                        //System.out.println("Moving left");
                        if (lastDirection == Node.right) {
                            break;
                        }
                        board[headPositionY][headPositionX].setDirection(Node.left);
                        break;
                    case 'd':
                        //System.out.println("Moving right");
                        if (lastDirection == Node.left) {
                            break;
                        }
                        board[headPositionY][headPositionX].setDirection(Node.right);
                        break;
                    case 'w':
                        //System.out.println("Moving up");
                        if (lastDirection == Node.down) {
                            break;
                        }
                        board[headPositionY][headPositionX].setDirection(Node.up);
                        break;
                    case 's':
                        //System.out.println("Moving down");
                        if (lastDirection == Node.up) {
                            break;
                        }
                        board[headPositionY][headPositionX].setDirection(Node.down);
                        break;
                }
            }

            public void keyPressed(KeyEvent ke) {
            }

            public void keyReleased(KeyEvent ke) {

            }
        });

        int delayTime = 300;

        while (true) {
            //System.out.println("Loop started!");
            this.lastDirection = board[headPositionY][headPositionX].getDirection();

            if (!this.appleAvailable) {
                generateApple();
            }

            try {
                Thread.sleep(delayTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!moveDirection()) {
                System.out.println("game over");
                return;
            }

            updateWindow(frame);
        }


    }

    /**
     * main function
     *
     * @param args
     */
    public static void main(String[] args) {
        Snake newGame = new Snake();
        JFrame frame = new JFrame("Snake");
        frame.setSize((newGame.width + 1) * newGame.getPixels(), (newGame.height + 2) * newGame.getPixels());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(newGame);
        frame.setVisible(true);
        newGame.gameloop(frame);
    }
}
