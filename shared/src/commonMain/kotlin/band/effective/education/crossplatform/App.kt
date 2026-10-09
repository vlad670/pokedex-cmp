package band.effective.education.crossplatform

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import band.effective.education.crossplatform.ui.ProteinDetailScreen
import band.effective.education.crossplatform.ui.ProteinListScreen
import band.effective.education.crossplatform.ui.isDarkTheme
import band.effective.education.crossplatform.ui.locale.AppEnvironment
import androidx.compose.runtime.mutableStateListOf
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

@Composable
fun App() {
    // Стек объявлен ДО AppEnvironment: иначе key(customAppLocale)
    // сбросит его при смене языка (та же проблема, что была с currentScreen)
    val backStack = remember { mutableStateListOf<Route>(Route.ProteinList) }

    AppEnvironment {
        MaterialTheme(
            colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme()
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                NavDisplay(
                    backStack = backStack,
                    onBack = { if (backStack.size > 1) backStack.removeLastOrNull() },
                    transitionSpec = {slideInHorizontally(initialOffsetX = { it }) togetherWith slideOutHorizontally(targetOffsetX = { -it }) },
                    popTransitionSpec = {slideInHorizontally(initialOffsetX = { -it }) togetherWith slideOutHorizontally(targetOffsetX = { it }) },
                    entryProvider = entryProvider {
                        entry<Route.ProteinList> {
                            ProteinListScreen(
                                onProteinClick = { backStack.add(Route.ProteinDetails(it)) }
                            )
                        }
                        entry<Route.ProteinDetails> { key ->
                            ProteinDetailScreen(
                                accession = key.accession,
                                onProteinClick = { backStack.add(Route.ProteinDetails(it)) },
                                onBackClick = { if (backStack.size > 1) backStack.removeLastOrNull() }
                            )
                        }
                    }
                )
            }
        }
    }
}