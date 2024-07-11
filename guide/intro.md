# Introduction to the domain

The application in which we are going to work on helps in the process of building decks for the Pokémon Trading Card Game (TCG). As usual in any software project, we first need to understand what all the words in the previous sentence mean; in other words, we need to dive into the _domain_.

In general, a _Trading Card Game_ is a card game in which the set of cards is not fixed, as in Póker or Mus. In the case of Pokémon TCG, every year more than 500 new cards are introduced. In order to play, each player chooses a subset of card; this is known as their _deck_. The _trading_ in TCG comes from the fact that traditionally you get the cards you need by exchanging them with your friends.

Most TCGs, and Pokémon is no exception, place some implicit and explicit restrictions on how decks may be built. Explicit restrictions include, among others, that your deck must contain exactly 60 cards. Other restrictions are implicit in the rules of the game; for example, a Pokémon deck cannot function with at least one basic Pokémon.

Once again we find a bunch of terms from the domain, what DDD practitioners call the _Ubiquituous Language_, so let's dive a bit more. Pokémon cards are divided in three big groups.

| | |
|---|---|
| ![Bulbasaur](https://images.pokemontcg.io/svp/46_hires.png) | _Pokémon_ cards represent little monsters that fight against those of your opponent. Each Pokémon has one or more _attacks_, and _health points_ (HP), which define how much damage they can take before fainting. |
| ![Grass Energy](https://images.pokemontcg.io/sve/1_hires.png) | _Energy_ cards are needed to power the attacks of Pokémon. |
| ![Great Ball](https://images.pokemontcg.io/sv2/183_hires.png) | _Trainer_ cards provide additional effects that you can use to help in the battle. |

Pokémon and energies also have a _type_. There are currently 8 different types in the Pokémon TCG — grass <img src="images/grass.png" height="15px" />, fire <img src="images/fire.png" height="15px" />, water <img src="images/water.png" height="15px" />, lightning <img src="images/lightning.png" height="15px" />, fighting <img src="images/fighting.png" height="15px" />, psychic <img src="images/psychic.png" height="15px" />, metal <img src="images/metal.png" height="15px" />, darkness <img src="images/darkness.png" height="15px" />, and dragon <img src="images/dragon.png" height="15px" /> — alongside a special colorless <img src="images/colorless.png" height="15px" /> type. In most cases a Pokémon of a certain type requires energy of the same type, but this is not a rule.

One key component of the Pokémon world is that the little monsters _evolve_, that is, the turn into bigger and more powerful creatures. In the TCG this is reflected by having to begin with _basic_ Pokémon (hence the implicit requirement to have at least one in your deck), which then may turn into _stage 1_, and eventually in _stage 2_.

This is enough for our purposes. If you are interested to learn how to play the game, check the [official rulebook](https://www.pokemon.com/us/pokemon-tcg/rules).
