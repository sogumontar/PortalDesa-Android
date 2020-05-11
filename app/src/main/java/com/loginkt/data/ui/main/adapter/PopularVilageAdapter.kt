package com.loginkt.data.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loginkt.R
import com.loginkt.data.model.response.KecamatanResponse
import com.loginkt.data.support.Flag
import com.loginkt.data.ui.main.activity.DetailKecamatanActivtiy
import com.loginkt.data.ui.main.activity.DetailProductAcitivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_popular_vilage.view.*

class PopularVilageAdapter(val context: Context, val listKec : List<KecamatanResponse>) : RecyclerView.Adapter<PopularVilageAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        val tvDesc = v.tv_desc
        val imgPopular = v.img_icon
        val ln_product = v.ln_product
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularVilageAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_popular_vilage, parent, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Picasso.get()
            .load("https://portal-desa.herokuapp.com/kecamatan/get/"+listKec.get(position).nama+".jpg")
            .into(holder.imgPopular)

        holder.tvDesc.text = listKec.get(position).nama
        holder.ln_product.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, DetailKecamatanActivtiy::class.java)
            intent.putExtra(Flag.PRODUCT_NAME, listKec.get(position).nama)
            context.startActivity(intent)
        })
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return listKec.size
    }
}