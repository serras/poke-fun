package tcg.api

import kotlinx.serialization.Serializable
import tcg.*

val CurrentRegulationMarks = listOf("G", "H", "I")

@Serializable
data class JsonCard(
    val name: String,
    val id: String,
    val supertype: String,
    val subtypes: List<String> = emptyList(),
    val types: List<String> = emptyList(),
    val regulationMark: String? = null,
) {
    val basicSubtypes = listOf("Basic", "Baby", "Restores", "V")
    val level1Subtypes = listOf("Stage 1", "V")
    val level2Subtypes = listOf("Stage 2", "VMAX", "VSTAR", "Restored")
    val otherSubtypes = listOf("Level-Up", "BREAK", "LEGEND", "V-UNION")

    val tcg: Card
        get() {
            val category = when (supertype) {
                "Pokémon" -> when {
                    "EX" in subtypes && "MEGA" in subtypes -> Category.Pokemon(PokemonStage.Stage1)
                    "EX" in subtypes -> Category.Pokemon(PokemonStage.Basic)
                    subtypes.any { it in otherSubtypes } -> Category.Pokemon(PokemonStage.Other)
                    subtypes.any { it in level2Subtypes } -> Category.Pokemon(PokemonStage.Stage2)
                    subtypes.any { it in level1Subtypes } -> Category.Pokemon(PokemonStage.Stage1)
                    subtypes.any { it in basicSubtypes } -> Category.Pokemon(PokemonStage.Basic)
                    else -> throw IllegalArgumentException("Pokémon $subtypes not recognized")
                }
                "Energy" -> when {
                    "Basic" in subtypes || subtypes.isEmpty() -> Category.Energy(EnergyCategory.Basic)
                    "Special" in subtypes -> Category.Energy(EnergyCategory.Special)
                    else -> throw IllegalArgumentException("Energy $subtypes not recognized")
                }
                "Trainer" -> when {
                    "Item" in subtypes || subtypes.isEmpty() -> Category.Trainer(TrainerCategory.Item)
                    "Pokémon Tool" in subtypes -> Category.Trainer(TrainerCategory.Tool)
                    "Stadium" in subtypes -> Category.Trainer(TrainerCategory.Stadium)
                    "Supporter" in subtypes -> Category.Trainer(TrainerCategory.Supporter)
                    else -> Category.Trainer(TrainerCategory.Other)
                }
                else -> throw IllegalArgumentException()
            }
            val type = types.firstOrNull()?.let(Type::valueOf)
            return Card(name, id, category, type, regulationMark)
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


