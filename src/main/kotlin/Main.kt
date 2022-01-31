import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

val DEFAULT_WIDTH = 700.dp
val DEFAULT_HEIGHT = 550.dp

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Kotlin Compose Calculator",
        state = rememberWindowState(width = DEFAULT_WIDTH, height = DEFAULT_HEIGHT)
    ) {
        MaterialTheme(colors = lightColors()) {
            val displayValue = remember {
                mutableStateOf(TextFieldValue("0"))
            }

            Column(Modifier.fillMaxHeight()) {
                DisplayPanel(
                    Modifier.weight(1f),
                    displayValue
                )
                Keyboard(
                    Modifier.weight(4f),
                    displayValue
                )
            }
        }
    }
}
