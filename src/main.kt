import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.lifecycle.viewmodel.compose.viewModel
import deck.DeckModel
import deck.DeckView
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.HorizontalSplitPane
import org.jetbrains.skiko.Cursor
import search.SearchView
import theme.AppTheme

@OptIn(ExperimentalSplitPaneApi::class)
fun main() = application {
    AppTheme {
        Window(
            title = "Poké-Fun",
            onCloseRequest = ::exitApplication
        ) {
            val deckModel = viewModel { DeckModel() }
            HorizontalSplitPane {
                first(200.dp) {
                    Box(Modifier.background(MaterialTheme.colorScheme.background).padding(10.dp).fillMaxSize()) {
                        SearchView(deckModel)
                    }
                }
                second {
                    Box(Modifier.background(MaterialTheme.colorScheme.background).padding(10.dp).fillMaxSize()) {
                        DeckView(deckModel)
                    }
                }
                splitter {
                    visiblePart {
                        Box(Modifier.requiredWidth(1.dp).fillMaxHeight().background(Color.Transparent))
                    }
                    handle {
                        Box(
                            Modifier.markAsHandle().pointerHoverIcon(PointerIcon(Cursor(Cursor.E_RESIZE_CURSOR)))
                                .requiredWidth(1.dp).fillMaxHeight().background(Color.Gray)
                        )
                    }
                }
            }
        }
    }
}
