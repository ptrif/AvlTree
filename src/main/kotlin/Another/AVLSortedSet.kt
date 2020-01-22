package Another

import java.util.*
import kotlin.Comparator
import kotlin.NoSuchElementException
import kotlin.collections.AbstractSet
import kotlin.math.max

open class AVLSortedSet<T : Comparable<T>>() : AbstractSet<T>(), SortedSet<T> {

    var root: Node<T>? = null

    // If we have these limits on the tree elements,
    // we should use them in iterator(), add(), remove(), ...
    private var fromElement: T? = null
    private var toElement: T? = null

    private constructor(root: Node<T>?, fromElement: T?, toElement: T?) : this() {
        this.root = root
        this.fromElement = fromElement
        this.toElement = toElement
    }

    private fun range(value: T): Boolean =
        (fromElement == null || value >= fromElement!!) && (toElement == null || value < toElement!!)

    // Fix this funky var with custom getter and **default** setter
    override var size = 0
        get() {
            var result = 0
            for (i in this)
                if (range(i))
                    result++
            return result
        }




    class Node<T>(val value: T) : TreePrinter.PrintableNode {

        private var height: Int = 1

        override var left: Node<T>? = null
        override var right: Node<T>? = null
        var parent: Node<T>? = null

        private val Node<T>?.height: Int
            get() = this@height?.height ?: 0

        fun update() {
            height = max(right.height, left.height) + 1
        }

        fun getBalanceFactor() = right.height - left.height

        override val text: String = value.toString()

    }

    override fun add(element: T): Boolean {
        require(range(element))
        val closest = find(element)
        val cmp = if (closest == null) -1 else element.compareTo(closest.value)

        if (cmp == 0) {
            return false
        }

        val newNode = Node(element)
        newNode.parent = closest

        if (closest == null)
            root = newNode

        when {
            cmp > 0 -> {
                assert(closest?.right == null)
                closest?.right = newNode

            }
            cmp < 0 -> {
                assert(closest?.left == null)
                closest?.left = newNode
            }
        }
        size++
        //     newNode.update()
//        balanceTree(root)
//        root?.update()
        balanceTree(newNode)

        return true
    }

    override fun remove(element: T): Boolean {
        require(range(element))
        val current = find(element)

        if (current == null)
            return false
        else {
            size--
            val parent = current.parent

            when {
                current.left == null && current.right == null -> parent.change(current, null)
                current.right == null -> parent.change(current, current.left)
                current.left == null -> parent.change(current, current.right)
                else -> {
                    var swap = current.left!!

                    while (swap.right != null)
                        swap = swap.right!!

                    swap.parent.change(swap, swap.left)

                    val newNode = Node(swap.value)
                    val curLeft = current.left

                    when {
                        curLeft?.value == swap.value -> newNode.left = curLeft.left
                        else -> newNode.left = curLeft
                    }
                    newNode.right = current.right
                    parent.change(current, newNode)

                }
            }
            // parent?.update()
            // Check whether we need to balance from parent
            // or from swap or from some other thing
            balanceTree(parent ?: root)
            return true

        }
    }

    fun Node<T>?.change(node: Node<T>, newNode: Node<T>?) {
        when {
            this != null -> when {
                this.left?.value?.compareTo(node.value) == 0 -> this.left = newNode
                else -> this.right = newNode
            }
            else -> root = newNode
        }
    }

    private fun balanceTree(node: Node<T>?) {
        if (node == null) return

        val parent = node.parent
        when {
            node.getBalanceFactor() < -1 ->
                if (node.left!!.getBalanceFactor() < 0)
                    rightRotation(node)
                else
                    bigRightRotation(node)
            node.getBalanceFactor() > 1 ->
                if (node.right!!.getBalanceFactor() > 0)
                    leftRotation(node)
                else
                    bigLeftRotation(node)

        }
        node.update()
        if (parent != null) {
            // parent.update()
            balanceTree(parent)
        }

    }

    private fun leftRotation(node: Node<T>): Node<T>? {
        val parent = node.parent
        val newNode = node.right
        val child = newNode?.left

        newNode?.left = node
        node.parent = newNode
        node.right = child
        child?.parent = node

        if (parent == null) {
            root = newNode
            newNode?.parent = null
            // Maybe do updates here?
            //newNode?.update()
            return newNode
        }

        newNode?.parent = parent
        if (parent.left == node)
            parent.left = newNode
        else
            parent.right = newNode

        // Some of these updates are not needed
        // node.update()
        // parent.update()
        newNode?.update()
        // root?.update()
        return newNode

    }

