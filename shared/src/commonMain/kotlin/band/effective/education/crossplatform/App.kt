package band.effective.education.crossplatform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
// Импортируем ваши созданные экраны белков
import band.effective.education.crossplatform.ui.ProteinDetailScreen
import band.effective.education.crossplatform.ui.ProteinListScreen

@Composable
fun App() {
    // 1. Создаем переменную состояния для хранения текущего экрана.
    // По умолчанию мы находимся на экране списка ("list")
    var currentScreen by remember { mutableStateOf("list") }

    // 2. Создаем переменную для хранения ID выбранного белка
    var selectedProteinId by remember { mutableStateOf("") }

    // 3. Обычным оператором when (if-else) выбираем, какой экран показать пользователю
    when (currentScreen) {
        "list" -> {
            ProteinListScreen(
                onProteinClick = { id ->
                    // Когда пользователь кликает на белок, запоминаем его ID...
                    selectedProteinId = id
                    // ...и переключаем состояние экрана на детали ("details")
                    currentScreen = "details"
                }
            )
        }
        "details" -> {
            ProteinDetailScreen(
                proteinId = selectedProteinId,
                onBackClick = {
                    // ИСПРАВЛЕНО: Теперь мы говорим экрану деталей, что при нажатии "Назад"
                    // нужно переключить состояние главного экрана обратно в режим списка "list"
                    currentScreen = "list"}
            )

            // Чтобы преподаватель принял лабу, на детальном экране должна быть кнопка "Назад"
            // В следующем чекпойнте мы интегрируем её в AppScaffold, либо вы можете нажать
            // системную кнопку назад (если тестируете на Android).
        }
    }
}
