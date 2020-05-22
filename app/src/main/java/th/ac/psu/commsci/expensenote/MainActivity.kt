package th.ac.psu.commsci.expensenote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var btn_Save: Button? = null
    private var btn_Reset: Button? = null
    private var btn_Report: Button? = null
    private var btn_Show: Button? = null
    private var Date: EditText? = null
    private var Time: EditText? = null
    private var Transcript: EditText? = null
    private var Location: EditText? = null
    private var Money: EditText? = null
    private var databaseHelper: DatabaseHelper? = null
    private var viewDetail: TextView? = null
    private var arrayList: ArrayList<String>? = null
    private var sumExpense: ArrayList<String>? = null
    private var sumRevenue: ArrayList<String>? = null
    private var sumExpend: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        databaseHelper = DatabaseHelper(this)
        viewDetail = findViewById(R.id.viewDetail) as TextView

        btn_Save = findViewById(R.id.btnSave) as Button
        btn_Reset = findViewById(R.id.btnReset) as Button
        btn_Report = findViewById(R.id.btnReport) as Button
        btn_Show = findViewById(R.id.btnShow) as Button
        Date = findViewById(R.id.date) as EditText
        Time = findViewById(R.id.time) as EditText
        Transcript = findViewById(R.id.transcript) as EditText
        Location = findViewById(R.id.location) as EditText
        Money = findViewById(R.id.money) as EditText

        btn_Save!!.setOnClickListener {
            databaseHelper!!.addMoneyDetail(Date!!.text.toString(), Time!!.text.toString(), Transcript!!.text.toString(), Location!!.text.toString(), Money!!.text.toString())
            Date!!.setText("")
            Time!!.setText("")
            Transcript!!.setText("")
            Location!!.setText("")
            Money!!.setText("")

            Toast.makeText(this@MainActivity, "บันทึกข้อมูลเรียบร้อยแล้ว", Toast.LENGTH_SHORT).show()
        }

        btn_Show!!.setOnClickListener {
            arrayList = databaseHelper!!.allTranscriptList
            viewDetail!!.text = ""
            var ord = 1
            for (i in arrayList!!.indices) {
                viewDetail!!.text = viewDetail!!.text.toString() + (ord++) + ". " + arrayList!![i] + "\n"
            }


        }

        btn_Report!!.setOnClickListener {
            sumExpense = databaseHelper!!.sumExpenses
            sumRevenue = databaseHelper!!.sumRevenue
            sumExpend = databaseHelper!!.sumExpend

            viewDetail!!.text = sumRevenue!![0] + "\n" + sumExpend!![0] + "\n" + sumExpense!![0]
            //Toast.makeText(this@MainActivity, "ขออภัยครับ รายงานข้อมูลค่าใช้จ่ายอยู่ระหว่างการพัฒนา", Toast.LENGTH_SHORT).show()

        }

        btn_Reset!!.setOnClickListener {
            databaseHelper!!.resetDatabase()
            viewDetail!!.text = ""

            Toast.makeText(this@MainActivity, "ล้างข้อมูลเรียบร้อยแล้ว", Toast.LENGTH_SHORT).show()
        }
    }
}
