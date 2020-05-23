package com.PortalDesa.data.ui.main.activity.merchant

import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import androidx.appcompat.app.AppCompatActivity
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
import com.PortalDesa.data.model.request.PenginapanRequest
import com.PortalDesa.data.model.response.DefaultResponse
import com.PortalDesa.data.model.response.PenginapanResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Flag
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.activity.PenginapanActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_update_penginapan.*
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class UpdatePenginapan : AppActivity(), View.OnClickListener {


    private val GALLERY = 1
    private val CAMERA = 2

    var name: String = ""
    var bitmap_val: Bitmap? = null
    var penginapanImageRequest: PenginapanImageRequest? = null
    private var data: PenginapanResponse? = null
    var sku: String = ""
    lateinit var preferences: Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        val skuPenginapan = intent.getStringExtra(Flag.SKU_PENGINAPAN)
        preferences = Preferences(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_penginapan)
        sku = preferences.getSku()
        getDetailPenginapan()
        btn_image.setOnClickListener { showPictureDialog() }
        update_btn_save.setOnClickListener(this)
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
    fun displayData(){
        Picasso.get()
            .load("https://portal-desa.herokuapp.com"+data?.gambar)
            .into(imageview)
        imageview
        update_penginapan_nama.setText(data?.nama)
        update_penginapan_harga.setText(data?.harga.toString())
        update_penginapan_deskripsi.setText(data?.deskripsi)
        update_penginapan_kamar.setText(data?.jumlahKamar.toString())
        update_penginapan_lokasi.setText(data?.lokasi)
    }


    fun updateGambar(){

    }

    fun getDetailPenginapan(){
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val call = client.lihatPenginapanBySku(intent.getStringExtra(Flag.SKU_PENGINAPAN))
            call.enqueue(object : Callback<PenginapanResponse> {
                override fun onResponse(
                    call: retrofit2.Call<PenginapanResponse>,
                    response: Response<PenginapanResponse>
                ) {
                    val detail = response.body()
                    data = detail
                    displayData()
                }
                override fun onFailure(
                    call: retrofit2.Call<PenginapanResponse>,
                    t: Throwable
                ) {
                }
            })
        }

    }
    fun updatePenginapan(request: PenginapanRequest) {
        showProgressDialog()
        val client = APIServiceGenerator().createService
        val coba =intent.getStringExtra(Flag.SKU_PENGINAPAN)
        val call = client.updatePenginapan(intent.getStringExtra(Flag.SKU_PENGINAPAN), request)
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

    fun reload(){
        val intent = Intent(this, PenginapanActivity::class.java)
        startActivity(intent)
    }

    private fun getData(): PenginapanRequest{
        val requestData = PenginapanRequest()
        requestData.nama= update_penginapan_nama.text.toString()
        requestData.jumlahKamar= Integer.parseInt(update_penginapan_kamar.text.toString())
        requestData.deskripsi= update_penginapan_deskripsi.text.toString()
        requestData.harga= Integer.parseInt(update_penginapan_harga.text.toString())
        requestData.lokasi= update_penginapan_lokasi.text.toString()
        requestData.desa="pintubatu"
        requestData.kecamatan="Silaen"
        requestData.skumerchant=preferences.getSku()
        return requestData
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            update_btn_save.id -> updatePenginapan(getData())
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

    fun uploadImagePenginapan(request: PenginapanImageRequest) {
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val call = client.updatePenginapanimage(request)
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

                    imageview!!.setImageBitmap(bitmap)

                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                }

            }

        } else if (requestCode == CAMERA) {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            bitmap_val = thumbnail
            imageview!!.setImageBitmap(thumbnail)
            val path = saveImage(thumbnail)
            name = path
            Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show()
        }
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


    companion object {
        private val IMAGE_DIRECTORY = "/bayunugrohoweb"
    }

}
