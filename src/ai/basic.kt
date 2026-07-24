package ai

import ai.koog.agents.core.agent.AIAgent
import ai.koog.prompt.executor.llms.MultiLLMPromptExecutor
import ai.koog.prompt.executor.ollama.client.OllamaClient
import tcg.Deck

class BasicAITitler : Titler {
    override suspend fun suggest(deck: Deck): String {
        val agent = AIAgent(
            promptExecutor = MultiLLMPromptExecutor(OllamaClient()),
            llmModel = Titler.ChosenOllamaModel,
        )
        val result = agent.run(
            """What is a good name for a cool Pokémon deck? 
                           Give me just a title of maximum 10 words, no explanation or further steps.
                        """)

        return result.lines().first()
    }
}
