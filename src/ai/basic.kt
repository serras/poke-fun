package ai

import ai.koog.prompt.dsl.prompt
import ai.koog.prompt.executor.llms.MultiLLMPromptExecutor
import ai.koog.prompt.executor.model.executeStructured
import ai.koog.prompt.executor.ollama.client.OllamaClient
import tcg.Deck

class BasicAITitler : Titler {
    override suspend fun suggest(deck: Deck): TitlerResult {
        val executor = MultiLLMPromptExecutor(OllamaClient())
        val result = executor.executeStructured<TitlerResult>(
            prompt = prompt("titler") {
                user("""
                    What are good titles for a cool Pokémon deck? 
                    Titles should have a maximum of 10 words.
                """)
            },
            model = Titler.ChosenOllamaModel,
        )
        return result.getOrNull()?.data ?: Titler.Simple.suggest(deck)
    }
}
