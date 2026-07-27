package ai

import ai.koog.agents.core.tools.annotations.LLMDescription
import ai.koog.prompt.llm.LLMCapability
import ai.koog.prompt.llm.LLMProvider
import ai.koog.prompt.llm.LLModel
import kotlinx.serialization.Serializable
import tcg.Deck

interface Titler {
    suspend fun suggest(deck: Deck): TitlerResult

    companion object {
        // const val ChosenOllamaModelId = "gemma4:e4b-mlx" // recommended on macOS, but currently structure output breaks
        const val ChosenOllamaModelId = "gemma4:e4b"  // recommended elsewhere

        val ChosenOllamaModel = LLModel(
            provider = LLMProvider.Ollama,
            id = ChosenOllamaModelId,
            capabilities = listOf(
                LLMCapability.Schema.JSON.Basic,
                LLMCapability.Temperature,
                LLMCapability.Thinking,
                LLMCapability.ToolChoice,
                LLMCapability.Tools,
            ),
            contextLength = 256_000
        )
    }

    object Simple : Titler {
        override suspend fun suggest(deck: Deck): TitlerResult =
            TitlerResult("Awesome Deck", emptyList())
    }
}

@Serializable
@LLMDescription("Result of requesting a title for a Pokémon TCG deck")
data class TitlerResult(
    @property:LLMDescription("Best title for the deck")
    val bestTitle: String,
    @property:LLMDescription("Other good titles for the deck")
    val otherTitles: List<String>
)
