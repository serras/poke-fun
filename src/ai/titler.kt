package ai

import ai.koog.prompt.llm.LLMCapability
import ai.koog.prompt.llm.LLMProvider
import ai.koog.prompt.llm.LLModel
import tcg.Deck

interface Titler {
    suspend fun suggest(deck: Deck): String

    companion object {
        const val ChosenOllamaModelId = "gemma4:e4b-mlx" // recommended on macOS
        // const val ChosenOllamaModelId = "gemma4:e4b"  // recommended elsewhere

        val ChosenOllamaModel = LLModel(
            provider = LLMProvider.Ollama,
            id = ChosenOllamaModelId,
            capabilities = listOf(
                LLMCapability.Schema.JSON.Basic,
                LLMCapability.Temperature,
                LLMCapability.Thinking,
                LLMCapability.ToolChoice,
                LLMCapability.Tools,
                LLMCapability.Vision.Image,
            ),
            contextLength = 256_000
        )
    }

    object Simple : Titler {
        override suspend fun suggest(deck: Deck): String = "Awesome Deck"
    }
}
