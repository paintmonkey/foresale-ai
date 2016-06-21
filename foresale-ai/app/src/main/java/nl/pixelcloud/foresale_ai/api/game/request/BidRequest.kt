package nl.pixelcloud.foresale_ai.api.game.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Rob Peek on 21/06/16.
 */
open class BidRequest {

    @SerializedName("PlayerKey")
    @Expose
    open var playerKey: String? = null

    @SerializedName("Amount")
    @Expose
    open var amount: String? = ""
}