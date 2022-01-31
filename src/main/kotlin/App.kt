import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val CALCULATOR_PADDING = 4.dp

@Composable
fun DisplayPanel(
    modifier: Modifier,
    displayValue: MutableState<TextFieldValue>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .padding(CALCULATOR_PADDING)
            .background(Color.White)
            .border(color = Color.Gray, width = 1.dp)
            .padding(start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = displayValue.value.text,
            style = TextStyle(fontSize = 48.sp),
            overflow = TextOverflow.Ellipsis,
            softWrap = false,
            maxLines = 1
        )
    }
}

@Composable
fun Keyboard(
    modifier: Modifier,
    displayValue: MutableState<TextFieldValue>
) {
    Surface(modifier) {
        KeyboardKeys(displayValue)
    }
}

@Composable
fun KeyboardKeys(
    displayValue: MutableState<TextFieldValue>
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        KeyboardLayout.forEach { keyColumn ->
            Column(modifier = Modifier.weight(1f)) {
                keyColumn.forEach { key ->
                    KeyboardKey(Modifier.weight(1f), key, displayValue)
                }
            }
        }
    }
}

@Composable
fun KeyboardKey(
    modifier: Modifier,
    key: Key?,
    displayValue: MutableState<TextFieldValue>
) {
    if (key == null) {
        return EmptyKeyView(modifier)
    }

    KeyView(modifier = modifier.padding(1.dp), onClick = key.onClick?.let {
        { it(displayValue) }
    } ?: {
        // Key has no defined action on click, just display it
        val textValue = displayValue.value.text.let {
            if (it == "0") key.text else it + key.text
        }
        displayValue.value = TextFieldValue(textValue)
    }) {
        Text(
            text = key.text,
            style = TextStyle(fontSize = 29.sp)
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun KeyView(
    modifier: Modifier,
    onClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val active = remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
            .padding(CALCULATOR_PADDING)
            .clickable(onClick = onClick)
            .background(color = if (active.value) Color.White else MaterialTheme.colors.background)
            .border(width = 1.dp, color = Color.Gray)
            .pointerMoveFilter(
                onEnter = {
                    active.value = true
                    false
                },
                onExit = {
                    active.value = false
                    false
                }
            ),
        content = content
    )
}

@Composable
fun EmptyKeyView(modifier: Modifier) = Box(
    modifier = modifier.fillMaxWidth()
        .background(MaterialTheme.colors.background)
        .border(width = 1.dp, color = Color.Gray)
)
