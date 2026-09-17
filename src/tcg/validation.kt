@file:OptIn(ExperimentalRaiseAccumulateApi::class)

package tcg

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.raise.ExperimentalRaiseAccumulateApi
import arrow.core.raise.context.Raise
import arrow.core.raise.context.accumulate
import arrow.core.raise.context.either
import arrow.core.toNonEmptyListOrNull

fun Deck.validate(): NonEmptyList<String>? = buildList {
    if (cards.size > 60) add("Too many cards")
    if (cards.size < 60) add("More cards needed")
    if (title.isBlank()) add("Title is blank")
}.toNonEmptyListOrNull()

// === implementation using Raise (preferred) ===

fun Deck.validateRaise(): NonEmptyList<String>? =
    either { this@validateRaise.validateRaiseImpl() }.leftOrNull()

context(raise: Raise<NonEmptyList<String>>)
fun Deck.validateRaiseImpl() = accumulate {
    // your implementation here
}

// === implementation using Either ===

fun Deck.validateEither(): NonEmptyList<String>? =
    this.validateEitherImpl().leftOrNull()

fun Deck.validateEitherImpl(): Either<NonEmptyList<String>, Unit> = either {
    accumulate {  }
}

