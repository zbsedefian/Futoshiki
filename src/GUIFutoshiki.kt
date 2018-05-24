import javax.swing.*
import java.awt.*

class GUIFutoshiki : JFrame()
{
    private val userPuzzleInput: Array<Array<JTextField>>
    private val labels: Array<Array<JLabel>>

    init
    {
        val fs = GenerateFutoshiki(5, 2)
        val puzzle = fs.getPuzzle()
        val puzzleSize = fs.getPuzzleSize()

        fs.printSolution()
        labels = Array(puzzleSize) {Array(puzzleSize) {JLabel("")}}
        userPuzzleInput = Array(puzzleSize) {Array(puzzleSize) {JTextField("")}}

        val puzzlePanel = JPanel()
        puzzlePanel.layout = GridLayout(puzzleSize, puzzleSize)

        for (i in 0 until puzzleSize)
        {
            for (j in 0 until puzzleSize)
            {
                if (puzzle[i][j] == "x")
                {
                    if (i % 2 == 0 && j % 2 == 0)
                    {
                        val textField = JTextField()
                        textField.font = Font("Courier New", Font.BOLD, 24)
                        textField.horizontalAlignment = JTextField.CENTER
                        puzzlePanel.add(textField)

                        userPuzzleInput[i][j] = textField
                    }
                    else
                    {
                        labels[i][j] = JLabel(" ", JLabel.CENTER)
                        puzzlePanel.add(labels[i][j])

                        val textField = JTextField("x")
                        userPuzzleInput[i][j] = textField
                    }

                }
                else
                {   //insert labels if not blank
                    labels[i][j] = JLabel(puzzle[i][j], JLabel.CENTER)
                    labels[i][j].background = Color.black
                    labels[i][j].font = Font("Courier New", Font.BOLD, 24)
                    puzzlePanel.add(labels[i][j]) //contents.add(labels[i][j]);
                    val textField = JTextField(puzzle[i][j])
                    userPuzzleInput[i][j] = textField
                }

            }
        }

        val exitButton = JButton("Exit")
        val resetButton = JButton("Reset")
        val submitButton = JButton("Submit")

        val optionPanel = JPanel()
        optionPanel.add(exitButton)
        optionPanel.add(resetButton)
        optionPanel.add(submitButton)

        add(puzzlePanel, BorderLayout.CENTER)
        add(optionPanel, BorderLayout.PAGE_END)

        exitButton.addActionListener { dispose() }

        resetButton.addActionListener {
            for (textFieldArray in userPuzzleInput)
                for (textField in textFieldArray)
                    textField.text = ""
        }

        submitButton.addActionListener {
            var isSolution = true
            val solution = fs.getSolution()
            for (i in 0 until userPuzzleInput.size)
                for (j in 0 until userPuzzleInput[0].size)
                    if (i % 2 == 0 && j % 2 == 0)
                        if (solution[i][j] != userPuzzleInput[i][j].text.trim())
                            isSolution = false
            if (isSolution)
                JOptionPane.showMessageDialog(null, "CORRECT! Great job.")
            else
                JOptionPane.showMessageDialog(null, "Incorrect solution.")
        }

        title = "Futoshiki"
        setSize(450, 450)
        minimumSize = Dimension(350, 350)
        isResizable = true
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isVisible = true
    }

}

fun main(args: Array<String>)
{
    GUIFutoshiki()
}