package nl.pixelcloud.foresale_ai

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by Rob Peek on 16/06/16.
 */
class App:Application() {
    override fun onCreate() {
        super.onCreate()
        val realmConfig = RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build()
        Realm.setDefaultConfiguration(realmConfig)
    }
}
