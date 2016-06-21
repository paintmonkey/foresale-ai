package nl.pixelcloud.foresale_ai.api.game.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Rob Peek on 17/06/16.
 */
open class JoinGameRequest {

    @SerializedName("GameKey")
    @Expose
    open var gameKey: String = "";

    @SerializedName("Name")
    @Expose
    open var name: String = "Botlin";

}