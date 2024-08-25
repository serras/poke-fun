package deck

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun DeckPaneWithDetails(
    deck: DeckViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(navController = rememberNavController(), startDestination = "main") {
        composable("main") {
            DeckPane(deck, modifier)
        }
        composable("detail/{cardId}") { entry ->
            val cardId = entry.arguments?.getString("cardId")
        }
    }
}