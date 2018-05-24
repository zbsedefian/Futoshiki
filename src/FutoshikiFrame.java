import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FutoshikiFrame extends JFrame {

    private JTextField[][] userPuzzleInput;
    private JLabel[][] labels;

    public FutoshikiFrame() {

        GenerateFutoshiki fs = new GenerateFutoshiki(3, 1);

        String[][] puzzle = fs.getPuzzle();
        int puzzleSize = fs.getPuzzleSize();

        fs.printBoard();

        labels = new JLabel[puzzleSize][puzzleSize];
        userPuzzleInput = new JTextField[puzzleSize][puzzleSize];

        JPanel puzzlePanel = new JPanel();
        puzzlePanel.setLayout(new GridLayout(puzzleSize, puzzleSize));

        for(int i = 0; i < puzzleSize; i++) {
            for(int j = 0; j < puzzleSize; j++) {
                if (puzzle[i][j].equals("x")) {
                    if (i % 2 == 0 && j % 2 == 0) {
                        JTextField textField = new JTextField();
                        textField.setFont(new Font("Courier New", Font.BOLD, 24));
                        textField.setHorizontalAlignment(JTextField.CENTER);
                        puzzlePanel.add(textField);

                        userPuzzleInput[i][j] = textField;
                    } else {
                        labels[i][j] = new JLabel(" ", JLabel.CENTER);
                        puzzlePanel.add(labels[i][j]);

                        JTextField textField = new JTextField("x");
                        userPuzzleInput[i][j] = textField;
                    }

                }
                else { //insert labels if not blank
                    labels[i][j] = new JLabel(puzzle[i][j], JLabel.CENTER);
                    labels[i][j].setBackground(Color.black);
                    labels[i][j].setFont(new Font("Courier New", Font.BOLD, 24));
                    puzzlePanel.add(labels[i][j]); //contents.add(labels[i][j]);
                    JTextField textField = new JTextField(puzzle[i][j]);
                    userPuzzleInput[i][j] = textField;
                }

            }
        }

        JButton exitButton = new JButton("Exit");
        JButton resetButton = new JButton("Reset");
        JButton submitButton = new JButton("Submit");

        JPanel optionPanel = new JPanel();
        optionPanel.add(exitButton);
        optionPanel.add(resetButton);
        optionPanel.add(submitButton);

        add(puzzlePanel, BorderLayout.CENTER);
        add(optionPanel, BorderLayout.PAGE_END);

        exitButton.addActionListener(e -> dispose());

        resetButton.addActionListener(e -> {
            for (JTextField[] textFieldArray : userPuzzleInput)
                for (JTextField textField : textFieldArray)
                    textField.setText("");
        });

        submitButton.addActionListener(e -> {
            boolean isSolution = true;
            String[][] solution = fs.getSolution();
            for (int i = 0; i < userPuzzleInput.length; i++)
                for (int j = 0; j < userPuzzleInput[0].length; j++)
                    if (i%2==0 && j%2==0)
                        if (!solution[i][j].equals(userPuzzleInput[i][j].getText()))
                            isSolution = false;
            if (isSolution)
                JOptionPane.showMessageDialog(null,"CORRECT! Great job.");
            else
                JOptionPane.showMessageDialog(null,"Incorrect solution.");
        });

        setTitle("Futoshiki");
        setSize(450,450);
        setMinimumSize(new Dimension(350, 350));
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        FutoshikiFrame frame = new FutoshikiFrame();
    }
}
