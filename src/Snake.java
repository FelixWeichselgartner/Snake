import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Snake extends JPanel {
    public static final char empty = 'e';
    public static final char body = 'b';
    public static final char head = 'h';
    public static final char apple = 'a';
    public static final char object = 'o';

    private int GameState;

    public final int width = 35, height = 35;

    int pixels = 20;

    private int headPositionX, headPositionY;

    public Node[][] board = new Node[width][height];

    public int getPixels() {
        return pixels;
    }

    public void setPixels(int pixels) {
        this.pixels = pixels;
    }

    public void initBoard() {
        for (int i = 0; i < width; i++) {
            for (int k = 0; k < height; k++) {
                board[i][k] = new Node();
            }
        }
    }

    public void setNodeParameters(int x, int y, int px, int py, char type, char direction) {
        board[y][x].setType(type);
        board[y][x].setDirection(direction);
        board[y][x].setThisX(x);
        board[y][x].setThisY(y);
        board[y][x].setNextX(px);
        board[y][x].setNextY(py);
    }

    public void setHead(int x, int y, int px, int py) {
        setNodeParameters(x, y, px, py, head, Node.right);
    }

    public void setBody(int x, int y, int px, int py) {
        setNodeParameters(x, y, px, py, body, Node.right);
    }

    public void setApple(int x, int y) {
        setNodeParameters(x, y, -1, -1, apple, Node.noDirection);
    }

    public void initSnake() {
        int headX = 10, headY = 10;
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
    }

    public void setWalls() {
        for (int i = 0; i < width; i++) {
            setNodeParameters(i, 0, -1, -1, object, Node.noDirection);
            setNodeParameters(i, height - 1, -1, -1, object, Node.noDirection);
        }
        for (int k = 1; k < height - 1; k++) {
            setNodeParameters(0, k, -1, -1, object, Node.noDirection);
            setNodeParameters(width - 1, k, -1, -1, object, Node.noDirection);
        }
    }

    Snake() {
        this.GameState = 0;
        initBoard();
        initSnake();
        setWalls();
    }

    boolean collision(int x, int y) {
        if (board[y][x].getType() != empty) {
            return true;
        }
        return false;
    }

    public boolean moveNode(int x, int y) {
        char direction = board[y][x].getDirection();
        switch (direction) {
            case Node.noDirection:
                break;
            case Node.right:
                if (collision(x + 1, y)) {
                    System.out.println("1");
                    return false;
                }
                board[y][x + 1] = board[y][x];
                break;
            case Node.left:
                if (collision(x - 1, y)) {
                    System.out.println("2");
                    return false;
                }
                board[y][x - 1] = board[y][x];
                break;
            case Node.up:
                if (collision(x, y - 1)) {
                    System.out.println("3");
                    return false;
                }
                board[y - 1][x] = board[y][x];
                break;
            case Node.down:
                if (collision(x, y + 1)) {
                    System.out.println("4");
                    return false;
                }
                board[y + 1][x] = board[y][x];
                break;
        }
        return true;
    }

    public boolean moveDirection() {
        int x = this.headPositionX, y = this.headPositionY;
        int tempx, tempy;
        int count = 0;
        while (board[y][x].getNextX() != -1 || board[y][x].getNextY() != -1) {
            System.out.println("moved!");
            System.out.println("x: " + x + " y: " + y);
            System.out.println("nextx: " + board[y][x].getNextX() + " nexty: " + board[y][x].getNextY());
            if (!moveNode(x, y) && count == 0) {
                return false;
            }
            board[board[y][x].getNextX()][board[y][x].getNextY()].setDirection(board[y][x].getDirection());
            tempx = board[y][x].getNextX();
            tempy = board[y][x].getNextY();
            if (tempx == -1 && tempy == -1) {
                board[tempy][tempx] = new Node();
                return false;
            }
            x = tempx;
            y = tempy;
            count++;
            if (count == 10) {
                return false;
            }
        }
        return true;
    }

    /**
     * paints the pipes, bird and the score
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
     * @param JFrame frame to update
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
                        System.out.println("Moving left");
                        board[headPositionY][headPositionX].setDirection(Node.left);
                        break;
                    case 'd':
                        System.out.println("Moving right");
                        board[headPositionY][headPositionX].setDirection(Node.right);
                        break;
                    case 'w':
                        System.out.println("Moving up");
                        board[headPositionY][headPositionX].setDirection(Node.up);
                        break;
                    case 's':
                        System.out.println("Moving down");
                        board[headPositionY][headPositionX].setDirection(Node.down);
                        break;
                }
            }

            public void keyPressed(KeyEvent ke) {
            }

            public void keyReleased(KeyEvent ke) {

            }
        });

        int delayTime = 500;

        while (true) {
            System.out.println("Loop started!");
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

    public static void main(String[] args) {
        Snake newGame = new Snake();
        JFrame frame = new JFrame("Snake");
        frame.setSize((newGame.width + 1) * newGame.getPixels(), (newGame.height + 3) * newGame.getPixels());
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(newGame);
        frame.setVisible(true);
        newGame.gameloop(frame);
    }
}
