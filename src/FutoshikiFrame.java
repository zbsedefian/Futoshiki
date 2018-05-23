import javax.swing.*;
import java.awt.*;

public class FutoshikiFrame extends JFrame {

    private Container contents;
    private JTextField[][] userPuzzleInput;
    private JLabel[][] labels;

    public FutoshikiFrame() {

        GenerateFutoshiki fs = new GenerateFutoshiki(5, 2);

        String[][] puzzle = fs.getPuzzle();
        int puzzleSize = fs.getPuzzleSize();

        labels = new JLabel[puzzleSize][puzzleSize];
        userPuzzleInput = new JTextField[puzzleSize][puzzleSize];

        contents = getContentPane();
        contents.setLayout(new GridLayout(puzzleSize, puzzleSize));

        for(int i = 0; i < puzzleSize; i++) {
            for(int j = 0; j < puzzleSize; j++) {
                if (puzzle[i][j].equals("x")) {
                    if (i % 2 == 0 && j % 2 == 0) {
                        JTextField textField = new JTextField();
                        textField.setFont(new Font("Courier New", Font.BOLD, 24));
                        textField.setHorizontalAlignment(JTextField.CENTER);
                        contents.add(textField);
                        userPuzzleInput[i][j] = textField;
                    } else {
                        labels[i][j] = new JLabel(" ", JLabel.CENTER);
                        contents.add(labels[i][j]);
                    }

                }
                else { //insert labels if not blank
                    labels[i][j] = new JLabel(puzzle[i][j], JLabel.CENTER);
                    labels[i][j].setBackground(Color.black);
                    labels[i][j].setFont(new Font("Courier New", Font.BOLD, 24));
                    contents.add(labels[i][j]);
                }

            }
        }

//        JButton resetButton = new JButton("Reset");
//        JButton exitButton = new JButton("Exit");
//        JPanel resetExitPanel = new JPanel();
//        resetExitPanel.add(resetButton);
//        resetExitPanel.add(exitButton);
//        contents.add(resetExitPanel);
//        JPanel numberPanel = new JPanel();
//        SpinnerNumberModel model = new SpinnerNumberModel(9.9, 1, 15, 1);
//        JSpinner spinner = new JSpinner(model);
//        numberPanel.add(spinner);
//
        //add(contents, BorderLayout.CENTER);
        //add(resetExitPanel, BorderLayout.PAGE_END);

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
