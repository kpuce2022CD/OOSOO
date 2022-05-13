package kr.ac.kpu.oosoosoo.connection

import kr.ac.kpu.oosoosoo.contents.ContentInfo
import kr.ac.kpu.oosoosoo.contents.ContentsReviewInfo
import kr.ac.kpu.oosoosoo.contents.RecommendContentInfo
import kr.ac.kpu.oosoosoo.contents.ReviewInfo
import kr.ac.kpu.oosoosoo.user.UserInfo
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.*
import java.util.concurrent.TimeUnit

class RetrofitBuilder {

    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(3, TimeUnit.MINUTES)
        .readTimeout(3, TimeUnit.MINUTES)
        .writeTimeout(3, TimeUnit.MINUTES)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://3.37.39.106/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val callRecommendContents = retrofit.create(RecommendContentsAPI::class.java)
    val callLogin = retrofit.create(SignInAPI::class.java)
    val callSignUp = retrofit.create(SignUpAPI::class.java)
    val callInterworking = retrofit.create(InterworkingAPI::class.java)

    val callSearchTitle = retrofit.create(SearchTitleAPI::class.java)
    // 검색 테스트용
    val callSearchTest = retrofit.create(SearchTestAPI::class.java)

    val callUserWishList = retrofit.create(UserWishListAPI::class.java)
    val callUserWatchingLog = retrofit.create(UserWatchingLogAPI::class.java)

    val callRating = retrofit.create(RatingAPI::class.java)
    val callUserInfo = retrofit.create(UserInfoAPI::class.java)

    val callReview = retrofit.create(CallReviewAPI::class.java)
    val deleteReview = retrofit.create(DeleteReviewAPI::class.java)
    val callAllReview = retrofit.create(CallAllReviewAPI::class.java)
    val likeReview = retrofit.create(LikeReviewAPI::class.java)
    val myReview = retrofit.create(MyReviewAPI::class.java)
    val myInterworking = retrofit.create(MyInterworkingAPI::class.java)

    val callAddWatchaWishlist = retrofit.create(AddWatchaWishlistAPI::class.java)
    val callAddWatchaWatchingLog = retrofit.create(AddWatchaWatchingLogAPI::class.java)

    val updateUser = retrofit.create(UpdateuserAPI::class.java)
    val deleteUser = retrofit.create(DeleteuserAPI::class.java)
}

interface RecommendContentsAPI {
    @FormUrlEncoded
    @POST("api/recommend/")
    fun getRecommendContents(@FieldMap params: HashMap<String, String>): Call<List<RecommendContentInfo>>
}

interface SignInAPI {
    @FormUrlEncoded
    @POST("api/sign_in/")
    fun getSignIn(@FieldMap params: HashMap<String, String>): Call<Boolean>
}

interface SignUpAPI {
    @FormUrlEncoded
    @POST("api/sign_up/")
    fun getSignUp(@FieldMap params: HashMap<String, String>): Call<Boolean>
}

interface InterworkingAPI {
    @FormUrlEncoded
    @POST("api/interworking/")
    fun getInterworking(@FieldMap params: HashMap<String, String>): Call<Boolean>
}

interface SearchTitleAPI {
    @FormUrlEncoded
    @POST("api/search_title/")
    fun getSearchTitle(@FieldMap params: HashMap<String, String>): Call<List<ContentInfo>>
}

//검색 테스트용
interface SearchTestAPI {
    @GET("api/search_test/")
    fun getSearchTest(): Call<List<ContentInfo>>
}

interface UserWishListAPI {
    @FormUrlEncoded
    @POST("api/user_wishlist/")
    fun getWishList(@FieldMap params: HashMap<String, String>): Call<List<ContentInfo>>
}

interface UserWatchingLogAPI {
    @FormUrlEncoded
    @POST("api/user_watchinglog/")
    fun getWatchingLog(@FieldMap params: HashMap<String, String>): Call<List<ContentInfo>>
}

interface RatingAPI {
    @FormUrlEncoded
    @POST("api/rating/")
    fun getRating(@FieldMap params: HashMap<String, String>): Call<Boolean>
}

interface UserInfoAPI {
    @FormUrlEncoded
    @POST("api/userinfo/")
    fun getUserInfo(@FieldMap params: HashMap<String, String>): Call<UserInfo>
}

interface CallReviewAPI {
    @FormUrlEncoded
    @POST("api/call_review/")
    fun callReview(@FieldMap params: HashMap<String, String>): Call<List<String>>
}

interface DeleteReviewAPI {
    @FormUrlEncoded
    @POST("api/delete_review/")
    fun deleteReview(@FieldMap params: HashMap<String, String>): Call<Boolean>
}

interface CallAllReviewAPI{
    @FormUrlEncoded
    @POST("api/all_review/")
    fun allReview(@FieldMap params: HashMap<String, String>): Call<ArrayList<ReviewInfo>>
}

interface LikeReviewAPI {
    @FormUrlEncoded
    @POST("api/like_review/")
    fun likeReview(@FieldMap params: HashMap<String, String>): Call<Boolean>
}

interface MyReviewAPI{
    @FormUrlEncoded
    @POST("api/my_review/")
    fun myReview(@FieldMap params: HashMap<String, String>): Call<ArrayList<ContentsReviewInfo>>
}

interface MyInterworkingAPI {
    @FormUrlEncoded
    @POST("api/my_interworking/")
    fun callMyInterworking(@FieldMap params: HashMap<String, String>): Call<List<String>>
}

interface AddWatchaWishlistAPI {
    @FormUrlEncoded
    @POST("api/add_w_wishlist/")
    fun callAddWatchaWishlist(@FieldMap params: HashMap<String, String>): Call<Boolean>
}

interface AddWatchaWatchingLogAPI {
    @FormUrlEncoded
    @POST("api/add_w_watchinglog/")
    fun callAddWatchaWatchingLog(@FieldMap params: HashMap<String, String>): Call<Boolean>
}

interface UpdateuserAPI {
    @FormUrlEncoded
    @POST("api/update_user/")
    fun updateUser(@FieldMap params: HashMap<String, String>): Call<Boolean>
}

interface DeleteuserAPI {
    @FormUrlEncoded
    @POST("api/delete_user/")
    fun deleteUser(@FieldMap params: HashMap<String, String>): Call<Boolean>
}
