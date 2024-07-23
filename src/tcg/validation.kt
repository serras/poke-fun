package tcg

import arrow.core.NonEmptyList
import arrow.core.toNonEmptyListOrNull

fun Deck.validate(): NonEmptyList<String>? = buildList {
    if (cards.size > 60) add("Too many cards")
    if (cards.size < 60) add("More cards needed")
    if (title.isBlank()) add("Title is blank")
}.toNonEmptyListOrNull()