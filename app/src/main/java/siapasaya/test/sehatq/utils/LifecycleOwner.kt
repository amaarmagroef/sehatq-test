package siapasaya.test.sehatq.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer


/**
 * Created by siapaSAYA on 7/28/2020
 */

inline fun <T> LifecycleOwner.observe(data: LiveData<T>, crossinline block: (T?) -> Unit) {
    data.observe(this, Observer { block(it) })
}