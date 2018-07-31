package cubex.mahesh.telephony_july7am

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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
    }
}
