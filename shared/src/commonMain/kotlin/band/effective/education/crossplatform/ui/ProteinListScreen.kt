package band.effective.education.crossplatform.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.education.crossplatform.data.Protein
import band.effective.education.crossplatform.data.ProteinApi

@Composable
fun ProteinListScreen(onProteinClick: (String) -> Unit, onProteinsLoaded: (List<Protein>) -> Unit) {
    val api = remember { ProteinApi() }

    var proteins by remember { mutableStateOf<List<Protein>>(emptyList()) }
    var totalCount by remember { mutableStateOf(0) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        isLoading = true
        errorMessage = null
        try {
            val (result, total) = api.fetchProteins()
            proteins = result
            totalCount = total
            onProteinsLoaded(result)
        } catch (e: Exception) {
            errorMessage = e.message ?: "Unknown error"
        } finally {
            isLoading = false
        }
    }

    AppScaffold {
        when {
            isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            errorMessage != null -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Ошибка загрузки: $errorMessage")
                }
            }
            else -> {
                Column {
                    Text(
                        text = "Найдено: $totalCount",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodySmall
                    )
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(proteins) { item ->
                            ProteinRow(
                                protein = item,
                                onClick = { onProteinClick(item.primaryAccession) }
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun ProteinRow(protein: Protein, onClick: () -> Unit) {
    CardSurface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = protein.primaryAccession,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = protein.proteinName,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = protein.organismName,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}