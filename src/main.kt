import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.lifecycle.viewmodel.compose.viewModel
import deck.DeckViewModel
import deck.DeckPane
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.HorizontalSplitPane
import search.SearchPane
import theme.AppTheme
import utils.HorizontalSplitPaneSplitter

@OptIn(ExperimentalSplitPaneApi::class)
fun main() = application {
    AppTheme {
        Window(
            title = "Poké-Fun",
            onCloseRequest = ::exitApplication
        ) {
            val sharedDeckModel = viewModel { DeckViewModel() }

            HorizontalSplitPane(
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            ) {
                first(320.dp) {
                    SearchPane(sharedDeckModel, modifier = Modifier.padding(10.dp).fillMaxSize())
                }
                second {
                    DeckPane(sharedDeckModel, modifier = Modifier.fillMaxSize())
                }
                splitter {
                    HorizontalSplitPaneSplitter()
                }
            }
        }
    }
}
