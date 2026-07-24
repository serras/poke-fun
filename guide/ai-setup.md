# Local AI setup

For this part of Poké-Fun we need to access a Large Language Model (LLM). Although you can use a cloud solution, [Ollama](https://ollama.com/) gives you the chance of running a simpler one locally, so you don't need to spend any money (nor send any potentially private information to a third party).

The easiest approach is to follow the [official download instructions](https://ollama.com/download) — although in macOS you can also use `brew`, and in Linux you can use your distribution's package manager. It's not needed to keep Ollama running as a background service, you can just fire it up using `ollama serve` whenever you need it.

To install a new model, you can use `ollama run <model-name>`. For Poké-Fun it's enough to use [Gemma 4](https://ollama.com/library/gemma4) with Effective 4B parameter (`gemma4:e4b`), which you can get up and running using `ollama run gemma4:e4b`. The first time it needs to download a big chunk of data.

```admonish tip title="Using MLX in macOS"

If you have an M-series computer, you can use the MLX versions for performance gains. Simply add `-mlx` to the model name, like `gemma:e4b-mlx` above.

```

By default, you get a small chat interface. You can try some queries, and then exit the prompt by typing `/bye`. 

```
>>> What is a good name for a workshop about Kotlin, Arrow, Compose Multiplatform, and AI?
Thinking...
Here's a thinking process that leads to the suggested names:

[ ... ]

## 🚀 Catchy & Marketing Names (For Event Websites)
*These are designed to sound exciting, future-proof, and appeal broadly.*

1. **CognitoFlow:** (A play on "cognition" + flow). *High concept, suggests intelligent design.*
2. **The Hyper-Intelligent Stack.**
3. **Digital Brains: Building AI-Powered Multiplatform Apps.**
4. **NextGen UI/UX: Functional Apps Meeting Intelligence.**
5. **Code X Future:** Kotlin, Arrow, and the Age of AI.

[...]

>>> /bye
```

The model is still waiting in the background for more queries, though; you can see the running models with `ollama ps` in a terminal, and stop one of them using `ollama stop <model-name>`.

```admonish example title="Choosing the model in Poké-Fun"

The `ai/titler.kt` file contains the code to set up the connection to local Ollama in using [Koog](https://docs.koog.ai/). Check the current parameters, and adapt as required, especially the choice of model.

```