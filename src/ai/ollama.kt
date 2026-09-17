package ai

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.ollama.OllamaChatModel
import org.springframework.ai.ollama.api.OllamaApi
import org.springframework.ai.ollama.api.OllamaChatOptions

object Ollama {
    // const val modelId = "gemma4:e4b-mlx" // recommended on macOS, but currently structure output breaks
    private const val modelId = "gemma4:e4b"  // recommended elsewhere

    fun chatModel(): OllamaChatModel =
        OllamaChatModel.builder()
            .ollamaApi(OllamaApi.builder().build())
            .options(OllamaChatOptions.builder().model(modelId).build())
            .build()

    fun chatClient(): ChatClient = ChatClient.builder(chatModel()).build()
}