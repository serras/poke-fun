package tcg.api

import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.delay
import tcg.Card
import tcg.Category
import tcg.PokemonStage
import tcg.Type

interface PokemonTcgApi {
    suspend fun search(name: String): List<Card>
    suspend fun getById(identifier: String): Card?
}

class FakePokemonTcgApi: PokemonTcgApi {
    override suspend fun search(name: String): List<Card> {
        delay(3.seconds)
        return FAKE_CARDS.filter { name.contains(name, ignoreCase = true) }
    }

    override suspend fun getById(identifier: String): Card? {
        delay(1.seconds)
        return FAKE_CARDS.firstOrNull { it.identifier == identifier }
    }

    companion object {
        val FAKE_CARDS: List<Card> = listOf(
            Card("Bulbasaur", "sv3pt5-1", Category.Pokemon(PokemonStage.Basic), Type.Grass),
            Card("Charmander", "sv3pt5-4", Category.Pokemon(PokemonStage.Basic), Type.Fire),
            Card("Squirtle", "sv3pt5-7", Category.Pokemon(PokemonStage.Basic), Type.Water),
            Card("Caterpie", "sv3pt5-10", Category.Pokemon(PokemonStage.Basic), Type.Grass),
        )
    }
}
