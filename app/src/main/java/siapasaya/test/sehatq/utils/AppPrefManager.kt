package siapasaya.test.sehatq.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import siapasaya.test.sehatq.model.ModelData


/**
 * Created by siapaSAYA on 7/28/2020
 */
 
class AppPrefManager(context : Context) {
    var mProduct : String?
    var mPurcashed : String?
    private val mPreference : SharedPreferences

    init {
        mPreference = context.getSharedPreferences(NAME,Context.MODE_PRIVATE)
        mProduct = mPreference.getString(KEY_PRODUCT, null)
        mPurcashed = mPreference.getString(KEY_PURCASHED, null)
    }

    fun setProduct(string : String){
        mProduct = string
        mPreference.edit().putString(KEY_PRODUCT, mProduct).apply()
    }

    fun setPurchased(data : String){
        val newData = setDataPurchased(data)
        mPurcashed = newData
        mPreference.edit().putString(KEY_PURCASHED, newData).apply()
    }

    fun setDataPurchased(string : String) : String {
        val gson = Gson()
        var data : ModelData.ListPromo? = null
        val tempObject = gson.fromJson(string, ModelData.ProductPromo::class.java)
        if(getPurchased()==null){
            data = ModelData.ListPromo()
            data.list = mutableListOf<ModelData.ProductPromo>()
            data.list!!.add(tempObject)
        } else {
            data = gson.fromJson(getPurchased(), ModelData.ListPromo::class.java)
            data.list?.add(tempObject)
        }
        val string = gson.toJson(data)
        return string
    }

    fun getProduct() = mProduct

    fun getPurchased() = mPurcashed

    companion object{
        const val NAME = "app_user_file"
        const val KEY_PRODUCT = "product"
        const val KEY_PURCASHED = "purcashed"
    }

}