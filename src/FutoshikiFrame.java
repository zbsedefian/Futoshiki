import javax.swing.*;
import java.awt.*;

public class FutoshikiFrame extends JFrame {

    private Container contents;
    private JButton[][] squares;

    public FutoshikiFrame() {

        GenerateFutoshiki fs = new GenerateFutoshiki(5, 2);
        int boardsize = fs.getBoardSize();
        squares = new JButton[boardsize][boardsize];

        contents = getContentPane();
        contents.setLayout(new GridLayout(boardsize, boardsize));

        //ButtonHandler buttonHandler = new ButtonHandler();

        for(int i = 0; i < boardsize; i++) {
            for(int j = 0; j < boardsize; j++) {
                squares[i][j] = new JButton();
                contents.add(squares[i][j]);
                //ksquares[i][j].addActionListener(buttonHandler);
            }
        }


//        JPanel numberPanel = new JPanel();
//        SpinnerNumberModel model = new SpinnerNumberModel(9.9, 1, 15, 1);
//        JSpinner spinner = new JSpinner(model);
//        numberPanel.add(spinner);
//
//        add(numberPanel, BorderLayout.PAGE_START);

        setTitle("Futoshiki");
        setSize(800,600);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);


    }

    public static void main(String[] args) {
        FutoshikiFrame frame = new FutoshikiFrame();
    }
}
