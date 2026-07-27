# Poké-Fun with Kotlin and Arrow

Welcome! In this guide we (well, actually you) are going to work on an application to build decks for the Pokémon Trading Card Game (TCG). Each chapter roughly corresponds to a different functionality: loading decks, searching cards, and so on. Following the spirit of these times, we also hint at using (local) AI in the application.

> The source code is available in [this repository](https://github.com/serras/poke-fun). Download or clone it, and you should be ready to go.

This book assumes that you know your way around [Kotlin](https://kotlinlang.org), but previous experience with functional programming or [Arrow](https://arrow-kt.io), or with Compose Multiplatform, is not required.

```admonish note title="Compose is multi-plaftorm"

For ease of development, the provided skeleton is a desktop application. Using Compose Multiplatform you can easily make it run in Android or iOS devices, with minor modifications.

```

The starting point introduces the domain and the main components of the technology.

- If you have never heard of the Pokémon Trading Card Game or don't know the rules, start with the [introduction to the domain](./tcg.md);
- If you are new to the Kotlin Toolchain or Compose Multiplatform, start with [the technology](./tech-intro.md),
- If you are new to local LLMs for AI, follow the [local LLM setup](./ai-setup.md) instructions.

```admonish warning title="Built with the Kotlin Toolchain"

Poké-Fun uses the [Kotlin Toolchain](https://kotlin-toolchain.org/) as build tool, as opposed to the most usual Gradle. In particular, you need to install the [corresponding plug-in](https://plugins.jetbrains.com/plugin/31850-kotlin-toolchain) if you are using IntelliJ or Android Studio.

```

Afterward, the [overview](./intro.md) describes the main components of the given code.
The rest of the guide is divided into a series of largely independent sections, so you can choose what you want to work on.
Each section contains an introduction to one or more topics, and pointers to additional tutorials or documentation about them.

- [What is (in) a deck](./adt.md): model data using data classes and sealed hierarchies.
- [Law-abiding decks](./validation.md): check that the deck follows the rules, and learn about `Raise` along the way.
- [Deck building](./build.md): design a good `ViewModel` using functional principles, and design undo/redo with actions-as-data.
- [Deal with bad internet](./resilience.md): improve the experience with `Schedule` and `CircuitBreaker`, and cache results using memoization.
- [Through the magnifying glass](./optics.md): use optics to improve the code, and use local storage for card data.
- [Loading and saving](./par.md): store your work locally, and learn about parallel combinators in Arrow Fx.
- [Better architecture](./architecture.md): introduce resource management, and overall nicer design.
- [Nicer UI](./cmp.md): implement more visual feedback using Compose Multiplatform.
- [Fancy deck titles](./ai-use.md): learn the basics of AI agents to generate titles for the deck.
- [More ideas for AI](./ai-use.md): use more AI functionality to spice the application.

```admonish tip title="A word from our sponsor"

Many of these sections complement the book [Functional Programming Ideas for the Curious Kotliner](https://leanpub.com/fp-ideas-kotlin).

```