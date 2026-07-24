package deck

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Redo
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import io.github.vinceglb.filekit.dialogs.FileKitDialogSettings
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.dialogs.compose.rememberFileSaverLauncher
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.VerticalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState
import tcg.MultipleCards
import utils.VerticalSplitPaneSplitter

@OptIn(ExperimentalSplitPaneApi::class)
@Composable
fun DeckPane(
    deck: DeckViewModel,
    modifier: Modifier = Modifier
) {
    val deckState by deck.deck.collectAsState()
    val problems by deck.problems.collectAsState()
    val sortedCards = remember(deckState.cards) { deckState.cards.sorted() }

    Column(modifier) {
        TopAppBar(
            title = {
                BasicTextField(
                    deckState.title,
                    onValueChange = deck::changeTitle,
                    textStyle = MaterialTheme.typography.headlineMedium.copy(color = MaterialTheme.colorScheme.primary),
                    singleLine = true,
                )
            },
            actions = {
                IconButton(
                    onClick = { deck.clear() }
                ) { Icon(Icons.Default.Delete, contentDescription = "Clear") }

                val openPicker = rememberFilePickerLauncher(
                    type = FileKitType.File(extensions = listOf("deck"))
                ) { file ->
                    /* what to do with the chosen file */
                }
                IconButton(
                    onClick = { openPicker.launch() },
                    enabled = false
                ) { Icon(Icons.Default.FileOpen, contentDescription = "Open") }

                val savePicker = rememberFileSaverLauncher(
                    dialogSettings = FileKitDialogSettings.createDefault()
                ) { file ->
                    if (file != null) {
                        /* what to do with the chosen file */
                    }
                }
                IconButton(
                    onClick = { savePicker.launch(suggestedName = deckState.title, defaultExtension = "deck") },
                    enabled = false,
                ) { Icon(Icons.Default.Save, contentDescription = "Save") }

                VerticalDivider()

                IconButton(
                    onClick = { },
                    enabled = false
                ) { Icon(Icons.AutoMirrored.Filled.Undo, contentDescription = "Undo") }
                IconButton(
                    onClick = { },
                    enabled = false
                ) { Icon(Icons.AutoMirrored.Filled.Redo, contentDescription = "Redo") }

                VerticalDivider()

                IconButton(
                    onClick = { deck.suggestTitle() }
                ) { Icon(Icons.Default.Diamond, contentDescription = "Suggest Title") }
            }
        )
        VerticalSplitPane(
            splitPaneState = rememberSplitPaneState(1.0f),
            modifier = Modifier.fillMaxSize().padding(5.dp)
        ) {
            first {
                MultipleCards(
                    cards = sortedCards,
                    modifier = Modifier.fillMaxSize()
                )
            }
            second(60.dp) {
                when (val problems = problems) {
                    null -> DeckProblemLine("Everything is fine :)", fontStyle = FontStyle.Italic)
                    else -> DeckProblems(
                        problems,
                        Modifier.background(MaterialTheme.colorScheme.background)
                    )
                }
            }
            splitter {
                VerticalSplitPaneSplitter()
            }
        }
    }
}

@Composable
fun DeckProblems(problems: List<String>, modifier: Modifier = Modifier) {
    Surface(modifier) {
        Box(Modifier.fillMaxSize()) {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier.verticalScroll(scrollState).fillMaxSize()
            ) {
                for (problem in problems) {
                    DeckProblemLine(problem)
                }
            }
            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                adapter = rememberScrollbarAdapter(scrollState)
            )
        }
    }
}

@Composable
fun DeckProblemLine(problem: String, fontStyle: FontStyle? = null, modifier: Modifier = Modifier) {
    Text(problem, fontStyle = fontStyle, modifier = modifier.padding(2.dp))
}