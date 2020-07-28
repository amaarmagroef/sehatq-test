package siapasaya.test.sehatq.model

import com.google.gson.annotations.SerializedName


/**
 * Created by siapaSAYA on 7/27/2020
 */

class ModelData {
    @SerializedName("data")
    var data: datas? = null

    class datas {
        @SerializedName("category")
        var category: MutableList<Category>? = null

        @SerializedName("productPromo")
        var productPromo: MutableList<ProductPromo>? = null
    }

    class Category {
        @SerializedName("imageUrl")
        var imageUrl: String? = null

        @SerializedName("id")
        var id: Int? = null

        @SerializedName("name")
        var name: String? = null
    }

    class ProductPromo {
        @SerializedName("id")
        var id: String? = null

        @SerializedName("imageUrl")
        var imageUrl: String? = null

        @SerializedName("title")
        var title: String? = null

        @SerializedName("description")
        var description: String? = null

        @SerializedName("price")
        var price: String? = null

        @SerializedName("loved")
        var loved: Int = 0

        fun setLoveds(loved : Int){
            this.loved = loved
        }
    }

    class ListPromo{
        var list : MutableList<ProductPromo>? = null

        class ProductPurcashed{
            @SerializedName("imageUrl")
            var imageUrl: String? = null

            @SerializedName("title")
            var title: String? = null

            @SerializedName("price")
            var price: String? = null


            constructor(imageUrl: String?, title: String?, price: String?) {
                this.imageUrl = imageUrl
                this.title = title
                this.price = price
            }
        }
    }
}
