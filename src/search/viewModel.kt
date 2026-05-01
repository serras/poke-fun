package search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tcg.Card
import tcg.api.LocalPokemonTcgApi
import tcg.api.PokemonTcgApi

sealed interface SearchStatus {
    data class Loading(val job: Job) : SearchStatus
    data class Ok(val results: List<Card>) : SearchStatus {
        val isEmpty: Boolean = results.isEmpty()
    }
    data object Error : SearchStatus
}

class SearchViewModel(
    private val api: PokemonTcgApi = LocalPokemonTcgApi() // KtorPokemonTcgApi()
) : ViewModel() {
    val options: StateFlow<SearchOptions>
        field: MutableStateFlow<SearchOptions> = MutableStateFlow(SearchOptions.INITIAL)

    val result: StateFlow<SearchStatus>
        field: MutableStateFlow<SearchStatus> = MutableStateFlow(SearchStatus.Ok(emptyList()))

    fun updateText(newText: String) {
        options.update { it.copy(text = newText) }

        // cancel previous job if loading
        (result.value as? SearchStatus.Loading)?.job?.cancel()
        // now start the new job
        result.value = SearchStatus.Loading(
            viewModelScope.launch {
                delay(500.milliseconds)
                Either.catch { api.search(newText) }
                    .fold(
                        ifLeft = { result.value = SearchStatus.Error },
                        ifRight = { result.value = SearchStatus.Ok(it) }
                    )
            }
        )
    }
}

data class SearchOptions(val text: String) {
    companion object {
        val INITIAL: SearchOptions = SearchOptions("")
    }
}