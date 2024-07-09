import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.foundation.text.BasicText

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        BasicText("Hello world!") 
    }
}