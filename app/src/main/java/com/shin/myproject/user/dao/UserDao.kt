package com.shin.myproject.user.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shin.myproject.data.authModel.User
import kotlinx.coroutines.flow.Flow

/**
 * Database access object to access the NBS database
 */
@Dao
interface UserDao {

    @Query("SELECT * from Users ORDER BY user_id ASC")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * from Users WHERE user_id = :id")
    fun getUser(id: Long): Flow<User>

    @Query("SELECT * from Users WHERE email = :email")
    fun getEmail(email: String): Flow<User?>

    @Query("SELECT * from Users WHERE phone = :phone")
    fun getUserByPhone(phone: String): Flow<User?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT EXISTS(SELECT 1 FROM Users WHERE phone = :phone)")
    fun isPhoneExist(phone: String): Flow<Boolean>

    @Query("SELECT EXISTS(SELECT 1 FROM Users WHERE email = :email)")
    fun isEmailExist(email: String): Flow<Boolean>
}