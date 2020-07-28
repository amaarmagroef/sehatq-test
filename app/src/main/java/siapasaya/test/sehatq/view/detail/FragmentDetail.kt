package siapasaya.test.sehatq.view.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_detail.view.*
import siapasaya.test.sehatq.R
import siapasaya.test.sehatq.databinding.FragmentDetailBinding
import siapasaya.test.sehatq.model.ModelData
import siapasaya.test.sehatq.utils.AppPrefManager
import siapasaya.test.sehatq.utils.BaseFragment


/**
 * Created by siapaSAYA on 7/28/2020
 */

class FragmentDetail : BaseFragment() {
    override fun layoutResId(): Int = R.layout.fragment_detail
    var mAppPrefManager : AppPrefManager? = null
    val gson = Gson()
    var mPosition = 0

    private val ids by lazy {
        arguments?.let { FragmentDetailArgs.fromBundle(it).id }
    }

    private val fragments by lazy {
        arguments?.let { FragmentDetailArgs.fromBundle(it).fragment }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAppPrefManager = context?.let { AppPrefManager(it) }
        val binding = FragmentDetailBinding.bind(view)
        val string = mAppPrefManager?.getProduct()
        val data = gson.fromJson(string, ModelData::class.java)
        for(item in 0 until data?.data?.productPromo!!.size){
            if(data.data?.productPromo!![item].id!!.equals(ids)){
                mPosition = item
            }
        }

        binding.model = data.data?.productPromo!![mPosition]
        binding.abcActionBack.setOnClickListener {
            val directionsSearch = FragmentDetailDirections.actionFragmentDetailToFragmentSEARCH()
            val directionHome = FragmentDetailDirections.actionFragmentDetailToFragmentMAIN()
            when {
                fragments.equals("search") -> it.findNavController().navigate(directionsSearch)
                fragments.equals("home") -> {
                    it.findNavController().navigate(directionHome)
                }
                fragments.equals("profile") -> {
                    it.findNavController().navigate(directionHome)
                }
            }
        }

        view.action_favorite.setOnClickListener {
            var number = 0
            val loveds = data.data?.productPromo!![mPosition].loved
            if(loveds==0){
                number = 1
            }
            data.data?.productPromo!![mPosition].setLoveds(number)
            binding.model = data.data?.productPromo!![mPosition]
            binding.actionFavorite.invalidate()

            val temp = gson.toJson(data)
            mAppPrefManager?.setProduct(temp)
        }

        view.abc_action_share.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, data.data?.productPromo!![mPosition].title)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        view.action_buy.setOnClickListener {
            val dataPurchased : ModelData.ProductPromo = data.data?.productPromo!![mPosition]
            val string = gson.toJson(dataPurchased)
            mAppPrefManager?.setPurchased(string)
            val directionsSearch = FragmentDetailDirections.actionFragmentDetailToFragmentSEARCH()
            val directionHome = FragmentDetailDirections.actionFragmentDetailToFragmentMAIN()
            when {
                fragments.equals("search") -> it.findNavController().navigate(directionsSearch)
                fragments.equals("home") -> {
                    it.findNavController().navigate(directionHome)
                }
                fragments.equals("profile") -> {
                    it.findNavController().navigate(directionHome)
                }
            }
        }
    }

}