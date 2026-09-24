package band.effective.education.crossplatform.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.education.crossplatform.data.Protein
import band.effective.education.crossplatform.data.mockProteins

@Composable
fun ProteinRow(protein: Protein, onClick: () -> Unit) {
    // CardSurface — это готовый компонент из лабы, создающий карточку
    CardSurface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() } // Делаем карточку кликабельной
    ) {
        Column(
            modifier = Modifier.padding(16.dp) // Внутренние отступы
        ) {
            // Выводим ID белка мелким шрифтом основного цвета
            Text(
                text = protein.primaryAccession,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(4.dp)) // Отступ между текстами

            // Название белка
            Text(
                text = protein.proteinName,
                style = MaterialTheme.typography.titleLarge
            )

            // Название организма
            Text(
                text = protein.organismName,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}



@Composable
fun ProteinListScreen(onProteinClick: (String) -> Unit) {
    // Просто вызываем AppScaffold, круглые скобки убираем.
    // Заголовок "Каталог белков" подтянется сам из strings.xml по ключу app_title
    AppScaffold {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(mockProteins) { item ->
                ProteinRow(
                    protein = item,
                    onClick = { onProteinClick(item.primaryAccession) }
                )
            }
        }
    }
}
