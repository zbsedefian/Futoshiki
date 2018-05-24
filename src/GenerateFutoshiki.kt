import java.util.*

class GenerateFutoshiki(private val boardSize: Int, private val difficulty: Int)
{
    // Properties of futoshiki puzzle & initialization
    private var board = Array(boardSize, {IntArray(boardSize)})
    private var horizontalComparison = Array(boardSize, {CharArray(boardSize-1)})
    private var verticalComparison = Array(boardSize-1, {CharArray(boardSize)})
    private var puzzle = Array(2*boardSize-1, {Array(2*boardSize-1){""}})
    private var solution = Array(2*boardSize-1, {Array(2*boardSize-1){""}})

    init {
        generateBoard()
        generateComparisons()
        generatePuzzle()
    }

    // Getters
    fun getPuzzle() : Array<Array<String>> = puzzle
    fun getPuzzleSize() : Int = puzzle.size
    fun getSolution() : Array<Array<String>> = solution

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
        do {
            randomizeBoard()
        } while (!isValid())
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
            random2 = Random().nextInt(boardSize)
            swap = board[i][random1]
            board[i][random1] = board[i][random2]
            board[i][random2] = swap
        }
    }

    // Checks validity of board by placing each element in a list.
    // Will return false when said list already contains the element.
    private fun isValid() : Boolean
    {
        val horizontalList = mutableListOf<Int>()
        val verticalList = mutableListOf<Int>()
        for (i in 0 until boardSize)
        {
            for (j in 0 until boardSize)
            {
                if (horizontalList.contains(board[i][j]))
                    return false
                if (verticalList.contains(board[j][i]))
                    return false
                horizontalList.add(board[i][j])
                verticalList.add(board[j][i])
            }
            horizontalList.clear()
            verticalList.clear()
        }
        return true
    }

    private fun generateRandomBoardWorse()
    {
        board = Array(boardSize, {IntArray(boardSize)})
        var random : Int
        val listOfNumbers = mutableListOf<Int>()
        val verticalList = Array(boardSize, { mutableListOf<Int>()})

        for (i in 0 until boardSize)
        {
            for (k in 1..boardSize)
            {
                listOfNumbers.add(k)
            }

            for (j in 0 until boardSize)
            {
                do {
                    random = Random().nextInt(boardSize-1)
                } while(verticalList[j].contains(random) || random >= listOfNumbers.size )

                board[i][j] = listOfNumbers.removeAt(random)

                println(Arrays.deepToString(board))

                if (j == i)
                {
                    for(k in 0 until boardSize)
                    {
                        if (board[k][j] != 0)
                            verticalList[i].add(board[k][j])
                    }
                    println("vert list: " + Arrays.deepToString(verticalList))
                }



            }
        }
    }

    // Checks validity of board by placing each element in a list.
    // Will return false when said list already contains the element.
    // Attempt at faster solution
    private fun generateRandomBoardBad()
    {
        val horizontalList = mutableListOf<Int>()
        val verticalList = mutableListOf<Int>()
        var random : Int
        var k = 0
        for (i in 0 until boardSize)
        {
            for (j in 0 until boardSize)
            {

                do {
                    random = Random().nextInt(boardSize) + 1
                } while (verticalList.contains(random) || horizontalList.contains(random))

                board[i][j] = random
                horizontalList.add(random)
                //verticalList.clear()

                if (i == j)
                {
                    for (k in 0 .. j)
                    if (board[k][i] != 0)
                        verticalList.add(board[k][i])
                }

                println("horizontal list: " + horizontalList)
                println("vertical list: " + verticalList)
                println(Arrays.deepToString(board))
            }
            //k++
//            for(j in 0 until boardSize)
//            {
//                if (board[j][i] != 0)
//                    verticalList.add(board[j][i])
//                horizontalList.contains(board[i][j])
//            }
//
//            for (j in i+1 until boardSize)
//            {
//                //println(i+1)
//
//                println("horizontal list: " + horizontalList)
//                println("vertical list: " + verticalList)
//
//                do {
//                    random = Random().nextInt(boardSize) + 1
//                } while (horizontalList.contains(random) && verticalList.contains(random))
//
//                board[j][i] = random
//                verticalList.add(random)
//
//
//            }
//            k++

            println(Arrays.deepToString(board))
            horizontalList.clear()
            verticalList.clear()
        }


    }

    // Compares each row value and returns a char array of comparison operators
    private fun generateComparisons()
    {
        for (i in 0 until boardSize)
        {
            for (j in 0 until boardSize)
            {
                if (j < boardSize-1 && board[i][j] < board[i][j+1])
                    horizontalComparison[i][j] = '<'
                else if (j < boardSize-1)
                    horizontalComparison[i][j] = '>'

                if (i < boardSize-1 && board[i][j] < board[i+1][j])
                    verticalComparison[i][j] = '^'
                else if (i < boardSize-1)
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
            1 -> 0
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

                // Add number
                if (random <= numThreshold)
                    puzzle[pi][pj] = board[i][j].toString()
                else
                    puzzle[pi][pj] = "x"
                solution[pi][pj] = board[i][j].toString()

                // Add horizontal comparison
                if (j < boardSize - 1)
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
                if (i < boardSize - 1)
                {
                    if (k % 2 == 0)
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
        val noBoxAroundThese = listOf("x", "<", ">", "^", "v")
        for (i in 0 until puzzle.size)
        {
            for (j in 0 until puzzle.size)
            {
                if (!noBoxAroundThese.contains(solution[i][j]))
                {
                    if (puzzle[i][j] == "x")
                        print("| |")
                    else
                        print("|" + puzzle[i][j] + "|")
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
    val fs = GenerateFutoshiki(5, 2)
    fs.printPuzzle()
    println("\n")
    fs.printSolution()
    //println(Arrays.deepToString(fs.getPuzzle()))
}