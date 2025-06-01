package gyanani.harish.practiceapicallwithretrofit2025

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    fun getPosts(): Call<List<Post>> // Suspend for coroutines
}

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)