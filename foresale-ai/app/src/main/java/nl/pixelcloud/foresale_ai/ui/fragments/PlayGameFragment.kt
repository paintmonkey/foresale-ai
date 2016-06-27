package nl.pixelcloud.foresale_ai.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nl.pixelcloud.foresale_ai.R
import nl.pixelcloud.foresale_ai.api.Client

/**
 * Created by Rob Peek on 22/06/16.
 */
class PlayGameFragment : Fragment() {

    var client: Client? = null

    companion object {
        fun newInstance(client:Client) : PlayGameFragment {
            var fragment = PlayGameFragment()
            fragment.client = client

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_play_game, container, false);
    }
}