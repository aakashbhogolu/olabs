import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class Puzzle extends JFrame implements ActionListener {
    private ArrayList<JButton> buttons = new ArrayList<>();
    private ArrayList<ImageIcon> originalImages = new ArrayList<>();
    private JPanel panel;
    private String levelPath;
    private JButton firstButton, secondButton;
    private boolean firstClick = false;
    private SnakeGame snakeGame;

    public Puzzle(String levelPath, SnakeGame snakeGame) {
        this.levelPath = levelPath;
        this.snakeGame = snakeGame;
        setTitle("Picture Puzzle - " + levelPath);
        loadImages();
        initUI();
    }

    // ✅ Load images and shuffle them
    private void loadImages() {
        for (int i = 1; i <= 9; i++) {
            originalImages.add(new ImageIcon(levelPath + "/" + i + ".jpg"));
        }
        Collections.shuffle(originalImages);
    }

    private void initUI() {
        panel = new JPanel(new GridLayout(3, 3));
        for (int i = 0; i < 9; i++) {
            JButton button = new JButton();
            button.setIcon(originalImages.get(i)); // ✅ Assign shuffled image
            button.putClientProperty("originalIndex", i);
            button.addActionListener(this);
            buttons.add(button);
            panel.add(button);
        }

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        if (!firstClick) {
            firstButton = clickedButton;
            firstClick = true;
        } else {
            secondButton = clickedButton;
            swapImages();
            firstClick = false;
            if (checkWin()) {
                showCompletionImage();
            }
        }
    }

    private void swapImages() {
        Icon temp = firstButton.getIcon();
        firstButton.setIcon(secondButton.getIcon());
        secondButton.setIcon(temp);
    }

    private boolean checkWin() {
        for (int i = 0; i < 9; i++) {
            ImageIcon currentIcon = (ImageIcon) buttons.get(i).getIcon();
            ImageIcon correctIcon = new ImageIcon(levelPath + "/" + (i + 1) + ".jpg");
            if (!currentIcon.getDescription().equals(correctIcon.getDescription())) {
                return false;
            }
        }
        return true;
    }

    private void showCompletionImage() {
        JFrame frame = new JFrame("Level Completed!");
        JLabel label = new JLabel(new ImageIcon(levelPath + "/complete.jpg"));
        frame.add(label);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // ✅ User must manually close
        frame.setVisible(true);

        // ✅ Close the puzzle window and wait for user to close completion image
        this.dispose();
    }
}
