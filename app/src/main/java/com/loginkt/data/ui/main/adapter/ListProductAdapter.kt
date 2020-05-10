package com.loginkt.data.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loginkt.R
import com.loginkt.data.model.response.ProductResponse
import com.loginkt.data.support.Flag
import com.loginkt.data.ui.main.activity.DetailProductAcitivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_product.view.*
import kotlinx.android.synthetic.main.activity_detail_product.view.img_icon
import kotlinx.android.synthetic.main.activity_detail_product.view.tv_desc
import kotlinx.android.synthetic.main.item_popular_vilage.view.*

/**
 * Created by Sogumontar Hendra Simangunsong on 06/05/2020.
 */
class ListProductAdapter(val context : Context, val listProduk : List<ProductResponse>) : RecyclerView.Adapter<ListProductAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        val tvDesc = v.tv_desc
        val ln_product = v.ln_product
        val imgPopular = v.img_icon
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListProductAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_popular_vilage, parent, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Picasso.get()
            .load(R.drawable.balige)
            .into(holder.imgPopular)

        holder?.tvDesc.text = listProduk.get(position).nama
        holder?.ln_product.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, DetailProductAcitivity::class.java)
            intent.putExtra(Flag.PRODUCT_NAME, listProduk.get(position).nama)
            context.startActivity(intent)
        })
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return listProduk.size
    }
}