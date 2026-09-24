package band.effective.education.crossplatform.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Импортируем наш список мок-данных, чтобы брать информацию о белках оттуда
import band.effective.education.crossplatform.data.mockProteins

@Composable
fun ProteinDetailScreen(proteinId: String, onBackClick:() -> Unit) {
    // .firstOrNull ищет в списке mockProteins белок, у которого ID совпадает с нажатым
    val protein = mockProteins.firstOrNull { it.primaryAccession == proteinId }

    // Если белок по какому-то странному ID не нашелся, покажем ошибку
    if (protein == null) {
        AppScaffold {
            Text("Белок не найден", modifier = Modifier.padding(16.dp))
        }
        return
    }

    // Отрисовываем контент экрана деталей белка
    AppScaffold (
        actions = {
            Button(
                onClick = { onBackClick() },
                modifier =  Modifier.padding(end = 8.dp)
            ){
                Text("Назад")
            }
        }
        ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                // Так как последовательность аминокислот гигантская, включаем прокрутку экрана
                .verticalScroll(rememberScrollState())
        ) {
            // Заголовок: Системное имя белка (например, INS_HUMAN)
            Text(
                text = protein.entryName,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Название
            Text(text = "Полное название:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
            Text(text = protein.proteinName, style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(16.dp))

            // Организм
            Text(text = "Организм:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
            Text(text = protein.organismName, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(16.dp))

            // Ген
            Text(text = "Ген:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
            Text(text = protein.geneName ?: "Неизвестно", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(16.dp))

            // Длинная аминокислотная цепочка
            Text(text = "Последовательность аминокислот:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
            Text(
                text = protein.sequence ?: "Нет данных",
                style = MaterialTheme.typography.bodyMedium,
                // Используем моноширинный шрифт Monospace — так цепочки букв ДНК/белков выравниваются ровно
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
            )
        }
    }
}
