# The technology

Poké-Fun is implemented using [Kotlin](https://kotlinlang.org/), [Arrow](https://arrow-kt.io/), [Koog](https://docs.koog.ai/), and [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/). The latter has been chosen because it provides the same concepts to build user interfaces in a variety of platforms. In particular, we can write a desktop application that runs easily everywhere (the perks of using the JVM 😉).

The one choice which goes out of the ordinary is using the [Kotlin Toolchain](https://kotlin-toolchain.org/) as build tool, instead of Gradle, much better-known among Kotliners. Feel free to look at the `module.yaml` file, but for the tasks you won't need to touch it. To start the application you can run `./kotlin run` in a command line. The first time it may take some time to start, since build tools, compiler, and dependencies need to be set up.

We recommend using [IntelliJ IDEA](https://www.jetbrains.com/idea/) or [Android Studio](https://developer.android.com/studio) to work on Poké-Fun. You need at least the corresponding [Kotlin Toolchain plug-in](https://plugins.jetbrains.com/plugin/31850-kotlin-toolchain), and the [Kotlin Multiplatform plug-in](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform) is highly recommended. In both cases, you should see a small play button to run the application from the IDE.

## Compose Multiplatform

In recent years we have seen an explosion of a new paradigm for UI development, based on managing the state separately from the view, which is then defined as a function which is re-executed every time the state changes. Some well-known frameworks include [React](https://react.dev/) for web, [SwiftUI](https://developer.apple.com/xcode/swiftui/) for iOS, and [Jetpack Compose](https://developer.android.com/develop/ui/compose) for Android. [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) uses the same concepts of the latter, but targeting several platforms (at the time of writing: desktop, Android, iOS, and web via WebAssembly).

```admonish info title="More about Compose Multiplatform"

There is still not much documentation about Compose Multiplatform, but most of the information about Jetpack Compose (for Android) applies only with minor modifications.

- [Android Basics with Compose](https://developer.android.com/courses/android-basics-compose/course),
- [Jetpack Compose guides](https://developer.android.com/develop/ui/compose/documentation) from Google,
- [Create a Compose Multiplatform app](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform-getting-started.html),
- [Philipp Lackner](https://www.youtube.com/@PhilippLackner/videos) has videos covering Compose Multiplarform.

```

Compose applications are typically built from two components:

- _View models_ keep (part of) the state of the application, and communicate with the outside world.
- _Views_ define how this state is mapped into a set of UI elements laid out in the screen. Views are defined as functions with the `@Composable` annotation, which is required for the framework to be able to run them whenever the state (or part of it) changes.

Let us look at the simplest application: a button which counts how many times it has been pressed. The state is basically a counter that changes over time. Values that change over time are represented in Kotlin as `StateFlow`s. At this point, we need to be careful to not over-expose implementation details:

- The `count` is defined with [explicit backing field](https://www.youtube.com/watch?v=PU-VdH8HhVA) of type `MutableStateFlow`. That means that within the `Counter` we can update the value, but publicly we only expose the read-only `StateFlow`;
- We provide _operations_ like `increment` with the different use cases for updating the state.

```kotlin
class Counter: ViewModel() {
  // 1. define a state that evolves over time
  val count: StateFlow<Int>
    // 2. private state, starting at 0
    field = MutableStateFlow(0)

  // 3. operations to change the state
  fun increment() {
    count.update { it + 1 }
  }
}
```

The view consumes this view model, and shows a button with a text indicating the amount of times it has been clicked. Note how the `.increment()` function in the view model is tied to the use case in the view.

```kotlin
@Composable fun Screen(counter: Counter) {
  Button(onClick = { counter.increment() }) {
    Text("Clicked ${counter.count.value} times")
  }
}
```

What happens when the button is pressed? Then the `onClick` lambda is executed, which eventually changes the value of `count`. Compose detects this change and _recomposes_ the UI, that is, re-executes `Screen` and applies any update to the visible screen. As discussed above, the `@Composable` annotation (alongside the Compose compiler) is the magic that makes this link work.

If you want to use the value of a `StateFlow` several times, it's better to use a combination of [property delegation](https://kotlinlang.org/docs/delegated-properties.html) and `.collectAsState()`.

```kotlin
@Composable fun Screen(counter: Counter) {
  Button(onClick = { counter.increment() }) {
    val count by counter.count.collectAsState()
    Text("Clicked $count times")
  }
}
```

The connection between view model and view is ultimately done when creating the application, or via navigation. In the case of Poké-Fun, the `main` entry point creates a new view model linked to lifecycle of the application window, and then passes this view model to the corresponding view.

Armed with this knowledge, you can read the [introduction](./intro.md) to Poké-Fun.
