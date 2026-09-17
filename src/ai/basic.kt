package ai

import org.springframework.ai.chat.messages.SystemMessage
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.prompt.Prompt
import tcg.Deck

class BasicAITitler : Titler {
    override suspend fun suggest(deck: Deck): TitlerResult {
        val system = SystemMessage("""
            You are a helpful Pokémon TCG deck builder
        """)
        val user = UserMessage("""
            What are good titles for a cool Pokémon deck? 
            Titles should have a maximum of 10 words.
        """)
        val prompt = Prompt(system, user)

        val client = Ollama.chatClient()
        val result = client.prompt(prompt)
            .call()
            .entity(TitlerResult::class.java) { spec ->
                spec.useProviderStructuredOutput().validateSchema()
            }

        return result ?: Titler.Simple.suggest(deck)

            /*
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

             */
    }
}
