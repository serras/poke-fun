# What is a deck

> **Topics**: sealed hierachies, data classes, immutability

One of the key components in the _functional_ approach to programming we promote is how we **model** the data. In other words, how we represent the information we care about throughout the execution of our application.

We prefer a **immutable** representation to one where mutation is available. This main benefit is at the level of _reasoning_, as it becomes much easier to understand what is going on and potential problems. If instead of modifying data we always transform it into a completely new value, we do not need to care about concurrent accesses. More bluntly, a whole source of potential bugs disappear when using immutability.

This property alone has a profound impact on our data types. Since there is no mutation, the values are **stateless**. Instead of thinking about modification, for example with `person.setName("me")`, we think in terms of transformation and copying, `person.copy(name = "me")`. Functional programmers are usually proud of their **anemic** domain models, in which operations always exist as transformations of data.

We also strive for a **precise** representation, which captures every possible _invariant_ (domain rule) in our data. A prime example from the UI world is data which may also be loading or have errors while obtaining. One potential representation is given by

```kotlin
class Result(
  val data: Card?,
  val problem: Throwable?
)
```

with the additional invariant that at most one of the values should be non-`null`, and both being `null` represents a loading state.

This is problematic, though, because there is nothing stopping us from breaking that invariant. A more precise representation capture the three possible states as three different types in a sealed hierarchy,

```kotlin
sealed interface Result {
  data object Loading: Result
  data class Success(val data: Card): Result
  data class Problem(val problem: Throwable): Result
}
```

Now the compiler guarantees that the right information is present at each point. Furthermore, we gain the ability to use `when` to check the current state, and the compiler guarantees that we always handle all possible cases.

```admonish tip title="Sealed hierarchies are everywhere"

The `SearchStatus` type used in `search/viewModel.kt` is quite similar to `Result` above. You can take a look at that file and the corresponding view to see how one operates with sealed hierarchies.

```

One nice advantage of using Compose is that it naturally leads to a more immutable representation of state. In the following tasks we focus on the precision of our domain model.

```admonish info title="More on functional domain modeling"

- [Domain modeling](https://arrow-kt.io/learn/design/domain-modeling/) in Arrow documentation.
- The book [Domain modeling made functional](https://pragprog.com/titles/swdddf/domain-modeling-made-functional/) by Scott Wlaschin introduces many of these ideas in the context of F#, but it maps quite well to Kotlin.

```

## More precise `type`

The given domain model uses a nullable `Type` in `Card`. This is because not every card in the Pokémon TCG has a type; this attribute is restricted to Pokémon and _basic_ Energy cards. Your **task** is to transform the given domain model to capture that invariant.

## More precise energies

Even the previous refinement is not completely true. In fact, two types have some special meaning in the game:

- _Dragon_ may be the type of a Pokémon, but never the type of an Energy. In the game, this manifests as attacks never requiring "dragon energy"; dragon Pokémon always use a combination of other energies.
- When _colorless_ energy appears in a cost, it may be paid by _any_ type of energy. There are no basic Colorless Energy card, but there are Colorless Pokémon.

| | | |
|---|---|--|
| ![Koraidon](https://images.pokemontcg.io/svp/91_hires.png) | ![Miraidon](https://images.pokemontcg.io/svp/92_hires.png) | These cards are of _dragon_ <img src="images/dragon.png" height="15px" /> type, but their attacks do not use that energy (since it's forbidden). However, they both use _colorless_ <img src="images/colorless.png" height="15px" /> energy. |
| ![Chatot](https://images.pokemontcg.io/sv5/181_hires.png) | ![Snorlax](https://images.pokemontcg.io/svp/51_hires.png) | These cards are of _colorless_ type. They are used in every type of deck, since their attack cost can be paid using any energy. |

Your **task** is to refine the given _Type_ to account for these nuances. However, your solution should _not_ be just two or more different types; by using inheritance you can create several subsets of types and share common cases.

## Information about evolution

One of the most important features of the Pokémon franchise is that Pokémon _evolve_, that is, they turn into (stronger) Pokémon as they progress. This is mapped in the TCG as Stage 1 and Stage 2 Pokémon describing which Pokémon they evolve from.

```admonish bug title="One direction does not imply the other"

Every Stage 1 or Stage 2 Pokémon evolves _from exactly one_ Pokémon. However, the converse is not true: a single Pokémon may evolve _to more than one_ Pokémon (or none). For example, Gloom may evolve into Vileplume and Bellossom, with Eevee having record eight different evolutions.

```

| | | | |
|--|--|--|--|
| ![Oddish](https://images.pokemontcg.io/sv3pt5/43_hires.png) | ![Gloom](https://images.pokemontcg.io/sv3pt5/44_hires.png) | ![Vileplume](https://images.pokemontcg.io/sv3pt5/45_hires.png) | ![Bellossom](https://images.pokemontcg.io/sv3/3_hires.png) |

Your **task** is to refine the domain model to include this information. You need to also update the `KtorPokemonTcgApi` implementation to account for this extra attribute, check the [Pokémon TCG API docs](https://docs.pokemontcg.io/) for the place where it appears.

```admonish info title="kotlinx.serialization"

The code uses `kotlinx.serialization` to transform the JSON returned by the API into Kotlin data classes. For more information, check the [introduction](https://kotlinlang.org/docs/serialization.html#serialize-and-deserialize-json) and the [basic guide](https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/basic-serialization.md).

```

As an **additional task**, you can improve the ordering of the deck shown in the right pane by taking evolution into account: evolution chains should appear together.
