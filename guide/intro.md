# Introduction

Now that you know about the [domain](./tcg.md) and the [technology](./tech-intro.md), we can describe the given implementation of Poké-Fun.

## Cards

The `tcg` module gives access to cards and their information.

- The `tcg.kt` file defines a set of types that represent the information in cards, including their name, identifier, category, and type. We shall delve on these types in the [_What is (in) a deck_](./adt.md) section.
- Basic validation is implemented in `validation.kt`. In the [_Validation_](./validation.md) section we'll improve this functionality.

## General design

The diagram below roughly represents how Poké-Fun is architected.

```mermaid
graph LR;
  PokemonTcgApi;
  SearchViewModel;
  PokemonTcgApi <-.- SearchViewModel;
  DeckViewModel;
  subgraph View [SplitPane]
    SearchPane;
    DeckPane;
  end
  SearchViewModel --- SearchPane;
  DeckViewModel --- SearchPane;
  DeckViewModel --- DeckPane;
```

In the center we find two different view models, which serve different purposes:

- `DeckViewModel` (in `deck/viewModel.kt`) keeps track of the current status of the deck, including the cards contained in it, and the (potential) problems with that choice of cards.
- `SearchViewModel` (in `search/viewModel.kt`) keeps track of the state of search, and is responsible for communicating with the [Pokémon TCG API](https://docs.pokemontcg.io/).

Access to the Pokémon TCG API is mediated by the `PokemonTcgApi` interface (in the `tcg/api` folder), for which we give a "real" implementation talking over the network using [Ktor](https://ktor.io), and a "fake" one with a few predefined cards. After finishing the [_Deal with bad internet_](./resilience.md) section, we'll have some respectable code.

Two different views represent the data of the view models in a graphical manner. Those are put together in a single screen using a `SplitPane`, one of the [desktop-specific components](https://github.com/JetBrains/compose-multiplatform/blob/master/tutorials/README.md#desktop) offered by Compose Multiplatform.

- On the left-hand side we have the `SearchPane` (in `search/view.kt`), where the users input their search and see results. This view also adds selected cards to the deck, hence the dependence on the `DeckViewModel`.
- On the right-hand side we have the `DeckPane` (in `deck/view.kt`), which simply shows the cards and problems.

Both view make use of common component to show a single `Card` and multiple `Card`s, found in `tcg/cardView.kt`. These components have an `extra` parameter which is used to provide the different elements required in each of the views (for example, the _Add_ button in the search pane).
