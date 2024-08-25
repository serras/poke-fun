# Better architecture

> **Topics**: resource management, `SuspendApp`

Not all the work you do in an application directly translates into new features. Ensuring that the code remains understandable at the macro and micro level is an important task of a good developer.

```admonish quote title="Scouts rule"

_Leave something slightly better of than you found it._

```

## As explicit as possible

One of the mottos of the style of functional programming we promote is making explicit as much of the function behavior as possible. In statically-typed languages like Kotlin, _explicit_ means _in the function signature_. The information we want to explicit in functions are, among others:

1. Services and resources required to execute the function. Those may be provided as simple arguments, or using _extension receivers_ (and in the future, with [_context parameters_](https://github.com/Kotlin/KEEP/blob/context-parameters/proposals/context-parameters.md)).
2. Whether the function is pure (in other words, it is just computation) or may perform side effects. In the latter case, we mark the function with `suspend`.

A longer explanation, including more examples of the usage of receivers, can be found in the [Arrow documentation](https://arrow-kt.io/learn/design/effects-contexts/).

One downside which is often mentioned of that style is that dependencies need to be _manually_ injected. That is, the developer creates the instances of every service used by the application, as opposed to using a dependency injection (DI) framework like [Koin](https://insert-koin.io/) and [Hilt](https://developer.android.com/training/dependency-injection/hilt-android). However, we don't see this as a downside: by taking control of the creation of services we end up with simpler logic, minimize the amount of inter-dependencies, and avoid runtime or compile-time costs associated to DI frameworks.

## Resource management

One of the challenges with this style of programming is managing the acquisition and release of resources and services. One of the problems is too much _nesting_ in their creation,

```kotlin
HttpClient().use { client ->
  KtorPokemonTcgApi(client).use { api ->
    // now start the application
  }
}
```

Arrow solves this problem with [`resourceScope` blocks](https://arrow-kt.io/learn/coroutines/resource-safety/). The code above can be rewritten with any nesting, given that they implement [`AutoCloseable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-auto-closeable/).

```kotlin
resourceScope {
  val client = autoCloseable { HttpClient() }
  val api = autoCloseable { KtorPokemonTcgApi(client) }
  // now start the application
}
```

One step further is [SuspendApp](https://arrow-kt.io/ecosystem/suspendapp/), which adds graceful shutdown to the whole application. By combining [SuspendApp with Resource](https://arrow-kt.io/ecosystem/suspendapp/#suspendapp-arrows-resource), you can ensure that finalizers runs correctly, even when the application is terminated.

### Poké-Resources

Your **task** is to improve the current architecture of the application by introducing Resource and SuspendApp. Feel free to change the service constructors from the more implicit version provided to a more explicit version; for example, creating the `HttpClient` for `KtorPokemonTcpApi` explicitly.