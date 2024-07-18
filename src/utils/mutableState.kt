package utils

import androidx.compose.runtime.State

fun <T, S> State<T>.map(transform: (T) -> S): State<S> = object : State<S> {
    override val value: S
        get() = transform(this@map.value)
}