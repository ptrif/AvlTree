package Another

import java.util.*
import kotlin.math.max


class AVLTree<T : Comparable<T>> : Iterable<T> {


    var root: Node? = null


    inner class Node(var value: T) : TreePrinter.PrintableNode {

        var balanceFactor: Int = 0
        var height: Int = 0

        override var left: Node? = null
        override var right: Node? = null
        override var text: String = value.toString()

    }

    var nodeCounter = 0 //needed for iterator, that is needed for Printer


    fun contains(value: T): Boolean {
        return contains(root, value)
    }

    // searches for node
    fun contains(node: Node?, value: T): Boolean {

        if (node == null)
            return false

        val cmp = value.compareTo(node.value)

        return when {
            cmp < 0 -> contains(node.left, value)
            cmp > 0 -> contains(node.right, value)
            else -> true
        }

    }


    fun insert(value: T?): Boolean {
        return when {
            value == null -> false
            !contains(root, value) -> {
                root = insert(root, value)
                nodeCounter++
                true
            }
            else -> false
        }
    }

    fun insert(node: Node?, value: T): Node {

        if (node == null)
            return Node(value)

        val cmp = value.compareTo(node.value)

        when {
            cmp < 0 -> node.left = insert(node.left, value)
            else -> node.right = insert(node.right, value)
        }

        update(node)
        return balance(node)
    }


    // boolean remove, as wished
    fun remove(elem: T?): Boolean {
        var result = false
        if (elem == null)
            return result

        if (contains(root, elem)) {
            root = remove(root, elem)
            nodeCounter--
            result = true
        }

        return result
    }

    fun remove(node: Node?, elem: T): Node? {

        if (node == null)
            return null

        val comparable = elem.compareTo(node.value)

        when {
            comparable < 0 -> node.left = remove(node.left, elem)
            comparable > 0 -> node.right = remove(node.right, elem)
            else ->
                when {
                    node.left == null -> return node.right //right subtree, swap removeable with its right child
                    node.right == null -> return node.left // same but left

                }
        }

        update(node)
        return balance(node)
    }

    fun update(node: Node) {
        var leftNodeHeight = 0
        var rightNodeHeight = 0

        when {
            node.left == null -> leftNodeHeight = -1
            else -> node.left!!.height
        }

        when {
            node.right == null -> rightNodeHeight = -1
            else -> node.right!!.height
        }

        node.height = 1 + max(leftNodeHeight, rightNodeHeight)
        node.balanceFactor = rightNodeHeight - leftNodeHeight
    }


    fun balance(node: Node): Node { // problemes! нужно добавить условие чтобы учитывался вес сабтри
        // что-то с ротациями

        return when {
            node.balanceFactor == -2 ->

                if (node.left!!.balanceFactor <= 0) {
                    //rightRotation(node)
                    leftRotation(node)
                } else {
                    //bigRightRotation(node)
                    bigLeftRotation(node)
                }

            node.balanceFactor == +2 ->

                if (node.right!!.balanceFactor >= 0) {
                    // leftRotation(node)
                    rightRotation(node)
                } else {
                    // bigLeftRotation(node)
                    bigRightRotation(node)
                }

            else -> node
        }
    }


    // Check rotations, might be mistake with names
    fun leftRotation(node: Node): Node {
        val newParent = node.right
        node.right = newParent!!.left
        newParent.left = node
        update(node)
        update(newParent)
        return newParent
    }

    fun rightRotation(node: Node): Node {
        val newParent = node.left
        node.left = newParent!!.right
        newParent.right = node
        update(node)
        update(newParent)
        return newParent
    }

    fun bigLeftRotation(node: Node): Node {
        node.right = rightRotation(node.right!!)
        return leftRotation(node)
    }

    fun bigRightRotation(node: Node): Node {
        node.left = leftRotation(node.left!!)
        return rightRotation(node)
    }


    // Returns as iterator to traverse the tree in order.
    override fun iterator(): Iterator<T> {

        val expectedNodeCount = nodeCounter
        val stack: Stack<Node> = Stack()
        stack.push(root)

        return object : Iterator<T> {
            var travers = root

            override fun hasNext(): Boolean =
                if (expectedNodeCount != nodeCounter) throw ConcurrentModificationException()
                else root != null && !stack.isEmpty()

            override fun next(): T {

                if (expectedNodeCount != nodeCounter) throw ConcurrentModificationException()

                while (travers != null && travers!!.left != null) {
                    stack.push(travers!!.left)
                    travers = travers!!.left
                }

                val node = stack.pop()

                if (node.right != null) {
                    stack.push(node.right)
                    travers = node.right
                }

                return node.value
            }

        }
    }

    override fun toString(): String {
        val printer = TreePrinter()
        return printer.getTreeDisplay(root)

    }

    fun checkInvariant(node: Node?): Boolean {
        if (node == null)
            return true
        val check = node.value
        val left = node.left
        val right = node.right
        var result = true

        if (left != null && left.value >= check)
            result = false
        if (right != null && right.value <= check)
            result = false

        return result
    }

}
