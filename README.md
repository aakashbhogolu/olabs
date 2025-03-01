import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SnakeGame extends JPanel implements ActionListener {
    private final int TILE_SIZE = 20;
    private final int WIDTH = 400, HEIGHT = 400;
    private Timer timer;
    private int snakeX = WIDTH / 2, snakeY = HEIGHT / 2;
    private int directionX = 0, directionY = TILE_SIZE;
    private boolean gamePaused = false;
    private boolean puzzleOpened = false;

    public SnakeGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (!gamePaused) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP -> { directionX = 0; directionY = -TILE_SIZE; }
                        case KeyEvent.VK_DOWN -> { directionX = 0; directionY = TILE_SIZE; }
                        case KeyEvent.VK_LEFT -> { directionX = -TILE_SIZE; directionY = 0; }
                        case KeyEvent.VK_RIGHT -> { directionX = TILE_SIZE; directionY = 0; }
                    }
                }
            }
        });
        timer = new Timer(200, this); // Increased delay to 200 milliseconds (slower speed)
        timer.start();
        System.out.println("Timer started with delay: " + timer.getDelay() + " ms"); // Debugging
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gamePaused) {
            snakeX += directionX;
            snakeY += directionY;

            // Boundary checks
            if (snakeX < 0 && !puzzleOpened) { openPuzzle("puzzle-level-1"); }
            if (snakeX >= WIDTH && !puzzleOpened) { openPuzzle("puzzle-level-2"); }
            if (snakeY < 0 && !puzzleOpened) { openPuzzle("puzzle-level-3"); }
            if (snakeY >= HEIGHT && !puzzleOpened) { openPuzzle("puzzle-level-4"); }

            repaint();
            System.out.println("Snake moved to: (" + snakeX + ", " + snakeY + ")"); // Debugging
        }
    }

    private void openPuzzle(String level) {
        gamePaused = true;
        puzzleOpened = true;
        System.out.println("Puzzle opened: " + level); // Debugging
        new Puzzle(level, this);
    }

    public void resumeGame() {
        gamePaused = false;
        puzzleOpened = false;
        System.out.println("Game resumed"); // Debugging
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GREEN);
        g.fillRect(snakeX, snakeY, TILE_SIZE, TILE_SIZE);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        SnakeGame game = new SnakeGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
