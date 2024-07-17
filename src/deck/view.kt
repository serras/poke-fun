package deck

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tcg.api.FakePokemonTcgApi
import tcg.view

@Composable
fun DeckView(deck: DeckModel, modifier: Modifier = Modifier) {
    FakePokemonTcgApi.FAKE_CARDS.view(modifier)
}