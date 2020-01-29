@file:Suppress("DEPRECATION")

import Another.AVLSortedSet
import junit.framework.Assert.assertEquals
import org.junit.Test
import java.util.*


class AVLtests {
    private val avlTree = AVLSortedSet<Int>()
    val tre = TreeSet<Int>()


    @Test
    fun testInsertion() {

        val testValues = arrayOf(8, 67, 167, 14, 15, 1, 89)

        testValues.forEach { avlTree.add(it) }
        avlTree.add(22)
        avlTree.add(200)
        avlTree.add(250)
        avlTree.add(300)
        avlTree.add(12)
        avlTree.add(16)
        avlTree.add(120)

        print(avlTree)

        testValues.forEach { tre.add(it) }
        tre.add(22)
        tre.add(200)
        tre.add(250)
        tre.add(300)
        tre.add(12)
        tre.add(16)
        tre.add(120)
        println(tre)

        assertEquals(tre, avlTree)

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

        values.forEach { tre.add(it) }
        tre.remove(85)
        tre.remove(100)
        tre.remove(68)
        tre.remove(65)

        print(avlTree)
        println(tre)
        assertEquals(tre, avlTree)

    }

    @Test
    fun subSetAvlTree() {
        val avlTree = AVLSortedSet<Int>()

        for (i in 0..9) {
            avlTree.add(i)
        }

        assertEquals(10, avlTree.size)
        assertEquals(5, avlTree.subSet(5, 10).size)


    }

    @Test
    fun headSetAvlTree() {
        val avlTree = AVLSortedSet<Int>()

        for (i in 0..9) {
            avlTree.add(i)
        }

        assertEquals(10, avlTree.size)
        assertEquals(5, avlTree.headSet(5).size)
    }

    @Test
    fun tailSetAvlTree() {
        val avlTree = AVLSortedSet<Int>()

        for (i in 0..9) {
            avlTree.add(i)
        }

        assertEquals(10, avlTree.size)
        assertEquals(8, avlTree.tailSet(2).size)
    }


}