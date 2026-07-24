package utils

import kotlinx.coroutines.ExperimentalForInheritanceCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalForInheritanceCoroutinesApi::class)
fun <T, S> StateFlow<T>.map(transform: (T) -> S): StateFlow<S> = object : StateFlow<S> {
    override val value: S get() = transform(this@map.value)
    override val replayCache: List<S> = this@map.replayCache.map(transform)

    override suspend fun collect(collector: FlowCollector<S>): Nothing =
        this@map.collect { collector.emit(transform(it)) }
}