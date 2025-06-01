package gyanani.harish.practiceapicallwithretrofit2025

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity: AppCompatActivity() {
    private val api = ApiClient.apiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        callApiUsingRunnable()
    }


    private fun callApiUsingRunnable() {
        Log.d("callApiUsingRunnable", "step1 thread="+ Thread.currentThread().toString())
        Thread {
            Log.d("callApiUsingRunnable", "step2 thread="+ Thread.currentThread().toString())
            val result = api.getPosts().execute()
            Log.d("callApiUsingRunnable", "step3 thread="+ Thread.currentThread().toString())
            //cannot do here because background thread cannot access UI in android
            //textView1.text = result
            runOnUiThread{
                Log.d("callApiUsingRunnable", "step4 thread="+ Thread.currentThread().toString())
                Log.d("callApiUsingRunnable", result.body().toString())
                findViewById<TextView>(R.id.textView1).text = result.body().toString()
            }
        }.start()
    }
}