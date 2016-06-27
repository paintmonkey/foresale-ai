package nl.pixelcloud.foresale_ai.ui.fragments

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import nl.pixelcloud.foresale_ai.R
import nl.pixelcloud.foresale_ai.api.Client
import nl.pixelcloud.foresale_ai.api.game.request.CreateGameRequest
import nl.pixelcloud.foresale_ai.game.GameRunner
import nl.pixelcloud.foresale_ai.service.GameEndpoint
import nl.pixelcloud.foresale_ai.util.logError
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


/**
 * Created by Rob Peek on 22/06/16.
 */
class JoinGameFragment() : Fragment(){

    var client: Client? = null
    var noPlayersPicker : NumberPicker? = null
    var noBotsPicker : NumberPicker? = null
    var hashView : TextView? = null


    // Interface to define communications with the activity.
    interface onJoinGameListener {
        fun onGameReady(ready:Boolean)
        fun onGameJoined()
    }

    // Companion methods.
    companion object {
        fun newInstance(client:Client) : JoinGameFragment {
            val fragment = JoinGameFragment()
            fragment.client = client

            return fragment;
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root:View = inflater!!.inflate(R.layout.fragment_join_game, container, false);

        hashView = root.findViewById(R.id.game_hash_text_view) as EditText?
        hashView!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                (activity as onJoinGameListener).onGameReady(s.length == 36)
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        noPlayersPicker = root.findViewById(R.id.no_player_picker) as NumberPicker;
        noBotsPicker = root.findViewById(R.id.no_bots_picker) as NumberPicker;

        // Attach the new game button.
        val startGame = root.findViewById(R.id.create_game_button);
        startGame!!.setOnClickListener { view -> createGame(view) }


        return root
    }

    private fun getGameRequest() : CreateGameRequest {
        val request = CreateGameRequest()
        request.noBots = noBotsPicker!!.value
        request.noPlayers = noPlayersPicker!!.value

        return request
    }

    private fun createGame(view: View) {

        // Create a default game request.
        val request = getGameRequest()
        val endpoint: GameEndpoint = client!!.getGameEndpoint()

        endpoint.createGame(request)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    Snackbar.make(view, resources.getString(R.string.game_created), Snackbar.LENGTH_LONG).setAction("Action", null).show()
                    hashView!!.setText(response.gameId)
                }, { error -> logError(error.message!!) })

    }

    fun joinGame() {
        val runner = GameRunner(activity)
        runner.join("Kotlin", hashView!!.text.toString())
    }
}