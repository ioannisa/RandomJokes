package eu.anifantakis.randomjokes

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "Joke")
data class Joke(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    @SerialName("id")
    val apiId: Int,  // from API JSON
    val setup: String,
    val punchline: String,
    val isFavorite: Boolean = false,
    val fetchedAt: Long = System.currentTimeMillis()
)