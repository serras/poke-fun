# Law-abiding decks

> **Topics**: validation, `Raise`, error accumulation

Handling errors is one of the scenarios where a functional approach shines. Using types like `Either` and contexts like `Raise`, we can easily compose larger validations from smaller ones.

This topic is well documented in the official Arrow documentation, we suggest the reader to check the following material:

- [Working with typed errors](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/)
- [Validation](https://arrow-kt.io/learn/typed-errors/validation/)

Feel free to use any style that you prefer in this section. When in doubt, using `Raise` (as opposed to `Either`) is the preferred option when using Arrow.

## Legal decks

Your **task** in this section is to implement the rules for a _legal_ deck, that is, one that can be used to play Pokémon TCG. The `tcg/validation.kt` file contains a barebones implementation of `validate`, which simply checks the number of cards in the deck, and a non-empty title.

```admonish info title="Nullable non-empty list"

In the given implementation of `deck/viewModel.kt` you may have noticed that `problems` has quite a strange type, namely `NonEmptyList<String>?`. This is equivalent to a simple `List<String>` if you consider "`null` = empty", so why bother with such a complication? Doing so forces us to handle the `null` case (no errors) explicitly, whereas with a `List<String>` we have no such guarantee: you may end up inadvertently showing that the deck has problems, but then showing none. The goal here is to make the compiler catch potential problems before they even arise.

```

```admonish tip title="Return value checker"

The `module.yaml` file enables the (experimental) [return value checker](https://kotlinlang.org/docs/unused-return-value-checker.html). This ensures that every `Either` you obtain is ultimately consumed; since otherwise you may lose some valuable errors along the way. To get yourself acquainted with this feature, try to remove some `.bind()` calls from the code you wrote for the previous tasks, and watch the compiler complaining.

```

### <img src="images/oddish.png" height="20px" /> Main rules

The main rules for the legality of a deck are:

- The deck must contain exactly 60 cards,
- There must be at most 4 cards with the same name,
  - Note that cards with different identifiers but the same name are added together,
  - The only exception to this rule are _basic_ Energy cards, of which you can have an unlimited amount,
- There must be at least one _basic_ Pokémon.

Implement this validation using `Raise` or `Either`, and try to break the process in different functions. The notion of [fail-first vs. accumulation](https://arrow-kt.io/learn/typed-errors/validation/#fail-first-vs-accumulation) is important here, so you can squeeze as much information as possible. The skeleton for the function is already given: you should write the body of either `validateRaiseImpl` or `validateEitherImpl`, and update `validate` to call the corresponding function.

### <img src="images/oddish.png" height="20px" /> Better (error) types

The current implementation goes against our aim of precise types, since it uses `String` to represent any problem. Your **task** here is to introduce an _error hierarchy_ that represents each possible problem with the deck. More concretely:

1. Introduce a new `DeckProblem` sealed hierarchy,
2. Introduce as many subclasses of `DeckProblem` as kinds of rules that may be broken, including in each of them any additional information that could be useful while reporting,
3. Modify the `validate` function to return a `NonEmptyList<DeckProblem>?`, and update the code to build instances of those errors instead of using strings.

As a result of this transformation the `DeckPane` view cannot directly print the errors, as it was doing until now. You need to define how each `DeckProblem` should be represented as a message.

### <img src="images/vileplume.png" height="20px" /> Problems tied to specific cards

Some problems pertain the whole deck (like having too few or too many cards), whereas others refer to specific cards (like having more than 4 cards) with the same name. We want the UI to show those problems differently; for example, by showing the name in the `MaterialTheme.colorScheme.error` color. But there may be some information missing to tie errors to specific cards. Your **task** is to refine your error hierarchy to account for this information, and update the UI accordingly.

This task is especially interesting to understand the different roles of view models and views when representing information. During validation it makes sense to account for errors in a uniform fashion, whereas in the UI we want them to appear separately. By introducing a good error hierarchy, making this distinction becomes easy.

### <img src="images/vileplume.png" height="20px" /> Evolution

Implement a rule to check that you can always _evolve_ every Pokémon in your deck. This means you if you have a Stage 1 or Stage 2 Pokémon, you should have a card for the Pokémon it evolves from.

### <img src="images/vileplume.png" height="20px" /> Gym Leader Challenge

The rules described above correspond to the _Standard_ format, which is the one sanctioned for tournaments. However, fans of the game have come with other formats, like [Gym Leader Challenge](https://gymleaderchallenge.com/) (GLC). As an **extra task**, you may implement [GLC rules](https://gymleaderchallenge.com/rules).

- You may need to add some UI element to specify the format your deck is in.
- GLC forbids some sorts of cards, namely those with a Rule Box and ACE SPECs. This information is available from the API, but currently not reflected in the domain model.

## <img src="images/vileplume.png" height="20px" /> Property-based testing

One big advantage of following an immutable approach to modelling decks is that testing becomes much easier, since there are no dependencies to account for. In particular you can use [property-based testing](https://kotest.io/docs/proptest/property-based-testing.html), an approach in which lots of random inputs are generated, and then _properties_ of the result are checked. This raises the level of abstraction: for example, you don't test that a certain deck returns certain error, but rather that "validating every deck with fewer than 60 card contains a `NotEnoughCards` error among those returned".

Your **task** is to add more tests to the `test/tcg/validation.kt` file. This file already contains one test you can use as a template. The project is set up to use [Kotest](https://kotest.io/), in particular using the [`checkAll`](https://kotest.io/docs/proptest/property-test-functions.html#check-all) function. For more complex scenarios yoy may need to write [custom generators](https://kotest.io/docs/proptest/custom-generators.html) that provide more exact input to your tests.

## <img src="images/jirachi.png" height="20px" /> Reactive problems

The current implementation has a potential problem: you need to update `problems` every time you update `deck`. But actually, the problems of a deck directly derive from the contents of the deck itself. Reactive frameworks like [RxJava](https://github.com/ReactiveX/RxJava) allow expressing this connection directly, and we can easily do the same using a `StateFlow`.

Your **task** is to replace each update to the `problems` mutable state with a new definition based on `deck`. You can use the function `map` in `utils/flow.kt`.

```admonish info title="Map as in lists"

An intuitive understanding for this operation arises if we look at a `MutableStateFlow` as a _list_ of all the values as the time flows. In that way, the problems arise as `map`ping the validation over each element of that list.

```
