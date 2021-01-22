package phonebook

import java.util.*
import kotlin.math.min
import kotlin.math.sqrt

class Directory(arg: List<String>) {
    private var records: MutableList<Record> = mutableListOf()

    init {
        for (line in arg) {
            records.add(Record(line.substringAfter(" "), line.substringBefore(" ")))
        }
    }

    fun linearSearch(name: String): Record? = records.find { record -> record.name == name }

    fun jumpSearch(name: String): Record? {
        var blockSize = sqrt(records.size.toDouble()).toInt()
        var index = 0
        while (blockSize < records.size && records[blockSize].name <= name) {
            index = blockSize
            blockSize += sqrt(records.size.toDouble()).toInt()
        }
        for (i in index until min(blockSize, records.size)) {
            if (records[i].name == name) return records[i]
        }
        return null
    }

    fun binarySearch(name: String): Record? {
        var left = 0
        var right = records.lastIndex
        while (left <= right) {
            val mid = (left + right) / 2
            when {
                records[mid].name < name -> left = mid + 1
                records[mid].name > name -> right = mid - 1
                else -> return records[mid]
            }
        }
        return null
    }

    fun bubbleSort() {
        for (i in records.indices) {
            for (j in 0 until records.lastIndex - i) {
                if (records[j].name > records[j + 1].name) {
                    records[j] = records[j + 1].also { records[j + 1] = records[j]}
                }
            }
        }
    }

    fun quickSort() {
        var pivot = 0
        var startIndex = 0
        var endIndex = records.lastIndex
        val stack = Stack<Int>()

        // Push initial values of indexes to stack
        stack.push(startIndex)
        stack.push(endIndex)

        while (stack.isNotEmpty()) {
            endIndex = stack.pop()
            startIndex = stack.pop()
            pivot = partition(startIndex, endIndex)

            // If there are elements on the left side of the pivot,
            // then push left side to stack
            if (pivot - 1 > startIndex) {
                stack.push(startIndex)
                stack.push(pivot - 1)
            }

            // If there are elements on the right side of the pivot,
            // then push right side to the stack
            if (pivot + 1 < endIndex) {
                stack.push(pivot + 1)
                stack.push(endIndex)
            }
        }
    }

    private fun partition(startIndex: Int, endIndex: Int): Int {
        var j = startIndex

        for (i in (startIndex + 1)..endIndex) {
            if (records[i].name <= records[startIndex].name) {
                j++
                records[j] = records[i].also { records[i] = records[j]}
            }
        }
        records[j] = records[startIndex].also { records[startIndex] = records[j]}
        return j
    }
}

data class Record(val name: String, val phoneNum: String)