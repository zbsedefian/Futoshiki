import java.util.*

class GenerateFutoshiki(private val boardSize: Int) {

    private var board : Array<IntArray> = emptyArray()
    private var horizontalComparison : Array<CharArray> = emptyArray()
    private var verticalComparison : Array<CharArray> = emptyArray()

    init {
        board = generateBoard()
        horizontalComparison = generateHorizontalComparisons()
        verticalComparison = generateVerticalComparisons()
    }

    fun generateFutoshiki() : Array<IntArray> {
        do {
            randomizeBoard()
        } while (!isValid())
        return board
    }

    private fun generateBoard() : Array<IntArray> {
        val board = Array(boardSize, {IntArray(boardSize)})
        var entry : Int
        for (i in 0 until boardSize) {
            entry = i + 1
            for (j in 0 until boardSize) {
                board[i][j] = entry++
                if (entry > boardSize) entry = 1
            }
        }
        return board
    }

    private fun randomizeBoard() {
        var random1 : Int
        var random2 : Int
        var swap : Int
        for (i in 0 until boardSize) {
            random1 = Random().nextInt(boardSize)
            random2 = Random().nextInt(boardSize)
            swap = board[i][random1]
            board[i][random1] = board[i][random2]
            board[i][random2] = swap
        }
    }

    fun isValid() : Boolean {
        var expectedSum = 0
        var actualRomSum : Int
        var actualColSum : Int
        for (i in 1..boardSize)
            expectedSum += i
        for (i in 0 until boardSize) {
            actualRomSum = 0
            actualColSum = 0
            for (j in 0 until boardSize) {
                actualRomSum += board[i][j]
                actualColSum += board[j][i]
            }
            if (actualColSum != expectedSum || actualRomSum != expectedSum)
                return false
        }
        return true
    }

    private fun generateHorizontalComparisons() : Array<CharArray> {
        val horizontalComparisonBoard = Array(boardSize, {CharArray(boardSize-1)})

        for (i in 0 until boardSize) {
            for (j in 0 until boardSize-1) {
                if (j != boardSize-1 && board[i][j] < board[i][j+1])
                    horizontalComparisonBoard[i][j] = '<'
                else
                    horizontalComparisonBoard[i][j] = '>'
            }
        }

        return horizontalComparisonBoard
    }


    private fun generateVerticalComparisons() : Array<CharArray> {
        val verticalComparisonBoard = Array(boardSize-1, {CharArray(boardSize)})

        for (i in 0 until boardSize-1) {
            for (j in 0 until boardSize) {
                if (i != boardSize-1 && board[i][j] < board[i+1][j])
                    verticalComparisonBoard[i][j] = '^'
                else
                    verticalComparisonBoard[i][j] = 'v'
            }
        }

        return verticalComparisonBoard
    }

    fun printBoard() {
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                print(board[i][j])
                if (j < boardSize - 1)
                    print(" " + horizontalComparison[i][j] + " ")

            }
            println()
            for (k in 0 until horizontalComparison.size)
                if (i < boardSize-1)
                    print(verticalComparison[i][k] + "   ")
            println()
        }
    }

}

fun main(args: Array<String>) {
    val futoshiki = GenerateFutoshiki(5)
    futoshiki.generateFutoshiki()
    futoshiki.printBoard()
    println(futoshiki.isValid())
}