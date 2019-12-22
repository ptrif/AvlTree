import Another.AVLTree
import Another.TreePrinter
import junit.framework.Assert.assertEquals
import org.junit.Test
import kotlin.random.Random

class RandomTest {
    private val avlTree = AVLTree<Int>()

    @Test
    fun testInsertion() {
        val randomValues = List(10){ Random.nextInt(1,100)}

        randomValues.forEach { avlTree.insert(it) }
        print(avlTree)

        val expected = "        67               \n" +
                "                                \n" +
                "       14              167      \n" +
                "                                \n" +
                "    1      15      89  "

        assertEquals(expected, avlTree)

    }


}