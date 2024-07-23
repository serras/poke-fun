package deck

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.Snapshot
import androidx.lifecycle.ViewModel
import arrow.core.NonEmptyList
import tcg.Card
import tcg.Deck
import tcg.validate

class DeckViewModel: ViewModel() {
    private val _deck = mutableStateOf(Deck.INITIAL)
    val deck: Deck by _deck
    
    private val _problems = mutableStateOf(deck.validate())
    val problems: NonEmptyList<String>? by _problems

    fun changeTitle(newTitle: String) {
        _deck.update { it.copy(title = newTitle) }
        _problems.value = deck.validate()
    }

    fun clear() {
        _deck.update { it.copy(cards = emptyList()) }
        _problems.value = deck.validate()
    }

    fun add(card: Card) {
        _deck.update { it.copy(cards = it.cards + card) }
        _problems.value = deck.validate()
    }
}


// FROM ARROW-OPTICS-COMPOSE
/**
 * Modifies the value in this [MutableState]
 * by applying the function [block] to the current value.
 */
public inline fun <T> MutableState<T>.update(crossinline block: (T) -> T) {
  Snapshot.withMutableSnapshot {
    value = block(value)
  }
}
