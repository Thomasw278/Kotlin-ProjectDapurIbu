package com.example.dapuribuproject.Helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.dapuribuproject.DataClass.ChatMessage
import com.example.dapuribuproject.DataClass.Katalog
import com.example.dapuribuproject.DataClass.User

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "dapuribu.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val queryKatalog = """
            CREATE TABLE katalog(
            id_katalog INTEGER PRIMARY KEY AUTOINCREMENT,
            judul_katalog TEXT,
            kategori_katalog TEXT,
            deskripsi_katalog TEXT,
            foto_katalog TEXT
            );
        """.trimIndent()

        val queryUser = """
            CREATE TABLE user(
            id_user INTEGER PRIMARY KEY AUTOINCREMENT,
            username TEXT,
            email TEXT,
            tanggal_lahir TEXT,
            role TEXT,
            password TEXT
            ); 
            """.trimIndent()

        val queryFavorit = """
            CREATE TABLE favorit(
            id_favorit INTEGER PRIMARY KEY AUTOINCREMENT,
            username TEXT,
            id_katalog INTEGER,
            FOREIGN KEY(id_katalog) REFERENCES katalog(id_katalog)
            );
        """.trimIndent()

        val queryPesan = """
            CREATE TABLE pesan(
            id_pesan INTEGER PRIMARY KEY AUTOINCREMENT,
            sender TEXT,
            receiver TEXT,
            isi_pesan TEXT,
            waktu TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
        """.trimIndent()

        db.execSQL(queryKatalog)
        db.execSQL(queryUser)
        db.execSQL(queryFavorit)
        db.execSQL(queryPesan)

        // Index Pesan
        db.execSQL("CREATE INDEX idx_pesan_participants ON pesan(sender, receiver)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS katalog")
        db.execSQL("DROP TABLE IF EXISTS user")
        db.execSQL("DROP TABLE IF EXISTS favorit")
        db.execSQL("DROP TABLE IF EXISTS pesan")
        onCreate(db)
    }

    fun insertData_Katalog(judul: String, kategori: String, deskripsi: String, foto: String) {
        val db = writableDatabase
        val deskripsi_clear = deskripsi.replace("'", "''")
        val query = "INSERT INTO katalog (judul_katalog, kategori_katalog, deskripsi_katalog, foto_katalog) VALUES ('$judul','$kategori','$deskripsi_clear','$foto')"
        db.execSQL(query)
        db.close()
    }

    fun deleteData_Katalog(id: Int) {
        val db = writableDatabase
        db.execSQL("DELETE FROM katalog WHERE id_katalog = $id")
        db.close()
    }

    fun insertData_User(username: String, email: String, tanggal_lahir: String, role: String, password: String) {
        val db = writableDatabase
        val query = "INSERT INTO user (username, email, tanggal_lahir,role, password) VALUES ('$username','$email','$tanggal_lahir','$role','$password')"
        db.execSQL(query)
        db.close()
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

    // Favorite Methods
    fun addFavorit(username: String, idKatalog: Int) {
        val db = writableDatabase
        val query = "INSERT INTO favorit (username, id_katalog) VALUES ('$username', $idKatalog)"
        db.execSQL(query)
        db.close()
    }

    fun removeFavorit(username: String, idKatalog: Int) {
        val db = writableDatabase
        db.execSQL("DELETE FROM favorit WHERE username = '$username' AND id_katalog = $idKatalog")
        db.close()
    }

    fun isFavorit(username: String, idKatalog: Int): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM favorit WHERE username = '$username' AND id_katalog = $idKatalog", null)
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    fun getFavoritByUser(username: String): List<Katalog> {
        val list = mutableListOf<Katalog>()
        val db = readableDatabase
        val query = """
            SELECT k.* FROM katalog k
            JOIN favorit f ON k.id_katalog = f.id_katalog
            WHERE f.username = '$username'
        """.trimIndent()
        val cursor = db.rawQuery(query, null)

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

    // Chat Methods
    fun insertPesan(sender: String, receiver: String, message: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("sender", sender)
            put("receiver", receiver)
            put("isi_pesan", message)
        }
        db.insert("pesan", null, values)
        db.close()
    }

    fun getChatMessages(user1: String, user2: String): List<ChatMessage> {
        val list = mutableListOf<ChatMessage>()
        val db = readableDatabase
        val query = "SELECT * FROM pesan WHERE (sender = ? AND receiver = ?) OR (sender = ? AND receiver = ?) ORDER BY waktu ASC"
        val cursor = db.rawQuery(query, arrayOf(user1, user2, user2, user1))

        if (cursor.moveToFirst()) {
            do {
                val sender = cursor.getString(1)
                val item = ChatMessage(
                    id = cursor.getInt(0),
                    sender = sender,
                    receiver = cursor.getString(2),
                    message = cursor.getString(3),
                    timestamp = cursor.getString(4),
                    isSentByMe = sender == user1
                )
                list.add(item)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun getChatUsersForAdmin(): List<Pair<String, String>> {
        val list = mutableListOf<Pair<String, String>>()
        val db = readableDatabase
        val query = """
            SELECT other_party, isi_pesan 
            FROM (
                SELECT receiver as other_party, waktu, isi_pesan FROM pesan WHERE sender = 'admin'
                UNION ALL
                SELECT sender as other_party, waktu, isi_pesan FROM pesan WHERE receiver = 'admin'
            )
            GROUP BY other_party
            HAVING waktu = MAX(waktu)
            ORDER BY waktu DESC
        """.trimIndent()

        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val user = cursor.getString(0)
                if (user != "admin") {
                    list.add(Pair(user, cursor.getString(1)))
                }
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
        return if (list.isNotEmpty()) list[0] else ""
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
        return if (list.isNotEmpty()) list[0] else ""
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
        return if (list.isNotEmpty()) list[0] else ""
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
        return if (list.isNotEmpty()) list[0] else ""
    }

    fun UpdatePassword(username: String, password: String) {
        val db = writableDatabase
        val query = "UPDATE user SET password = '$password' WHERE username = '$username'"
        db.execSQL(query)
        db.close()
    }

    fun Search(keyword: String) : List<Katalog>{
        val list = mutableListOf<Katalog>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM katalog WHERE judul_katalog LIKE '%$keyword%' OR kategori_katalog LIKE '%$keyword%'", null)

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