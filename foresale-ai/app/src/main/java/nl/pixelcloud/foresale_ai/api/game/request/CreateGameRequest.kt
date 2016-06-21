package nl.pixelcloud.foresale_ai.api.game.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Rob Peek on 16/06/16.
 */
open class CreateGameRequest {

    @SerializedName("noPlayers")
    @Expose
    open var noPlayers: Int = 4;

    @SerializedName("noBots")
    @Expose
    open var noBots: Int = 3;

}