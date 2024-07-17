package tcg

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import io.kamel.image.asyncPainterResource
import io.kamel.core.Resource as KamelResource

data class Card(
    val name: String,
    val identifier: String,
    val category: Category,
    val type: Type?
): Comparable<Card> {
    val imageUrl: String
        get() {
            val (set, id) = identifier.split('-')
            return "https://images.pokemontcg.io/$set/${id}_hires.png"
        }

    val imageResource: KamelResource<Painter>
      @Composable get() = asyncPainterResource(data = imageUrl)

    override fun compareTo(other: Card): Int {
        if (this.category != other.category) return this.category.compareTo(other.category)
        if (this.name != other.name) return this.name.compareTo(other.name)
        return this.identifier.compareTo(other.identifier)
    }
}

sealed interface Category: Comparable<Category> {
    data class Pokemon(val stage: PokemonStage) : Category
    data class Energy(val category: EnergyCategory) : Category
    data class Trainer(val category: TrainerCategory) : Category

    override fun compareTo(other: Category): Int =
        when {
            this is Pokemon && other is Pokemon ->
                this.stage.compareTo(other.stage)
            this is Pokemon -> -1
            other is Pokemon -> 1
            this is Energy && other is Energy ->
                this.category.compareTo(other.category)
            this is Energy -> -1
            other is Energy -> 1
            this is Trainer && other is Trainer ->
                this.category.compareTo(other.category)
            this is Trainer -> -1
            other is Trainer -> 1
            else -> 0
        }
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
    
    val imageResource: KamelResource<Painter>
      @Composable get() = asyncPainterResource(data = imageUrl)
}