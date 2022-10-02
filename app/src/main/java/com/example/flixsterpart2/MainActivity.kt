package com.example.flixsterpart2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import com.example.flixsterpart2.databinding.ActivityMainBinding
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONException

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

fun tvCreateJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false

}

private const val TAG = "MainActivity/"
private const val SEARCH_API_KEY = BuildConfig.API_KEY
private const val MOVIE_SEARCH_URL = "https://api.themoviedb.org/3/movie/top_rated?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
private const val TV_SEARCH_URL = "https://api.themoviedb.org/3/tv/top_rated?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"

class MainActivity : AppCompatActivity() {
    private val movies = mutableListOf<Movie>()
    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding

    // ??????????????????????????
    private val tvs = mutableListOf<Tv>()
    private lateinit var tvsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        moviesRecyclerView = findViewById(R.id.topMovieRecyclerView)
        val movieAdapter = MovieAdapter(this, movies)
        moviesRecyclerView.adapter = movieAdapter
        moviesRecyclerView.layoutManager = LinearLayoutManager(this, HORIZONTAL, false)


        tvsRecyclerView = findViewById(R.id.topTvRecyclerView)
        val tvAdapter = TvAdapter(this, tvs)
        tvsRecyclerView.adapter = tvAdapter
        tvsRecyclerView.layoutManager = LinearLayoutManager(this, HORIZONTAL, false)

        val movieClient = AsyncHttpClient()
        movieClient.get(MOVIE_SEARCH_URL, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch articles: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched articles: $json")
                try {
                    // TODO: Do something with the returned json (contains article information)
                    val parsedJson = createJson().decodeFromString(
                        BaseResponse.serializer(),
                        json.jsonObject.toString()
                    )

                    // TODO: Save the articles and reload the screen
                    parsedJson.result?.let { list ->
                        movies.addAll(list)
                        // Reload the screen
                        movieAdapter.notifyDataSetChanged()
                    }
                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }
        })



        val tvClient = AsyncHttpClient()
        tvClient.get(TV_SEARCH_URL, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch articles: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched articles: $json")
                try {
                    // TODO: Do something with the returned json (contains article information)
                    val parsedJson = createJson().decodeFromString(
                        TvBaseResponse.serializer(),
                        json.jsonObject.toString()
                    )

                    // TODO: Save the articles and reload the screen
                    parsedJson.result?.let { list ->
                        tvs.addAll(list)
                        // Reload the screen
                        tvAdapter.notifyDataSetChanged()
                    }
                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }
        })

    }
}