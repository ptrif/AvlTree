import Another.AVLTree
import Another.TreePrinter
import junit.framework.Assert.assertEquals
import org.junit.Test
import kotlin.random.Random

class AVLtests {
    private val avlTree = AVLTree<Int>()

    @Test
    fun testInsertion() {
        val testValues = arrayOf(67, 167, 14, 15, 1, 89)



        testValues.forEach { avlTree.insert(it) }
        //print(avlTree)
        avlTree.insert(22)
        avlTree.insert(200)
        avlTree.insert(250)
        avlTree.insert(300)

        print(avlTree)

        val expected = "        67               \n" +
                "                                \n" +
                "       14              167      \n" +
                "                                \n" +
                "    1      15      89  "

        assertEquals(expected, avlTree)

    }

    @Test
    fun testRemoval(){ // SOMETHING WRONG HERE!
        //не удаляет рут и не балансирует
        val values = arrayOf(89,100,68, 57, 11, 85, 116)
        values.forEach { avlTree.insert(it) }
        avlTree.remove(100)
        avlTree.remove(1)
        avlTree.remove(57)
        // avlTree.remove(89) //doesnt work!
        // avlTree.remove(11)


        print(avlTree)
        val exp = ""
        assertEquals(exp, avlTree)


    }

}