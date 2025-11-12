# Deal with bad internet

> **Topics**: resilience, `Schedule`, circuit breaker, caching

The given implementation of Poké-Fun works fine... if the internet connection is fine (extra points for irony if the room where you are working on this tasks has bad internet). Any realistic application that accesses other services must protect itself against potential disconnections, lags, or services which are down. We refer with the term _resilience_ to all those techniques which help in providing a better experience in problematic scenarios.

This topic is well documented in the official Arrow documentation, we suggest the reader to check the [Resilience section](https://arrow-kt.io/learn/resilience/intro/), both the introduction and the pages describing [`Schedule`](https://arrow-kt.io/learn/resilience/retry-and-repeat/) and [`CircuitBreaker`](https://arrow-kt.io/learn/resilience/circuitbreaker/).

```admonish tip title="Decorator"

We strongly recommend to use the [Decorator pattern](https://refactoring.guru/design-patterns/decorator) in the following tasks. For a good introduction, fully in Kotlin, check [this video](https://www.youtube.com/watch?v=erWsXSqQ-CM) by Dave Leeds.

```

```admonish warning title="Pokémon TCG API is down"

Unfortunately, the [Pokémon TCG API](https://pokemontcg.io/) we use for this exercise is routinely down.
For that reason, the current code uses the [local variant](./local.md) by default.
If you want to run the code against the remote API, change the constructor for `SearchViewModel`.

```

## Retry if fails

The task here is to create a wrapper that adds retry capabilities to an inner `PokemonTcgApi` instance. Explore different variations of the [`Schedule`](https://arrow-kt.io/learn/resilience/retry-and-repeat/#constructing-a-policy), from a simple fixed repetition, to exponential backoff policies.

### Use a circuit breaker

Improve the previous soltuion with a [circuit breaker](https://arrow-kt.io/learn/resilience/circuitbreaker/), which ensures that we do not overload the service or the client in case the (transient) failure takes a long time to recover.

As described in the [documentation](https://arrow-kt.io/learn/resilience/circuitbreaker/), the best option for resilience is to combine both approaches.

> A common pattern to make resilient systems is to compose a circuit breaker with a backing-off policy that prevents the resource from overloading. `Schedule` is insufficient to make your system resilient because you also have to consider parallel calls to your functions. In contrast, a circuit breaker track failures of every function call or even different functions to the same resource or service.

## Introduce a cache

The given implementation queries the Pokémon TCG API service for _every_ search and every card. However, cards with an existing identifier almost never change (except for errata), so there is no need to get them over and over. Searches also change rarely: new sets with additional cards only appear every 3 months, and we do not expect our users to stay that long in the application.

Introducing a _cache_ improves performance, and also makes our application more resilient, since fewer calls need to communicate outside. Your **task** is to introduce [Cache4k](https://reactivecircus.github.io/cache4k/), a nice Kotlin Multiplatform option for this matter.

```admonish info title="Caching and memoization"

_Memoization_ is a concept in functional programming very related to caching. If you have a completely _pure_ function — no other effect except computation — then every run with the same input argument should give you the same output. As a result, there is no need to run the function twice. This becomes especially relevant for recursive functions like Fibonacci. Arrow comes with [`MemoizedDeepRecursiveFunction`](https://arrow-kt.io/learn/collections-functions/recursive/#memoized-recursive-functions), which improved over the usual deep recursion support in Kotlin.

The task at hand, however, is not really in the realm of memoization. First of all, we do not have a pure computation, but rather data coming from external sources. In addition, recursion is completely absent from API calls.

```