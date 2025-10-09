package kotlinapi.AttendanceApi.dataSources

import kotlinapi.AttendanceApi.model.User
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class UserRepository(private val jdbc: JdbcTemplate) {

    private val rowMapper: RowMapper<User> = RowMapper { rs, _ ->
        User(
            id = rs.getInt("id"),
            name = rs.getString("name"),
            email = rs.getString("email"),
            password = rs.getString("password")
        )
    }

    fun getUsers(): List<User> {
        return jdbc.query("SELECT id, name, email, password FROM users", rowMapper)
    }

    fun getUserById(id: Int): User? {
        val sql = "SELECT id, name, email, password FROM users WHERE id = ?"
        return try {
            jdbc.queryForObject(sql, rowMapper, id)
        } catch (ex: Exception) {
            null
        }
    }

    fun getUserByEmail(email: String): User? {
        val sql = "SELECT id, name, email, password FROM users WHERE email = ?"
        return try {
            jdbc.queryForObject(sql, rowMapper, email)
        } catch (ex: Exception) {
            null
        }
    }

    fun createUser(user: User): User {
        val sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)"
        val keyHolder = org.springframework.jdbc.support.GeneratedKeyHolder()
        val preparedStatementCreator = org.springframework.jdbc.core.PreparedStatementCreator { con ->
            val ps = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)
            ps.setString(1, user.name)
            ps.setString(2, user.email)
            ps.setString(3, user.password)
            ps
        }
        jdbc.update(preparedStatementCreator, keyHolder)
        val generatedId = keyHolder.key?.toInt() ?: throw RuntimeException("Failed to obtain generated id")
        return user.copy(id = generatedId)
    }

    fun updateUser(id: Int, user: User): Boolean {
        val sql = "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?"
        val updated = jdbc.update(sql, user.name, user.email, user.password, id)
        return updated > 0
    }

    fun deleteUser(id: Int): Boolean {
        val sql = "DELETE FROM users WHERE id = ?"
        val deleted = jdbc.update(sql, id)
        return deleted > 0
    }
}