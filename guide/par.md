# Loading and saving

> **Topics**: parallel operations, `Raise` + exceptions + concurrency

Poké-Fun as provided has a very big limitation. You can only work on your deck in one go: if you want to devote several sessions to it, you must either (1) not close the application, or (2) write down your cards in a piece of paper and add them back the next time. In this section we add support for loading and saving _deck files_, and learn about high-level parallelism on the way.



## Load and store

The first **task** is to implement saving the deck as a file, and being able to read it back afterward. Feel free to choose whatever format you like, from the list of identifiers separated by new lines, to some sort of JSON.

The code provided in `deck/view.kt` integrates [FileKit](https://github.com/vinceglb/FileKit) to show the file picker of the platform the application is running on. The button are disabled, remember to set `enabled = true` in the call to `IconButton`.

Saving the deck is easy, but loading it potentially involves getting the information from each of them. You should use the `getById` method from `PokemonTcgApi` to retrieve the `Card` corresponding to a given identifier.

In a first approximation, using `map` over the sequence of identifiers should be enough. Albeit simple, that solution lacks performance. Arrow provides [_high-level concurrency_](https://arrow-kt.io/learn/coroutines/parallel/) which solves the problem quite succintly. Use [`parMap`](https://arrow-kt.io/learn/coroutines/parallel/#independently-in-parallel) to turn the sequential iteration into a concurrent set of operations.

Another problem with the simple approach, depending on how you store the data from a deck, is that you may ask information about the same card more than once. One potential solution is to group the cards by identifier, but a more general approach is to use [caching](./resilience.md#introduce-a-cache) that works independently of the number of consumers.

## From exceptions to `Raise`

Problems may arise during the retrieval of card information, but the current code is not prepared for that eventuality. In this section we improve the situation by using `Raise`.

```admonish info title="About Raise"

It is strongly recommended to read the [_Law-abiding decks_](./validation.md) section, which introduces the basics of `Raise`, before attempting the following task.

The integration of `parMap` (and `parZip`) with `Raise` and error accumulation is discussed in the [Arrow documentation](https://arrow-kt.io/learn/coroutines/parallel/#integration-with-typed-errors). Although the TL;DR is simply "replace `mapOrAccumulate` with `parMapOrAccumulate` and enjoy".

```

Your **task** is to use [`Either.catch`](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/#from-exceptions) to capture any potential exceptions, and transform then into `Either`. As hinted in the [_Law-abiding decks_](./validation.md) section, you need to define an error hierarchy to represent those problems.

```admonish tip title="Several error hierarchies"

It is _not_ necessary to have a single error hierarchy for the entire domain. You only need a common parent whenever you may be mixing those in a single function, which means your error hierarchy is actually shared by two parts of the domain.

```

By default, using the `either` builder means following a fail-first approach to errors. If you have not done it directly on the previous task, change the behavior to _accumulation_. In other words, you should report every problem you find loading a file, not only the first identifier you fail to obtain.


