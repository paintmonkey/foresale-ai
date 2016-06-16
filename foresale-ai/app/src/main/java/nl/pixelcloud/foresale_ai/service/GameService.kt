package nl.pixelcloud.foresale_ai.service

import nl.pixelcloud.foresale_ai.domain.CreateGameRequest
import nl.pixelcloud.foresale_ai.domain.CreateGameResponse
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

/**
 * Created by Rob Peek on 16/06/16.
 */
interface GameService {

    @POST("/api/CreateGame")
    fun createGame(@Body request:CreateGameRequest): Observable<CreateGameResponse>

}