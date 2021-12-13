package com.example.contentproviderapp.data.datastore.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.contentproviderapp.data.datastore.provider.DictionaryContentProvider

class DataBaseOpenHelper(
    context: Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)  {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_DICTIONARY_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }


    companion object {
        private const val DATABASE_NAME = "dictionary.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "dictionary"


        private const val CREATE_DICTIONARY_TABLE =
            "CREATE TABLE $TABLE_NAME (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${Columns.KEYWORD} TEXT," +
                    "${Columns.TRANSLATION} TEXT," +
                    "${Columns.LOGO} TEXT)"

        private const val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }


    object Columns : BaseColumns {
        const val KEYWORD = "keyword"
        const val TRANSLATION = "translation"
        const val LOGO = "logo"
    }
}