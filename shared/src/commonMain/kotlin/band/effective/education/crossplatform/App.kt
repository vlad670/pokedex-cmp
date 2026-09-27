package band.effective.education.crossplatform

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import band.effective.education.crossplatform.data.Protein
import band.effective.education.crossplatform.ui.ProteinDetailScreen
import band.effective.education.crossplatform.ui.ProteinListScreen
import band.effective.education.crossplatform.ui.isDarkTheme
import band.effective.education.crossplatform.ui.locale.AppEnvironment
import band.effective.education.crossplatform.ui.locale.customAppLocale

@Composable
fun App() {
    var currentScreen by remember { mutableStateOf("list") }
    var selectedProteinId by remember { mutableStateOf("") }
    var proteins by remember { mutableStateOf<List<Protein>>(emptyList()) }

    AppEnvironment {
        MaterialTheme(
            colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme()
        ) {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                if (currentScreen == "list") {
                    ProteinListScreen(
                        onProteinsLoaded = { proteins = it },
                        onProteinClick = { id ->
                            selectedProteinId = id
                            currentScreen = "details"
                        }
                    )
                } else {
                    val protein = proteins.firstOrNull { it.primaryAccession == selectedProteinId }
                    ProteinDetailScreen(
                        protein = protein,
                        onBackClick = { currentScreen = "list" }
                    )
                }
            }
        }
    }
}