package com.companyshivani.hars

import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.FileOutputStream
import java.io.IOException

class DatabaseHelper(val myContext: Context) : SQLiteOpenHelper(myContext, DB_NAME, null, DB_VERSION) {
    private var DB_PATH: String? = null
    private var myDataBase: SQLiteDatabase? = null
    var cur: Cursor? = null

    init {
        this.DB_PATH = "/data/data/" +myContext.packageName + "/" + "databases/"
        Log.e("Path 1", DB_PATH)

    }

    @Throws(IOException::class)
    fun createDataBase() {
        val dbExist = checkDataBase()
        if (!dbExist) {

            this.readableDatabase
            try {
                myContext.deleteDatabase(DB_NAME)
                copyDataBase()

            } catch (e: IOException) {
                throw Error("Error copying database")
            }

        }

    }

    fun checkDataBase(): Boolean {
        var checkDB: SQLiteDatabase? = null
        try {
            val myPath = DB_PATH!! + DB_NAME
            Log.d("naxa","v")
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY)

            Log.d("naxb","aa")
        } catch (e: SQLiteException) {
            e.printStackTrace()
            Log.d("naxc",e.toString())
        }

       checkDB?.close()

        return if (checkDB != null) true else false
    }


    @Throws(IOException::class)
    private fun copyDataBase() {

        val myInput = myContext.assets.open(DB_NAME)
        val outFileName = DB_PATH!! + DB_NAME
        val myOutput = FileOutputStream(outFileName)
        val buffer = ByteArray(1024)
        var length: Int
        while (( myInput.read(buffer)) > 0) {
            length = myInput.read(buffer)
            myOutput.write(buffer, 0, length)
        }
        myOutput.flush()
        myOutput.close()
        myInput.close()
        Log.i("copyDataBase", "Database copied")


    }

    @Throws(SQLException::class)
    fun openDataBase() {
        val myPath = DB_PATH!! + DB_NAME
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE)

    }





    @Synchronized
    override fun close() {
        if (myDataBase != null)
            myDataBase!!.close()
        super.close()
    }

    override fun onCreate(db: SQLiteDatabase?) {


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        try {
            this.readableDatabase
            myContext.deleteDatabase(DB_NAME)
            copyDataBase()
        } catch (e: IOException) {
            e.printStackTrace()

        }
    }

    fun getName():Cursor{
//        cursor = myDataBase!!.query("electriacl_data",
//            arrayOf("name","location"),
//            "unit = $text",
//           null,null,null,null,null)
//        Log.d("naxxx",cursor.toString())
//
//        return cursor!!
        Log.d("cuaa1","aaa1")

        try {
            cur= myDataBase!!.rawQuery(
                "SELECT * FROM electriacl_data",
                null
            )
        }catch (e:Exception){
            Log.d("cuaa2",e.toString())
        }
        return cur!!
    }

    companion object {
        var DB_NAME: String = "harsh.db"// database name
        var DB_VERSION: Int = 2// database version
    }

}