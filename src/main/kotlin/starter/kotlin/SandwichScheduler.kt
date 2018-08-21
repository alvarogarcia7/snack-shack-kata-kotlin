package starter.kotlin

class SandwichScheduler(val amountOfSandwiches: Int) {

    private val SANDWICH_PREPARATION_TIME = 60
    private val SANDWICH_SERVING_TIME = 30

    fun calculate(): Schedule {
        val tasks = mutableListOf<Task>()
        tasks.add(Task("0", "start making sandwich 1"))
        var time = SANDWICH_PREPARATION_TIME
        for (i in 1..amountOfSandwiches) {
            tasks.add(Task("" + time, "serve sandwich $i"))
            time += SANDWICH_SERVING_TIME
            if (`shouldStartANewSandwich?`(i)) {
                tasks.add(Task("" + time, "make sandwich ${i + 1}"))
                time += SANDWICH_PREPARATION_TIME
            }
        }
        tasks.add(Task("" + time, "take a well earned break"))
        return Schedule(tasks)
    }

    private fun `shouldStartANewSandwich?`(i: Int) = i < amountOfSandwiches

}
