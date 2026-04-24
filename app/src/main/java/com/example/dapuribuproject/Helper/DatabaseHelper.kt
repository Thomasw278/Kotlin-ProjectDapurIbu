package com.example.dapuribuproject.Helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.dapuribuproject.DataClass.Katalog
import com.example.dapuribuproject.DataClass.User
import java.util.Date

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "dapuribu.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val query_katalog = """
            CREATE TABLE katalog(
            id_katalog INTEGER PRIMARY KEY AUTOINCREMENT,
            judul_katalog TEXT,
            kategori_katalog TEXT,
            deskripsi_katalog TEXT,
            foto_katalog TEXT
            );
        """.trimIndent()

        val query_user = """
            CREATE TABLE user(
            id_user INTEGER PRIMARY KEY AUTOINCREMENT,
            username TEXT,
            email TEXT,
            tanggal_lahir TEXT,
            role TEXT,
            password TEXT
            ); 
            """.trimIndent()

        db.execSQL(query_katalog)
        db.execSQL(query_user)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS katalog")
        db.execSQL("DROP TABLE IF EXISTS user")
        onCreate(db)
    }

    fun insertData_Katalog(judul: String, kategori: String, deskripsi: String, foto: String) {
        val db = writableDatabase
        val query = "INSERT INTO katalog (judul_katalog, kategori_katalog, deskripsi_katalog, foto_katalog) VALUES ('$judul','$kategori','$deskripsi','$foto')"
        db.execSQL(query)
    }

    fun insertData_User(username: String, email: String, tanggal_lahir: String, role: String, password: String) {
        val db = writableDatabase
        val query = "INSERT INTO user (username, email, tanggal_lahir,role, password) VALUES ('$username','$email','$tanggal_lahir','$role','$password')"
        db.execSQL(query)
    }

    fun getAllDataKatalog(): List<Katalog> {
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

    fun getAllDataUser(): List<User> {
        val list = mutableListOf<User>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM user", null)

        if (cursor.moveToFirst()) {
            do {
                val item = User(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
                )
                list.add(item)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun getRoles(username: String): String {
        val list = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT role FROM user WHERE username = '$username'", null)
        if (cursor.moveToFirst()) {
            do {
                val item = cursor.getString(0)
                list.add(item)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list[0]
    }

    fun getEmail(username: String): String {
        val list = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT email FROM user WHERE username = '$username'", null)
        if (cursor.moveToFirst()) {
            do {
                val item = cursor.getString(0)
                list.add(item)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list[0]
    }

    fun getTanggalLahir(username: String): String {
        val list = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT tanggal_lahir FROM user WHERE username = '$username'", null)
        if (cursor.moveToFirst()) {
            do {
                val item = cursor.getString(0)
                list.add(item)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list[0]
    }

    fun getPassword(username: String): String {
        val list = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT password FROM user WHERE username = '$username'", null)
        if (cursor.moveToFirst()) {
            do {
                val item = cursor.getString(0)
                list.add(item)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list[0]
    }

    fun UpdatePassword(username: String, password: String) {
        val db = writableDatabase
        val query = "UPDATE user SET password = '$password' WHERE username = '$username'"
        db.execSQL(query)
    }

}