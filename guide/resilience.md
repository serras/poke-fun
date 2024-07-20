# Deal with bad internet

> **Topics**: resilience, `Schedule`, circuit breaker, memoization

The given implementation of Poké-Fun works fine... if the internet connection is fine (extra points for irony if the room where you are working on this tasks has bad internet). Any realistic application that accesses other services must protect itself against potential disconnections, lags, or services which are down. We refer with the term _resilience_ to all those techniques which help in providing a better experience in problematic scenarios.

This topic is well documented in the official Arrow documentation, we suggest the reader to check the [Resilience section](https://arrow-kt.io/learn/resilience/intro/), both the introduction and the pages describing [`Schedule`](https://arrow-kt.io/learn/resilience/retry-and-repeat/) and [`CircuitBreaker`](https://arrow-kt.io/learn/resilience/circuitbreaker/).

## Retry if fails

## Use a circuit breaker

## Introduce a cache

Cache4k
