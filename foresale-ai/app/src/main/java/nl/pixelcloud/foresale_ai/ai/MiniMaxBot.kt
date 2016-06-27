package nl.pixelcloud.foresale_ai.ai

import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import java.lang.Math
import nl.pixelcloud.foresale_ai.api.game.response.GameInfoResponse
import nl.pixelcloud.foresale_ai.domain.Bid
import nl.pixelcloud.foresale_ai.domain.Card
import nl.pixelcloud.foresale_ai.domain.Round.TypeOfRound
import nl.pixelcloud.foresale_ai.service.GameEndpoint
import nl.pixelcloud.foresale_ai.util.logAi
import nl.pixelcloud.foresale_ai.util.logInfo
import java.util.*

/**
 * Created by Rob Peek on 22/06/16.
 */
class MiniMaxBot(endpoint: GameEndpoint) : SimpleBot(endpoint) {

    var info: GameInfoResponse? = null
    var type: TypeOfRound? = null
    var highestBid : Int? = null
    var money : Int? = null
    var availableCards : Array<Card> = arrayOf()
    var houseCards: Array<Card>? = null
    var chequeCards: Array<Card>? = null
    var playerNumber : Int  = -1
    var lastBids : Array<Bid> = arrayOf()
    val division : Double = 2.0
    var availableCardsInHand: List<Int> = listOf(1..30).flatMap { it }
    var availableChequesInHand: Int = 238

    enum class Outcome {
        WIN_HIGHEST_BID,
        WIN_BY_PASSING
    }
    var potentialOutcomes : Array<Outcome> = arrayOf(Outcome.WIN_HIGHEST_BID, Outcome.WIN_BY_PASSING)

    private fun prepare(info: GameInfoResponse) {
        this.info = info;
        this.type = info.game!!.round!!.typeOfRound
        this.money = info.status!!.money
        this.availableCards = info.game!!.round!!.availableCards
        this.houseCards = info.status!!.houseCards
        this.chequeCards = info.status!!.chequeCards
        this.highestBid = info.game!!.round!!.highestBid
        this.playerNumber = info.status!!.playerNumber!!
        this.lastBids = info.game!!.round!!.bids

        for(card:Card in availableCards) {
            availableCardsInHand = availableCardsInHand.filter {
                it != card.value
            }
        }
        logAi("Cards still in hand:" + availableCardsInHand.joinToString(","))
    }

    override fun bidHouse(info: GameInfoResponse) {
        prepare(info);
        val table: Table<Int, String, Double> = generatePayOffTable()
        val amount : Int = maxiMinRegret(table)

        super.bid(info, amount)
    }
    override fun bidCheques(info: GameInfoResponse) {

        prepare(info);

        for(card:Card in availableCards) {
            availableChequesInHand -= card.value;
        }


        houseCards!!.sortBy { it.value }
        logAi("My cards:" + houseCards!!.joinToString(","))
        val table:Table<Int, String, Double> = generateCashTable();
        val amount : Int = maxiMinRegret(table);

        super.bid(info, amount);
    }

    private fun maxiMinRegret(table: Table<Int, String, Double>) : Int {

        logAi("--> Calculating regret")
        logAi("--> Last bid:" + highestBid)
        logAi("--> My Money:" + money)
        logAi("--> Available cards:" + availableCards.joinToString(","))

        val columns: MutableSet<String> = table.columnKeySet()
        val rows: MutableSet<Int> = table.rowKeySet()
        val maxPayoff : HashMap<String, Double> = hashMapOf()
        val maxRegret : HashMap<Int, Double> = hashMapOf()

        for(outcome in columns){
            for(row in rows) {
                val currentMax = maxPayoff.get(outcome) ?: table.get(row, outcome)
                val max = Math.max(currentMax, table.get(row, outcome))
                maxPayoff.put(outcome, max)
            }
        }

        // Calculate regret. (Best payoff - actual payoff)
        for(column in columns) {
            val max: Double = maxPayoff.get(column) ?: 0.0
            for(row in rows) {
                table.put(row, column, max-table.get(row, column))
            }
        }

        for(row in rows) {
            for(column in columns) {
                val currentMax = maxRegret.get(row) ?: table.get(row, column)
                val max = Math.max(currentMax, table.get(row, column))
                maxRegret.put(row, max)
            }
        }

        logAi("--> MaxRegret table" + maxRegret.toString());

        var minValue : Double? = null
        var option: Int = -1
        for(key :Int  in maxRegret.keys) {
            if( minValue == null) {
                option = key
                minValue = maxRegret[key]
            } else if(minValue > maxRegret[key] as Double){
                minValue = maxRegret[key]
                option = key
            }
        }
        logAi("-->" + minValue)
        logAi("--> Picking option:" + option)
        logAi("--> Done\n")

        return option
    }

