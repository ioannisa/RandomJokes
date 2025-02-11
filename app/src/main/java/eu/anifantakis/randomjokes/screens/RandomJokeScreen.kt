package eu.anifantakis.randomjokes.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import eu.anifantakis.randomjokes.Joke
import org.koin.androidx.compose.koinViewModel

@Composable
fun RandomJokeScreen(viewModel: JokeViewModel = koinViewModel()) {
    val jokes = viewModel.jokes.collectAsStateWithLifecycle().value

    Column(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = { viewModel.refreshJokes() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Refresh Jokes")
        }
        LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            items(jokes) { joke ->
                JokeItem(joke = joke, onFavoriteClick = { viewModel.markFavorite(joke) })
                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
            }
        }
    }
}

@Composable
fun JokeItem(joke: Joke, onFavoriteClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Text(text = joke.setup, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = joke.punchline, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        IconButton(onClick = onFavoriteClick) {
            if (joke.isFavorite) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Favorite",
                    tint = MaterialTheme.colorScheme.primary
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "Not Favorite"
                )
            }
        }
    }
}