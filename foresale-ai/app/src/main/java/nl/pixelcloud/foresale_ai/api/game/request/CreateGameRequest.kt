package nl.pixelcloud.foresale_ai.api.game.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

/**
 * Created by Rob Peek on 16/06/16.
 */
@RealmClass
open class CreateGameRequest : RealmObject() {

    @SerializedName("noPlayers")
    @Expose
    open var noPlayers: Int = 4;

    @SerializedName("noBots")
    @Expose
    open var noBots: Int = 3;

}