package siapasaya.test.sehatq.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import siapasaya.test.sehatq.R


/**
 * Created by siapaSAYA on 7/28/2020
 */

@BindingAdapter("sourceOriginal")
fun ImageView.sourceOriginal(path: String?) {
    path?.also {
        val original =  it
        Glide.with(this)
            .load(original)
            .placeholder(R.drawable.ic_baseline_image_24)
            .skipMemoryCache(true)
            .into(this)
    }
}

@BindingAdapter("sourceDrawable")
fun ImageView.sourceDrawable(id: Int) {
    if(id==0) setImageResource(R.drawable.ic_baseline_favorite_border_24)
    else setImageResource(R.drawable.ic_round_favorite)
}