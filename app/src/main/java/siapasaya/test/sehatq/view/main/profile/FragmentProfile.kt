package siapasaya.test.sehatq.view.main.profile

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_profile.*
import siapasaya.test.sehatq.R
import siapasaya.test.sehatq.model.ModelData
import siapasaya.test.sehatq.utils.AppPrefManager
import siapasaya.test.sehatq.utils.BaseFragment
import siapasaya.test.sehatq.view.main.FragmentMain
import siapasaya.test.sehatq.view.search.AdapterProduct


/**
 * Created by siapaSAYA on 7/28/2020
 */

class FragmentProfile : BaseFragment() {

    val gson = Gson()
    var mAppPrefManager : AppPrefManager? = null
    var adapterProduct = AdapterProduct()

    override fun layoutResId(): Int = R.layout.fragment_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAppPrefManager = AppPrefManager(requireContext())
        configureRecyclerView()
        val string = mAppPrefManager?.getPurchased()
        val _data = gson.fromJson(string, ModelData.ListPromo::class.java)
        if(_data?.list != null) {
            adapterProduct.clear()
            adapterProduct.update(_data.list!!)
        }
    }


    private fun configureRecyclerView() {
        abc_recyclerView_purchased.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            isNestedScrollingEnabled = true
            adapter = this@FragmentProfile.adapterProduct
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }

        adapterProduct.setOnChangeItem(object : AdapterProduct.OnChangeItem{
            override fun onClick(id: String) {
                FragmentMain.getFragmented.setDetail(id, "profile")
            }

        })
    }
}