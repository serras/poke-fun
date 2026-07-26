package tcg

import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.property.Arb
import io.kotest.property.arbitrary.bind
import io.kotest.property.arbitrary.list
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class ValidationTest {
    @Test
    fun emptyReturnsProblem() = runTest {
        checkAll(
            Arb.string(),
            Arb.list(Arb.bind<Card>(), range = 0 .. 59)
        ) { title, cards ->
            val deck = Deck(title, cards)
            deck.validate().shouldNotBeNull()
        }
    }
}