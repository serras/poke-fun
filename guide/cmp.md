# Nicer UI

> **Topics**: Compose, navigation

Compose Multiplatform is a great UI library based of functional principles. Although more tutorials and guides are slowly hitting the shelves, most of the material about Jetpack Compose (the Android version) still applies here. In this section we propose a couple of tasks in case you want to dive further in the UI side of things.

## Better search

Right now Poké-Fun only searches cards by name. However, the API has [many more options](https://docs.pokemontcg.io/api-reference/cards/search-cards/), so you can filter with respect to the different attributes in a card. For example, the player may want to look for cards of a specific type to build a thematic deck.

Your **task** is to provide an _advanced_ search view (you can look at the [official card database](https://www.pokemon.com/us/pokemon-tcg/pokemon-cards/) for inspiration). This requires changes in a few places:

- You have to extend `search` method in the `PokemonTcgApi` interface to take the additional information as input.
- You have to modify the Ktor implementation to generate the correct query string.
- You have to provide additional UI elements for the player to change the filters.

To help with the UI side of things, the provided `Type` enumeration already contains the URL of the image corresponding to each of the types.

## Card detail view

Sometimes you may want to check the text on a card, but Poké-Fun does not make that easy, since the deck pane focuses on an _overview_ of the deck. Your **task** is to add a way to show a detailed view; for example, when the card is (double) clicked.

We encourage you to use the [navigation library](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-navigation-routing.html) provided by Compose. To help you get started, we provide a very basic version in `deck/navigation.kt`. As you can see, navigation is done via _routes_, which may hold some arguments. Inside each of them, you define how the UI should look like, as we've been doing until now.

```kotlin
composable("main") {
  DeckPane(deck, modifier)
}
composable("detail/{cardId}") { entry ->
  // get the value of the argument
  val cardId = entry.arguments?.getString("cardId")
  // build the UI from this information
}
```

```admonish info title="Type safe routes"

Newer versions of Jetpack Compose no longer use strings to define routes, they use [serializable classes instead](https://developer.android.com/codelabs/basic-android-kotlin-compose-navigation). At the moment of writing this feature is only available in alpha builds of Compose Multiplatform.

```