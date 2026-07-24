# Using AI for decks

> **Topics**: simple requests, tools

As you may have hinted from the UI, our first goal using AI is to suggest titles for decks.
The code snippets in this section relate to [Koog](https://docs.koog.ai/), a Kotlin-first library to work with AI agents, but the concepts behind them are pretty similar in other AI libraries, even within other ecosystems.

```admonish tip title="Learn more about Koog"

Apart from the [official documentation](https://docs.koog.ai/), the following resources may help you learn more about Koog, especially if your goal is to integrate them in larger systems.

- [_Koog your own AI!_ at Devoxx UK](https://www.youtube.com/watch?v=RBMGnOAaf6A)
- [_Full-Stack Kotlin AI_ at KotlinConf 2026](https://www.youtube.com/watch?v=0ttH-wnawtA)

```

## Improving the basic titler

In the `deck/viewModel.kt` file we are currently using a very simple `Titler` to suggest titles. In fact, it always returns `"Awesome Deck"`. If instead of `Titler.Simple` you use the `BasicAITitler`, the request is given to an AI agent instead, so you will get different titles every time.

Still, the titles tend to be very generic. The problem is that you are not introducing any _contextual_ information into the query. You **task** is to improve the request used by `suggest` in `BasicAITitler` to have that additional information. Potential candidates are the names of cards in the decks, the previous title, and even the date the request is sent!

## Using tools

Although you can craft your prompts directly, AI agents support a better strategy: using [_tools_](https://docs.koog.ai/tools/). We no longer have a one-shot call, instead there's a _reasoning loop_ in which the AI agent may request additional information, knowing which data is available from the tools. This is the best way to inject contextual information, but also to provide behavior which is better coded separately (think of performing basic arithmetic).

The `ai/tool.kt` file provides a blueprint of how you can introduce tools in Koog. Your **task** is to add new functions to `DeckInformation`, and mark them with the corresponding annotations, so that more information about your deck is available to the AI agent.

Tools do not work on their own, though, the definition of the `AIAgent` now introduces a _strategy_ (in particular, the [ReAct strategy](https://docs.koog.ai/predefined-agent-strategies/#react-strategy)). This strategy describes an interaction between our client and the AI model itself, so that there can be a back-and-forth of messages requesting and providing information. The code provides a few hooks so you can see when each event is happening: feel free to introduce more information there if you're interested on the actual conversation happening under the hood.
