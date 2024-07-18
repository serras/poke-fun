package utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.SplitterScope
import org.jetbrains.skiko.Cursor

@OptIn(ExperimentalSplitPaneApi::class)
fun SplitterScope.HorizontalSplitPaneSplitter() {
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

@OptIn(ExperimentalSplitPaneApi::class)
fun SplitterScope.VerticalSplitPaneSplitter() {
    visiblePart {
        Box(Modifier.requiredHeight(1.dp).fillMaxWidth().background(Color.Transparent))
    }
    handle {
        Box(
            Modifier.markAsHandle().pointerHoverIcon(PointerIcon(Cursor(Cursor.N_RESIZE_CURSOR)))
                .requiredHeight(1.dp).fillMaxWidth().background(Color.Gray)
        )
    }
}