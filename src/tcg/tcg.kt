package tcg

import androidx.compose.runtime.Composable
import io.kamel.image.asyncPainterResource

data class Card(
    val name: String,
    val identifier: String,
    val category: Category,
    val type: Type?
)

sealed interface Category {
    data class Pokemon(val stage: PokemonStage) : Category
    data class Energy(val category: EnergyCategory) : Category
    data class Trainer(val category: TrainerCategory) : Category
}

enum class PokemonStage {
    Basic,
    Stage1,
    Stage2;
}

enum class EnergyCategory {
    Basic,
    Special;
}

enum class TrainerCategory {
    Item,
    Tool,
    Supporter,
    Stadium;
}

enum class Type(private val imageUrl: String) {
    Colorless("https://archives.bulbagarden.net/media/upload/thumb/1/1d/Colorless-attack.png/40px-Colorless-attack.png"),
    Grass("https://archives.bulbagarden.net/media/upload/thumb/2/2e/Grass-attack.png/40px-Grass-attack.png"),
    Water("https://archives.bulbagarden.net/media/upload/thumb/1/11/Water-attack.png/40px-Water-attack.png"),
    Fire("https://archives.bulbagarden.net/media/upload/thumb/a/ad/Fire-attack.png/40px-Fire-attack.png"),
    Lightning("https://archives.bulbagarden.net/media/upload/thumb/0/04/Lightning-attack.png/40px-Lightning-attack.png"),
    Fighting("https://archives.bulbagarden.net/media/upload/thumb/4/48/Fighting-attack.png/40px-Fighting-attack.png"),
    Psychic("https://archives.bulbagarden.net/media/upload/thumb/e/ef/Psychic-attack.png/40px-Psychic-attack.png"),
    Darkness("https://archives.bulbagarden.net/media/upload/thumb/a/ab/Darkness-attack.png/40px-Darkness-attack.png"),
    Metal("https://archives.bulbagarden.net/media/upload/thumb/6/64/Metal-attack.png/40px-Metal-attack.png"),
    Dragon("https://archives.bulbagarden.net/media/upload/thumb/8/8a/Dragon-attack.png/40px-Dragon-attack.png");
    
    val imageResource
      @Composable get() = asyncPainterResource(data = imageUrl)
}