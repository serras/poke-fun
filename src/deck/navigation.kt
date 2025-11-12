@file:Suppress("PLUGIN_IS_NOT_ENABLED")
package deck

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import kotlinx.serialization.Serializable

sealed interface Routes : NavKey {
    @Serializable data object Main : Routes
    @Serializable data class Detail(val cardId: String) : Routes
}

@Composable
fun DeckPaneWithDetails(
    deck: DeckViewModel,
    modifier: Modifier = Modifier
) {
    val backStack = remember { mutableStateListOf<Routes>(Routes.Main) }

    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<Routes.Main> {
                DeckPane(deck, modifier)
            }
            entry<Routes.Detail> {
                Text("Card with id ${it.cardId}")
            }
        }
    )
}