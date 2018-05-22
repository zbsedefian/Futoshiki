import java.util.*

class GenerateFutoshiki(val boardSize: Int) {
    //val random = Random()
    //fun rand(from: Int, to: Int) = random.nextInt(to - from) + from

    fun generateBoard() : Array<IntArray> {
        val board = Array(boardSize, {IntArray(boardSize)})
        var entry : Int
        for (i in 0 until boardSize) {
            entry = i + 1
            //entry += i
            for (j in 0 until boardSize) {
                board[i][j] = entry++
                if (entry > boardSize) entry = 1
            }
        }
        return board
    }

    fun generateHorizontalComparisons(board : Array<IntArray>) : Array<CharArray> {
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


    fun generateVerticalComparisons(board : Array<IntArray>) : Array<CharArray> {
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

    fun printBoard(board: Array<IntArray>,
                   horiBoard: Array<CharArray>,
                   vertBoard : Array<CharArray>)
    {
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                print(board[i][j])
                if (j < boardSize - 1)
                    print(" " + horiBoard[i][j] + " ")

            }
            println()
            for (k in 0 until horiBoard.size)
                if (i < boardSize-1)
                    print(vertBoard[i][k] + "   ")
            println()
        }
    }

    fun generatePuzzle(board: Array<IntArray>,
                       horiBoard: Array<CharArray>,
                       vertBoard : Array<CharArray>)
                      : Array<CharArray>
    {
        val puzzleSize = 2*boardSize
        val puzzle = Array(puzzleSize, {CharArray(puzzleSize)})
        //val boardChar = board.map { it.toChar() }
        var boardIndexI = 0
        var boardIndexJ = 0
        var horiIndexI = 0
        var horiIndexJ = 0
        for (i in 0 until puzzleSize) {
            for (j in 0 until puzzleSize step 2) {
                if (boardIndexJ < board[0].size)
                    puzzle[i][j] = board[boardIndexI][boardIndexJ++].toChar()
                if (horiIndexJ < horiBoard[0].size)
                    puzzle[i][j+1] = horiBoard[horiIndexI][horiIndexJ++]
            }
//            for (k in 0 until horiBoard.size-1)
//                if (i < boardSize-1)
//                    puzzle[i][0] = vertBoard[i][k]
//            boardIndexI++
        }
        return puzzle
    }
}

fun main(args: Array<String>) {
    val futoshiki = GenerateFutoshiki(5)
    val boardy = futoshiki.generateBoard()
    val horicomp = futoshiki.generateHorizontalComparisons(boardy)
    val vertcomp = futoshiki.generateVerticalComparisons(boardy)
    //println(Arrays.deepToString(vertcomp))
   // futoshiki.printBoard(boardy, horicomp, vertcomp)
    val puzzle = futoshiki.generatePuzzle(boardy, horicomp, vertcomp)
    println(Arrays.deepToString(puzzle))
}