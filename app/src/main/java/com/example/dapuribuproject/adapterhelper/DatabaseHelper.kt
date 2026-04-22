package com.example.dapuribuproject.adapterhelper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "dapuribu.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = """
            CREATE TABLE katalog(
            id_katalog INTEGER PRIMARY KEY AUTOINCREMENT,
            judul_katalog TEXT,
            kategori_katalog TEXT,
            deskripsi_katalog TEXT,
            foto_katalog TEXT
            )
        """

        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS katalog")
        onCreate(db)
    }

    fun insertData(judul: String, kategori: String, deskripsi: String, foto: String) {
        val db = writableDatabase
        val query = "INSERT INTO katalog (judul_katalog, kategori_katalog, deskripsi_katalog, foto_katalog) VALUES ('$judul','$kategori','$deskripsi','$foto')"
        db.execSQL(query)
    }

    fun getAllData(): List<Katalog> {
        val list = mutableListOf<Katalog>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM katalog", null)

        if (cursor.moveToFirst()) {
            do {
                val item = Katalog(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
                )
                list.add(item)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }
}
