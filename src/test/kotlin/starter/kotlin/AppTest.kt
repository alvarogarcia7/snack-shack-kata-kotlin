package starter.kotlin

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it

object AppTest : Spek({
    describe("Sandwich scheduler") {
        given("it takes 60 seconds to make a sandwich, 30 to serve it and charge the customer") {
            it("should make a schedule for 4 sandwiches") {
                SandwichScheduler(starter.kotlin.Clock()).schedule(4).shouldEqual(Schedule(listOf(
                        Task(0, "start making sandwich 1"),
                        Task(60, "serve sandwich 1"),
                        Task(90, "make sandwich 2"),
                        Task(150, "serve sandwich 2"),
                        Task(180, "make sandwich 3"),
                        Task(240, "serve sandwich 3"),
                        Task(270, "take a well earned break"))))
            }
            given("should give you an estimate") {
                it("from the beginning") {
                    val elapsedTime = 0
                    val clock = clockReading(elapsedTime)
                    val scheduler = SandwichScheduler(clock)
                    scheduler.schedule(4)

                    scheduler.howLongFor(1).shouldEqual(Estimate(270 + 60 + 30 - elapsedTime))
                }
                it("after the schedule has started") {
                    val elapsedTime = 1
                    val clock = clockReading(elapsedTime)
                    val scheduler = SandwichScheduler(clock)
                    scheduler.schedule(4)

                    scheduler.howLongFor(1).shouldEqual(Estimate(270 + 60 + 30 - elapsedTime))
                }
            }

            given("reject orders with more than 5 minutes of waiting") {
                it("accept them when the wait time is less than that") {
                    val elapsedTime = 0
                    val clock = clockReading(elapsedTime)
                    val scheduler = SandwichScheduler(clock)
                    scheduler.schedule(3).shouldEqual(Schedule(listOf(
                            Task(0, "start making sandwich 1"),
                            Task(60, "serve sandwich 1"),
                            Task(90, "make sandwich 2"),
                            Task(150, "serve sandwich 2"),
                            Task(180, "make sandwich 3"),
                            Task(240, "serve sandwich 3"),
                            Task(270, "take a well earned break"))))

                }
                it("denies them when the wait time is more than that") {
                    val elapsedTime = 0
                    val clock = clockReading(elapsedTime)
                    val scheduler = SandwichScheduler(clock)
                    scheduler.schedule(4).shouldEqual(Schedule(listOf(
                            Task(0, "start making sandwich 1"),
                            Task(60, "serve sandwich 1"),
                            Task(90, "make sandwich 2"),
                            Task(150, "serve sandwich 2"),
                            Task(180, "make sandwich 3"),
                            Task(240, "serve sandwich 3"),
                            Task(270, "take a well earned break"))))
                }
            }
        }
    }
})

private fun clockReading(elapsedTime: Int): Clock {
    val clock = mock<Clock> {
        on { currentTime() } doReturn elapsedTime
    }
    return clock
}