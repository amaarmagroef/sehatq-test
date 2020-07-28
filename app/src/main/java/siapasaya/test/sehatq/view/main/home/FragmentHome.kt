package siapasaya.test.sehatq.view.main.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_home.*
import siapasaya.test.sehatq.R
import siapasaya.test.sehatq.model.ModelData
import siapasaya.test.sehatq.utils.AppPrefManager
import siapasaya.test.sehatq.utils.BaseFragment
import siapasaya.test.sehatq.utils.observe
import siapasaya.test.sehatq.view.main.FragmentMain
import siapasaya.test.sehatq.view.main.FragmentMainDirections
import siapasaya.test.sehatq.view.search.FragmentPencarianDirections
import siapasaya.test.sehatq.viewmodel.main.MainViewModel


/**
 * Created by siapaSAYA on 7/28/2020
 */
 
class FragmentHome : BaseFragment() {

    override fun layoutResId(): Int = R.layout.fragment_home

    private val gson = Gson()
    private var adapterKategori = AdapterKategori()
    private var adapterItem = AdapterItem()
    private var mAppPrefManager : AppPrefManager? = null
    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
        bindViewModel()
        mAppPrefManager = AppPrefManager(requireContext())
        abc_logo_favorite.setOnClickListener {
            viewModel.getLocalData()
        }
        search.setOnClickListener {
            FragmentMain.getFragmented.setNavigation()
        }

        abc_action_search.setOnClickListener {
            FragmentMain.getFragmented.setNavigation()
        }
    }

    private fun bindViewModel(){
        observe(viewModel.kategori) {
            showLoading(viewModel.isLoading)
            if (view == null) return@observe
            it?.let {
                adapterKategori.clear()
                adapterKategori.update(it)
                showLoading(false)
            }
        }


        observe(viewModel.promo) {
            showLoading(viewModel.isLoading)
            if (view == null) return@observe
            it?.let {
                adapterItem.clear()
                adapterItem.update(it)
                showLoading(false)
            }
        }
    }

    fun showLoading(show: Boolean){
        abc_action_loading.visibility = if (show) {
            abc_action_search.isEnabled = false
            View.VISIBLE
        }
        else {
            abc_action_search.isEnabled = true
            View.GONE
        }
    }

    private fun configureRecyclerView(){
        abc_recyclerView_item.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            isNestedScrollingEnabled = true
            adapter = this@FragmentHome.adapterItem
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }

        adapterItem.setOnChangeAdapter(object : AdapterItem.OnChangeAdapter{
            override fun onLoveClick(id : String, loved : Int) {
                /**
                 * edit sharedPreferences
                 * convert from string to json
                 */
                var string = mAppPrefManager?.getProduct()
                var _tempModelData = gson.fromJson(string, ModelData::class.java)
                val product = _tempModelData.data?.productPromo!!
                for(item in 0 until product.size){
                    if(_tempModelData.data?.productPromo!![item].id == id){
                        _tempModelData.data?.productPromo!![item].setLoveds(loved)
                    }
                }

                /**
                 * save shared preferences
                 * convert from json to string
                 */
                string = gson.toJson(_tempModelData)
                mAppPrefManager?.setProduct(string)
            }

            override fun onItemClick(id: String) {
                FragmentMain.getFragmented.setDetail(id, "home")
            }
        })

        abc_recyclerView_category.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL ,false)
            setHasFixedSize(true)
            isNestedScrollingEnabled = true
            adapter = this@FragmentHome.adapterKategori
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.initContext(requireContext())
        viewModel.requestDATA()
    }
}