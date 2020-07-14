package com.PortalDesa.data.ui.main.activity.admin

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.request.KecamatanRequest
import com.PortalDesa.data.model.response.DefaultResponse
import com.PortalDesa.data.ui.main.activity.KecamatanActivity
import kotlinx.android.synthetic.main.activity_create_kecamatan.*
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class CreateKecamatan : AppActivity(), View.OnClickListener {
    private val GALLERY = 1
    private val CAMERA = 2
    var name: String = ""
    var bitmap_val: Bitmap? = null
    var kecamatanRequest: KecamatanRequest? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_kecamatan)
        btn_image.setOnClickListener { showPictureDialog() }
        btn_save.setOnClickListener(this)
        btn_save.setOnClickListener {

            save(getData())
        }
    }
    fun goToPenginapan() {
        val intent = Intent(this, KecamatanActivity::class.java)
        startActivity(intent)
    }
    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
    private fun getData(): KecamatanRequest {
        val nama = penginapan_nama.text.toString()
        val gambar = bitmap_val
        val requestUser = KecamatanRequest()
        requestUser.nama = nama
        requestUser.gambar = gambar.toString()
        return requestUser
    }

    fun save(request: KecamatanRequest) {
        showProgressDialog()
        val client = APIServiceGenerator().createService
        val call = client.addKecamatan(request)
        call.enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(
                call: retrofit2.Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                dismissProgressDialog()
                goToPenginapan()
                finish()
            }

            override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {
                dismissProgressDialog()
            }
        })

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


    fun getImageStringBase64(bitmap: Bitmap): String? {
        val bao = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bao)
        val ba = bao.toByteArray()
        val imageStringBase64 =
            Base64.encodeToString(ba, Base64.DEFAULT)
        Log.d("Image Base64", imageStringBase64)
        return imageStringBase64
    }
}
