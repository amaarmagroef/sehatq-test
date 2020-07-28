package siapasaya.test.sehatq.view.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import siapasaya.test.sehatq.R
import siapasaya.test.sehatq.databinding.ItemBarangBinding
import siapasaya.test.sehatq.databinding.ItemPencarianBinding
import siapasaya.test.sehatq.model.ModelData
import siapasaya.test.sehatq.utils.BindableViewHolder
import siapasaya.test.sehatq.view.main.home.AdapterItem


/**
 * Created by siapaSAYA on 7/28/2020
 */

class AdapterProduct() : RecyclerView.Adapter<AdapterProduct.ItemViewHolder>() {

    private var data = mutableListOf<ModelData.ProductPromo>()
    private var mOnChangeItem : OnChangeItem? = null

    fun update(data: MutableList<ModelData.ProductPromo>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun clear() {
        this.data.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_pencarian,
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
        holder.itemView.setOnClickListener {
            if(mOnChangeItem!=null){
                mOnChangeItem?.onClick(model.id!!)
            }
        }
    }

    class ItemViewHolder(view: View) :
        BindableViewHolder<ItemPencarianBinding, ModelData.ProductPromo>(view) {
        override fun bind(viewModel: ModelData.ProductPromo) {
            binding?.model =viewModel
            ViewCompat.setTransitionName(binding!!.image, viewModel.imageUrl)
        }
    }

    fun setOnChangeItem(listener : OnChangeItem) {
        mOnChangeItem = listener
    }


    interface OnChangeItem{
        fun onClick(id: String)
    }


}