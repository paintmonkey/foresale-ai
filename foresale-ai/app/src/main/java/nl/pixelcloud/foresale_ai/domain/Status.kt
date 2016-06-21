package nl.pixelcloud.foresale_ai.domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Rob Peek on 18/06/16.
 */
open class Status {

    @SerializedName("Key")
    @Expose
    open var key : String? = null

    @SerializedName("Name")
    @Expose
    open var name : String? = null

    @SerializedName("Money")
    @Expose
    open var money : Int = 0

    @SerializedName("HouseCards")
    @Expose
    open var houseCards : Array<Card> = arrayOf<Card>()

    @SerializedName("ChequeCards")
    @Expose
    open var chequeCards : Array<Card> = arrayOf<Card>()

    @SerializedName("PlayerNumber")
    @Expose
    open var playerNumber : Int? = null

    @SerializedName("Score")
    @Expose
    open var score : Int? = null

    @SerializedName("LocalAI")
    @Expose
    open var localAI : Int? = null

    @SerializedName("isLocalAI")
    @Expose
    open var isLocalAI : Boolean? = null

}