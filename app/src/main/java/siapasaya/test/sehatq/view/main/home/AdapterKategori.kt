package siapasaya.test.sehatq.view.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import siapasaya.test.sehatq.R
import siapasaya.test.sehatq.databinding.ItemCategoryBinding
import siapasaya.test.sehatq.model.ModelData
import siapasaya.test.sehatq.utils.BindableViewHolder


/**
 * Created by siapaSAYA on 7/28/2020
 */

class AdapterKategori() : RecyclerView.Adapter<AdapterKategori.ItemViewHolder>() {

    private var data = mutableListOf<ModelData.Category>()

    fun update(data: MutableList<ModelData.Category>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun clear() {
        this.data.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_category,
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

    }

    class ItemViewHolder(view: View) :
        BindableViewHolder<ItemCategoryBinding, ModelData.Category>(view) {
        override fun bind(viewModel: ModelData.Category) {
            binding?.model =viewModel
            ViewCompat.setTransitionName(binding!!.image, viewModel.imageUrl)
        }
    }


}