    private fun rightRotation(node: Node<T>): Node<T>? {
        val parent = node.parent
        val newNode = node.left
        val child = newNode?.right

        newNode?.right = node
        node.parent = newNode
        node.left = child
        child?.parent = node

        if (parent == null) {
            root = newNode
            newNode?.parent = null
            // newNode?.update()
            return newNode
        }

        newNode?.parent = parent
        if (parent.left == node)
            parent.left = newNode
        else
            parent.right = newNode

        // node.update()
        newNode?.update()
        // root?.update()
        return newNode
    }

    private fun bigRightRotation(node: Node<T>): Node<T>? {
        node.left = leftRotation(node.left!!)
        return rightRotation(node)
    }

    private fun bigLeftRotation(node: Node<T>): Node<T>? {
        node.right = rightRotation(node.right!!)
        return leftRotation(node)
    }

    fun checkInvariant(): Boolean =
        root?.let { checkInvariant(it) } ?: true

    private fun checkInvariant(node: Node<T>): Boolean {
        val left = node.left
        return when {
            left != null && (left.value >= node.value || !checkInvariant(left)) -> false
            else -> {
                val right = node.right
                right == null || right.value > node.value && checkInvariant(right)
            }
        }
    }

    override operator fun contains(element: T): Boolean {
        val closest = find(element)
        return closest != null && element.compareTo(closest.value) == 0
    }

    private fun find(value: T): Node<T>? =
        root?.let { find(it, value) }

    private fun find(start: Node<T>, value: T): Node<T>? {
        val cmp = value.compareTo(start.value)
        return when {
            cmp == 0 -> start
            cmp < 0 -> start.left?.let { find(it, value) } ?: start
            else -> start.right?.let { find(it, value) } ?: start
        }

    }


    open inner class BinaryTreeIterator internal constructor() : MutableIterator<T> {

        private val iterator: Queue<Node<T>> = ArrayDeque()
        private var current: Node<T>? = null

        init {
            fun generateIterator(state: Node<T>) {
                if (state.left != null)
                    generateIterator(state.left!!)
                iterator.offer(state)

                if (state.right != null)
                    generateIterator(state.right!!)
                iterator.offer(state)
            }

            if (root != null) {
                generateIterator(root!!)
                current = iterator.peek()
            }
        }

        override fun hasNext(): Boolean = iterator.isNotEmpty()

        override fun next(): T {
            current = iterator.poll()
            if (current == null) throw NoSuchElementException()
            return current!!.value
        }

        override fun remove() {
            if (current == null) return
            remove(current!!.value)
        }
    }

    override fun iterator(): MutableIterator<T> = BinaryTreeIterator()

    override fun comparator(): Comparator<in T>? = null

    override fun first(): T {
        var current: Node<T> = root ?: throw NoSuchElementException()
        while (current.left != null) {
            current = current.left!!
        }
        return current.value
    }

    override fun last(): T {
        var current: Node<T> = root ?: throw NoSuchElementException()
        while (current.right != null) {
            current = current.right!!
        }
        return current.value
    }

    override fun isEmpty(): Boolean = root == null

    override fun toString(): String {
        val printer = TreePrinter()
        return printer.getTreeDisplay(root)
    }

    override fun clear() {
        root = null
        size = 0
    }

    override fun addAll(elements: Collection<T>): Boolean {
        return elements.all { add(it) }
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        return elements.all { contains(it) }
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        return elements.all { remove(it) }
    }

    //not sure
    override fun retainAll(elements: Collection<T>): Boolean {
        var result = false
        val marked = mutableListOf<T>()
        for (node in BinaryTreeIterator()) {
            if (elements.any { it != node}) {
                marked += node
                result = true
            }
        }
        for (node in marked)
            remove(node)
        return result
    }



    //  Найти множество всех элементов больше или равных заданного
    override fun tailSet(fromElement: T): SortedSet<T> {
        val node = root
        if (node != null) {
            if (node.value > fromElement && node.right != null)
                node.right
        }
        return AVLSortedSet(root, fromElement, null)
    }

    //Найти множество всех элементов меньше заданного
    override fun headSet(toElement: T): SortedSet<T> {
        val node = root
        if (node != null) {
            if (node.value < toElement && node.left != null)
                node.left
        }
        return AVLSortedSet(root, null, toElement)
    }

    // Найти множество всех элементов в диапазоне [fromElement, toElement)
    override fun subSet(fromElement: T, toElement: T): SortedSet<T> {
        val node = root
        if (node != null) {
            if (node.value < toElement && node.left != null)
                node.left
        }
        return AVLSortedSet(root, fromElement, toElement)
    }
}

