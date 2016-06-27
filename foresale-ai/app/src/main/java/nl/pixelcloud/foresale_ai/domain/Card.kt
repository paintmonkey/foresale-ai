package nl.pixelcloud.foresale_ai.domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Rob Peek on 18/06/16.
 */
open class Card {

    @SerializedName("Value")
    @Expose
    open var value : Int = 0

    override fun toString() : String {
        return "" + value
    }

}