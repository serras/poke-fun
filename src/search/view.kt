package search

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import tcg.Card
import tcg.MultipleCards

@Composable
fun SearchPane(
    onAdd: (Card) -> Unit,
    search: SearchViewModel = viewModel { SearchViewModel() },
    modifier: Modifier = Modifier
) {
    val options by search.options.collectAsState()
    val searchResult by search.result.collectAsState()

    Box(modifier) {
        Column {
            TextField(
                value = options.text,
                onValueChange = search::updateText,
                label = { Text("Card name", fontSize = 10.sp) },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search by card name") },
                modifier = Modifier.fillMaxWidth()
            )
            when (val result = searchResult) {
                is SearchStatus.Loading ->
                    CircularProgressIndicator(
                        modifier = Modifier.height(20.dp).padding(10.dp)
                    )
                is SearchStatus.Error ->
                    Text(
                        "Problems during search",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(10.dp)
                    )
                is SearchStatus.Ok if result.isEmpty -> Text(
                    "No match found",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(10.dp)
                )
                is SearchStatus.Ok -> MultipleCards(
                    cards = result.results,
                    modifier = Modifier.fillMaxSize()
                ) { card ->
                    TextButton(
                        onClick = { onAdd(card) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Add ${card.name}")
                        Text("Add", color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}