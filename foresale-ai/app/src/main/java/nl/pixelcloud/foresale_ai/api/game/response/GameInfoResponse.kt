package nl.pixelcloud.foresale_ai.api.game.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import nl.pixelcloud.foresale_ai.domain.Game

/**
 * Created by Rob Peek on 17/06/16.
 */
open class GameInfoResponse {

    @SerializedName("Game")
    @Expose
    open var game: Game? = null

    @SerializedName("IsItMyTurn")
    @Expose
    open var isItMyTurn : Boolean = false
}