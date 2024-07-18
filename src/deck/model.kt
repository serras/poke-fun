package deck

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import tcg.Card

class DeckModel: ViewModel() {
    private val _title = mutableStateOf("Awesome Deck")
    val title by _title

    private val _deck = mutableStateOf(emptyList<Card>())
    val deck: List<Card> by _deck
    
    private val _problems = mutableStateOf(validate())
    val problems: List<String> by _problems

    fun changeTitle(newTitle: String) {
        _title.value = newTitle
    }

    fun clear() {
        _deck.value = emptyList()
        _problems.value = validate()
    }

    fun add(card: Card) {
        _deck.value += card
        _problems.value = validate()
    }

    private fun validate(): List<String> = when {
        deck.size > 60 -> listOf("Too many cards")
        deck.size < 60 -> listOf("More cards needed")
        else -> emptyList()  // exactly 60
    }
}