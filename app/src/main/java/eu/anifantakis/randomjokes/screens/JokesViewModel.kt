package eu.anifantakis.randomjokes.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.anifantakis.randomjokes.Joke
import eu.anifantakis.randomjokes.repository.JokeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class JokesViewModel(
    private val repository: JokeRepository
) : ViewModel() {
    // Expose cached jokes as a StateFlow

    private val _jokes = MutableStateFlow<List<Joke>>(emptyList())

    // all the jokes that have been fetched from the API
    val jokes = _jokes.asStateFlow()

    // all the jokes that have been marked as favorite
    val favoriteJokes = jokes.map { list ->
        list.filter { it.isFavorite }
    }

    // all the jokes that have been marked as non favorite
    val nonFavoriteJokes = jokes.map { list ->
        list.filter { !it.isFavorite }
    }

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getCachedJokes().collect { jokesList ->
                _jokes.value = jokesList
            }
        }
    }

    fun refreshJokes() {
        viewModelScope.launch {
            _isLoading.value = true  // start loading
            try {
                repository.fetchAndStoreRandomJokes()
            } catch (e: Exception) {
                // Optionally handle or log the error here
            } finally {
                _isLoading.value = false  // loading finished (either success or error)
            }
        }
    }

    fun markFavorite(joke: Joke) {
        viewModelScope.launch {
            repository.markAsFavorite(joke)
        }
    }

    fun markNotFavorite(joke: Joke) {
        viewModelScope.launch {
            repository.markAsNotFavorite(joke)
        }
    }
}
