@file:Suppress("PLUGIN_IS_NOT_ENABLED")
package tcg.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import tcg.*

fun HttpClientWithJson(): HttpClient = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
        })
    }
}

class KtorPokemonTcgApi(
    private val httpClient: HttpClient = HttpClientWithJson()
): PokemonTcgApi {
    override suspend fun search(name: String): List<Card> {
        if (name.isBlank()) return emptyList()
        val response = httpClient.get("https://api.pokemontcg.io/v2/cards") {
            url {
                // bound the search to the newest regulation mark (do not show old cards)
                val regulationMark = CurrentRegulationMarks.joinToString(separator = " OR ") { "regulationMark:$it" }
                parameters.append("q", "name:\"*$name*\" ($regulationMark OR set.id:sve)")
                parameters.append("orderBy", "name")
                parameters.append("pageSize", "30")
            }
        }
        if (response.status != HttpStatusCode.OK) return emptyList()
        return response.body<JsonMultipleResult>().data.map { it.tcg }
    }

    override suspend fun getById(identifier: String): Card? {
        val response = httpClient.get("https://api.pokemontcg.io/v2/cards/$identifier")
        if (response.status != HttpStatusCode.OK) return null
        return response.body<JsonSingleResult>().data.tcg
    }
}
