import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class Test{

    @Test
    fun testInsertion(){
        val testValues = arrayOf(89,167,14,15,1,67)
        val avlTree = AvlTree()
        testValues.forEach { avlTree.insert(it) }
        print(avlTree.printKey())

        var expected = "1,14,15,67,89,167"

        assertEquals(expected, avlTree.printKey())

        val anotherseq = arrayOf(99,100,16)
        anotherseq.forEach { avlTree.insert(it) }
        avlTree.insert(14)
        expected="1,14,15,16,67,89,99,100,167"
        assertEquals(expected,avlTree.printKey())

    }

    @Test
    fun testRemoval(){
        val avl = AvlTree()
        val values = arrayOf(89,100,67)
        values.forEach { avl.insert(it) }
        avl.remove(100)
        val exp = "67,89"
        assertEquals(exp, avl.printKey())


    }
}