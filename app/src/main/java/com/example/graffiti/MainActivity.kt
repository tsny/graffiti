package com.example.graffiti

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.speech.tts.TextToSpeech
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener,
    SubredditLookupDialog.SubredditDialogListener {

    private var tts: TextToSpeech? = null
    private var lastPostId: String? = null

    private var currentSub = "apexlegends"
    private var homeSub = "AskReddit"

    private lateinit var swiper: SwipeRefreshLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawer: DrawerLayout
    private lateinit var listView: ListView

    private lateinit var listingsAdapter: ArrayAdapter<DataX>
    private var listingsArray: List<DataX> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        Old Stuff Here for Notifs
//        createNotificationChannel()

        setCurrentSub(currentSub)

        // Nav View
        drawer = findViewById(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navView = findViewById<NavigationView>(R.id.navView)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.goToSubBtn -> {
                    SubredditLookupDialog().show(supportFragmentManager, "customDialog")
                }
                R.id.home_sub_btn -> {
                    getSubredditListings(homeSub, "")
                }
                R.id.nextPageBtn -> {
                    getSubredditListings(currentSub, lastPostId)
                }
            }
            true
        }

        // Swiping
        swiper = findViewById(R.id.swiper)
        swiper.setOnRefreshListener {
            getSubredditListings(currentSub, "")
            swiper.isRefreshing = false
        }

        // set API call policy allowed in main thread
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        listView = findViewById(R.id.listview1)
        listingsAdapter =
            ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, listingsArray)
        listView.adapter = listingsAdapter


        listView.setOnItemClickListener { _, _, i, _ ->
            setThread(currentSub, listingsArray[i].id)
        }

        tts = TextToSpeech(this, this)
        getSubredditListings(currentSub, "")
    }

    private fun setCurrentSub(sub: String) {
        title = sub
        currentSub = sub
    }

    // Sets the list items on click to open the comments section
    private fun setItemOnClickToOpenComment() {
        listView.setOnItemClickListener { _, _, i, _ ->
            val postName = listingsArray[i].name
            title = "$currentSub -- $postName"
            setThread(currentSub, listingsArray[i].id)
        }
    }

    override fun onSubredditDialogSearched(dialog: SubredditLookupDialog) {
        dialog.btn.setOnClickListener {
            getSubredditListings(dialog.input.text.toString(), "")
            dialog.dismiss()
            drawer.closeDrawers()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getSubredditListings(sub: String, after: String?): Throwable? {
        CoroutineScope(IO).launch {
            val req = ServiceBuilder.buildService(ServiceBuilder.RedditService::class.java)
            val call = req.listSubmissions(sub, after)
            println(call.request().url().toString())

            call.enqueue(object : Callback<Listing> {
                override fun onFailure(call: Call<Listing>, t: Throwable) {
                    println(t.message)
                }

                override fun onResponse(call: Call<Listing>, response: Response<Listing>) {
                    if (response.body()?.data?.children?.isEmpty() == true) {
                        Toast.makeText(applicationContext, "Invalid Sub Name", Toast.LENGTH_SHORT)
                            .show()
                        return
                    }
                    listingsAdapter.clear()
                    setItemOnClickToOpenComment()
                    response.body()?.data?.children?.forEach { listingsAdapter.add(it.data) }
                    lastPostId = response.body()?.data?.after.toString()
                    if (sub != currentSub) {
                        setCurrentSub(sub)
                    } else {
                        Toast.makeText(applicationContext, "Refreshed", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }

        return null
    }

    private fun setItemOnClickToTTS() {
        listView.setOnItemClickListener { _, _, i, _ ->
            tts?.speak(listingsArray[i].toTTSString(), TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

    // Sets the viewed subreddit thread
    private fun setThread(sub: String, post: String): Throwable? {
        CoroutineScope(IO).launch {
            val req = ServiceBuilder.buildService(ServiceBuilder.RedditService::class.java)
            val call = req.getPost(sub, post, null, null)
            println(call.request().url().toString())

            call.enqueue(object : Callback<Comments> {
                override fun onFailure(call: Call<Comments>, t: Throwable) {
                    println(t.message)
                }

                override fun onResponse(call: Call<Comments>, response: Response<Comments>) {
                    if (response.body().isNullOrEmpty()) {
                        Toast.makeText(applicationContext, "Bad Listing", Toast.LENGTH_SHORT)
                            .show()
                        return
                    }
                    listingsAdapter.clear()
                    setItemOnClickToTTS()
                    response.body()?.forEach() { listing ->
                        listing.data.children.forEach() { comment ->
                            listingsAdapter.add(comment.data)
                        }
                    }
                }
            })
        }

        return null
    }

    // Initialize the TTS engine
    override fun onInit(p0: Int) {
        if (p0 == TextToSpeech.SUCCESS) {
            tts!!.language = Locale.US
        }
    }
}
