@file:Suppress("PLUGIN_IS_NOT_ENABLED")
package deck

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

object Routes {
    @Serializable data object Main
    @Serializable data class Detail(val cardId: String)
}

@Composable
fun DeckPaneWithDetails(
    deck: DeckViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(navController = rememberNavController(), startDestination = Routes.Main) {
        composable<Routes.Main> {
            DeckPane(deck, modifier)
        }
        composable<Routes.Detail> { entry ->
            val cardId = entry.toRoute<Routes.Detail>().cardId
        }
    }
}