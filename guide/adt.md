# What is (in) a deck

> **Topics**: sealed hierarchies, data classes, immutability

One of the key components in the _functional_ approach to programming we promote is how we **model** the data. In other words, how we represent the information we care about throughout the execution of our application.

We prefer an **immutable** representation to one where mutation is available. This main benefit is at the level of _reasoning_, as it becomes much easier to understand what is going on and potential problems. If instead of modifying data we always transform it into a completely new value, we do not need to care about concurrent accesses. More bluntly, a whole source of potential bugs disappear when using immutability.

This property alone has a profound impact on our data types. Since there is no mutation, the values are **stateless**. Instead of thinking about modification, for example with `person.setName("me")`, we think in terms of transformation and copying, `person.copy(name = "me")`. Functional programmers are usually proud of their **anemic** domain models, in which operations always exist as transformations of data.

We also strive for a **precise** representation, which captures every possible _invariant_ (domain rule) in our data. A prime example from the UI world is data which may also be loading or have errors while obtaining. One potential representation is given by

```kotlin
class Result(
  val data: Card?,
  val problem: Throwable?
)
```

with the additional invariant that at most one of the values should be non-`null`, and both being `null` represents a loading state.

This is problematic, though, because there is nothing stopping us from breaking that invariant. A more precise representation captures the three possible states as three different types in a sealed hierarchy,

```kotlin
sealed interface Result {
  data object Loading: Result
  data class Success(val data: Card): Result
  data class Problem(val problem: Throwable): Result
}
```

Now the compiler guarantees that the right information is present at each point. Furthermore, we gain the ability to use `when` to check the current state, and the compiler guarantees that we always handle all possible cases.

One nice advantage of using Compose is that it naturally leads to a more immutable representation of state. In the following tasks we focus on the precision of our domain model.

```admonish info title="More on functional domain modeling"

- [Domain modeling](https://arrow-kt.io/learn/design/domain-modeling/) in Arrow documentation.
- The book [Domain modeling made functional](https://pragprog.com/titles/swdddf/domain-modeling-made-functional/) by Scott Wlaschin introduces many of these ideas in the context of F#, but it maps quite well to Kotlin.

```

## Improving the domain

For this set of tasks you need to work on the `tcg/tcg.kt` file, where the main domain model for decks and cards is defined.

### <img src="images/oddish.png" height="20px" /> More precise `type`

The given domain model uses a nullable `Type` in `Card`. This is because not every card in the Pokémon TCG has a type; this attribute is restricted to Pokémon and _basic_ Energy cards. However, this means that we can define cards outside the real-world model, the `wrongTrainer` given in that file is an example thereof.

Your **task** is to transform the given domain model to better capture the invariant that over `Type`. Once this task is finished, you should not be able to make `wrongCard` compile if you want to keep it a _Trainer_ card. Note that in most cases your solution requires not only modifying `Card`, but also changes in other classes.

<details>
<summary><i>If you are stuck...</i></summary>

The main question here is: where should this information live if not in `Card`? Since `Category` already displays a separation between different groups of cards, this is a natural place to include information that it is only available to some of them.

</details>

<details>
<summary><i>Some discussion on the answer</i></summary>

Note that having a nullable `type` when reading information about a card is not a problem per se, we just need to avoid _creating_ one such card. One nice feature of Kotlin is that you can refine the nullability of a property in children classes, so a great solution to this task is to make `type` part of `Category`, and have it non-nullable in `Pokemon` and `Energy`.

```kotlin
sealed interface Category: Comparable<Category> {
    val type: Type?
    
    data class Pokemon(val stage: PokemonStage, override val type: Type) : Category
    data class Energy(val category: EnergyCategory, override val type: Type) : Category
    data class Trainer(val category: TrainerCategory) : Category {
        override val type: Type? = null
    }
}
```

</details>

### <img src="images/oddish.png" height="20px" /> More precise energies

The given domain model gives all types the same treatment, but this is not true in the game.
In particular, two types follow some special rules:

- _Dragon_ acts as the type of a Pokémon, but never as the type of an Energy. In the game, this manifests as attacks never requiring "dragon energy"; dragon Pokémon always use a combination of other energies.
- When _colorless_ energy appears in a cost, it may be paid by _any_ type of energy. There are no basic Colorless Energy card, but there are Colorless Pokémon.

