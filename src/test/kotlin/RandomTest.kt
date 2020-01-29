@file:Suppress("DEPRECATION")

import Another.AVLSortedSet
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import org.junit.Test
import java.util.*
import kotlin.random.Random

class RandomTest {
    private val avlTree = AVLSortedSet<Int>()
    val tree =TreeSet<Int>()

    @Test
    fun testInsertion() {
        val randomValues = List(10){ Random.nextInt(1,100)}

        randomValues.forEach { avlTree.add(it) }
        print(avlTree)
        randomValues.forEach{tree.add(it)}
        assertEquals(tree, avlTree)

    }

    @Test
    fun testRemove() {

        val random = java.util.Random()

        for (i in 0..100) {
            val addingValues = random.nextInt(100)
            tree.add(addingValues)
            avlTree.add(addingValues)
        }

        for (i in 0..50) {
            var removeValues = random.nextInt(100)

            while (!tree.contains(removeValues)) {
                removeValues = random.nextInt(100)
            }
            avlTree.remove(removeValues)
            tree.remove(removeValues)
            assertEquals(tree.size, avlTree.size)


        }
    }

    @Test
    fun testContains() {
        val avlTree = AVLSortedSet<Int>()
        val random = java.util.Random()
        val treeSet = TreeSet<Int>()

        for (i in 0..100) {
            val addingValues = random.nextInt(100)
            treeSet.add(addingValues)
            avlTree.add(addingValues)
        }

        for (i in 0..50) {
            var values = random.nextInt(100)

            while (!treeSet.contains(values)) {
                values = random.nextInt(100)
            }

            Assert.assertTrue(treeSet.contains(values))
            Assert.assertTrue(avlTree.contains(values))

        }
    }


}