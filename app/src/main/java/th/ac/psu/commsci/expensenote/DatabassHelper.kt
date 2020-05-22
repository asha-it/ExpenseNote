package th.ac.psu.commsci.expensenote

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.util.ArrayList

class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    val allTranscriptList: ArrayList<String> //Get All Detail of Expenses
        get() {
            val moneyArrayList = ArrayList<String>()
            var date: String
            var time: String
            var transcript: String
            var location: String
            var money: Int
            val selectQuery = "SELECT * FROM $TABLE_EXPENSE"
            val db = this.readableDatabase
            val c = db.rawQuery(selectQuery, null)
            if (c.moveToFirst()) {
                do {
                    date = c.getString(c.getColumnIndex(E_Date))
                    time = c.getString(c.getColumnIndex(E_Time))
                    transcript = c.getString(c.getColumnIndex(E_Transcript))
                    location = c.getString(c.getColumnIndex(E_Location))
                    money = c.getInt(c.getColumnIndex(E_Money))
                    moneyArrayList.add(date + " " + time + " " + transcript + " " + location + " " + money + " บาท")
                } while (c.moveToNext())
                Log.d("array", moneyArrayList.toString())
            }
            return moneyArrayList
        }

    val sumExpenses: ArrayList<String> //Get Balance of Expenses
        get() {
            val balanceExpense = ArrayList<String>()
            var sumExpense = 0
            val selectQuery = "SELECT  SUM(money) as sum_expense FROM $TABLE_EXPENSE"
            val db = this.readableDatabase
            val sum = db.rawQuery(selectQuery, null)


            if (sum.moveToFirst()) {
                sumExpense = sum.getInt(sum.getColumnIndex(E_sumExpense))
            }
            balanceExpense.add("\nยอดเงินคงเหลือ : " + sumExpense + " บาท \n")

            return balanceExpense
       }

    val sumRevenue: ArrayList<String> //Get Sum of Revenue
        get() {
            val balanceExpense = ArrayList<String>()
            var sumRevenue = 0
            val selectQuery = "SELECT  SUM(money) as sum_revenue FROM $TABLE_EXPENSE WHERE money > 0"
            val db = this.readableDatabase
            val sum = db.rawQuery(selectQuery, null)
            if (sum.moveToFirst()) {
                sumRevenue = sum.getInt(sum.getColumnIndex(E_sumRevenue))
            }
            balanceExpense.add("ยอดรวมรายรับ  : " + sumRevenue + " บาท")

            return balanceExpense
        }

    val sumExpend: ArrayList<String> //Get Sum of Expend
        get() {
            val balanceExpense = ArrayList<String>()
            var sumExpend = 0
            val selectQuery = "SELECT  SUM(money) as sum_expend FROM $TABLE_EXPENSE WHERE money < 0"
            val db = this.readableDatabase
            val sum = db.rawQuery(selectQuery, null)
            if (sum.moveToFirst()) {
                sumExpend = sum.getInt(sum.getColumnIndex(E_sumExpend))
            }
            balanceExpense.add("ยอดรวมรายจ่าย : " + sumExpend + " บาท")

            return balanceExpense
        }

    init {

        Log.d("table", CREATE_TABLE_EXPENSE)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_EXPENSE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS '$TABLE_EXPENSE'")
        onCreate(db)
    }

    fun resetDatabase() {  //Reset Data in Database
        val db = this.writableDatabase
        db.execSQL("DROP TABLE IF EXISTS '$TABLE_EXPENSE'")
        db.execSQL(CREATE_TABLE_EXPENSE)
    }

    fun addMoneyDetail(date: String, time: String, transcript: String, location: String, money: String ): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(E_Date, date)
        values.put(E_Time, time)
        values.put(E_Transcript, transcript)
        values.put(E_Location, location)
        values.put(E_Money, money)

        return db.insert(TABLE_EXPENSE, null, values)
    }


    companion object {
        var DATABASE_NAME = "expense_database"
        private val DATABASE_VERSION = 1
        private val TABLE_EXPENSE = "expense"
        private val E_Date = "date"
        private val E_Time = "time"
        private val E_Transcript = "transcript"
        private val E_Location = "location"
        private val E_Money = "money"
        private val E_sumExpense = "sum_expense"
        private val E_sumRevenue = "sum_revenue"
        private val E_sumExpend = "sum_expend"

        private val CREATE_TABLE_EXPENSE = ("CREATE TABLE " + TABLE_EXPENSE +
                "(" + E_Date + " TEXT, "+ E_Time +
                " TEXT, " + E_Transcript + " TEXT, " + E_Location + " TEXT," +
                E_Money + " INTEGER);")
    }
}