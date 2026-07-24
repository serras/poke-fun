package ai

import ai.koog.agents.core.agent.AIAgent
import ai.koog.agents.core.tools.ToolRegistry
import ai.koog.agents.core.tools.annotations.LLMDescription
import ai.koog.agents.core.tools.annotations.Tool
import ai.koog.agents.core.tools.reflect.ToolSet
import ai.koog.agents.ext.agent.reActStrategy
import ai.koog.agents.features.eventHandler.feature.handleEvents
import ai.koog.prompt.executor.llms.MultiLLMPromptExecutor
import ai.koog.prompt.executor.ollama.client.OllamaClient
import tcg.Deck

class ToolAITitler : Titler {
    override suspend fun suggest(deck: Deck): String {
        val agent = AIAgent(
            promptExecutor = MultiLLMPromptExecutor(OllamaClient()),
            llmModel = Titler.ChosenOllamaModel,
            strategy = reActStrategy(reasoningInterval = 50),
            toolRegistry = ToolRegistry {
                tools(DeckInformation(deck))
            }
        ) {
            handleEvents {
                onLLMCallStartingBlocking { println("<llm call starting>") }
                onLLMCallCompletedBlocking { println("<llm call completed>") }
                onToolCallStartingBlocking { println("<tool call ${it.toolName} starting>") }
                onToolCallCompletedBlocking { println("<tool call ${it.toolName} completed>") }
            }
        }
        val result = agent.run(
            """What is a good name for a cool Pokémon deck? 
                           At the end give me just a title of maximum 10 words, no explanation or further steps.
                           Do not ask for user input, only use the available tools.
                        """)
        return result.lines().last()
    }

    class DeckInformation(val deck: Deck): ToolSet {
        @Tool
        @LLMDescription("Returns the number of cards in the deck.")
        fun numberOfCards(): Int = deck.cards.size
    }
}