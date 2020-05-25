package com.PortalDesa.data.ui.main.activity.merchant

import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.request.PenginapanImageRequest
import com.PortalDesa.data.model.request.ProdukRequest
import com.PortalDesa.data.model.response.DefaultResponse
import com.PortalDesa.data.model.response.ProductResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Flag
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.activity.DetailProductAcitivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_update_produk.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class UpdateProdukActivity : AppActivity(), View.OnClickListener  {

    private val GALLERY = 1
    private val CAMERA = 2

    var name: String = ""
    var skuProduk: String = ""
    var bitmap_val: Bitmap? = null

    var penginapanImageRequest: PenginapanImageRequest? = null
    private var data: ProductResponse? = null
    var sku: String = ""
    lateinit var preferences: Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        val skuProduct =intent.getStringExtra(Flag.PRODUCT_NAME)
        preferences = Preferences(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_produk)
        initView()
        getDetailProduk()
        update_produk_btn_send.setOnClickListener(this)
        update_produk_btn_image.setOnClickListener { showPictureDialog() }
    }

    private fun initView(){
        initToolbar(R.id.toolbar)
        tv_toolbar_title.text = "Update Produk"
    }

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
        pictureDialog.setItems(
            pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }
    fun getDetailProduk(){
        showProgressDialog()
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            skuProduk =intent.getStringExtra(Flag.SKU_PRODUCT_UPDATE)
            val call = client.getProductBySku(intent.getStringExtra(Flag.SKU_PRODUCT_UPDATE))
            call.enqueue(object : Callback<ProductResponse> {
                override fun onResponse(
                    call: retrofit2.Call<ProductResponse>,
                    response: Response<ProductResponse>
                ) {
                    val detail = response.body()
                    data = detail
                    displayData()
                }
                override fun onFailure(
                    call: retrofit2.Call<ProductResponse>,
                    t: Throwable
                ) {
                    dismissProgressDialog()
                }
            })
        }

    }

    fun displayData(){
        Picasso.get()
            .load("https://portal-desa.herokuapp.com"+data?.gambar)
            .into(update_produk_imageview)
        update_produk_imageview
        update_produk_produk_nama.setText(data?.nama)
        update_produk_produk_harga.setText(data?.harga.toString())
        update_produk_produk_deskripsi.setText(data?.deskripsi)
        dismissProgressDialog()
    }

    fun updateProduk(request: ProdukRequest) {
        showProgressDialog()
        val client = APIServiceGenerator().createService

        val call = client.updateProduk(skuProduk, request)
        call.enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(
                call: retrofit2.Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                val signupResponse = response.body()
                if(bitmap_val!=null) {
                    uploadImagePenginapan(penginapanImageRequest!!)
                }
                finish()

                reload()
            }

            override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {
                dismissProgressDialog()
            }
        })

    }
    fun uploadImagePenginapan(request: PenginapanImageRequest) {
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val call = client.updateprodukImage(request)
            call.enqueue(object : Callback<DefaultResponse> {
                override fun onResponse(
                    call: retrofit2.Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    Log.i("cek", "cek")
                    val listKecamatanResponse = response.body()
                }

                override fun onFailure(
                    call: retrofit2.Call<DefaultResponse>,
                    t: Throwable
                ) {
                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                    Log.i("cek", "cek2 Exceptions : $t")
                    dismissProgressDialog()
                }
            })
        }

    }

    fun reload(){
        val intent = Intent(this, DetailProductAcitivity::class.java)
        intent.putExtra(Flag.PRODUCT_NAME,skuProduk)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
/* if (resultCode == this.RESULT_CANCELED)
{
return
}*/
        if (requestCode == GALLERY) {
            if (data != null) {
                val contentURI = data!!.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    val path = saveImage(bitmap)
                    bitmap_val = bitmap
                    name = path
                    Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show()

                    update_produk_imageview!!.setImageBitmap(bitmap)

                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                }

            }

        } else if (requestCode == CAMERA) {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            bitmap_val = thumbnail
            update_produk_imageview!!.setImageBitmap(thumbnail)
            val path = saveImage(thumbnail)
            name = path
            Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show()
        }
    }
    fun getImageStringBase64(bitmap: Bitmap): String? {
        val bao = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bao)
        val ba = bao.toByteArray()
        val imageStringBase64 =
            Base64.encodeToString(ba, Base64.DEFAULT)
        Log.d("Image Base64", imageStringBase64)
        return imageStringBase64
    }

    fun saveImage(myBitmap: Bitmap): String {
        val request = PenginapanImageRequest()
        request.nama = data?.gambar
        request.gambar = "image, " + getImageStringBase64(myBitmap)

        penginapanImageRequest = request
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY
        )
// have the object build the directory structure, if needed.
        Log.d("fee", wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists()) {

            wallpaperDirectory.mkdirs()
        }

        try {
            Log.d("heel", wallpaperDirectory.toString())
            val f = File(
                wallpaperDirectory, ((Calendar.getInstance()
                    .getTimeInMillis()).toString() + ".jpg")
            )
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(
                this,
                arrayOf(f.getPath()),
                arrayOf("image/jpeg"), null
            )
            fo.close()
            name = f.getAbsolutePath()
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())

            return f.getAbsolutePath()
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }

    private fun getData(): ProdukRequest{
        val requestData = ProdukRequest()
        requestData.nama= update_produk_produk_nama.text.toString()
        requestData.deskripsi= update_produk_produk_deskripsi.text.toString()
        requestData.harga= Integer.parseInt(update_produk_produk_harga.text.toString())
        requestData.skuDesa =data?.skuDesa
        return requestData
    }

    companion object {
        private val IMAGE_DIRECTORY = "/bayunugrohoweb"
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            update_produk_btn_send.id -> updateProduk(getData())
        }
    }
}
