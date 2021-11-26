package com.example.finalproject.data.db.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.finalproject.args.DEFAULT_USER_DB
import com.example.finalproject.data.db.Database
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test


/**
 * Тестирование базы данных [UserDao] по работе с данными пользователя
 */
class UserDaoTest {

    private lateinit var db: Database
    private lateinit var userDao: UserDao


    /**
     * Данный метод будет вызван перед каждым тестовым методом.
     */
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            Database::class.java
        ).build()
        userDao = db.userDao()
    }

    /**
     * Данный метод будет вызван после каждого тестового метода.
     */
    @After
    fun closeDb() {
        db.close()
    }

    /**
     * Тестирование [UserDao.insertUser] и [UserDao.updateAndGetUser]
     */
    @Test
    fun updateAndGetUserTest() {
        val expectedValue = DEFAULT_USER_DB
        userDao.insertUser(DEFAULT_USER_DB)
        val result = userDao.updateAndGetUser(DEFAULT_USER_DB).blockingGet()
        assertEquals(expectedValue, result)
    }

}