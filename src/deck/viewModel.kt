package deck

import ai.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.NonEmptyList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tcg.Card
import tcg.Deck
import tcg.validate

class DeckViewModel(val titler: Titler) : ViewModel() {
    val deck: StateFlow<Deck>
        field: MutableStateFlow<Deck> = MutableStateFlow(Deck.INITIAL)

    val problems: StateFlow<NonEmptyList<String>?>
        field: MutableStateFlow<NonEmptyList<String>?> = MutableStateFlow(Deck.INITIAL.validate())

    private fun updateDeck(transform: (Deck) -> Deck) {
        deck.update(transform)
        problems.value = deck.value.validate()
    }

    fun changeTitle(newTitle: String) { updateDeck { it.copy(title = newTitle) } }
    fun clear() { updateDeck { it.copy(cards = emptyList()) } }
    fun add(card: Card) { updateDeck { it.copy(cards = it.cards + card) } }

    fun suggestTitle() {
        viewModelScope.launch {
            changeTitle(titler.suggest(deck.value).bestTitle)
        }
    }
}