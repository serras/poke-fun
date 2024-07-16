package deck

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import tcg.FakePokemonTcgApi
import tcg.view

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BoxScope.DeckView(deck: DeckModel) {
    val scrollState = rememberScrollState()
    FlowRow(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState)
    ) {
        FakePokemonTcgApi.FAKE_CARDS.forEach { it.view() }
    }
    VerticalScrollbar(
        modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
        adapter = rememberScrollbarAdapter(scrollState)
    )
}