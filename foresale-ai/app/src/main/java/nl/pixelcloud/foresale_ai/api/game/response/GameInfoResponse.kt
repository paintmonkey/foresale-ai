package nl.pixelcloud.foresale_ai.api.game.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Created by Rob Peek on 17/06/16.
 */
@RealmClass
open class GameInfoResponse : RealmObject() {

    @PrimaryKey
    @SerializedName("GameKey")
    @Expose
    open var gameKey: String? = null
}