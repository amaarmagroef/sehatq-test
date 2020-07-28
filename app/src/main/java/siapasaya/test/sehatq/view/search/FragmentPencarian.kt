package siapasaya.test.sehatq.view.search

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_search.*
import siapasaya.test.sehatq.R
import siapasaya.test.sehatq.model.ModelData
import siapasaya.test.sehatq.utils.AppPrefManager
import siapasaya.test.sehatq.utils.BaseFragment
import siapasaya.test.sehatq.view.main.FragmentMainDirections
import java.util.*


/**
 * Created by siapaSAYA on 7/28/2020
 */

class FragmentPencarian : BaseFragment() {
    var adapterProduct = AdapterProduct()
    var mAppPrefManager : AppPrefManager? = null
    var gson = Gson()
    var data = mutableListOf< ModelData.ProductPromo>()

    override fun layoutResId(): Int = R.layout.fragment_search

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        abc_action_search.addTextChangedListener {
            if(it.isNullOrEmpty()){
                adapterProduct.clear()
                showAll()
            } else {
                getDATA(it.toString())
            }
        }
        mAppPrefManager = AppPrefManager(requireContext())
        configureRecyclerView()
    }

    fun getDATA(s : String){
        var _data = mutableListOf<ModelData.ProductPromo>()
        for(item in data){
            if (item.title!!.toLowerCase(Locale.ROOT).contains(s.toLowerCase(Locale.ROOT))) {
                _data.add(item)
            }
        }
        adapterProduct.clear()
        adapterProduct.update(_data)
    }

    private fun configureRecyclerView() {
        abc_recyclerView_pencarian.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            isNestedScrollingEnabled = true
            adapter = this@FragmentPencarian.adapterProduct
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }

        adapterProduct.setOnChangeItem(object : AdapterProduct.OnChangeItem{
            override fun onClick(id: String) {
                val directions = FragmentPencarianDirections.actionFragmentSEARCHToFragmentDetail()
                directions.id = id
                directions.fragment = "search"
                findNavController().navigate(directions)
            }
        })
        showAll()
    }

    fun showAll(){
        var string = mAppPrefManager?.getProduct()
        val _tempModelData = gson.fromJson(string, ModelData::class.java)
        data = _tempModelData.data?.productPromo!!
        adapterProduct.clear()
        adapterProduct.update(data)
    }
}