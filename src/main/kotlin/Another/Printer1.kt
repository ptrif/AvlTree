package Another

import java.util.ArrayList
import kotlin.math.ceil
import kotlin.math.floor

//взято со StackOverFlow

class TreePrinter {

    interface PrintableNode {

        val left: PrintableNode?
        val right: PrintableNode?
        var text: String

    }

    // Print a binary tree.
    fun getTreeDisplay(root: PrintableNode?): String {

        val sb = StringBuilder()
        val lines = ArrayList<List<String?>>()
        var level = ArrayList<PrintableNode?>()
        var next = ArrayList<PrintableNode?>()

        level.add(root)
        var nn = 1
        var widest = 0

        while (nn != 0) {
            nn = 0
            val line = ArrayList<String?>()
            for (n in level) {
                if (n == null) {
                    line.add(null)
                    next.add(null)
                    next.add(null)
                } else {
                    val aa = n.text
                    line.add(aa)
                    if (aa.length > widest) widest = aa.length

                    next.add(n.left)
                    next.add(n.right)

                    if (n.left != null)
                        nn++
                    if (n.right != null)
                        nn++
                }
            }

            if (widest % 2 == 1)
                widest++

            lines.add(line)

            val tmp = level
            level = next
            next = tmp
            next.clear()
        }

        var perPiece = lines[lines.size - 1].size * (widest + 4)
        for (i in lines.indices) {
            val line = lines[i]
            val hpw = floor((perPiece / 2f).toDouble()).toInt() - 1
            if (i > 0) {
                for (j in line.indices) {

                    // split node
                    var c = ' '
                    if (j % 2 == 1) {
                        if (line[j - 1] != null) {
                            if (line[j] != null)
                                c = ' '
                            else
                                c = ' '  //'это страшно, но я не знаю как по другому
                        } else
                            if (j < line.size && line[j] != null)
                                c = ' '
                    }
                    sb.append(c)

                    // lines and spaces
                    if (line[j] == null) {
                        (0 until perPiece - 1).forEach {
                            sb.append(' ')
                        }
                    } else {
                        (0 until hpw).forEach {
                            sb.append(
                                if (j % 2 == 0)
                                    " "
                                else
                                    " "
                            )
                        }
                        sb.append(
                            if (j % 2 == 0)
                                "|"
                            else
                                "|"
                        )
                        (0 until hpw).forEach {
                            sb.append(
                                if (j % 2 == 0)
                                    " "
                                else " "
                            )
                        }
                    }
                }
                sb.append('\n')
            }

            for (j in line.indices) {
                var f: String? = line[j]
                if (f == null)
                    f = ""
                val gapPos = ceil((perPiece / 2f - f.length / 2f).toDouble()).toInt()
                val gapNeg = floor((perPiece / 2f - f.length / 2f).toDouble()).toInt()

                (0 until gapPos).forEach {
                    sb.append(' ')
                }
                sb.append(f)

                (0 until gapNeg).forEach {
                    sb.append(' ')
                }
            }
            sb.append('\n')

            perPiece /= 2
        }
        return sb.toString()
    }
}

