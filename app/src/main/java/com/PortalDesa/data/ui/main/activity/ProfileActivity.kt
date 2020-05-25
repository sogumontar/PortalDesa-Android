package com.PortalDesa.data.ui.main.activity

import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.request.GambarDesaRequest
import com.PortalDesa.data.model.request.UsersUpdateRequest
import com.PortalDesa.data.model.response.PenginapanImageResponse
import com.PortalDesa.data.model.response.ProfileResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Preferences
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import android.util.Base64

class ProfileActivity : AppActivity(), View.OnClickListener {
    private val GALLERY = 1
    private val CAMERA = 2

    var name: String = ""
    var bitmap_val: Bitmap? = null
    var gambarDesaRequest: GambarDesaRequest? = null

    private var data: ProfileResponse? = null
    var sku: String = ""
    var role: String = ""
    lateinit var preferences: Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = Preferences(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        btn_update.setOnClickListener(this)
        sku = preferences.getSku()
        role = preferences.getRoles()
        btn_image.setOnClickListener { showPictureDialog() }
        initView()
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

    private fun initView() {
        initToolbar(R.id.toolbar)
        tv_toolbar_title.text = getString(R.string.sign_up_tab_title_profile)

        showProgressDialog()
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val call = client.getDetailProfile(sku)
            call.enqueue(object : Callback<ProfileResponse> {
                override fun onResponse(
                    call: retrofit2.Call<ProfileResponse>,
                    response: Response<ProfileResponse>
                ) {
                    val detail = response.body()
                    data = detail
                    displayData()
                    dismissProgressDialog()
                }
                override fun onFailure(
                    call: retrofit2.Call<ProfileResponse>,
                    t: Throwable
                ) {
                    dismissProgressDialog()
                }
            })
        }

    }

    fun updateDetailUsers(request: UsersUpdateRequest) {
        showProgressDialog()
        val client = APIServiceGenerator().createService
        val call = client.updateProfileBySku(sku, request)
        call.enqueue(object : Callback<UsersUpdateRequest> {
            override fun onResponse(
                call: retrofit2.Call<UsersUpdateRequest>,
                response: Response<UsersUpdateRequest>
            ) {
                val signupResponse = response.body()
                val sksu=sku
                if(bitmap_val!=null && role.equals("ROLE_MERCHANT")) {
                    uploadImagePenginapan(gambarDesaRequest!!)
                }
                finish()
            }

            override fun onFailure(call: retrofit2.Call<UsersUpdateRequest>, t: Throwable) {
                dismissProgressDialog()
            }
        })

    }

    fun displayData() {
        if(role.equals("ROLE_MERCHANT")){
            btn_image.visibility= View.VISIBLE
            imageview.visibility=View.VISIBLE
            Picasso.get()
                .load("https://portal-desa.herokuapp.com/desa/get/"+data?.sku+".png")
                .into(imageview)
        }
        et_email.setText(data?.email)
        et_name.setText(data?.name)
        et_alamat.setText(data?.alamat)
    }
    private fun getUser(): UsersUpdateRequest{
        val requestUser = UsersUpdateRequest()
        requestUser.name = et_name.text.toString()
        requestUser.alamat = et_alamat.text.toString()
        requestUser.email = et_email.text.toString()
        return requestUser
    }
    override fun onClick(v: View?) {
        when (v!!.id) {
            btn_update.id -> updateDetailUsers(getUser())
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

    fun uploadImagePenginapan(request: GambarDesaRequest) {
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val call = client.addDesaimage(request)
            call.enqueue(object : Callback<PenginapanImageResponse> {
                override fun onResponse(
                    call: retrofit2.Call<PenginapanImageResponse>,
                    response: Response<PenginapanImageResponse>
                ) {
                    Log.i("cek", "cek")
                    val listKecamatanResponse = response.body()
                }

                override fun onFailure(
                    call: retrofit2.Call<PenginapanImageResponse>,
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
        val request = GambarDesaRequest()
        request.skuDesa = sku
        request.base64File = "image, " + getImageStringBase64(myBitmap)

        gambarDesaRequest = request
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
