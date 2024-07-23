package tcg

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import androidx.compose.material3.Card as Material3Card

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MultipleCards(
    cards: List<Card>,
    modifier: Modifier = Modifier,
    extra: @Composable ColumnScope.(Card) -> Unit = { }
) {
    Box(modifier) {
        val scrollState = rememberScrollState()
        FlowRow(
            modifier = Modifier.fillMaxSize().verticalScroll(scrollState)
        ) {
            for (card in cards) {
                SingleCard(card, extra = extra)
            }
        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(scrollState)
        )
    }
}

@Composable
fun SingleCard(
    card: Card,
    modifier: Modifier = Modifier,
    extra: @Composable ColumnScope.(Card) -> Unit = { }
) {
    Material3Card(modifier = modifier.width(150.dp).padding(5.dp)) {
        Column {
            Text(
                card.name,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth().padding(2.dp)
            )
            KamelImage(
                resource = { card.imageResource },
                contentDescription = card.identifier,
                modifier = Modifier.padding(5.dp)
            )
            extra(card)
        }
    }
}