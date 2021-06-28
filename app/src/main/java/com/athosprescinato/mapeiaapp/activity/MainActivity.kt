package com.athosprescinato.mapeiaapp.activity

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.athosprescinato.mapeiaapp.R
import com.athosprescinato.mapeiaapp.adapter.CustomAdapter
import com.athosprescinato.mapeiaapp.helper.DBHelper

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val dbHandler = DBHelper(this, null)
    var dataList = ArrayList<HashMap<String, String>>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        fab.setOnClickListener { view ->
            val intent = Intent(this, ViagemActivity::class.java)
            startActivity(intent)
        }


    }

    //@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun carregarList() {
        dataList.clear()
        val cursor = dbHandler.getAllRow()

        cursor!!.moveToFirst()

        while (!cursor.isAfterLast) {
            val map = HashMap<String, String>()
            map["id"] = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ID))
            map["viagem"] = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_VIAGEM))
            map["distancia"] = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DISTANCIA))
            map["combustivel"] =
                cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_COMBUSTIVEL))
            map["autonomia"] = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_AUTONOMIA))
            map["resultado"] = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_RESULTADO))
            dataList.add(map)


            cursor.moveToNext()

            findViewById<ListView>(R.id.listView).adapter = CustomAdapter(this@MainActivity, dataList)
            findViewById<ListView>(R.id.listView).setOnItemClickListener { _, _, position, _ ->

                val builder = AlertDialog.Builder(this)
                with(builder) {
                    setTitle("Deletar")
                    setMessage("Você têm certeza que deseja deletar?")
                    setPositiveButton("DELETE",{dialogInterface, which ->
                        var idPosition = dataList[position]["id"]
                        dbHandler.deleteRow(idPosition.toString())
                        recarregarAdapter()
                    })
                }
                val alertDialog = builder.create()
                alertDialog.show()


            }
        }


    }


    public override fun onResume() {
        super.onResume()
        carregarList()
    }

    fun recarregarAdapter() {
        dataList.clear()
        dataList.addAll(dataList)
        CustomAdapter(this@MainActivity, dataList).notifyDataSetChanged()
        findViewById<ListView>(R.id.listView).adapter = CustomAdapter(this@MainActivity, dataList)
    }

}
