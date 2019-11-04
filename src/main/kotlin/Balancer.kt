import kotlin.math.max


class Balancer {

    fun rebalance(node: Node) {
        setBalance(node)
        var nn = node
        if (nn.balance == -2)
            if (height(nn.left!!.left) >= height(nn.left!!.right))
                nn = rotateRight(nn)
            else
                nn = bigLeftRotation(nn)
        else if (nn.balance == 2)
            if (height(nn.right!!.right) >= height(nn.right!!.left))
                nn = rotateLeft(nn)
            else
                nn = bigRightRotation(nn)
        if (nn.parent != null) rebalance(nn.parent!!)
        else AvlTree().root = nn
    }

    fun rotateLeft(node: Node): Node {
        val newNode = node.right
        val grand = node.parent
        if (newNode == null) return node

        //Rotate
        node.right = newNode.left
        newNode.left = node
        //change parents
        node.parent = newNode
        node.right?.parent = node
        newNode.parent = grand

        if (grand != null) {
            if (node == grand.left) {
                grand.left = newNode
            } else
                grand.right = newNode
        }

        setBalance(node, newNode)
        return newNode
    }

    fun rotateRight(node: Node): Node {
        val newNode = node.left
        val grand = node.parent
        if (newNode == null) return node

        //Rotate
        node.left = newNode
        newNode.right = node
        //change parents
        node.parent = newNode
        node.left?.parent = node
        newNode.parent = grand

        if (grand != null) {
            if (node == grand.left) {
                grand.left = newNode
            } else
                grand.right = newNode
        }

        setBalance(node, newNode)
        return newNode
    }

    //not sure
    fun bigLeftRotation(node: Node): Node {
        node.left = rotateLeft(node.left!!)
        return rotateRight(node)
    }
    //not sure
    fun bigRightRotation(node: Node): Node {
        node.right = rotateRight(node.right!!)
        return rotateLeft(node)
    }

     fun height(node: Node?): Int {
        if (node == null) return -1
        return height(node.left).coerceAtLeast(height(node.right)) //sometimes stack overflows here especially when +1 added
    }

     fun setBalance(vararg nodes: Node) {
        for (n in nodes) n.balance = height(n.right) - height(n.left)
    }

}