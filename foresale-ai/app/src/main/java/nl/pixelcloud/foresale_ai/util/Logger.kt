package nl.pixelcloud.foresale_ai.util

import android.util.Log

/**
 * Created by Rob Peek on 18/06/16.
 */

val ERROR_TAG : String = "ERROR";
val GAME_INFO_TAG : String = "GAMEINFO"
val NETWORK_TAG : String = "NETWORK"
val AI_TAG : String = "AI"

fun logError(message : String) {
    Log.e(ERROR_TAG, message)
}

fun logInfo(message: String) {
    Log.i(GAME_INFO_TAG, message)
}

fun logAi(message: String) {
    Log.i(AI_TAG, message)
}
