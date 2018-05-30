import java.io.FileWriter
import java.util.Random
import java.util.stream.IntStream

class Futoshiki(private val boardSize: Int = 5, private val difficulty: Int = 2)
{
    // Properties of futoshiki puzzle & initialization
    private var board = Array(boardSize, { IntArray(boardSize) })
    private var horizontalComparison = Array(boardSize, { CharArray(boardSize - 1) })
    private var verticalComparison = Array(boardSize - 1, { CharArray(boardSize) })
    private var puzzle = Array(2 * boardSize - 1, { Array(2 * boardSize - 1) { "" } })
    private var solution = Array(2 * boardSize - 1, { Array(2 * boardSize - 1) { "" } })
    private var iterations: Int = 0 // Kill switch

    init
    {
        val (lower, upper) = determineBounds()
        do generateBoard()
        while (!isValidBoard(board) || iterations !in lower..upper)
        generateComparisons()
        generatePuzzle()
    }

    // Getters
    fun getPuzzle(): Array<Array<String>> = puzzle
    fun getSolution(): Array<Array<String>> = solution

    // Determine iteration bounds
    private fun determineBounds() : Pair<Int, Int>
    {
        var bounds = Pair(0, 0)

        when (boardSize) {
            3 -> bounds = when (difficulty) {
                1 -> Pair(0, 10)
                2 -> Pair(11, 17)
                else -> Pair(18, boardSize*100000)
            }
            4 -> bounds = when (difficulty) {
                1 -> Pair(0, 22)
                2 -> Pair(23, 45)
                else -> Pair(46, boardSize*100000)
            }
            5 -> bounds = when (difficulty) {
                1 -> Pair(0, 52)
                2 -> Pair(53, 369)
                else -> Pair(370, boardSize*100000)
            }
            6 -> bounds = when (difficulty) {
                1 -> Pair(0, 250)
                2 -> Pair(251, 2000)
                else -> Pair(2001, boardSize*100000)
            }
            7 -> bounds = when (difficulty) {
                1 -> Pair(0, 1500)
                2 -> Pair(1501, 10000)
                else -> Pair(10000, boardSize*100000)
            }
            8 -> bounds = when (difficulty) {
                1 -> Pair(0, 2000)
                2 -> Pair(2001, 15000)
                else -> Pair(15001, boardSize*100000)
            }
            9 -> bounds = when (difficulty) {
                1 -> Pair(0, 3000)
                2 -> Pair(3001, 20000)
                else -> Pair(20001, boardSize*100000)
            }
        }

        return bounds
    }

    // Resets board to 0's, sets random cells to random values, then solves board
    private fun generateBoard()
    {
        board = Array(boardSize, { IntArray(boardSize) })
        iterations = 0
        initializeBoard()
        solve()
    }

    // Places random values in random valid cells
    private fun initializeBoard()
    {
        var randomIndex: Int
        var randomEntry: Int
        for (i in 0 until boardSize)
        {
            randomIndex = Random().nextInt(boardSize)
            for (j in 0 until boardSize)
            {
                if (randomIndex == j || randomIndex == j - 1)
                {
                    do
                    {
                        randomEntry = Random().nextInt(boardSize) + 1
                        iterations++
                        if(iterations > boardSize*100)
                            break
                    }
                    while (invalidMove(i, j, randomEntry))
                    board[i][j] = randomEntry
                }
            }
        }
    }

    private fun invalidMove(x: Int, y: Int, entry: Int) : Boolean
    {
        val horizontalValues = mutableListOf<Int>()
        val verticalValues = mutableListOf<Int>()
        for (i in 0 until boardSize)
        {
            horizontalValues.add(board[x][i])
            verticalValues.add(board[i][y])
        }
        if (horizontalValues.contains(entry) || verticalValues.contains(entry))
            return true
        return false
    }

    // Recursively enters numbers until board is solved
    private fun solve(): Boolean
    {
        for (row in 0 until boardSize)
        {
            for (column in 0 until boardSize)
            {
                if (board[row][column] == 0)
                {
                    for (k in 1..boardSize)
                    {
                        // Kill switch. Taking too long, so restart.
                        if (iterations > boardSize*10000)
                            break
                        board[row][column] = k
                        if (isValid(board, row, column) && solve())
                            return true
                        board[row][column] = 0
                        iterations++
                    }
                    return false
                }
            }
        }
        return true
    }

    // Checks row and column for invalid placement
    // The following 4 methods referenced http://www.baeldung.com/java-sudoku
    private fun isValid(board: Array<IntArray>, row: Int, column: Int): Boolean =
            rowConstraint(board, row) && columnConstraint(board, column)

    private fun rowConstraint(board: Array<IntArray>, row: Int): Boolean
    {
        val constraint = BooleanArray(boardSize)
        return IntStream.range(0, boardSize)
                .allMatch { column -> checkConstraint(board, row, constraint, column) }
    }

    private fun columnConstraint(board: Array<IntArray>, column: Int): Boolean
    {
        val constraint = BooleanArray(boardSize)
        return IntStream.range(0, boardSize)
                .allMatch { row -> checkConstraint(board, row, constraint, column) }
    }

    private fun checkConstraint(board: Array<IntArray>,
                                row: Int,
                                constraint: BooleanArray,
                                column: Int): Boolean
    {
        if (board[row][column] != 0)
        {
            if (!constraint[board[row][column]-1])
                constraint[board[row][column]-1] = true
            else
                return false
        }
        return true
    }

    // Will return false is board is not well-formed
    fun isValidBoard(board: Array<IntArray> = this.board) : Boolean
    {
        // Invalid if board is of form A...B
        //                             B...A
        val topLeft = board[0][0]
        val topRight = board[0][boardSize-1]
        val bottomLeft = board[boardSize-1][0]
        val bottomRight = board[boardSize-1][boardSize-1]

        if (topLeft == bottomRight && topRight == bottomLeft)
            return false

        // Invalid if board contains >1 of the same value on row or column
        val horizontalList = mutableListOf<Int>()
        val verticalList = mutableListOf<Int>()
        for (i in 0 until boardSize)
        {
            for (j in 0 until boardSize)
            {
                if (board[i][j] < 1 ||
                    board[i][j] > boardSize ||
                    horizontalList.contains(board[i][j]) ||
                    verticalList.contains(board[j][i])
                    )
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

    // Places board and comparison operators in the same array
    private fun generatePuzzle()
    {
        var random : Int
        var pi = 0
        var pj : Int
        var pk : Int
        val numThreshold = when (difficulty) {
            1 -> 30
            2 -> 8
            3 -> 7
            else -> 100
        }
        val comparisonThreshold = when (difficulty) {
            1 -> 15
            2 -> 13
            3 -> 12
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