| | |                                                                                                                                                                                                                                                                                      |
|---|---|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| ![Koraidon](https://images.pokemontcg.io/svp/91_hires.png) | ![Miraidon](https://images.pokemontcg.io/svp/92_hires.png) | These cards are of _dragon_ <img src="images/dragon.png" height="15px" /> type, but their attacks use energy of a different type (since the dragon type energy doesn't exist). In this case, they both use the _colorless_ <img src="images/colorless.png" height="15px" /> energy. |
| ![Chatot](https://images.pokemontcg.io/sv5/181_hires.png) | ![Snorlax](https://images.pokemontcg.io/svp/51_hires.png) | These cards are of _colorless_ type. They are used in every type of deck, since their attack cost can be paid using any energy.                                                                                                                                                      |

Your **task** is to refine the given _Type_ to account for these nuances, so that `wrongDragon` defined in the file no longer compiles. However, your solution should _not_ be just two or more different types; by using inheritance you can create several subsets of types and share common cases. Hint: sometimes enumerations are too limiting.

<details>
<summary><i>If you are stuck...</i></summary>

Consider the following enumeration:

```kotlin
enum class ThisOrThat { This, That }
```

We can represent the same elements by using a sealed hierarchy and a couple of objects:

```kotlin
sealed interface ThisOrThat {
    data object This : ThisOrThat
    data object That : ThisOrThat
}
```

But now we have gained the ability to introduce intermediate interfaces in the hierarchy, between the overarching parent and each of the objects.

</details>

### <img src="images/vileplume.png" height="20px" /> Information about evolution

One of the most important features of the Pokémon franchise is that Pokémon _evolve_, that is, they turn into (stronger) Pokémon as they progress. This is reflected in the TCG as Stage 1 and Stage 2 Pokémon describing which Pokémon they evolve from.

```admonish bug title="One direction does not imply the other"

Every Stage 1 or Stage 2 Pokémon evolves _from exactly one_ Pokémon. However, the converse is not true: a single Pokémon may evolve _to more than one_ Pokémon (or none). For example, Gloom may evolve into Vileplume and Bellossom, with Eevee having a record eight different evolutions.

```

| | | | |
|--|--|--|--|
| ![Oddish](https://images.pokemontcg.io/sv3pt5/43_hires.png) | ![Gloom](https://images.pokemontcg.io/sv3pt5/44_hires.png) | ![Vileplume](https://images.pokemontcg.io/sv3pt5/45_hires.png) | ![Bellossom](https://images.pokemontcg.io/sv3/3_hires.png) |

Your **task** is to refine the domain model to include this information — using `evolvesFrom` as the property name is the best choice because it aligns with serialization, but feel free to choose any other name. This in turns creates a domino effect in other files. This is a _good thing_: it means that the compiler is helping us understand where we need changes to keep everything aligned.

You need to also update the `KtorPokemonTcgApi` implementation to account for this extra attribute, check the [Pokémon TCG API docs](https://docs.pokemontcg.io/) for the place where it appears.

```admonish info title="kotlinx.serialization"

The code uses `kotlinx.serialization` to transform the JSON returned by the API into Kotlin data classes. For more information, check the [introduction](https://kotlinlang.org/docs/serialization.html#serialize-and-deserialize-json) and the [basic guide](https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/basic-serialization.md).

```

As an **additional task**, you can improve the ordering of the deck shown in the right pane by taking evolution into account: evolution chains should appear together. At this point you have two options:

1. Make this ordering "inherent" to the `Card` type, and modify its `Comparable` implementation in `tcg/tcg.kt`;
2. Introduce this new ordering only in the `deck/view.kt` file, modifying its call to `sorted` using `sortedBy`.

### <img src="images/jirachi.png" height="20px" /> Revamping the representation

The way decks are modelled in the `Deck` type — as a simple list of `Card`s — makes some operations quite simple (like obtaining the size of the deck) while making some others much more complicated (like understanding how many copies of a certain card are in a deck). As an **extra over-arching task** you may explore other representations, like using a `Map<Card, Int>` or a `List<Pair<Card, Int>>` instead. The important question to answer here is what becomes harder and what easier, and understand the trade-offs in domain modelling.

```admonish tip title="Everybody thinks differently"

If you are following this guide as a workshop, or alongside some friends or colleagues, we encourage you to share your code and findings with others. Domain modelling almost never has a single perfect answer, and people see different trade-offs depending on their previous knowledge and experience.

```

## <img src="images/jirachi.png" height="20px" /> New search status

The `SearchStatus` type used in `search/viewModel.kt` is quite similar to `Result` describe above. You can take a look at that file and the corresponding view to see how one operates with sealed hierarchies.

To understand how the exhaustiveness helps, add a new `data object Unavailable : SearchStatus` to this type, which should represent the case in which search cannot be performed because we could not contact the Pokémon TCG API. Your **task** is to fix all the compiler errors you find. Also, try to think about what would have changed if the code had used `else` instead of matching on each of the cases separately.
