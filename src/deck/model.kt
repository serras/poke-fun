package deck

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import tcg.Card

class DeckModel: ViewModel() {
    private val _deck = mutableStateOf(emptyList<Card>())
    val deck: List<Card> by _deck
    
    private val _problems = mutableStateOf(emptyList<String>())
    val problems: List<String> by _problems

    fun add(card: Card) {
        _deck.value += card
        _problems.value = validate()  // should be replaced with proper validation
    }

    private fun validate(): List<String> = when {
        deck.size > 60 -> listOf("Too many cards")
        deck.size < 60 -> listOf("More cards needed")
        else -> emptyList()  // exactly 60
    }
}