package nl.pixelcloud.foresale_ai.domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Created by Rob Peek on 16/06/16.
 */
@RealmClass
open class CreateGameResponse : RealmObject() {

    @PrimaryKey
    @SerializedName("Id")
    @Expose
    open var gameId: String? = null
}