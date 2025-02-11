package eu.anifantakis.randomjokes.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import eu.anifantakis.randomjokes.screens.components.JokeItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoriteJokesScreen(viewModel: JokesViewModel = koinViewModel()) {
    val jokes = viewModel.favoriteJokes.collectAsStateWithLifecycle(emptyList()).value

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            items(jokes) { joke ->
                JokeItem(joke = joke, onFavoriteClick = { viewModel.markNotFavorite(joke) })
                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
            }
        }
    }
}