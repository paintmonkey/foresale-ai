package nl.pixelcloud.foresale_ai.ai

import nl.pixelcloud.foresale_ai.api.game.request.BidRequest
import nl.pixelcloud.foresale_ai.api.game.response.GameInfoResponse
import nl.pixelcloud.foresale_ai.domain.Card
import nl.pixelcloud.foresale_ai.domain.Game
import nl.pixelcloud.foresale_ai.service.GameEndpoint
import nl.pixelcloud.foresale_ai.util.logError
import nl.pixelcloud.foresale_ai.util.logInfo
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 * Created by Rob Peek on 21/06/16.
 */
class SimpleBot(endpoint: GameEndpoint) : ForeSaleBot {

    var endpoint: GameEndpoint? = null

    init {
        this.endpoint = endpoint;
    }

    fun bid(info: GameInfoResponse, amount: Int) {

        var bid: Int? = amount
        if(info.game!!.stateOfGame!!.equals(Game.GameState.HOUSE_CARDS) && amount > info.status!!.money){
            bid = null
        }

        logInfo("${info.game!!.stateOfGame.toString()} :I bid $bid")

        var request: BidRequest = BidRequest()
        request.playerKey = info.status?.key
        if (bid != null) request.amount = bid.toString() else request.amount = null

        endpoint!!.bid(request)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response -> logInfo(response.toString()) }, { error -> logError(error.message!!) })
    }

    override fun bidHouse(info: GameInfoResponse) {
        bid(info, info.game!!.round!!.highestBid + 1)
    }

    override fun bidCheques(info: GameInfoResponse) {
        val cards: Array<Card> = info.status!!.houseCards
        val index: Int = Random().nextInt((cards.size))

        bid(info, cards[index].value)
    }
}