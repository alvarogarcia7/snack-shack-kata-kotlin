package starter.kotlin

class SandwichScheduler(val amountOfSandwiches: Int) {
    fun calculate(): Schedule {
        val tasks = mutableListOf<Task>()
        tasks.add(Task("0", "start making sandwich 1"))
        var time = 60
        for (i in 1..amountOfSandwiches - 1) {
            tasks.add(Task("" + time, "serve sandwich $i"))
            time += 30
            tasks.add(Task("" + time, "make sandwich ${i + 1}"))
            time += 60
        }
        tasks.add(Task("" + time, "serve sandwich $amountOfSandwiches"))
        time += 30
        tasks.add(Task("" + time, "take a well earned break"))
        return Schedule(tasks)
    }

}
