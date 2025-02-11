package eu.anifantakis.randomjokes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.anifantakis.randomjokes.screens.FavoriteJokesScreen
import eu.anifantakis.randomjokes.screens.RandomJokesScreen
import eu.anifantakis.randomjokes.screens.components.LoadingIndicator
import eu.anifantakis.randomjokes.ui.theme.RandomJokesTheme
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val globalStateHolder: GlobalStateHolder by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isLoading by globalStateHolder.isLoading.collectAsStateWithLifecycle()

            RandomJokesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RandomJokesApp(
                        isLoading,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

class GlobalStateHolder {
    var isLoading = MutableStateFlow(false)
}

@Composable
fun RandomJokesApp(isLoading: Boolean, modifier: Modifier = Modifier) {
    MaterialTheme {
        Box {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = { BottomNavigationBar(navController) }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "random",
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable("random") { RandomJokesScreen() }
                    composable("favorites") { FavoriteJokesScreen() }
                }
            }

            LoadingIndicator(
                isLoading = isLoading,
                isCritical = false
            )
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Jokes") },
            label = { Text("Jokes") },
            selected = currentRoute(navController) == "random",
            onClick = { navController.navigate("random") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favorites") },
            label = { Text("Favorites") },
            selected = currentRoute(navController) == "favorites",
            onClick = { navController.navigate("favorites") }
        )
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    return navController.currentBackStackEntry?.destination?.route
}