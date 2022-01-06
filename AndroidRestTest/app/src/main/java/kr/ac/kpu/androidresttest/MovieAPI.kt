package kr.ac.kpu.androidresttest

import retrofit2.Call
import retrofit2.http.GET

interface MovieAPI {

    @GET("api/movie")
    fun getMovieInfo(): Call<List<MovieInfo>>
}