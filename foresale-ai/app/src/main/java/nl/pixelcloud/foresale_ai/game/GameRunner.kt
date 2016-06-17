package nl.pixelcloud.foresale_ai.game

import android.content.Context
import android.util.Log
import nl.pixelcloud.foresale_ai.R
import nl.pixelcloud.foresale_ai.api.Client
import nl.pixelcloud.foresale_ai.api.game.request.JoinGameRequest
import nl.pixelcloud.foresale_ai.service.GameEndpoint
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by Rob Peek on 17/06/16.
 */
class GameRunner(ctx:Context) {

    var client : Client? = null
    var endpoint : GameEndpoint? = null

    init {
        client = Client(ctx.resources.getString(R.string.base_url))
        endpoint = client!!.getGameEndpoint()
    }

    fun join(name: String, gameKey : String) {
        val request = JoinGameRequest()
        request.gameKey = gameKey
        request.name = name

        endpoint!!.joinGame(request)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    response -> Log.d("GameRunner", response.toString())
                })
    }
}