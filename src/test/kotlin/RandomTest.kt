
import Another.AVLSortedSet
import junit.framework.Assert.assertEquals
import org.junit.Test
import kotlin.random.Random

class RandomTest {
    private val avlTree = AVLSortedSet<Int>()

    @Test
    fun testInsertion() {
        val randomValues = List(10){ Random.nextInt(1,100)}

        randomValues.forEach { avlTree.add(it) }
        print(avlTree)

        val expected = "        67               \n" +
                "                                \n" +
                "       14              167      \n" +
                "                                \n" +
                "    1      15      89  "

        assertEquals(expected, avlTree)

    }

    @Test
    fun testRemove(){
        val insertV = List(6){ Random.nextInt(1,100)}
        insertV.forEach { avlTree.add(it) }
        print(avlTree)
        val removeR = List(3){insertV[Random.nextInt(1,6)]}

        removeR.forEach{avlTree.remove(it)}
        print(avlTree)
        val expected = ""
        assertEquals(expected, avlTree)




    }





}