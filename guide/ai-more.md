# More ideas for AI

## Deck archetype

Apart from the title, there's more to describe a deck. For example, we have its [_archetype_](https://tcgprotectors.com/blogs/pokemon-blog/pokemon-tcg-deck-archetypes-guide-aggro-control-combo-midrange), which roughly describes the way in which the deck is played:

- _Aggro_ decks focus on big and fast attacks,
- _Control_ decks focus on controlling what happens in the game, both for you and your opponent,
- _Combo_ decks focus on getting one specific combination of cards into play,
- _Toolbox_ decks focus on responding to any situation it may face,
- ...

Archetypes are not set in stone, but may help understand what the deck is about. Your **task** is to use AI to categorize the current deck into one (or more) of those archetypes. It may be interesting to consider:

- How much information does the LLM model already have about the Pokémon TCG?
- What is the easiest way to get the information back? Think, among others, of structured output.

Once you have this information, you may consider bringing a bit of spice to your Poké-Fun application, by changing some color details or backgrounds to better reflect the archetype of the deck.

## Card suggestions

What would be more useful than AI actually _helping_ you to build your deck? You can implement this functionality, and see how much it helps!

From the point of view of this workshop, the most interesting bit is how to _model_ this information — something we already covered in a [past section](./adt.md). You can think about:

- How does my representation change depending on whether we only want to give recommendations on _more_ cards, or we also want to recommend _removing_ or even _swapping_ one card for a better one?
- How could we introduce additional explanation about the reason for the change?