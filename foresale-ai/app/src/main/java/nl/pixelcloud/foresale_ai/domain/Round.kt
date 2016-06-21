package nl.pixelcloud.foresale_ai.domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Rob Peek on 18/06/16.
 */
open class Round {

    @SerializedName("HighestBid")
    @Expose
    open var highestBid : Int = 0

    @SerializedName("AvailableCards")
    @Expose
    open var availableCards : Array<Card> = arrayOf<Card>()

    @SerializedName("Bids")
    @Expose
    open var bids : Array<Bid> = arrayOf<Bid>()

    @SerializedName("TypeOfRound")
    @Expose
    open var typeOfRound : Int? = null
}