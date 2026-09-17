package ai

import ai.koog.agents.core.tools.annotations.LLMDescription
import com.fasterxml.jackson.annotation.JsonClassDescription
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import kotlinx.serialization.Serializable
import tcg.Deck

interface Titler {
    suspend fun suggest(deck: Deck): TitlerResult

    object Simple : Titler {
        override suspend fun suggest(deck: Deck): TitlerResult =
            TitlerResult("Awesome Deck", emptyList())
    }
}

@JsonClassDescription("Result of requesting a title for a Pokémon TCG deck")
class TitlerResult() {
    constructor(bestTitle: String, otherTitles: List<String>): this() {
        this.bestTitle = bestTitle
        this.otherTitles = otherTitles
    }

    @all:JsonPropertyDescription("Best title for the deck")
    lateinit var bestTitle: String

    @all:JsonPropertyDescription("Other good titles for the deck")
    lateinit var otherTitles: List<String>
}
