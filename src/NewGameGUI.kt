import java.awt.Font
import java.awt.BorderLayout
import javax.swing.JLabel
import javax.swing.WindowConstants
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JPanel
import javax.swing.JFrame
import java.awt.GridLayout

class NewGameGUI : JFrame()
{
    init
    {
        val topPanel = JPanel()
        val centerPanel = JPanel()
        val bottomPanel = JPanel()
        centerPanel.layout = GridLayout(2, 0)

        val welcomeLabel = JLabel("Welcome to Futoshiki!")
        val sizeLabel = JLabel("Choose puzzle size: ")
        val sizeDropDown = JComboBox(arrayOf("3", "4", "5", "6", "7", "8", "9"))
        val difficultyLabel = JLabel("Choose difficulty: ")
        val difficultyDropDown = JComboBox(arrayOf("Easy", "Medium", "Hard", "Solved"))
        val confirmButton = JButton("OK")

        welcomeLabel.font = Font("Courier New", Font.BOLD, 20)
        sizeLabel.font = Font("Courier New", Font.BOLD, 12)
        difficultyLabel.font = Font("Courier New", Font.BOLD, 12)
        sizeDropDown.font = Font("Courier New", Font.BOLD, 14)
        difficultyDropDown.font = Font("Courier New", Font.BOLD, 14)
        confirmButton.font = Font("Courier New", Font.BOLD, 14)

        sizeDropDown.selectedIndex = 2
        difficultyDropDown.selectedIndex = 1

        topPanel.add(welcomeLabel)
        centerPanel.add(sizeLabel)
        centerPanel.add(sizeDropDown)
        centerPanel.add(difficultyLabel)
        centerPanel.add(difficultyDropDown)
        bottomPanel.add(confirmButton)

        add(topPanel, BorderLayout.PAGE_START)
        add(centerPanel, BorderLayout.CENTER)
        add(bottomPanel, BorderLayout.PAGE_END)

        confirmButton.addActionListener {
            dispose()
            val sizeChoice: Int = Integer.parseInt(sizeDropDown.selectedItem.toString())
            val difficultyChoice = difficultyDropDown.selectedItem.toString()
            when (difficultyChoice) {
                "Easy" -> FutoshikiGUI(sizeChoice, 1)
                "Medium" -> FutoshikiGUI(sizeChoice, 2)
                "Hard" -> FutoshikiGUI(sizeChoice, 3)
                else -> {
                    FutoshikiGUI(sizeChoice, 0)
                }
            }
        }

        title = "Futoshiki"
        setSize(300, 150)
        setLocationRelativeTo(null)
        isResizable = false
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isVisible = true
    }
}