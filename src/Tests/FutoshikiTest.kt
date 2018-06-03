package Tests

import Futoshiki
import org.junit.*
import org.junit.Assert.*

class FutoshikiTest
{
    private val fs3diff1 = Futoshiki(3, 1)
    private val fs5diff1 = Futoshiki(5, 1)
    private val fs9diff1 = Futoshiki(9, 1)
    private val fs4diff2 = Futoshiki(4, 2)
    private val fs6diff2 = Futoshiki(6, 2)
    private val fs7diff2 = Futoshiki(7, 2)
    private val fs5diff3 = Futoshiki(5, 3)
    private val fs8diff3 = Futoshiki(8, 3)
    private val fs9diff3 = Futoshiki(9, 3)

    @Test
    fun testIterations()
    {
        assertTrue(fs3diff1.iterations <= 10)
        assertTrue(fs5diff1.iterations <= 52)
        assertTrue(fs9diff1.iterations <= 3000)

        assertTrue(fs4diff2.iterations >= 23)
        assertTrue(fs4diff2.iterations <= 45)
        assertTrue(fs6diff2.iterations >= 251)
        assertTrue(fs6diff2.iterations <= 2000)
        assertTrue(fs7diff2.iterations >= 1501)
        assertTrue(fs7diff2.iterations <= 10000)

        assertTrue(fs5diff3.iterations >= 370)
        assertTrue(fs5diff3.iterations <= 500000)
        assertTrue(fs8diff3.iterations >= 10000)
        assertTrue(fs8diff3.iterations <= 800000)
        assertTrue(fs9diff3.iterations >= 20001)
        assertTrue(fs9diff3.iterations <= 900000)
    }

    @Test
    fun testValidBoard()
    {
        val badBoardABBA = Array(4, { IntArray(4) })
        badBoardABBA[0] = intArrayOf(1, 2, 4, 3)
        badBoardABBA[1] = intArrayOf(2, 3, 1, 4)
        badBoardABBA[2] = intArrayOf(4, 1, 3, 2)
        badBoardABBA[3] = intArrayOf(3, 4, 2, 1)

        assertFalse(fs4diff2.isValidBoard(badBoardABBA))
    }

}