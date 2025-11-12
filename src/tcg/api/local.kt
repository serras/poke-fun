package tcg.api

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import tcg.Card
import kotlin.io.path.Path
import kotlin.io.path.inputStream
import kotlin.io.path.listDirectoryEntries

const val LOCAL_DATA_FOLDER = "pokemon-tcg-data/cards/en"
const val LOCAL_DATA_GLOB = "*.json"

class LocalPokemonTcgApi(): PokemonTcgApi{
    val json = Json { ignoreUnknownKeys = true }

    @OptIn(ExperimentalSerializationApi::class)
    inline fun forEachCard(block: (Card) -> Unit) {
        val localDataFolder = Path(LOCAL_DATA_FOLDER)
        for (file in localDataFolder.listDirectoryEntries(LOCAL_DATA_GLOB)) {
            file.inputStream().use { stream ->
                try {
                    json.decodeFromStream<List<JsonCard>>(stream).forEach {
                        block(it.tcg)
                    }
                } catch (e: Exception) {
                    println("Failed to parse $file")
                    e.printStackTrace()
                }
            }
        }
    }

    fun Card.inFormat(): Boolean =
        regulationMark in CurrentRegulationMarks || identifier.startsWith("sve-")

    override suspend fun search(name: String): List<Card> = buildList {
        forEachCard { card ->
            if (card.inFormat() && card.name.contains(name, ignoreCase = true))
                add(card)
        }
    }

    override suspend fun getById(identifier: String): Card? {
        forEachCard { card ->
            if (card.identifier == identifier) return@getById card
        }
        return null
    }
}