package eu.anifantakis.randomjokes.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.anifantakis.randomjokes.Joke
import eu.anifantakis.randomjokes.repository.JokeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class JokeViewModel(
    private val repository: JokeRepository
) : ViewModel() {
    // Expose cached jokes as a StateFlow
    val jokes: StateFlow<List<Joke>> = repository.getCachedJokes().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    fun refreshJokes() {
        viewModelScope.launch {
            repository.fetchAndStoreRandomJokes()
        }
    }

    fun markFavorite(joke: Joke) {
        viewModelScope.launch {
            repository.markAsFavorite(joke)
        }
    }
}
