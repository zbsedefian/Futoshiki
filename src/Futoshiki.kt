import java.util.*

class Futoshiki(private val boardSize: Int = 5, private val difficulty: Int = 2)
{
    // Properties of futoshiki puzzle & initialization
    private var board = Array(boardSize, {IntArray(boardSize)})
    private var horizontalComparison = Array(boardSize, {CharArray(boardSize-1)})
    private var verticalComparison = Array(boardSize-1, {CharArray(boardSize)})
    private var puzzle = Array(2*boardSize-1, {Array(2*boardSize-1){""}})
    private var solution = Array(2*boardSize-1, {Array(2*boardSize-1){""}})

    init
    {
        generateBoard()
        generateComparisons()
        generatePuzzle()
    }

    // Getters
    fun getPuzzle() : Array<Array<String>> = puzzle
    fun getSolution(): Array<Array<String>> = solution

    // Generates numbers for futoshiki board
    private fun generateBoard()
    {
        var entry : Int
        for (i in 0 until boardSize)
        {
            entry = i + 1
            for (j in 0 until boardSize)
            {
                board[i][j] = entry++
                if (entry > boardSize)
                    entry = 1
            }
        }
        do randomizeBoard()
        while (!isValid())
    }

    // Swaps cells at random
    private fun randomizeBoard()
    {
        var random1 : Int
        var random2 : Int
        var swap : Int
        for (i in 0 until boardSize)
        {
            random1 = Random().nextInt(boardSize)
            do random2 = Random().nextInt(boardSize)
            while (random1 == random2)
            swap = board[i][random1]
            board[i][random1] = board[i][random2]
            board[i][random2] = swap
        }
    }

    // Will return false when list already contains the element.
    fun isValid(board: Array<IntArray> = this.board) : Boolean
    {
        val topLeft = board[0][0]
        val topRight = board[0][boardSize-1]
        val bottomLeft = board[boardSize-1][0]
        val bottomRight = board[boardSize-1][boardSize-1]

        if (topLeft == bottomRight && topRight == bottomLeft)
            return false

        val horizontalList = mutableListOf<Int>()
        val verticalList = mutableListOf<Int>()
        for (i in 0 until boardSize)
        {
            for (j in 0 until boardSize)
            {
                if (horizontalList.contains(board[i][j]) || verticalList.contains(board[j][i]))
                    return false
                horizontalList.add(board[i][j])
                verticalList.add(board[j][i])
            }
            horizontalList.clear()
            verticalList.clear()
        }
        return true
    }

    // Returns a char array of comparison operators
    private fun generateComparisons()
    {
        for (i in 0 until boardSize)
        {
            for (j in 0 until boardSize)
            {
                if (j < boardSize-1)
                    if (board[i][j] < board[i][j+1])
                        horizontalComparison[i][j] = '<'
                    else
                        horizontalComparison[i][j] = '>'

                if (i < boardSize-1)
                    if (board[i][j] < board[i+1][j])
                        verticalComparison[i][j] = '^'
                    else
                        verticalComparison[i][j] = 'v'
            }
        }
    }

    private fun generatePuzzle()
    {
        var random : Int
        var pi = 0
        var pj : Int
        var pk : Int
        val numThreshold = when (difficulty) {
            1 -> 30
            2 -> 8
            3 -> 4
            else -> 100
        }
        val comparisonThreshold = when (difficulty) {
            1 -> 15
            2 -> 11
            3 -> 10
            else -> 100
        }

        for (i in 0 until boardSize)
        {
            pj = 0
            for (j in 0 until boardSize)
            {
                random = Random().nextInt(100)

                // Add number or x
                if (random <= numThreshold)
                    puzzle[pi][pj] = board[i][j].toString()
                else
                    puzzle[pi][pj] = "x"

                solution[pi][pj] = board[i][j].toString()

                // Add horizontal comparison
                if (j < boardSize-1)
                {
                    if (random <= comparisonThreshold)
                        puzzle[pi][pj+1] = horizontalComparison[i][j].toString()
                    else
                        puzzle[pi][pj+1] = "x"

                    solution[pi][pj+1] = horizontalComparison[i][j].toString()
                }
                pj+=2
            }
            pi++

            // Add vertical comparison
            pk = 0
            for (k in 0 until puzzle.size)
            {
                if (i < boardSize-1)
                {
                    if (k%2 == 0)
                    {
                        random = Random().nextInt(100)
                        if (random <= comparisonThreshold)
                            puzzle[pi][pk] = verticalComparison[i][k / 2].toString()
                        else
                            puzzle[pi][pk] = "x"
                        solution[pi][pk] = verticalComparison[i][k / 2].toString()
                    }
                    else
                    {
                        puzzle[pi][pk] = "x"
                        solution[pi][pk] = "x"
                    }
                    pk++
                }
            }
            pi++
        }
    }

    // Console print of the puzzle (not the solution)
    fun printPuzzle(puzzle: Array<Array<String>> = this.puzzle)
    {
        println()
        val noBoxAroundThese = listOf("x", "<", ">", "^", "v")
        for (i in 0 until puzzle.size)
        {
            for (j in 0 until puzzle.size)
            {
                if (!noBoxAroundThese.contains(solution[i][j]))
                    when {
                        puzzle[i][j] == "x" -> print("| |")
                        puzzle[i][j] == "" -> print("   ")
                        else -> print("|" + puzzle[i][j] + "|")
                    }
                else if (puzzle[i][j] != "x")
                    print(" " + puzzle[i][j] + " ")
                else
                    print("   ")
            }
            println()
        }
    }

    // Console print of the solution
    fun printSolution() = printPuzzle(solution)
}

fun main(args: Array<String>)
{
    Futoshiki().apply {
        printPuzzle()
        printSolution()
    }
}