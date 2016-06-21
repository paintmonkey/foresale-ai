package nl.pixelcloud.foresale_ai.domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Rob Peek on 18/06/16.
 */
open class Bid {

    @SerializedName("Amount")
    @Expose
    open var amount : Int = 0

    @SerializedName("AmountPaid")
    @Expose
    open var amountPaid : Int = 0

    @SerializedName("Player")
    @Expose
    open var player : String = ""

    @SerializedName("PlayerNumber")
    @Expose
    open var playerNumber : Int = -1

    @SerializedName("AquiredCard")
    @Expose
    open var aquiredCard : Card? = null

}