package nl.pixelcloud.foresale_ai.api.game.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

/**
 * Created by Rob Peek on 17/06/16.
 */
@RealmClass
open class JoinGameRequest : RealmObject() {

    @SerializedName("GameKey")
    @Expose
    open var gameKey: String = "";

    @SerializedName("Name")
    @Expose
    open var name: String = "Robot";

}