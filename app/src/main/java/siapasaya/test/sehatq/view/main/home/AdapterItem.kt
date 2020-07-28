package siapasaya.test.sehatq.view.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_barang.view.*
import siapasaya.test.sehatq.R
import siapasaya.test.sehatq.databinding.ItemBarangBinding
import siapasaya.test.sehatq.model.ModelData
import siapasaya.test.sehatq.utils.BindableViewHolder


/**
 * Created by siapaSAYA on 7/28/2020
 */

class AdapterItem() : RecyclerView.Adapter<AdapterItem.ItemViewHolder>() {

    private var data = mutableListOf<ModelData.ProductPromo>()
    private var mOnChangeAdapter : OnChangeAdapter? = null

    fun update(data: MutableList<ModelData.ProductPromo>) {
        clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun clear() {
        this.data.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_barang,
            parent,
            false)
        return ItemViewHolder(
            view
        )

    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val model = data[position]
        holder.bind(model)
        holder.itemView.action_favorite.setOnClickListener {
            var number = 1
            if(model.loved==1){
                number = 0
                holder.itemView.action_favorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            } else
                holder.itemView.action_favorite.setImageResource(R.drawable.ic_round_favorite)
            model.setLoveds(number)
            mOnChangeAdapter?.onLoveClick(model.id!!, number)
            data[position] = model
            notifyDataSetChanged()
        }

        holder.itemView.setOnClickListener {
            mOnChangeAdapter?.onItemClick(model.id!!)
        }

    }

    class ItemViewHolder(view: View) :
        BindableViewHolder<ItemBarangBinding, ModelData.ProductPromo>(view) {
        override fun bind(viewModel: ModelData.ProductPromo) {
            binding?.model =viewModel
            ViewCompat.setTransitionName(binding!!.image, viewModel.imageUrl)
        }
    }

    fun setOnChangeAdapter(listener : OnChangeAdapter){
        mOnChangeAdapter = listener
    }

    interface OnChangeAdapter{
        fun onLoveClick(id : String, loved : Int){}
        fun onItemClick(id : String)
    }


}