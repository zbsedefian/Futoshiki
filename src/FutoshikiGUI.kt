import java.awt.Font
import java.awt.Color
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JLabel
import javax.swing.WindowConstants
import javax.swing.JButton
import javax.swing.JTextField
import javax.swing.JPanel
import javax.swing.JFrame
import java.awt.GridLayout
import javax.swing.JOptionPane

class FutoshikiGUI(
        boardSize: Int = 5,
        difficulty: Int = 2,
        solution: Array<Array<String>> = emptyArray(),
        solve: Boolean = false
        ) : JFrame()
{
    init
    {
        val fs = Futoshiki(boardSize, difficulty)
        val puzzle: Array<Array<String>>

        // Used when "Solve" button is pressed
        puzzle = if (solve)
        {
            solution.copyOf()
        }
        else
        {
            fs.getPuzzle()
        }

        val puzzleSize = puzzle.size
        val labels = Array(puzzleSize) {Array(puzzleSize) {JLabel("")}}
        val userPuzzleInput = Array(puzzleSize) {Array(puzzleSize) {JTextField("")}}
        val puzzlePanel = generatePuzzlePanel(puzzle, labels, userPuzzleInput)

        add(puzzlePanel, BorderLayout.CENTER)

        // Buttons
        val resetButton = JButton("Reset")
        val submitButton = JButton("Submit")
        val settingsButton = JButton("Settings")
        val solveButton = JButton("Solve")
        val newGameButton = JButton("New game")

        val courierTwelve = Font("Courier New", Font.BOLD, 12)
        resetButton.font = courierTwelve
        submitButton.font = courierTwelve
        settingsButton.font = courierTwelve
        solveButton.font = courierTwelve
        newGameButton.font = courierTwelve

        // Button panel
        val optionPanel = JPanel()
        optionPanel.add(newGameButton)
        optionPanel.add(settingsButton)
        optionPanel.add(resetButton)
        optionPanel.add(solveButton)
        optionPanel.add(submitButton)

        add(optionPanel, BorderLayout.PAGE_END)

        // Button actions
        settingsButton.addActionListener {
            dispose()
            NewGameGUI()
        }

        newGameButton.addActionListener {
            dispose()
            FutoshikiGUI(boardSize, difficulty)
        }

        resetButton.addActionListener {
            for (textFieldArray in userPuzzleInput)
                for (textField in textFieldArray)
                    textField.text = ""
        }

        solveButton.addActionListener {
            dispose()
            FutoshikiGUI(boardSize, difficulty, fs.getSolution(), true)
        }

        submitButton.addActionListener {
            val userSolution = getInput(boardSize, userPuzzleInput)
            // When I can guarantee unique puzzles, change the condition below to userSolution.contentDeepEquals(solution)
            if (fs.isValidBoard(userSolution))
            {
                val message = JLabel("CORRECT! Great job!")
                message.font = courierTwelve
                val result = JOptionPane.showOptionDialog(null, message,
                        "Solved", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        null, null, null)
                if (result == 0)
                {
                    dispose()
                    NewGameGUI()
                }
            }
            else
            {
                val message = JLabel("Incorrect solution.")
                message.font = courierTwelve
                JOptionPane.showMessageDialog(null, message)
            }
        }

        title = "Futoshiki"
        setSize(500, 500)
        minimumSize = Dimension(350, 350)
        setLocationRelativeTo(null)
        isResizable = true
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isVisible = true
    }

    private fun generatePuzzlePanel(
            puzzle: Array<Array<String>>,
            labels: Array<Array<JLabel>>,
            userPuzzleInput: Array<Array<JTextField>>
            ) : JPanel
    {
        val puzzlePanel = JPanel()
        val puzzleSize = puzzle.size
        puzzlePanel.layout = GridLayout(puzzleSize, puzzleSize)
        for (i in 0 until puzzleSize)
        {
            for (j in 0 until puzzleSize)
            {
                if (puzzle[i][j] == "x")
                {
                    if (i%2 == 0 && j%2 == 0)
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
                    puzzlePanel.add(labels[i][j])
                    val textField = JTextField(puzzle[i][j])
                    userPuzzleInput[i][j] = textField
                }

            }
        }
        return puzzlePanel
    }

    private fun getInput(boardSize: Int, userPuzzleInput: Array<Array<JTextField>>) : Array<IntArray>
    {
        val userSolution = Array(boardSize, { IntArray(boardSize) })
        var pi = 0
        var pj : Int
        val message = StringBuilder("<html>")
        var showMessage = false
        for (i in 0 until userPuzzleInput.size)
        {
            pj = 0
            for (j in 0 until userPuzzleInput[0].size)
            {
                if (i % 2 == 0 && j % 2 == 0)
                {
                    try
                    {
                        userSolution[pi][pj++] = userPuzzleInput[i][j].text.trim().toInt()
                        if (userPuzzleInput[i][j].text.trim().toInt() > boardSize ||
                                userPuzzleInput[i][j].text.trim().toInt() < 1)
                        {
                            message.append("Entries must be between 1 and $boardSize. Check ($pi, $pj).<br/>")
                            showMessage = true
                        }
                    }
                    catch(e: IllegalArgumentException)
                    {
                        message.append("You didn't enter a number at ($pi, ${pj-1})<br/>")
                        showMessage = true
                    }
                }
            }
            if (i%2 == 0) pi++
        }
        message.append("</html>")
        val messageLabel = JLabel(message.toString())
        messageLabel.font = Font("Courier New", Font.BOLD, 12)
        if(showMessage) JOptionPane.showMessageDialog(null, messageLabel)
        return userSolution
    }

}