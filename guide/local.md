# Using local data

> **Topics**: optics, JSON manipulation

Until this point we have focused on requesting data from a remote API. In some scenarios such requests are not possible or desirable, and a local data source is a better option. When you clone the repository for these exercises, you should find a `pokemon-tcg-data` submodule that contains a [copy of the data for the remote API](https://github.com/PokemonTCG/pokemon-tcg-data).

## Targeting data with optics

The current implementation is extremely inefficient: it transforms every card in every `.json` file into a `Card` object, and then filters the results. A better solution is to perform the query directly on the JSON document, and only transform the data if it matches.

## Understanding `forEachCard`