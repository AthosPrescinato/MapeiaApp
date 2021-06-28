package com.athosprescinato.mapeiaapp.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.EditText


class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
        SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION){


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_VIAGEM TEXT, $COLUMN_DISTANCIA FLOAT, $COLUMN_COMBUSTIVEL FLOAT, $COLUMN_AUTONOMIA FLOAT, $COLUMN_RESULTADO)")
       //db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertRow(viagem: String, distancia: Float, combustivel: Float, autonomia: Float, resultado: Float) {

        val values = ContentValues()
        values.put(COLUMN_VIAGEM, viagem)
        values.put(COLUMN_DISTANCIA, distancia)
        values.put(COLUMN_COMBUSTIVEL, combustivel)
        values.put(COLUMN_AUTONOMIA, autonomia)
        values.put(COLUMN_RESULTADO, resultado)

        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }


    fun deleteRow(row_id: String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(row_id))
        db.close()
    }

    fun getAllRow(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "viagens.db"
        const val TABLE_NAME = "gastoestimado"

        const val COLUMN_ID = "id"
        const val COLUMN_VIAGEM = "viagem"
        const val COLUMN_DISTANCIA = "distancia"
        const val COLUMN_COMBUSTIVEL = "combustivel"
        const val COLUMN_AUTONOMIA = "autonomia"
        const val COLUMN_RESULTADO = "resultado"
    }

}