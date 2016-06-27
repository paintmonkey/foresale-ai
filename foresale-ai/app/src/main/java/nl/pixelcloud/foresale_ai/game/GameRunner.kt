package nl.pixelcloud.foresale_ai.game

import android.content.Context
import nl.pixelcloud.foresale_ai.R
import nl.pixelcloud.foresale_ai.ai.MiniMaxBot
import nl.pixelcloud.foresale_ai.ai.SimpleBot
import nl.pixelcloud.foresale_ai.api.Client
import nl.pixelcloud.foresale_ai.api.game.request.JoinGameRequest
import nl.pixelcloud.foresale_ai.api.game.response.GameInfoResponse
import nl.pixelcloud.foresale_ai.domain.Game
import nl.pixelcloud.foresale_ai.domain.Game.*
import nl.pixelcloud.foresale_ai.domain.Ranking
import nl.pixelcloud.foresale_ai.service.GameEndpoint
import nl.pixelcloud.foresale_ai.util.logError;
import nl.pixelcloud.foresale_ai.util.logInfo
import rx.Observable

import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit


/**
 * Created by Rob Peek on 17/06/16.
 */
class GameRunner(ctx: Context) {

    var ctx: Context;
    var client: Client? = null
    var endpoint: GameEndpoint? = null
    var myKey: String? = null
    var bot: MiniMaxBot? = null

    init {
        this.ctx = ctx
        client = Client(ctx.resources.getString(R.string.base_url))
        endpoint = client!!.getGameEndpoint()
        bot = MiniMaxBot(endpoint as GameEndpoint)
    }

    // Joins a game and start the play
    fun join(name: String, gameKey: String) {
        val request = JoinGameRequest()
        request.gameKey = gameKey
        request.name = name

        endpoint!!.joinGame(request)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response ->
                            myKey = response.status?.key
                            play(response)
                        },
                        { error -> logError(String.format(ctx.resources.getString(R.string.join_game_failed), error.message)) }
                )
    }

    // Refreshes the game info.
    fun refresh() {
        endpoint!!.refresh(myKey!!)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response -> play(response) },
                        { error -> logError(String.format(ctx.resources.getString(R.string.refresh_game_failed), error.message)) }
                )
    }

    fun play(info: GameInfoResponse) {

        // If the game has ended: display the results.
        if (info.game!!.stateOfGame == GameState.COMPLETED) {
            return displayResults(info)
        }

        Observable.create(
                Observable.OnSubscribe<GameInfoResponse> {
                    subscriber ->
                    if (info.isItMyTurn) {
                        // Exciting!
                        when (info.game!!.stateOfGame) {
                            GameState.WAITING_FOR_PLAYERS -> logInfo("WAITING FOR PLAYERS")
                            GameState.CHEQUE_CARDS -> bot!!.bidCheques(info)
                            GameState.HOUSE_CARDS -> bot!!.bidHouse(info)
                        }
                    }

                    subscriber.onNext(info)
                })
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({ response -> refresh() },
                           { error -> logError(String.format(ctx.resources.getString(R.string.refresh_game_failed), error.message))
                               System.out.print(error.printStackTrace())
                           }
                )
    }


    fun displayResults(gameInfo: GameInfoResponse) {
        logInfo(ctx.resources.getString(R.string.game_completed));
        logInfo(ctx.resources.getString(R.string.game_results));
        for ((index, ranking: Ranking) in gameInfo.game!!.finalRanking.withIndex()) {
            logInfo(String.format(ctx.resources.getString(R.string.game_result_row),
                    index.inc(),
                    ranking.name,
                    ranking.score))
        }
    }
}