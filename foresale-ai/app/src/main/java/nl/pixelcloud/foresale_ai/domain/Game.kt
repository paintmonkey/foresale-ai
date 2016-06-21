package nl.pixelcloud.foresale_ai.domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Rob Peek on 17/06/16.
 */
open class Game {

    @SerializedName("Id")
    @Expose
    open var id: String = ""

    @SerializedName("NoPlayers")
    @Expose
    open var noPlayers : Int = 0

    enum class GameState {
        @SerializedName("0")
        WAITING_FOR_PLAYERS,

        @SerializedName("1")
        HOUSE_CARDS,

        @SerializedName("2")
        CHEQUE_CARDS,

        @SerializedName("3")
        COMPLETED
    }

    @SerializedName("StateOfGame")
    @Expose
    open var stateOfGame : GameState? = null

    @SerializedName("CanAddPlayer")
    @Expose
    open var canAddPlayer : Boolean = false

    @SerializedName("FinalRanking")
    @Expose
    open var finalRanking : Array<Ranking> = arrayOf<Ranking>()

    @SerializedName("CompletedHouseRounds")
    @Expose
    open var completedHouseRounds : Array<Round> = arrayOf<Round>()

    @SerializedName("CompletedCheckRounds")
    @Expose
    open var completedCheckRounds : Array<Round> = arrayOf<Round>()

    @SerializedName("CurrentRound")
    @Expose
    open var round: Round? = null
}