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
import java.util.*

import javax.swing.JOptionPane
/*
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
*/

class FutoshikiGUI(boardSize: Int = 5, difficulty: Int = 2) : JFrame()
{

    init
    {
        val fs = Futoshiki(boardSize, difficulty)
        val puzzle = fs.getPuzzle()
        val puzzleSize = fs.getPuzzleSize()
        val labels = Array(puzzleSize) {Array(puzzleSize) {JLabel("")}}
        val userPuzzleInput = Array(puzzleSize) {Array(puzzleSize) {JTextField("")}}

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

        val resetButton = JButton("Reset")
        val submitButton = JButton("Submit")
        val settingsButton = JButton("Settings")
        val newGameButton = JButton("New game")

        resetButton.font = Font("Courier New", Font.BOLD, 12)
        submitButton.font = Font("Courier New", Font.BOLD, 12)
        settingsButton.font = Font("Courier New", Font.BOLD, 12)
        newGameButton.font = Font("Courier New", Font.BOLD, 12)

        val optionPanel = JPanel()
        optionPanel.add(newGameButton)
        optionPanel.add(settingsButton)
        optionPanel.add(resetButton)
        optionPanel.add(submitButton)

        add(puzzlePanel, BorderLayout.CENTER)
        add(optionPanel, BorderLayout.PAGE_END)

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

        submitButton.addActionListener {
            var isSolution = true
            val solution = fs.getSolution()
            val userSolution = Array(boardSize, { IntArray(boardSize) })
            var pi = 0
            var pj = 0
            for (i in 0 until userPuzzleInput.size)
            {
                pj = 0
                for (j in 0 until userPuzzleInput[0].size)
                {
                    if (i%2 == 0 && j%2 == 0)
                    {
                        try
                        {
                            userSolution[pi][pj++] = userPuzzleInput[i][j].text.trim().toInt()
                        }
                        catch(e: IllegalArgumentException)
                        {
                            println(e.printStackTrace())
                            println("You entered a non-number.")
                        }
                    }
                }
                if (i%2 == 0) pi++
            }
            if (fs.isValid(userSolution))
            {
                val result = JOptionPane.showOptionDialog(null, "CORRECT! Great job!",
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
                JOptionPane.showMessageDialog(null, "Incorrect solution.")
            }
        }

        title = "Futoshiki"
        setSize(450, 450)
        minimumSize = Dimension(350, 350)
        setLocationRelativeTo(null)
        isResizable = true
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isVisible = true
    }

}