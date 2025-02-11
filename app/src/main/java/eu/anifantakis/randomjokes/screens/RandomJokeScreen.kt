package eu.anifantakis.randomjokes.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import eu.anifantakis.randomjokes.screens.components.JokeItem
import eu.anifantakis.randomjokes.screens.components.LoadingIndicator
import org.koin.androidx.compose.koinViewModel

@Composable
fun RandomJokesScreen(viewModel: JokesViewModel = koinViewModel()) {
    val jokes = viewModel.nonFavoriteJokes.collectAsStateWithLifecycle(emptyList()).value
    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle().value

    LoadingIndicator(
        isLoading = isLoading,
        isCritical = false
    )

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

