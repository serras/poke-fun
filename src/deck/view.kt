package deck

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BoxScope.DeckView(deck: DeckModel) {
    val scrollState = rememberScrollState()
    FlowRow(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState)
    ) {
        for (i in 1 .. 100) {
            Card(modifier = Modifier.width(150.dp).padding(5.dp)) {
                Column {
                    Text("#$i", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                    KamelImage(
                        resource = asyncPainterResource(data = "https://images.pokemontcg.io/sv2/183_hires.png"),
                        contentDescription = null,
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
        }
    }
    VerticalScrollbar(
        modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
        adapter = rememberScrollbarAdapter(scrollState)
    )
}