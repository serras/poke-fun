# Poké-Fun with Kotlin and Arrow

Welcome! In this guide we (well, actually you) are going to work on an application to build decks for the Pokémon Trading Card Game (TCG). Each chapter roughly corresponds to a different functionality: loading decks, searching cards, and so on.

This book assumes that you know your way around [Kotlin](https://kotlinlang.org), but previous experience with functional programming or [Arrow](https://arrow-kt.io), or with Compose Multiplatform, is not required.

```admonish note

For ease of development, the provided skeleton is a desktop application. Using Compose Multiplatform you can easily make it run in Android or iOS devices, with minor modifications.

```

The starting point introduces the domain and the main components of the technology.

- If you have never heard of the Pokémon Trading Card Game or don't know the rules, start with the [introduction to the domain](./tcg.md);
- If you are new to Amper or Compose Multiplatform, start with [the technology](./tech-intro.md),

Afterward, the [introduction](./intro.md) describes the main components of the given code.
The rest of the guide is divided into a series of more or less independent sections, so you can choose what you want to work on.

- [What is (in) a deck](./adt.md): model data using data classes and sealed hierarchies, and learn about optics for JSON
- [Law-abiding decks](./validation.md): check that the deck follows the rules, and learn about `Raise` and `Ior` along the way
- [Deck building](./build.md): design a good `ViewModel` using functional principles, and design undo/redo with actions-as-data
- [Deal with bad internet](./resilience.md): improve the experience with `Schedule` and `CircuitBreaker`, and cache results using memoization
- [Loading and saving](./par.md): store your work locally, and learn about parallel combinators in Arrow Fx
- [Better architecture](./architecture.md): introduce resource management, and overall nicer design
- [Nicer UI](./cmp.md): implement more visual feedback using Compose Multiplatform