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
import com.PortalDesa.data.model.response.PenginapanImageResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.FormValidation
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.support.TopSnackBar
import kotlinx.android.synthetic.main.activity_create_produk_form.*
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class CreateProdukForm : AppActivity(), View.OnClickListener {
    var sku: String = ""
    var nickName: String =""
    private val GALLERY = 1
    private val CAMERA = 2
    var penginapanImageRequest : PenginapanImageRequest?=null
    var name : String = ""
    var bitmap_val : Bitmap? = null
    lateinit var preferences: Preferences
    lateinit var topSnackBar: TopSnackBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = Preferences(this)
        sku = preferences.getSku()
        nickName = preferences.getNamap()
        setContentView(R.layout.activity_create_produk_form)
        topSnackBar = TopSnackBar()
//        btn_produk_save.setOnClickListener(this)
        btn_image.setOnClickListener { showPictureDialog() }
        btn_send.setOnClickListener{
            if(checkField()) {
                uploadImagePenginapan(penginapanImageRequest!!)
            }
        }
    }

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
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
    private fun showMessage(message: String) {
        topSnackBar.showError(this, findViewById(R.id.snackbar_container), message)
    }
    private fun checkField(): Boolean {
        var check = true
        if (!FormValidation().required(produk_nama.getText().toString())) {
            showMessage("Nama tidak boleh kosong")
            check = false
        }else if (!FormValidation().required(produk_harga.getText().toString())) {
            showMessage("Harga Produk tidak boleh kosong")
            check = false
        }else if (!FormValidation().required(produk_deskripsi.getText().toString())) {
            showMessage("Deskripsi tidak boleh kosong")
            check = false
        }else if(bitmap_val==null){
            showMessage("Gambar tidak boleh kosong")
            check = false
        }

        return check
    }


    fun uploadImagePenginapan(request : PenginapanImageRequest){

            if (Connectivity().isNetworkAvailable(this)) {
                val client = APIServiceGenerator().createService
                val call = client.addProdukimage(request)
                call.enqueue(object : Callback<PenginapanImageResponse> {
                    override fun onResponse(
                        call: retrofit2.Call<PenginapanImageResponse>,
                        response: Response<PenginapanImageResponse>
                    ) {
                        Log.i("cek", "cek")
                        save(getData())
                        val listKecamatanResponse = response.body()
                    }

                    override fun onFailure(
                        call: retrofit2.Call<PenginapanImageResponse>,
                        t: Throwable
                    ) {
                        Log.i(
                            this.javaClass.simpleName,
                            " Requested API : " + call.request().body()!!
                        )
                        Log.e(this.javaClass.simpleName, " Exceptions : $t")
                        Log.i("cek", "cek2 Exceptions : $t")
                        dismissProgressDialog()
                    }
                })
            }


    }

    fun save(request: ProdukRequest) {
        showProgressDialog()
        val client = APIServiceGenerator().createService
        val call = client.addProduk(request)
        call.enqueue(object : Callback<ProdukRequest> {
            override fun onResponse(
                call: retrofit2.Call<ProdukRequest>,
                response: Response<ProdukRequest>
            ) {
                dismissProgressDialog()
                goToProduk()
                finish()
            }

            override fun onFailure(call: retrofit2.Call<ProdukRequest>, t: Throwable) {
                dismissProgressDialog()
                goToProduk()
            }
        })

    }

    private fun getData(): ProdukRequest{
        val harga=produk_harga.text.toString()
        val requestUser = ProdukRequest()
        requestUser.nama = produk_nama.text.toString()
        requestUser.harga= Integer.parseInt(harga);
        requestUser.deskripsi= produk_deskripsi.text.toString()
        requestUser.skuDesa= sku;
        return requestUser
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
//            btn_produk_save.id -> save(getData())
        }
    }

    override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
/* if (resultCode == this.RESULT_CANCELED)
{
return
}*/
        if (requestCode == GALLERY)
        {
            if (data != null)
            {
                val contentURI = data!!.data
                try
                {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    val path = saveImage(bitmap)
                    bitmap_val = bitmap
                    name = path
                    Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show()

                    imageview!!.setImageBitmap(bitmap)

                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                }

            }

        }
        else if (requestCode == CAMERA)
        {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            bitmap_val = thumbnail
            imageview!!.setImageBitmap(thumbnail)
            val path = saveImage(thumbnail)
            name = path
            Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show()
        }
    }
    fun saveImage(myBitmap: Bitmap):String {
        val request = PenginapanImageRequest()
        request.nama = preferences!!.getSku()
        request.gambar = "image, "+getImageStringBase64(myBitmap)

        penginapanImageRequest = request
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            (Environment.getExternalStorageDirectory()).toString() + "/bayunugrohoweb")
// have the object build the directory structure, if needed.
        Log.d("fee",wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists())
        {

            wallpaperDirectory.mkdirs()
        }

        try
        {
            Log.d("heel",wallpaperDirectory.toString())
            val f = File(wallpaperDirectory, ((Calendar.getInstance()
                .getTimeInMillis()).toString() + ".jpg"))
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this,
                arrayOf(f.getPath()),
                arrayOf("image/jpeg"), null)
            fo.close()
            name = f.getAbsolutePath()
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())

            return f.getAbsolutePath()
        }
        catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }


    fun goToProduk(){
        val intent = Intent(this, this::class.java)
        startActivity(intent)
    }
}
