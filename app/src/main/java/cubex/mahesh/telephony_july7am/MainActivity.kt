package cubex.mahesh.telephony_july7am

import android.app.Activity
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.Bitmap
import android.provider.MediaStore
import java.io.ByteArrayOutputStream


class MainActivity : AppCompatActivity() {
    var uri:Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sms.setOnClickListener({
           var numbers =  et1.text.toString().split(",")
            for( number in numbers) {
                var sIntent = Intent(this@MainActivity,
                        SentActivity::class.java)
                var dIntent = Intent(this@MainActivity,
                        DeliverActivity::class.java)
                var spIntent = PendingIntent.getActivity(this@MainActivity,
                        0, sIntent, 0)
                var dpIntent = PendingIntent.getActivity(this@MainActivity,
                        0, dIntent, 0)
                var sManager = SmsManager.getDefault()
                sManager.sendTextMessage(number,
                        null, et2.text.toString(),
                        spIntent, dpIntent)
            }
        })
        call.setOnClickListener({
            var i = Intent( )
            i.action = Intent.ACTION_CALL
            i.data = Uri.parse("tel:${et1.text.toString()}")
            startActivity(i)

        })

        attach.setOnClickListener({

            var adialog = AlertDialog.Builder(this@MainActivity)
            adialog.setTitle("Message")
            adialog.setIcon(R.mipmap.ic_launcher_round)
            adialog.setMessage("Please select an option")
            adialog.setPositiveButton("Camera",
                    object : DialogInterface.OnClickListener {
                        override fun onClick(p0: DialogInterface?, p1: Int) {
           var i = Intent("android.media.action.IMAGE_CAPTURE")
            startActivityForResult(i,123)
                        }
                    })
            adialog.setNegativeButton("Files",
                    object : DialogInterface.OnClickListener {
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                                var i  = Intent( )
                                i.action = Intent.ACTION_GET_CONTENT
                                i.type = "*/*"
                                startActivityForResult(i,124)
                        }
                    })
            adialog.show()
        })

        smail.setOnClickListener({

            var i = Intent( )
            i.action = Intent.ACTION_SEND
            i.putExtra(Intent.EXTRA_EMAIL, arrayOf(et3.text.toString()))
            i.putExtra(Intent.EXTRA_SUBJECT, et4.text.toString())
            i.putExtra(Intent.EXTRA_TEXT,et5.text.toString())
            i.putExtra(Intent.EXTRA_STREAM,uri)
            i.type = "message/rfc822"
            startActivity(i)

        })

    } // onCreate

    override fun onActivityResult(requestCode: Int,
                                  resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==123 &&
                resultCode== Activity.RESULT_OK){
                var bmp = data!!.extras.get("data") as Bitmap
                uri = getImageUri(this@MainActivity,
                                    bmp)
        }else if(requestCode==124 &&
                resultCode== Activity.RESULT_OK){
                uri = data!!.data
        }
    }
    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null)
        return Uri.parse(path)
    }

} // MainActivity
