package band.effective.education.crossplatform.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Готовая подложка под карточку списка: скруглённые углы, тень, отступ содержимого.
 *
 * Используйте её в своей карточке, чтобы не верстать одно и то же дважды.
 */
@Composable
fun CardSurface(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp,
        shadowElevation = 1.dp,
    ) {
        Box(Modifier.padding(12.dp)) { content() }
    }
}
