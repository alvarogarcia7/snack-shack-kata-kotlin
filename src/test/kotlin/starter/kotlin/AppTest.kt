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
                SandwichScheduler(4, starter.kotlin.Clock()).calculate().shouldEqual(Schedule(listOf(
                        Task("0", "start making sandwich 1"),
                        Task("60", "serve sandwich 1"),
                        Task("90", "make sandwich 2"),
                        Task("150", "serve sandwich 2"),
                        Task("180", "make sandwich 3"),
                        Task("240", "serve sandwich 3"),
                        Task("270", "make sandwich 4"),
                        Task("330", "serve sandwich 4"),
                        Task("360", "take a well earned break"))))
            }
            given("should give you an estimate") {
                it("from the beginning") {
                    SandwichScheduler(4, starter.kotlin.Clock()).order(1).shouldEqual(Estimate("" + (360 + 60 + 30)))
                }
                it("after the schedule has started") {
                    val clock = mock<Clock> {
                        on { currentTime() } doReturn 1
                    }
                    SandwichScheduler(4, clock).order(1).shouldEqual(Estimate("" + (360 + 60 + 30 - 1)))
                }
            }
        }
    }
})