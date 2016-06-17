package nl.pixelcloud.foresale_ai.api

import android.util.Log
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import io.realm.RealmObject
import nl.pixelcloud.foresale_ai.service.GameEndpoint
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Rob Peek on 17/06/16.
 */
class Client(baseUrl: String)  {

    val TAG : String = "Client"
    var retrofit : Retrofit? = null

    init {

        val gson = GsonBuilder().setExclusionStrategies(object: ExclusionStrategy {
            override fun shouldSkipField(f: FieldAttributes): Boolean {
                return f.declaringClass == RealmObject::class.java
            }

            override fun shouldSkipClass(clazz: Class<*>): Boolean {
                return false
            }
        }).create();

        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder().addInterceptor(interceptor)

        retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .baseUrl(baseUrl)
                .build();

        Log.d(TAG, "Api client initialized with baseUrl ${baseUrl}")
    }

    fun getGameEndpoint() : GameEndpoint {
        return retrofit!!.create(GameEndpoint::class.java)
    }

}