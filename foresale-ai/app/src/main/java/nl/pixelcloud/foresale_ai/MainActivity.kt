package nl.pixelcloud.foresale_ai

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.NumberPicker
import nl.pixelcloud.foresale_ai.api.Client
import nl.pixelcloud.foresale_ai.api.game.request.CreateGameRequest
import nl.pixelcloud.foresale_ai.game.GameRunner
import nl.pixelcloud.foresale_ai.service.GameEndpoint
import nl.pixelcloud.foresale_ai.util.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var client: Client? = null
    var noPlayersPicker : NumberPicker? = null
    var noBotsPicker : NumberPicker? = null
    var rotateForward : Animation? = null
    var rotateBackward : Animation?= null
    var fab : FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)

        val textView = findViewById(R.id.game_hash_text_view) as EditText?

        fab = findViewById(R.id.fab) as FloatingActionButton?
        fab!!.setOnClickListener { view -> joinGame(textView!!.getText().toString()) }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout?
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer!!.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView?
        navigationView!!.setNavigationItemSelectedListener(this)

        textView!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(s.length == 36) {
                    fab!!.show();
                } else {
                    fab!!.hide()
                }
            }


            override fun afterTextChanged(s: Editable) {

            }
        })

        // Attach the new game button.
        val startGame = findViewById(R.id.create_game_button);
        startGame!!.setOnClickListener { view -> createGame(view, textView) }

        noPlayersPicker = findViewById(R.id.no_player_picker) as NumberPicker;
        noBotsPicker = findViewById(R.id.no_bots_picker) as NumberPicker;

        client = Client(resources.getString(nl.pixelcloud.foresale_ai.R.string.base_url))
    }

    private fun getGameRequest() : CreateGameRequest {
        val request = CreateGameRequest()
        request.noBots = noBotsPicker!!.value
        request.noPlayers = noPlayersPicker!!.value

        return request
    }

    private fun createGame(view: View, textView: EditText?) {

        // Create a default game request.
        val request = getGameRequest()
        val endpoint: GameEndpoint = client!!.getGameEndpoint()

        endpoint.createGame(request)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    Snackbar.make(view, resources.getString(R.string.game_created), Snackbar.LENGTH_LONG).setAction("Action", null).show()
                    textView!!.setText(response.gameId)
                }, { error -> logError(error.message!!) })

    }

    private fun joinGame(gameKey: String) {
        val runner = GameRunner(this)
        runner.join("Kotlin", gameKey)
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout?
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    @SuppressWarnings("StatementWithEmptyBody")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout?
        drawer!!.closeDrawer(GravityCompat.START)
        return true
    }
}
