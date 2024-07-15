package search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SearchModel: ViewModel() {
    private val _options = mutableStateOf(SearchOptions.INITIAL)
    val options: SearchOptions by _options
    
    fun updateText(newText: String) {
        _options.value = _options.value.copy(text = newText)
    }
}

data class SearchOptions(val text: String) {
    companion object {
        val INITIAL: SearchOptions = SearchOptions("Pikachu")
    }
}