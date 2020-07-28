package siapasaya.test.sehatq.viewmodel.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import siapasaya.test.sehatq.model.ModelData
import siapasaya.test.sehatq.repo.ApiClient
import siapasaya.test.sehatq.repo.ApiRoute
import siapasaya.test.sehatq.utils.AppPrefManager
import kotlin.coroutines.CoroutineContext


/**
 * Created by siapaSAYA on 7/28/2020
 */

class MainViewModel() : ViewModel(), CoroutineScope {
    private var disposables : Disposable? = null
    private val gson = Gson()
    private var mAppPrefManager : AppPrefManager? = null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    val _kategori =MutableLiveData<MutableList<ModelData.Category>?>()
    val kategori : LiveData<MutableList<ModelData.Category>?>
        get() =_kategori

    val _promo =MutableLiveData<MutableList<ModelData.ProductPromo>?>()
    val promo : LiveData<MutableList<ModelData.ProductPromo>?>
        get() =_promo

    var isLoading = true

    fun initContext(context : Context){
        mAppPrefManager = AppPrefManager(context)
    }

    fun requestDATA(){
        val observable = setObservable()
        disposables?.dispose()
        isLoading = true
        launch {
            disposables = observable.subscribe({
                _kategori.value = it!![0].data!!.category
                _promo.value = it[0].data!!.productPromo
                val string = gson.toJson(it[0], ModelData::class.java)
                mAppPrefManager?.setProduct(string)
            }, {

            }, {
                isLoading = false
            })
        }
    }

    fun getLocalData(){
        val string = mAppPrefManager?.getProduct()
        val localData = gson.fromJson(string, ModelData::class.java)
        _promo.value = localData.data!!.productPromo
    }


    private fun setObservable(): Observable<MutableList<ModelData>?> {
        val mApiRoute = ApiClient.GetClient("https://private-4639ce-ecommerce56.apiary-mock.com/")!!.create(
            ApiRoute::class.java)
        return mApiRoute.getDATA()
            .toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}