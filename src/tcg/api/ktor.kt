@file:Suppress("PLUGIN_IS_NOT_ENABLED")
package tcg.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import tcg.*

@Serializable
data class JsonCard(
    val name: String,
    val id: String,
    val supertype: String,
    val subtypes: List<String> = emptyList(),
    val types: List<String> = emptyList(),
) {
    val tcg: Card
        get() {
            val category = when (supertype) {
                "Pokémon" -> when {
                    "Basic" in subtypes -> Category.Pokemon(PokemonStage.Basic)
                    "Stage 1" in subtypes -> Category.Pokemon(PokemonStage.Stage1)
                    "Stage 2" in subtypes -> Category.Pokemon(PokemonStage.Stage2)
                    else -> throw IllegalArgumentException()
                }
                "Energy" -> when {
                    "Basic" in subtypes -> Category.Energy(EnergyCategory.Basic)
                    "Special" in subtypes -> Category.Energy(EnergyCategory.Special)
                    else -> throw IllegalArgumentException()
                }
                "Trainer" -> when {
                    "Pokémon Tool" in subtypes -> Category.Trainer(TrainerCategory.Tool)
                    "Item" in subtypes -> Category.Trainer(TrainerCategory.Item)
                    "Stadium" in subtypes -> Category.Trainer(TrainerCategory.Stadium)
                    "Supporter" in subtypes -> Category.Trainer(TrainerCategory.Supporter)
                    else -> throw IllegalArgumentException()
                }
                else -> throw IllegalArgumentException()
            }
            val type = types.firstOrNull()?.let(Type::valueOf)
            return Card(name, id, category, type)
        }
}

@Serializable
data class JsonSingleResult(
    val data: JsonCard
)

@Serializable
data class JsonMultipleResult(
    val data: List<JsonCard>
)

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
                parameters.append("q", "name:\"*$name*\" (regulationMark:G OR regulationMark:H OR regulationMark:I OR set.id:sve)")
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
