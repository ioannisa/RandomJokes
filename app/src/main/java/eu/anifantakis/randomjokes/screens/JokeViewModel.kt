package eu.anifantakis.randomjokes.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.anifantakis.randomjokes.Joke
import eu.anifantakis.randomjokes.repository.JokeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class JokeViewModel(
    private val repository: JokeRepository
) : ViewModel() {
    // Expose cached jokes as a StateFlow

    private val _jokes = MutableStateFlow<List<Joke>>(emptyList())
    val jokes = _jokes.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getCachedJokes().collect { jokesList ->
                _jokes.value = jokesList
            }
        }
    }

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
