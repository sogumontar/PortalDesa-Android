package com.PortalDesa.data.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import com.PortalDesa.data.ui.main.activity.DetailProductAcitivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_product.view.*

class ProductAdapter(val context : Context) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        val lnProduct = v.ln_product
        val tvNama = v.tv_nama
        val tvHarga = v.tv_harga
        val imgPopular = v.img_icon
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Picasso.get()
            .load(R.drawable.img_singkong)
            .into(holder.imgPopular)

        holder?.tvNama.text = "Singkong"
        holder?.tvHarga.text = "Rp 10.000"
        holder?.lnProduct.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, DetailProductAcitivity::class.java)
            context.startActivity(intent)
        })

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return 5
    }
}