package com.companyshivani.hars

import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*

class MainActivity : AppCompatActivity() {

    var searchBtn: Button? = null
    var editName: EditText? = null
    var db: SQLiteDatabase? = null
    var listName: ListView? = null
    var listLocation: ListView? = null
    var databaseHelper: DatabaseHelper? = null
    var databaseOpen: Boolean = false
    var cursorHistory: Cursor? = null
    var cursor: Cursor? = null
    var listN: ArrayList<String>? = null
    var spinV:String?=  null
    var spinNameT:String? = null
    var nameText:TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spin = findViewById(R.id.spinner) as Spinner

        searchBtn = findViewById<Button>(R.id.searchButton)
        editName = findViewById(R.id.editTextName)
        nameText = findViewById(R.id.nameText)

        try {


            databaseHelper = DatabaseHelper(this)
            if (databaseHelper!!.checkDataBase()) {
                openDatabase()
            } else {
                var task: LoadDataBaseAsync = LoadDataBaseAsync(this)
                task.execute()
            }

        } catch (e:Exception){
            Log.d("errorf",e.toString())
           Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show()

        }



        searchBtn!!.setOnClickListener {

           //spinV = spin.selectedItem.toString()
            //spinNameT = editName!!.text.toString()

            try {
                cursor = databaseHelper!!.getName()
                if (cursor!!.moveToFirst()) {
                    Log.d("cu","aaa")
                    //Get the drink details from the cursor
                    var nameT = cursor!!.getString(1);
                    var locationT = cursor!!.getString(2);

                    Log.d("cuaa","aaa1")
                    nameText!!.text = nameT

                }
                cursor!!.close()
                db!!.close()
             } catch (e:Exception){
                Log.d("errorffff",e.toString())
            //Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show()
        }
            }



    }

    fun openDatabase() {
        try {

            databaseHelper!!.openDataBase()
            databaseOpen = true
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }
}
