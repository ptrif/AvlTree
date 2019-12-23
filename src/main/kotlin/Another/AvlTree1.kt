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
        override val text: String = value.toString()

    }


    var nodeCounter = 0 //needed for iterator, that is needed for Printer


    fun insert(value: T?): Boolean {

        return if (value != null && !contains(value)) {
            root = insert(root, value)
            balance(find(value)!!)
            nodeCounter++
            true
        } else
            false

    }

    private fun insert(node: Node?, value: T): Node {

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

    /*

    fun remove(elem: T?): Boolean {

        return if (elem != null && contains(elem)) {
            root = remove(root, elem)
            nodeCounter--
            true
        } else
            false

    }

     */
    private var isLeftChild: Boolean = false

    fun remove(element: T): Boolean {
        if (root == null)
            return false

        var parent = root
        var current = root
        isLeftChild = true
        
        while (current!!.value.compareTo(element) != 0) {
            parent = current
            when {
                current.value > element -> {
                    isLeftChild = true
                    current = current.left
                }
                else -> {
                    isLeftChild = false
                    current = current.right
                }
            }

            if (current == null)
                return false
        }
        remove(current, parent!!)
        nodeCounter--
        return true
    }

    private fun remove(current: Node, parent: Node): Node {
        when {
            current.left == null && current.right == null ->
                when {
                current == root -> root = null
                isLeftChild -> parent.left = null
                else -> parent.right = null
            }
            current.left == null ->
                when {
                current == root -> root = current.right
                isLeftChild -> parent.left = current.right
                else -> parent.right = current.right
            }
            current.right == null ->
                when {
                current == root -> root = current.left
                isLeftChild -> parent.left = current.left
                else -> parent.right = current.left
            }
            else -> {
                val successor = getSuccessor(current)
                when {
                    current == root -> root = successor
                    isLeftChild -> parent.left = successor
                    else -> parent.right = successor
                }
                successor.left = current.left
            }
        }
        update(current)
        return balance(current)

    }
//helper for two-links node
    private fun getSuccessor(node: Node): Node {
        var parent = node
        var successor = node
        var current = node.right

        while (current != null) {
            parent = successor
            successor = current
            current = current.left
        }
        if (successor !== node.right) {
            parent.left = successor.right
            successor.right = node.right
        }
        return successor
    }


    fun contains(elem: T): Boolean {
        val closest = find(elem)
        return closest != null && elem.compareTo(closest.value) == 0
    }

    // searches for node
    private fun find(value: T): Node? =
        root?.let { find(it, value) }

    private fun find(node: Node?, value: T): Node? {

        if (node == null)
            return node

        val cmp = value.compareTo(node.value)

        return when {
            cmp < 0 -> node.left?.let { find(it, value) } ?: node
            cmp > 0 -> node.right?.let { find(it, value) } ?: node
            else -> node
        }

    }


    private fun update(node: Node) {

        val leftNodeHeight = if (node.left == null) -1 else node.left!!.height
        val rightNodeHeight = if (node.right == null) -1 else node.right!!.height

        node.height = 1 + max(leftNodeHeight, rightNodeHeight)
        node.balanceFactor = rightNodeHeight - leftNodeHeight

    }


    private fun balance(node: Node): Node {

        return when {
            node.balanceFactor == -2 ->

                if (node.left!!.balanceFactor <= 0) {
                    rightRotation(node)


                } else {
                    bigRightRotation(node)
                }

            node.balanceFactor == +2 ->

                if (node.right!!.balanceFactor >= 0) {
                    leftRotation(node)


                } else {
                    bigLeftRotation(node)

                }

            else -> node
        }
    }


    private fun leftRotation(node: Node): Node {
        val newParent = node.right
        node.right = newParent!!.left
        newParent.left = node
        update(node)
        update(newParent)
        return newParent
    }

    private fun rightRotation(node: Node): Node {
        val newParent = node.left
        node.left = newParent!!.right
        newParent.right = node
        update(node)
        update(newParent)
        return newParent
    }

    private fun bigLeftRotation(node: Node): Node {
        node.right = rightRotation(node.right!!)
        return leftRotation(node)
    }

    private fun bigRightRotation(node: Node): Node {
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

    internal fun checkInvariant(node: Node?): Boolean {
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
