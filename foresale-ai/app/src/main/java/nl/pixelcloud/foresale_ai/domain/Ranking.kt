package nl.pixelcloud.foresale_ai.domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Rob Peek on 21/06/16.
 */
class Ranking {

    @SerializedName("Name")
    @Expose
    open var name:String? = ""

    @SerializedName("Score")
    @Expose
    open var score:Int? = 0
}