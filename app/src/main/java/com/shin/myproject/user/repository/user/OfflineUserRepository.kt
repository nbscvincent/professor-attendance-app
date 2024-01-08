package com.shin.myproject.user.repository.user

import com.shin.myproject.data.authModel.User
import com.shin.myproject.user.dao.UserDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class OfflineUserRepository(private val userDao: UserDao) : UserRepository {
    override fun getAllUsersStream(): Flow<List<User>> = userDao.getAllUsers()

    override fun getUserStream(id: Long): Flow<User?> = userDao.getUser(id)

    override suspend fun getUserById(userId: Long): User? {
        return userDao.getUser(userId).firstOrNull()
    }

    override suspend fun getUserByEmail(email: String): User? {
        return userDao.getEmail(email).firstOrNull()
    }

    override fun isPhoneExist(phone: String): Flow<Boolean> {
        return userDao.isPhoneExist(phone)
    }

    override fun isEmailExist(email: String): Flow<Boolean> {
        return userDao.isEmailExist(email)
    }

    override suspend fun insertUser(user: User) = userDao.insert(user)

    override suspend fun deleteUser(user: User) = userDao.delete(user)

    override suspend fun updateUser(user: User) = userDao.update(user)

    override suspend fun deleteUserById(userId: Long) {
        val user = userDao.getUser(userId).firstOrNull()
        if (user != null) {
            userDao.delete(user)
        }
    }
}