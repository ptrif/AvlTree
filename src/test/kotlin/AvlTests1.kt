import Another.AVLSortedSet
import junit.framework.Assert.assertEquals
import org.junit.Test


class AVLtests {
    private val avlTree = AVLSortedSet<Int>()

    @Test
    fun testInsertion() {

        val testValues = arrayOf(8, 67, 167, 14, 15, 1, 89)

        testValues.forEach { avlTree.add(it) }
        avlTree.add(22)
        avlTree.add(200)
        avlTree.add(250)
        avlTree.add(300)
        avlTree.add(12)
        avlTree.add(13)
        avlTree.add(16)
        avlTree.add(120)

        print(avlTree)

        val expected =
                "                               67                               \n" +
                "                |                               |               \n" +
                "               14                              167              \n" +
                "        |               |               |               |       \n" +
                "        8              16              89              250      \n" +
                "    |       |       |       |               |       |       |   \n" +
                "    1      12      15      22              120     200     300  \n"

        assertEquals(expected, avlTree)

    }

    @Test
    fun testRemoval() {

        val values = arrayOf(89, 100, 68, 57, 11, 65, 85, 116)

        values.forEach { avlTree.add(it) }

        print(avlTree)
        avlTree.remove(85)
        avlTree.remove(100)
        avlTree.remove(68)
        avlTree.remove(65)

        print(avlTree)
        val exp =
                "               57               \n" +
                "        |               |       \n" +
                "       11              89       \n" +
                "                            |   \n" +
                "                           116  \n"
        assertEquals(exp, avlTree)

    }

}