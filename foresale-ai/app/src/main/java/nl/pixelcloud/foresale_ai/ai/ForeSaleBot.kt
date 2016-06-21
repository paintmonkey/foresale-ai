package nl.pixelcloud.foresale_ai.ai

import nl.pixelcloud.foresale_ai.api.game.response.GameInfoResponse
import nl.pixelcloud.foresale_ai.domain.Game

/**
 * Created by Rob Peek on 21/06/16.
 */
interface ForeSaleBot {
    fun bidHouse(info:GameInfoResponse)
    fun bidCheques(info:GameInfoResponse)
}