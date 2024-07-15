package search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import deck.DeckModel

@Composable
fun SearchView(
    deck: DeckModel,
    search: SearchModel = viewModel<SearchModel>()
) {
    Column {
        TextField(
            value = search.options.text,
            onValueChange = search::updateText,
            label = { Text("Card name", fontSize = 10.sp) },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search by card name") },
            modifier = Modifier.fillMaxWidth(),
        )
        TextButton(
            onClick = { },
            modifier = Modifier.padding(top = 5.dp).fillMaxWidth()
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add card to deck")
            Text("Add")
        }
    }
}