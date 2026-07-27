# Through the magnifying glass

> **Topics**: optics, JSON manipulation

You may have noticed, while reading the types `Deck`, `Card`, and others in `tcg.md`, that all of them are marked with the `@optics` annotation. _Optics_ are a key concept for concise query and manipulation of immutable data. If you have never used them, we strongly recommend to check [Arrow's documentation on the matter](https://arrow-kt.io/learn/immutable-data/intro/). 

## Sprinkling some optics magic dust

The usual "gateway drug" to optics is replacing complex updates of immutable data with an imperative-style approach. This doesn't mean you're modifying the data in place, simply that the code you write _looks similar_ to doing so. This is explained at length in [_More powerful `copy`_](https://arrow-kt.io/learn/immutable-data/lens/#more-powerful-copy). Your **task** is to update `deck/viewModel.kt` to use this technique instead of `copy`.

Right now `DeckPane` is very empty whenever there are no problems. Instead of a simple message, `DeckProblems` could show some statistics about the deck: how many cards of each category there are, how many of each energy type, and so on. This **task** should be implemented using [prisms](https://arrow-kt.io/learn/immutable-data/prism-iso/#sealed-class-hierarchies), [optionals](https://arrow-kt.io/learn/immutable-data/optional/), and [traversals](https://arrow-kt.io/learn/immutable-data/traversal/) over the `deck`.

Speaking of problems, [deck validation](./validation.md) is another place in which optics many become very handy, since you often need to dive deep inside the properties in `Deck`. The outcome of this **task** depends a lot on the way you've coded validation, but try to replace navigation within the structure with different optics.

## Targeting JSON

Until this point we have focused on requesting data from a remote API. In some scenarios such requests are not possible or desirable, and a local data source is a better option. When you clone the repository for these exercises, you should find a `pokemon-tcg-data` submodule that contains a [copy of the data for the remote API](https://github.com/PokemonTCG/pokemon-tcg-data).

```admonish tip title="The inline iterator"

The core of the implementation of `LocalPokemonTcgApi` builds upon a function that looks pretty much like `map` on lists, but with its `block` working only for `Card`s.

`inline fun forEachCard(block: (Card) -> Unit)`

We could look at this function simply as a functional-first way to encode the pattern of iterating among cards, but in Kotlin the [`inline` modifier](https://kotlinlang.org/docs/inline-functions.html) brings additional benefits. In particular, you can return _early_ from an `inline` function, meaning that you don't need to fully consume all the `Card`s if you already know the result of a function. This exact fact is used in `getById` — the `return@getById` syntax means that we want to exit not the local lambda passed to the `forEachCard` function, but actually from the entire `getById` function.

```

The current implementation is extremely inefficient: it transforms every card in every `.json` file into a `Card` object, and then filters the results. A better solution is to perform the query directly on the JSON document, and only transform the data if it matches. The `tcg/api/optics.kt` contains an implementation for `getById`, your **task** is to complete it for `search`.

The current stub implementation of `OpticsPokemonTcgApi` uses [optics](https://arrow-kt.io/learn/immutable-data/intro/) as a way to obtain information from the JSON document without too much manipulation. In general, optics are a way to query (and modify) immutable data in a concise way. In this case, the core Arrow Optics library is supported by [JSON-specific optics](https://github.com/nomisRev/kotlinx-serialization-jsonpath).