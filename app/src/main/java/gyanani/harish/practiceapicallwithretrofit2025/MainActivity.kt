package gyanani.harish.practiceapicallwithretrofit2025

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Response
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {
    private val api = ApiClient.apiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //callApiUsingRunnable()
        //callApiUsingCoroutine()
        callApiUsingCoroutineWithSuspendApiCall()
    }

    /**
     * How Retrofit Handles suspend
     * When you define a suspend function in Retrofit:
     *
     * Retrofit automatically switches to a background thread.
     *
     * The call suspends until the network response arrives.
     *
     * Resumes execution when the data is ready, returning the result directly (no Call<T> wrapper needed).
     */
    private fun callApiUsingCoroutineWithSuspendApiCall() {
        lifecycleScope.launch {
            var result = api.getPostsWithSuspend()
            findViewById<TextView>(R.id.textView1).text = result.toString()
        }
    }

    private fun callApiUsingCoroutine() {
        lifecycleScope.launch {
            var result: retrofit2.Response<List<Post>>? = null
            withContext(Dispatchers.IO) {
                result = api.getPosts().execute()
            }
            findViewById<TextView>(R.id.textView1).text = result?.body().toString()
        }
    }


    private fun callApiUsingRunnable() {
        Log.d("callApiUsingRunnable", "step1 thread=" + Thread.currentThread().toString())
        Thread {//worker thread
            Log.d("callApiUsingRunnable", "step2 thread=" + Thread.currentThread().toString())
            val result = api.getPosts().execute()
            Log.d("callApiUsingRunnable", "step3 thread=" + Thread.currentThread().toString())
            //cannot do here because background thread cannot access UI in android
            //textView1.text = result
            runOnUiThread {
                Log.d("callApiUsingRunnable", "step4 thread=" + Thread.currentThread().toString())
                Log.d("callApiUsingRunnable", result.body().toString())
                findViewById<TextView>(R.id.textView1).text = result.body().toString()
            }
        }.start()
    }
}