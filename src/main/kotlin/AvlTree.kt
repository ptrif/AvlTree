class AvlTree {
    internal var root: Node? = null
    private var balancer = Balancer()

    fun insert(key: Int): Boolean {

        if (root == null)
            root = Node(key, null)
        else {
            var n = root
            var parent: Node?

            while (true) {
                if (n!!.key == key) return false
                parent = n
                val k = n.key //this is needed, else tests would be unreached

                if (k > key)
                    n = n.left
                else
                    n = n.right

                if (n == null) {
                    if (k > key)
                        parent.left = Node(key, parent)
                    else
                        parent.right = Node(key, parent)
                    balancer.rebalance(parent)
                    break
                }
            }
        }
        return true


    }

    fun remove(keyToRemove: Int) {
        if (root == null)
            return

        var n = root
        var parent = root
        var nodeToRemove: Node? = null
        var child = root

        while (child != null) {
            parent = n
            n = child
            if (keyToRemove >= n.key)
                child = n.right
            else
                child = n.left

            if (keyToRemove == n.key)
                nodeToRemove = n
        }
        if (nodeToRemove != null) {
            nodeToRemove.key = n!!.key

            if (n.left != null)
                child = n.left
            else
                child = n.right

            if (0 == root!!.key.compareTo(keyToRemove)) {
                root = child

                if (null != root) {
                    root!!.parent = null
                }

            } else {
                if (parent!!.left != n)
                    parent.right = child
                else
                    parent.left = child

                if (null != child) {
                    child.parent = parent
                }

                balancer.rebalance(parent)
            }
        }
    }


    fun printKey(): String {
        return printKey(root).removeSuffix(",")
    }

    private fun printKey(n: Node?): String {
        var treeStr = ""
        if (n != null) {
            treeStr = printKey(n.left)
            treeStr += "${n.key},"
            treeStr += printKey(n.right)
        }
        return treeStr
    }

}
