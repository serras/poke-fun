# Law-abiding decks

> **Topics**: validation, `Raise`, error accumulation

Handling errors is one of the scenarios where a functional approach shines. Using types like `Either` and contexts like `Raise`, we can easily compose larger validations from smaller ones.

This topic is well documented in the official Arrow documentations, we suggest the reader to check the following material:

- [Working with typed errors](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/)
- [Validation](https://arrow-kt.io/learn/typed-errors/validation/)

Feel free to use any style that you prefer in this section. When in doubt, using `Raise` (as opposed to `Either`) is the preferred option when using Arrow.

## Legal decks

Your **task** in this section is to implement the rules for a _legal_ deck, that is, one that can be used to play Pokémon TCG. The `DeckModel` class (in `search/model.kt`) contains a barebones implementation of `validate`, which simply checks the number of cards in the deck.

The main rules for the legality of a deck are:

- The deck must contain exactly 60 cards,
- There must be at most 4 cards with the same name,
  - Note that cards with different identifiers but the same name are added together,
  - The only exception to this rule are _basic_ Energy cards, of which you can have an unlimited amount,
- There must be at least one _basic_ Pokémon.

Implement this validation using `Either` or `Raise`, and try to break the process in different functions. The notion of [fail-first vs. accumulation](https://arrow-kt.io/learn/typed-errors/validation/#fail-first-vs-accumulation) is important here, so you can squeeze as much information as possible.

**Extra task**: implement a rule to check that you can always _evolve_ every Pokémon in your deck. This means you if you have a Stage 1 or Stage 2 Pokémon, you should have a card for the Pokémon it evolves from.

## Problems tied to specific cards

This first task simply gives back a list of string for each problem, but this approach goes against our aim of precise types. Your **task** here is introduce an _error hierarchy_ that represents each possible problem with the deck. The transformation to string should now happen in the `DeckView` instead.

**Extra task**: show problems related to specific cards directly on them. For example, by showing the name in the `MaterialTheme.colorScheme.error` color. Think about how the information required in the error hierarchy.

## Reactive problems

The current implementation has a potential problem: you need to update `_problems` every time you update `_deck`. But actually, the problems of a deck directly derive from the contents of the deck itself. Reactive frameworks like [RxJava](https://github.com/ReactiveX/RxJava) allow expressing this connection directly, and we can easily do the same using `MutableState`.

Your **task** is to replace each update to the `_problems` mutable state with a new definition based on `_deck`. You can use the function `map` in `utils/mutableState.kt`.

```admonish info title="Map as in lists"

An intuitive understanding for this operation arises if we look at a `MutableState` as a _list_ of all the values as the time flows. In that way, the problems arise as `map`ping the validation over each element of that list.

```
