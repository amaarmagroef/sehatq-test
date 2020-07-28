package siapasaya.test.sehatq.view.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_main.*
import siapasaya.test.sehatq.R
import siapasaya.test.sehatq.utils.BaseFragment
import siapasaya.test.sehatq.view.detail.FragmentDetailArgs
import siapasaya.test.sehatq.view.main.home.FragmentHome
import siapasaya.test.sehatq.view.main.profile.FragmentProfile
import siapasaya.test.sehatq.viewmodel.main.MainViewModel


/**
 * Created by siapaSAYA on 7/28/2020
 */

class FragmentMain : BaseFragment() {

    override fun layoutResId(): Int = R.layout.fragment_main

    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    private var mFragmentTag: String? = null
    private val KEY_CURRENT_FRAGMENT = "current_fragment"
    private val KEY_FRAGMENT_TAG = "fragment_tag"
    private var mFragmentManager : FragmentManager? = null
    private var mCurrentFragment: Fragment? = null

    companion object{
        @JvmStatic
        var getFragmented = FragmentMain()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFragmentManager = childFragmentManager
        if (savedInstanceState != null) {
            mCurrentFragment =
                mFragmentManager?.getFragment(savedInstanceState, KEY_CURRENT_FRAGMENT)
            mCurrentFragment =
                childFragmentManager.getFragment(savedInstanceState, KEY_CURRENT_FRAGMENT)
            mFragmentTag = savedInstanceState.getString(KEY_FRAGMENT_TAG)
        } else {

            mCurrentFragment = FragmentHome()
            mFragmentTag = "home"
            initFragment()

        }
        getFragmented = this

        bottomnavigation.setOnNavigationItemSelectedListener {
            if(it.itemId == R.id.FragmentHOME){
                mCurrentFragment = FragmentHome()
                mFragmentTag = "home"
                initFragment()
            }
            if(it.itemId == R.id.FragmentPROFILE){
                mCurrentFragment = FragmentProfile()
                mFragmentTag = "profile"
                initFragment()
            }

            true
        }
    }


    fun setNavigation(){
        val directions = FragmentMainDirections.actionFragmentMAINToFragmentSEARCH()
        findNavController().navigate(directions)
    }

    fun setDetail(id : String, from : String){
        val direction = FragmentMainDirections.actionFragmentMAINToFragmentDetail()
        direction.id = id
        direction.fragment = from
        findNavController().navigate(direction)
    }

    private fun initFragment() {
        val prev = childFragmentManager.findFragmentByTag(mFragmentTag)
        val transaction = childFragmentManager.beginTransaction()
        if (prev != null) {
            transaction.show(prev)
        } else {
            transaction.replace(R.id.container_fragment, mCurrentFragment!!, mFragmentTag)
        }
        transaction.commit()
    }
}