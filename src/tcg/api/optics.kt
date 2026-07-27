package tcg.api

import io.github.nomisrev.JsonPath
import io.github.nomisrev.array
import io.github.nomisrev.select
import io.github.nomisrev.string
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.decodeFromStream
import tcg.Card
import kotlin.io.path.Path
import kotlin.io.path.inputStream
import kotlin.io.path.listDirectoryEntries

class OpticsPokemonTcgApi(): PokemonTcgApi{
    val json = Json { ignoreUnknownKeys = true }

    @OptIn(ExperimentalSerializationApi::class)
    inline fun forEachCard(block: (JsonElement) -> Unit) {
        val localDataFolder = Path(LOCAL_DATA_FOLDER)
        for (file in localDataFolder.listDirectoryEntries(LOCAL_DATA_GLOB)) {
            file.inputStream().use { stream ->
                try {
                    val document = json.decodeFromStream<JsonElement>(stream)
                    JsonPath.array.getOrNull(document)?.map(block)
                } catch (e: Exception) {
                    println("Failed to parse $file")
                    e.printStackTrace()
                }
            }
        }
    }

    override suspend fun search(name: String): List<Card> = TODO()

    override suspend fun getById(identifier: String): Card? {
        forEachCard { cardJson ->
            val jsonId = JsonPath.select("id").string.getOrNull(cardJson)
            if (jsonId == identifier) { return@getById json.decodeFromJsonElement(cardJson) }
        }
        return null
    }
}