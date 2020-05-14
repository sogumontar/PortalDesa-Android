package com.PortalDesa.data.ui.main.activity.merchant

import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.model.request.PenginapanImageRequest
import com.PortalDesa.data.model.response.PenginapanImageResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Preferences
import kotlinx.android.synthetic.main.activity_penginapan_form.*
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class PenginapanForm : AppCompatActivity() {

    private val GALLERY = 1
    private val CAMERA = 2

    var preferences : Preferences? = null

    var name : String = ""
    var bitmap_val : Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_penginapan_form)
        preferences = Preferences(this)
        btn_image.setOnClickListener { showPictureDialog() }
        btn_send.setOnClickListener{encoder()}
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

    fun imageToString(BitmapData: Bitmap): String? {
        val bos = ByteArrayOutputStream()
        BitmapData.compress(Bitmap.CompressFormat.PNG, 100, bos)
        val byte_arr = bos.toByteArray()
        //appendLog(file);
        return Base64.encodeToString(byte_arr, Base64.DEFAULT)
    }

    fun encoder(){
        val baos = ByteArrayOutputStream()
        bitmap_val!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageBytes = baos.toByteArray()
        val imageString = Base64.encodeToString(imageBytes, Base64.NO_WRAP or Base64.URL_SAFE)
        uploadImagePenginapan(imageString)
    }

    fun uploadImagePenginapan(imageString: String?){
        if (Connectivity().isNetworkAvailable(this)) {
            val request = PenginapanImageRequest()
            request.nama = preferences!!.getSku()
            request.gambar = imageString
            val client = APIServiceGenerator().createService
            val call = client.addPenginapanimage(request)
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
                    Log.i("cek", "cek2")
                }
            })
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
                    uploadImagePenginapan(imageToString(bitmap))
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
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)
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


    companion object {
        private val IMAGE_DIRECTORY = "/bayunugrohoweb"
    }

}
