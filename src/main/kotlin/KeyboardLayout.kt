import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue

data class Key(
    val text: String,
    val onClick: ((displayValue: MutableState<TextFieldValue>) -> Unit)? = null
)

fun String.operand() = Key(this)

val keyClear = Key("CLEAR", onClick = {
    displayValue -> displayValue.value = TextFieldValue("0")
})

val keyBackspace = Key("BKSP", onClick = { displayValue ->
    val textValue = displayValue.value.text
    if (textValue.isNotEmpty()) {
        val newTextValue = if (textValue.length == 1) {
            "0"
        } else {
            textValue.substring(0, textValue.length - 1)
        }
        displayValue.value = TextFieldValue(newTextValue)
    }
})

val keyEquals = Key("=", onClick = { displayValue ->
    val input = displayValue.value.text
    calculate(input)?.let { result ->
        displayValue.value = TextFieldValue(text = result)
    }
})

val KeyboardLayout = listOf(
    listOf("7".operand(), "4".operand(), "1".operand(), keyClear),
    listOf("8".operand(), "5".operand(), "2".operand(), "0".operand()),
    listOf("9".operand(), "6".operand(), "3".operand(), ".".operand()),
    listOf("/".operand(), "*".operand(), "-".operand(), "+".operand()),
    listOf(keyBackspace, keyEquals)
)
