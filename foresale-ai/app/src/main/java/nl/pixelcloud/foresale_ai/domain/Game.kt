package nl.pixelcloud.foresale_ai.domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by Rob Peek on 17/06/16.
 */
open class Game {

    @PrimaryKey
    @SerializedName("Id")
    @Expose
    open var id: String = ""

    @SerializedName("NoPlayers")
    @Expose
    open var noPlayers : Int = 0

    @SerializedName("StateOfGame")
    @Expose
    open var stateOfGame : Int = 0

    @SerializedName("CanAddPlayer")
    @Expose
    open var canAddPlayer : Boolean = false

    @SerializedName("FinalRanking")
    @Expose
    open var finalRanking : Int = 0

    @SerializedName("CompletedHouseRounds")
    @Expose
    open var completedHouseRounds : Array<String> = arrayOf<String>()



}