    private fun generatePayOffTable() : Table<Int, String, Double> {
        var table: Table<Int, String, Double> = HashBasedTable.create()
        val cards : Array<Card> = info!!.game!!.round!!.availableCards
        val options : List<Int> = getAvailableOptions()

        val factor = 1 -( availableCardsInHand.sum() / 465.0)
        logInfo("Factor:" + factor)

        for(option in options) {
            for(card in cards) {
                for(outcome in potentialOutcomes){
                    table.put(option, columnFor(card.value, outcome), calculatePayOff(option, card.value, outcome, factor))
                }
            }
        }

        return table
    }

    private fun generateCashTable() : Table<Int, String, Double> {
        var table: Table<Int, String, Double> = HashBasedTable.create()
        val cards : Array<Card> = info!!.game!!.round!!.availableCards
        val options : List<Int> = getAvailableOptions()

        val factor = 1 - (availableChequesInHand / 238.0)
        logInfo("Factor:" + factor)

        for(option in options) {
            for(card in cards) {
                for(outcome in potentialOutcomes){
                    table.put(option, columnFor(card.value, outcome), calculateCashOff(option, card.value, outcome, factor))
                }
            }
        }

        return table
    }

    private fun myLastBid() : Int {
        val index = lastBids.indexOfFirst {
            it.playerNumber == this.playerNumber
        }
        if (index == -1) return 0 else return lastBids[index].amount
    }

    private fun lowestCardValue() : Int {

        val min = availableCards.minBy { it.value }

        if(min == null) return 0 else return min.value

    }

    private fun calculatePayOff(bid: Int, value:Int, outcome: Outcome, factor:Double) : Double {

        // If we pass now, the outcome is deterministic
        if(bid == -1) {
            // We are given the card with lowest value.
            return -Math.ceil(bid/division) - lowestCardValue()
        }

        // When we decide to bid, calculate the payoff for the potential outcomes.
        // No way to know what happens, but in the future we will end up with one
        // of the potential outcomes.
        when(outcome) {
            // Have to pay full amount
            Outcome.WIN_HIGHEST_BID -> return (-bid+value) * factor
            // Lose half of my bid.
            Outcome.WIN_BY_PASSING -> return (-Math.ceil(bid/division).toInt() + value) * factor
        }

    }

    private fun calculateCashOff(bid: Int, value:Int, outcome: Outcome, factor:Double) : Double {
        when(outcome) {
            // Have to pay full amount
            Outcome.WIN_HIGHEST_BID -> return (-bid)*(value-7.5) * factor
            // Lose half of my bid.
            Outcome.WIN_BY_PASSING -> return (-bid)*(value-15) * (factor)
        }
    }

    private fun columnFor(value:Int, outcome: Outcome) : String {
        when(outcome){
            Outcome.WIN_HIGHEST_BID -> return "WIN_BID_" + value
            Outcome.WIN_BY_PASSING -> return "WIN_PASS_" + value
        }
    }

    private fun getAvailableOptions() : List<Int> {

        var options : MutableList<Int> = mutableListOf()

        when(type) {
            TypeOfRound.BID_HOUSE -> {
                var max: Int = if(money == null) 0 else money as Int
                var min: Int = if(highestBid != null) highestBid as Int + 1 else 0

                options.add(-1) // -1 represents passing
                for(i in min..max){
                    options.add(i)
                }
            }
            TypeOfRound.BID_CHEQUE -> {
                for((index, card) in houseCards!!.withIndex()){
                    options.add(card.value)
                }
            }
        }

        return options
    }
}