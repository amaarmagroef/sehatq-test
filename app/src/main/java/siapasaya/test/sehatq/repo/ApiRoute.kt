package siapasaya.test.sehatq.repo

import io.reactivex.rxjava3.core.Maybe
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import siapasaya.test.sehatq.model.ModelData


/**
 * Created by siapaSAYA on 7/18/2020
 */
 
interface ApiRoute {
    @GET("home")
    fun getDATA(): Maybe<MutableList<ModelData>>
}