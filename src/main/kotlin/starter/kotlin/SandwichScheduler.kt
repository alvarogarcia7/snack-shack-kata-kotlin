package starter.kotlin

class SandwichScheduler(val amountOfSandwiches: Int, val clock: starter.kotlin.Clock) {

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
        takeABreak(tasks, time)
        return Schedule(tasks)
    }

    private fun takeABreak(tasks: MutableList<Task>, time: Int) {
        tasks.add(Task("" + time, "take a well earned break"))
    }

    private fun startMakingFirstSandwich(tasks: MutableList<Task>, time: Int): Int {
        tasks.add(Task("" + time, "start making sandwich 1"))
        return time + Companion.SANDWICH_PREPARATION_TIME
    }

    private fun makeSandwich(tasks: MutableList<Task>, time: Int, i: Int): Int {
        tasks.add(Task("" + time, "make sandwich ${i + 1}"))
        return time + Companion.SANDWICH_PREPARATION_TIME
    }

    private fun serveSandwich(tasks: MutableList<Task>, time: Int, i: Int): Int {
        tasks.add(Task("" + time, "serve sandwich $i"))
        return time + Companion.SANDWICH_SERVING_TIME
    }

    private fun `shouldStartANewSandwich?`(i: Int) = i < amountOfSandwiches

    fun order(amountOfSandwiches: Int): Estimate {
        return Estimate("" + SandwichScheduler(this.amountOfSandwiches + amountOfSandwiches, starter.kotlin.Clock()).calculate().tasks.last().timeOfStart)
    }

    companion object {
        private const val SANDWICH_SERVING_TIME = 30
        private const val SANDWICH_PREPARATION_TIME = 60
    }

}
