package kotlinapi.AttendanceApi.services

import org.springframework.stereotype.Service
import kotlinapi.AttendanceApi.dataSources.UserRepository
import kotlinapi.AttendanceApi.model.User


@Service
class UserService(private val repository: UserRepository) {

    fun getUsers(): List<User> = repository.getUsers()

    fun getUser(id: Int): User? = repository.getUserById(id)

    fun createUser(user: User): User = repository.createUser(user)

    fun updateUser(id: Int, user: User): Boolean = repository.updateUser(id, user)

    fun deleteUser(id: Int): Boolean = repository.deleteUser(id)

}