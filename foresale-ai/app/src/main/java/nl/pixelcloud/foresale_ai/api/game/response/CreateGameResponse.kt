package nl.pixelcloud.foresale_ai.api.game.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Rob Peek on 16/06/16.
 */
open class CreateGameResponse {

    @SerializedName("Id")
    @Expose
    open var gameId: String? = null
}