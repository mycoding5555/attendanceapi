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
}