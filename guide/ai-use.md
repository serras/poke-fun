# Fancy deck names

> **Topics**: simple requests, tools

As you may have hinted from the UI, our first goal using AI is to suggest titles for decks.
The code snippets in this section relate to [Koog](https://docs.koog.ai/), a Kotlin-first library to work with AI agents, but the concepts behind them are pretty similar in other AI libraries, even within other ecosystems.

```admonish tip title="Learn more about Koog"

Apart from the [official documentation](https://docs.koog.ai/), the following resources may help you learn more about Koog, especially if your goal is to integrate them in larger systems.

- [_Koog your own AI!_ at Devoxx UK](https://www.youtube.com/watch?v=RBMGnOAaf6A)
- [_Full-Stack Kotlin AI_ at KotlinConf 2026](https://www.youtube.com/watch?v=0ttH-wnawtA)

```

## Improving the basic titler

The `main.kt` file is currently injecting a a very simple `Titler` to suggest titles. In fact, it always returns `"Awesome Deck"`. If instead of `Titler.Simple` you use the `BasicAITitler`, the request is processed via an LLM, so you will get different titles every time.

Still, the titles tend to be very generic. The problem is that you are not introducing any _contextual_ information into the query. You **task** is to improve the request used by `suggest` in `BasicAITitler` to have that additional information. Potential candidates are the names of cards in the decks, the previous title, and even the date the request is sent! It may be convenient to read Koog's documentation about [prompts](https://docs.koog.ai/prompts/) to understand how to better reflect this information for the LLM to use.

```admonish info title="Structured output"

The prompt executor in `BasicAITitler` directly responds with a `TitlerResult`. This feature of some LLMs (including Gemma4) is called [_structured output_](https://docs.koog.ai/structured-output/). Koog automatically sends the corresponding JSON schema to the LLM, you only need to mark the types as `@Serializable`, as we've done in the code.

```

## Using agents

Although you can craft your prompts directly, it is better to use AI [_agents_](https://docs.koog.ai/agents/) instead. Agents follow an _strategy_ to have a "conversation" with the LLM model, so that there can be a back-and-forth of messages requesting and providing information. In this case we use the [ReAct strategy](https://docs.koog.ai/predefined-agent-strategies/#react-strategy). 

In addition, AI agents may also use [_tools_](https://docs.koog.ai/tools/). As part of its response, the LLM model may request additional information, knowing which data is available from the tools. This is the best way to inject contextual information, but also to provide behavior which is better coded separately (think of performing basic arithmetic).

The `ai/tool.kt` file provides a blueprint of how you can introduce tools in Koog. Your **task** is to add new functions to `DeckInformation`, and mark them with the corresponding annotations, so that more information about your deck is available to the AI agent.

```admonish tip title="A look under the hood"

If you are curious about the actual "conversation" between Poké-Fun and the LLM model, you can introduce more logging in the `handleEvents` block.

```
