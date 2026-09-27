package band.effective.education.crossplatform.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import band.effective.education.crossplatform.resources.Res
import band.effective.education.crossplatform.resources.app_title
import band.effective.education.crossplatform.resources.action_toggle_lang
import band.effective.education.crossplatform.resources.action_toggle_theme
import band.effective.education.crossplatform.ui.locale.customAppLocale

/**
 * Шапка приложения и место под содержимое экрана.
 *
 * В слот [actions] уезжает то, что должно жить в правом углу шапки на всех экранах.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (Modifier) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.app_title)) },
                actions = {
                    actions()

                    Button(onClick = { isDarkTheme = !isDarkTheme }) {
                        Text(stringResource(Res.string.action_toggle_theme))
                    }

                    Button(onClick = {
                        customAppLocale = if (customAppLocale == "en") "ru" else "en"
                    }) {
                        Text(stringResource(Res.string.action_toggle_lang))
                    }
                }
            )
        },
    ) { insets ->
        Box(Modifier.padding(insets)) {
            content(Modifier.fillMaxSize())
        }
    }
}
