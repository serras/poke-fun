package tcg

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import androidx.compose.material3.Card as Material3Card

@Composable
fun Card.view(
    extra: @Composable ColumnScope.() -> Unit = { }
) {
    Material3Card(modifier = Modifier.width(150.dp).padding(5.dp)) {
        Column {
            Text(name, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            KamelImage(
                resource = asyncPainterResource(data = "https://images.pokemontcg.io/sv2/183_hires.png"),
                contentDescription = null,
                modifier = Modifier.padding(5.dp)
            )
            extra()
        }
    }
}