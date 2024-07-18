package deck

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.VerticalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState
import tcg.view
import utils.VerticalSplitPaneSplitter

@OptIn(ExperimentalSplitPaneApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DeckView(deck: DeckModel, modifier: Modifier = Modifier) {
    Column {
        TopAppBar(
            title = {
                BasicTextField(
                    deck.title,
                    onValueChange = deck::changeTitle,
                    textStyle = MaterialTheme.typography.headlineMedium,
                    singleLine = true
                )
            },
            actions = {
                IconButton(
                    onClick = { deck.clear() }
                ) { Icon(Icons.Default.Delete, contentDescription = "Clear") }
                IconButton(
                    onClick = { }
                ) { Icon(Icons.Default.FileOpen, contentDescription = "Open") }
                IconButton(
                    onClick = { }
                ) { Icon(Icons.Default.Save, contentDescription = "Save") }
            }
        )
        VerticalSplitPane(
            splitPaneState = rememberSplitPaneState(1.0f),
            modifier = Modifier.fillMaxSize().padding(5.dp)
        ) {
            first {
                deck.deck.sorted().view(Modifier.fillMaxSize())
            }
            second(50.dp) {
                deck.problems.problems()
            }
            splitter {
                VerticalSplitPaneSplitter()
            }
        }
    }
}

@Composable
fun List<String>.problems() {
    Box {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier.verticalScroll(scrollState).fillMaxSize()
        ) {
            forEach { Text(it, modifier = Modifier.padding(2.dp)) }
        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(scrollState)
        )
    }
}
