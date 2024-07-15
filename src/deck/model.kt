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
}