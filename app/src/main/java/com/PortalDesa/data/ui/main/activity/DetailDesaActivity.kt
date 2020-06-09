package com.PortalDesa.data.ui.main.activity

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
import com.PortalDesa.data.model.request.GambarDesaRequest
import com.PortalDesa.data.model.request.UpdateDesaRequest
import com.PortalDesa.data.model.request.UsersUpdateRequest
import com.PortalDesa.data.model.response.DefaultResponse
import com.PortalDesa.data.model.response.DesaResponse
import com.PortalDesa.data.model.response.PenginapanImageResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Flag
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.support.TopSnackBar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_desa.*
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class DetailDesaActivity : AppActivity(), View.OnClickListener {

    private val GALLERY = 1
    private val CAMERA = 2

    var name: String = ""
    var skuDesa: String = ""
    var bitmap_val: Bitmap? = null
    var gambarDesaRequest: GambarDesaRequest? = null

    private var desaResponse: DesaResponse? = null
    lateinit var preferences: Preferences
    var role: String? = ""
    var metode: String = ""
    var skuLogin: String? = ""
    var namaDesa: String? = ""
    lateinit var topSnackBar: TopSnackBar
    var skuFix: String? = ""
    var sku: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_desa)
        preferences = Preferences(this)
        topSnackBar = TopSnackBar()
        sku = preferences.getSku()
        role = preferences.getRoles()
        skuLogin = preferences.getSku()
        btn_image.setOnClickListener { showPictureDialog() }
        btn_update.setOnClickListener(this)
        btn_produk.setOnClickListener(this)
        btn_penginapan.setOnClickListener(this)
        initData()
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


    fun initData() {
        showProgressDialog()
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val nam=intent.getStringExtra(Flag.NAMA_DESA)
            namaDesa = intent.getStringExtra(Flag.NAMA_DESA)
            val call = client.getDesaByNama(nam!!)
            call.enqueue(object : retrofit2.Callback<DesaResponse> {
                override fun onResponse(
                    call: retrofit2.Call<DesaResponse>,
                    response: Response<DesaResponse>
                ) {
                    val listProduk = response.body()
                    desaResponse = listProduk
                    skuDesa = desaResponse!!.skuAdmin!!
                    displayProduct()
                    dismissProgressDialog()
                    initView()
                }

                override fun onFailure(call: retrofit2.Call<DesaResponse>, t: Throwable) {
                    dismissProgressDialog()
                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })

        }
    }

    fun displayProduct() {
        Picasso.get()
            .load("https://portal-desa.herokuapp.com/desa/get/" + desaResponse?.skuAdmin+".png")
            .into(img_icon)
        Picasso.get()
            .load("https://portal-desa.herokuapp.com/desa/get/" + desaResponse?.skuAdmin+".png")
            .into(imageview)
        et_name.setText(desaResponse?.nama)
        et_namaKD.setText(desaResponse?.namaKepalaDesa)
        et_jumlah.setText(desaResponse?.jumlahPenduduk.toString())
        et_kecamatan.setText(desaResponse?.kec)
        if (!role.equals("ROLE_MERCHANT")) {
            et_name.isEnabled=false
            et_namaKD.isEnabled=false
            et_jumlah.isEnabled=false
            et_kecamatan.isEnabled=false
        }
    }

    fun initView() {
        if (role.equals("ROLE_MERCHANT") && skuLogin.equals(skuDesa)) {
            img_icon.visibility = View.GONE
            btn_image.visibility = View.VISIBLE
            imageview.visibility = View.VISIBLE
            btn_update.visibility = View.VISIBLE
        } else {
            btn_penginapan.setOnClickListener{goToPenginapan()}
            btn_produk.setOnClickListener{goToProduk()}
            img_icon.visibility = View.VISIBLE
            btn_image.visibility = View.GONE
            imageview.visibility = View.GONE
            btn_update.visibility = View.GONE
        }
    }
    fun goToPenginapan(){
        val intent = Intent(this, ListPenginapanPerMerchantActivity::class.java)
        intent.putExtra(Flag.SKU_MERCHANT,desaResponse!!.skuAdmin )
        startActivity(intent)
    }
    fun goToProduk(){
        val intent = Intent(this, ListProdukPerMerchantActivity::class.java)
        intent.putExtra(Flag.SKU_Penginapan_MERCHANT,desaResponse!!.skuAdmin )
        startActivity(intent)
    }

    fun updateDetailDesa(request: UpdateDesaRequest) {
        if(bitmap_val!=null && role.equals("ROLE_MERCHANT")) {
            val s=gambarDesaRequest!!.skuDesa
            val ss=gambarDesaRequest!!.base64File
            uploadImagePenginapan(gambarDesaRequest!!)
        }
        showProgressDialog()
        val client = APIServiceGenerator().createService
        val skus=preferences.getSku()
        val call = client.updateDesaBySku(skus,request)
        call.enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(
                call: retrofit2.Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                val signupResponse = response.body()
                val sksu=sku

                finish()
            }

            override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {
                dismissProgressDialog()
            }
        })

    }


    private fun getRequest(): UpdateDesaRequest{
        val requestDesa = UpdateDesaRequest()
        requestDesa.nama =et_name.text.toString()
        requestDesa.namaKepalaDesa =et_namaKD.text.toString()
        requestDesa.jumlahPenduduk =Integer.parseInt(et_jumlah.text.toString())
        requestDesa.kecamatan =et_kecamatan.text.toString()
        return requestDesa
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
        val skus=sku
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

    override fun onClick(v: View?) {
        when (v!!.id) {
            btn_update.id -> updateDetailDesa(getRequest())
            btn_produk.id -> goToProduk()
            btn_penginapan.id -> goToPenginapan()
        }
    }
}
