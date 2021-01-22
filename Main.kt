package phonebook

import java.io.File

fun humanizeTime(time: Long): String {
    val minutes = time / (1000 * 60)
    val seconds = (time % (1000 * 60)) / 1000
    val milliseconds = time % 1000
    return "$minutes min. $seconds sec. $milliseconds ms."
}

fun linearSearch(targets: List<String>, directory: Directory) {
    println("Start searching (linear search)...")
    var foundCount = 0
    val linearStartTime = System.currentTimeMillis()
    for (target in targets) {
        val found = directory.linearSearch(target)
        if (found != null) foundCount++
    }

    println("Found $foundCount / ${targets.size} entries. Time taken: " +
            humanizeTime(System.currentTimeMillis() -linearStartTime))
    println()
}

fun bubbleSortAndJumpSearch(targets: List<String>, directory: Directory) {
    println("Start searching (bubble sort + jump search)...")

    val bubbleStartTime = System.currentTimeMillis()
    directory.bubbleSort()
    val bubbleTime = System.currentTimeMillis() - bubbleStartTime

    val jumpStartTime = System.currentTimeMillis()
    var foundCount = 0
    for (target in targets) {
        val found = directory.jumpSearch(target)
        if (found != null) foundCount++
    }
    val jumpTime = System.currentTimeMillis() - jumpStartTime

    println("Found $foundCount / ${targets.size} entries. Time taken: " +
            humanizeTime(jumpTime + bubbleTime))
    println("Sorting time: ${humanizeTime(bubbleTime)}")
    println("Searching time: ${humanizeTime(jumpTime)}")
    println()
}

fun quickSortAndBinarySearch(targets: List<String>, directory: Directory) {
    println("Start searching (quick sort + binary search)...")

    val quickStartTime = System.currentTimeMillis()
    directory.quickSort()
    val quickTime = System.currentTimeMillis() - quickStartTime

    val binaryStartTime = System.currentTimeMillis()
    var foundCount = 0
    for (target in targets) {
        val found = directory.binarySearch(target)
        if (found != null) foundCount++
    }
    val binaryTime = System.currentTimeMillis() - binaryStartTime

    println("Found $foundCount / ${targets.size} entries. Time taken: " +
            humanizeTime(binaryTime + quickTime))
    println("Sorting time: ${humanizeTime(quickTime)}")
    println("Searching time: ${humanizeTime(binaryTime)}")
    println()
}

fun hashTableSearch(targets: List<String>, list: List<String>) {
    println("Start searching (hash table)...")
    val hashmap = mutableMapOf<String, String>()
    val creationStartTime = System.currentTimeMillis()
    for (line in list) {
        hashmap[line.substringAfter(" ")] = line
    }
    val creationTime = System.currentTimeMillis() - creationStartTime

    val searchStartTime = System.currentTimeMillis()
    var foundCount = 0
    for (target in targets) {
        if (hashmap.containsKey(target)) {
            foundCount++
        }
    }
    val searchTime = System.currentTimeMillis() - searchStartTime

    println("Found $foundCount / ${targets.size} entries. Time taken: " +
            humanizeTime(creationTime + searchTime))
    println("Creating time: ${humanizeTime(creationTime)}")
    println("Searching time: ${humanizeTime(searchTime)}")
}


fun main() {
    val targets = File("find.txt").readLines()
    val dirList = File("directory.txt").readLines().subList(0, 50000)
    val directory = Directory(dirList)

    linearSearch(targets, directory)
    bubbleSortAndJumpSearch(targets, directory)
    quickSortAndBinarySearch(targets, directory)
    hashTableSearch(targets, dirList)
}
