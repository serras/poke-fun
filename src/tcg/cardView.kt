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
fun List<Card>.view(
    modifier: Modifier = Modifier,
    extra: @Composable ColumnScope.(Card) -> Unit = { }
) {
    Box(modifier) {
        val scrollState = rememberScrollState()
        FlowRow(
            modifier = Modifier.fillMaxSize().verticalScroll(scrollState)
        ) {
            this@view.forEach { card -> card.view { extra(card) } }
        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(scrollState)
        )
    }
}

@Composable
fun Card.view(
    extra: @Composable ColumnScope.() -> Unit = { }
) {
    Material3Card(modifier = Modifier.width(150.dp).padding(5.dp)) {
        Column {
            Text(
                name,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth().padding(2.dp)
            )
            KamelImage(
                resource = { imageResource },
                contentDescription = identifier,
                modifier = Modifier.padding(5.dp)
            )
            extra()
        }
    }
}