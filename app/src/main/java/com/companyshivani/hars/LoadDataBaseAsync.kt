package com.companyshivani.hars

import android.app.AlertDialog
import android.content.Context
import android.database.SQLException
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import java.io.IOException
import java.lang.Error

class LoadDataBaseAsync: AsyncTask<Void, Void, Boolean> {

    var alertDialog: AlertDialog? = null
    var dbHelper: DatabaseHelper? = null
    var context: Context? = null

    constructor(context: Context) {
        this.context = context
    }

    override fun doInBackground(vararg params: Void?): Boolean? {


        dbHelper = DatabaseHelper(context!!)

        try {
            dbHelper!!.createDataBase()
        } catch (e: IOException) {
            throw Error("Database not created")
        }

        dbHelper!!.close()
        return null
    }

    override fun onPreExecute() {
        super.onPreExecute()

        var builder: AlertDialog.Builder = AlertDialog.Builder(context, R.style.MyDialogTheme)
        var inflater: LayoutInflater = LayoutInflater.from(context)
        var dialogView: View = inflater.inflate(R.layout.alert_dialog_database, null)
        builder.setTitle("Loading Database...")
        builder.setView(dialogView)
        alertDialog = builder.create()

        alertDialog!!.setCancelable(false)
        alertDialog!!.show()
    }

    override fun onPostExecute(result: Boolean?) {
        super.onPostExecute(result)
        alertDialog!!.dismiss()

        var ma = MainActivity()
        ma.openDatabase()
    }

    override fun onProgressUpdate(vararg values: Void?) {
        super.onProgressUpdate(*values)
    }

}