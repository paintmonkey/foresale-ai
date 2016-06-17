package nl.pixelcloud.foresale_ai.service

import nl.pixelcloud.foresale_ai.api.game.request.CreateGameRequest
import nl.pixelcloud.foresale_ai.api.game.request.JoinGameRequest
import nl.pixelcloud.foresale_ai.api.game.response.CreateGameResponse
import nl.pixelcloud.foresale_ai.api.game.response.GameInfoResponse
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

/**
 * Created by Rob Peek on 16/06/16.
 */
interface GameEndpoint {

    @POST("/api/CreateGame")
    fun createGame(@Body request: CreateGameRequest): Observable<CreateGameResponse>

    @POST("/api/JoinGame")
    fun joinGame(@Body request: JoinGameRequest): Observable<GameInfoResponse>

}