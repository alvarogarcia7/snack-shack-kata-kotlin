package starter.kotlin

class SandwichScheduler(val amountOfSandwiches: Int) {

    private val SANDWICH_PREPARATION_TIME = 60
    private val SANDWICH_SERVING_TIME = 30

    fun calculate(): Schedule {
        val tasks = mutableListOf<Task>()
        var time = 0
        time = startMakingFirstSandwich(tasks, time)
        for (i in 1..amountOfSandwiches) {
            time = serveSandwich(tasks, time, i)
            if (`shouldStartANewSandwich?`(i)) {
                time = makeSandwich(tasks, time, i)
            }
        }
        tasks.add(Task("" + time, "take a well earned break"))
        return Schedule(tasks)
    }

    private fun startMakingFirstSandwich(tasks: MutableList<Task>, time: Int): Int {
        tasks.add(Task("" + time, "start making sandwich 1"))
        return time + SANDWICH_PREPARATION_TIME
    }

    private fun makeSandwich(tasks: MutableList<Task>, time: Int, i: Int): Int {
        tasks.add(Task("" + time, "make sandwich ${i + 1}"))
        return time + SANDWICH_PREPARATION_TIME
    }

    private fun serveSandwich(tasks: MutableList<Task>, time: Int, i: Int): Int {
        tasks.add(Task("" + time, "serve sandwich $i"))
        return time + SANDWICH_SERVING_TIME
    }

    private fun `shouldStartANewSandwich?`(i: Int) = i < amountOfSandwiches

}
