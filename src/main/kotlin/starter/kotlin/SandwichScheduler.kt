package starter.kotlin

class SandwichScheduler(private val clock: starter.kotlin.Clock) {

    private var amountOfSandwiches: Int = 0

    fun schedule(amountOfSandwiches: Int): Schedule {
        this.amountOfSandwiches = amountOfSandwiches
        val tasks = mutableListOf<Task>()
        var time = 0
        time = startMakingFirstSandwich(tasks, time)
        for (i in 1..amountOfSandwiches) {
            time = serveSandwich(tasks, time, i)
            if (`shouldStartANewSandwich?`(i, amountOfSandwiches)) {
                time = makeSandwich(tasks, time, i)
            }
        }
        takeABreak(tasks, time)
        return Schedule(tasks)
    }

    private fun takeABreak(tasks: MutableList<Task>, time: Int) {
        tasks.add(Task(time, "take a well earned break"))
    }

    private fun startMakingFirstSandwich(tasks: MutableList<Task>, time: Int): Int {
        tasks.add(Task(time, "start making sandwich 1"))
        return time + Companion.SANDWICH_PREPARATION_TIME
    }

    private fun makeSandwich(tasks: MutableList<Task>, time: Int, i: Int): Int {
        tasks.add(Task(time, "make sandwich ${i + 1}"))
        return time + Companion.SANDWICH_PREPARATION_TIME
    }

    private fun serveSandwich(tasks: MutableList<Task>, time: Int, i: Int): Int {
        tasks.add(Task(time, "serve sandwich $i"))
        return time + Companion.SANDWICH_SERVING_TIME
    }

    private fun `shouldStartANewSandwich?`(i: Int, amountOfSandwiches: Int) = i < amountOfSandwiches

    fun order(amountOfSandwiches: Int): Estimate {
        val timeOfStartFromNow = SandwichScheduler(this.clock).schedule(this.amountOfSandwiches + amountOfSandwiches).tasks.last().timeOfStart - clock.currentTime()
        return Estimate(timeOfStartFromNow)
    }

    companion object {
        private const val SANDWICH_SERVING_TIME = 30
        private const val SANDWICH_PREPARATION_TIME = 60
    }

}
