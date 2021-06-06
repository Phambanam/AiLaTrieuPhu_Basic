package com.example.ailatrieuphu1.data

import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.ailatrieuphu1.model.Question
import java.io.*
import kotlin.jvm.Throws

class DBManager(
    context: Context,
) : SQLiteOpenHelper(context, DB_NAME, null, TABLE_VERSION) {
    private val myContext: Context = context
    private var database: SQLiteDatabase? = null
    companion object {
        private const val TABLE_NAME = "Question"
        private const val TABLE_QUESTION = "Question"
        private const val TABLE_ID = "_id"
        private const val TABLE_CASE_A = "CaseA"
        private const val TABLE_CASE_B = "CaseB"
        private const val TABLE_CASE_C = "CaseC"
        private const val TABLE_CASE_D = "CaseD"
        private const val TABLE_TRUE_CASE = "TrueCase"
        private const val TABLE_VERSION = 1
        private const val DB_NAME = "Question.sqlite"
        private const val DB_PATH = "/data/data/com.example.ailatrieuphu1/databases/"
    }

    init {
        val dbexist = checkdatabase()
        if (dbexist) {
            //System.out.println("Database exists");
            openDatabase()
        } else {
            System.out.println("Database doesn't exist")
            createDatabase()
        }
        handlerCopyDataFromAssets()
    }

    private fun createDatabase() {
        val dbexist = checkdatabase()
        database = null
        if (dbexist) {
            println(" Database exists.")
        } else {
            database = this.readableDatabase
            try {
                copyDatabase()
            } catch (e: IOException) {
                throw Error("Error copying database: $e")
            }
        }
    }


    private fun checkdatabase(): Boolean {
        //SQLiteDatabase checkdb = null;
        var checkdb = false
        try {
            val myPath = DB_PATH + DB_NAME
            val dbfile = File(myPath)
            //checked = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
            checkdb = dbfile.exists()
        } catch (e: SQLiteException) {
            println("Database doesn't exist")
        }
        return checkdb

    }


    private fun handlerCopyDataFromAssets() {
        val dbFIle =File(DB_PATH+ DB_NAME)
        if (!dbFIle.exists()) {
            copyDatabase()
        } else {
            dbFIle.delete()
            copyDatabase()
        }
    }

    @Throws(IOException::class)
    private fun copyDatabase() {
        val myInput: InputStream = myContext.assets.open(DB_NAME)

        val myOutPut: OutputStream = FileOutputStream("/data/data/com.example.ailatrieuphu1/databases/Question.sqlite")
        val buffer = ByteArray(1024)
        var length: Int
        while (myInput.read(buffer).also { length = it } > 0) {
            myOutPut.write(buffer, 0, length)
        }

        //Close the streams
        myOutPut.flush()
        myOutPut.close()
        myOutPut.close()
    }

    @Throws(SQLException::class)
    fun openDatabase() {
        //Open the database
        println(myContext.filesDir.path)
        val myPath = myContext.getDatabasePath(DB_NAME).path
        database = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE)
    }

  fun getData(): MutableList<Question> {
        openDatabase()
        val arrQuestions: MutableList<Question> = mutableListOf()
        for (i in 1..15) {
            val table = TABLE_NAME + i + ""
            val sql = "SELECT * FROM $table ORDER BY random() limit 1"
            val db: SQLiteDatabase = this.readableDatabase
            val cursor: Cursor = db.rawQuery(sql, null)
            val indexId: Int = cursor.getColumnIndex(TABLE_ID)
            val indexQuestion: Int = cursor.getColumnIndex(TABLE_QUESTION)
            val indexCaseA: Int = cursor.getColumnIndex(TABLE_CASE_A)

            val indexCaseB: Int = cursor.getColumnIndex(TABLE_CASE_B)
            val indexCaseC: Int = cursor.getColumnIndex(TABLE_CASE_C)
            val indexCaseD: Int = cursor.getColumnIndex(TABLE_CASE_D)
            val indexTrueCase: Int = cursor.getColumnIndex(TABLE_TRUE_CASE)

            cursor.moveToFirst()
            val id: Int = cursor.getInt(indexId)
            val question: String = cursor.getString(indexQuestion)
            val caseA: String = cursor.getString(indexCaseA)
            val caseB: String = cursor.getString(indexCaseB)
            val caseC: String = cursor.getString(indexCaseC)
            val caseD: String = cursor.getString(indexCaseD)
            val trueCase: Int = cursor.getInt(indexTrueCase)
            val question1 = Question(question, id,i.toString(), trueCase, caseA, caseB, caseC, caseD)
            arrQuestions.add(question1)
            Log.d("aaaa",caseA)

        }
        return arrQuestions
    }

    override fun onCreate(db: SQLiteDatabase?) {
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (newVersion > oldVersion) {
            try {
                copyDatabase()
            } catch (e: IOException) {
            }
        }
    }
}