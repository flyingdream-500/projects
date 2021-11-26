package com.example.finalproject.di

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.finalproject.data.db.Database
import com.example.finalproject.data.db.dao.BankCardsDao
import com.example.finalproject.data.db.dao.CurrentCurrencyDao
import com.example.finalproject.data.db.dao.TransactionsDao
import com.example.finalproject.data.db.dao.UserDao
import com.example.finalproject.data.db.typeconverter.CurrencyTypeConverter
import com.example.finalproject.data.network.api.OkHttpCurrencyApiImpl
import com.example.finalproject.data.network.converter.CurrentCurrencyConverter
import com.example.finalproject.data.network.converter.CurrentCurrencyConverterImpl
import com.example.finalproject.utils.common.DefaultClasses.defaultBankCard
import com.example.finalproject.utils.common.DefaultClasses.defaultUser
import com.example.finalproject.utils.constants.DataBaseConstants.BANK_CARDS_TABLE_NAME
import com.example.finalproject.utils.constants.DataBaseConstants.DATABASE_NAME
import com.example.finalproject.utils.constants.DataBaseConstants.USER_TABLE_NAME
import com.example.finalproject.utils.constants.PrefsConstants.PREFS_NAME
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton


/**
 * Модуль сетевого слоя
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    
    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    fun provideCurrentCurrencyConverter(moshi: Moshi) =
        CurrentCurrencyConverterImpl(moshi)

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    fun provideOkHttpApiImpl(
        okHttpClient: OkHttpClient,
        converter: CurrentCurrencyConverter
    ) =
        OkHttpCurrencyApiImpl(okHttpClient, converter)

}


/**
 * Модуль для БД
 */
@Module
@InstallIn(SingletonComponent::class)
class DataBase {

    @Singleton
    @Provides
    fun provideDatabase(application: Application, moshi: Moshi): Database {
        CurrencyTypeConverter.initialize(moshi)
        return Room.databaseBuilder(
            application.applicationContext,
            Database::class.java,
            DATABASE_NAME
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                db.apply {
                    beginTransaction()
                    insert(
                        BANK_CARDS_TABLE_NAME,
                        SQLiteDatabase.CONFLICT_ABORT,
                        defaultBankCard()
                    )
                    insert(USER_TABLE_NAME, SQLiteDatabase.CONFLICT_ABORT, defaultUser())
                    setTransactionSuccessful()
                    endTransaction()
                }
            }
        }).build()
    }

    @Singleton
    @Provides
    fun provideTransactionsDao(database: Database): TransactionsDao {
        return database.transactionsDao()
    }

    @Singleton
    @Provides
    fun provideBankCardsDao(database: Database): BankCardsDao {
        return database.bankCardsDao()
    }

    @Singleton
    @Provides
    fun provideCurrentCurrencyDao(database: Database): CurrentCurrencyDao {
        return database.currentCurrencyDao()
    }

    @Singleton
    @Provides
    fun provideUserDao(database: Database): UserDao {
        return database.userDao()
    }


}

/**
 * Модуль для провайда SharedPreferences и ContentResolver
 */
@Module
@InstallIn(SingletonComponent::class)
class AndroidComponentsModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return application.applicationContext.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    fun provideContentResolver(application: Application): ContentResolver {
        return application.contentResolver
    }
}

