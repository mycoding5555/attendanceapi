package kotlinapi.AttendanceApi.dataSources

import kotlinapi.AttendanceApi.model.Student
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class StudentRepository(private val jdbc: JdbcTemplate) {

    private val rowMapper: RowMapper<Student> = RowMapper { rs, _ ->
        Student(
            id = rs.getInt("id"),
            name = rs.getString("name"),
            email = rs.getString("email"),
            attendance = emptyList(),
            absence = emptyList(),
            present = emptyList(),
            late = emptyList()
        )
    }

    fun getStudents(): List<Student> = jdbc.query("SELECT id, name, email FROM students", rowMapper)

    fun getStudentById(id: Int): Student? {
        val sql = "SELECT id, name, email FROM students WHERE id = ?"
        return try {
            jdbc.queryForObject(sql, rowMapper, id)
        } catch (ex: Exception) {
            null
        }
    }

    fun createStudent(name: String, email: String): Student {
        val sql = "INSERT INTO students (name, email) VALUES (?, ?)"
        val keyHolder = org.springframework.jdbc.support.GeneratedKeyHolder()
        val psc = org.springframework.jdbc.core.PreparedStatementCreator { con ->
            val ps = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)
            ps.setString(1, name)
            ps.setString(2, email)
            ps
        }
        jdbc.update(psc, keyHolder)
        val id = keyHolder.key?.toInt() ?: throw RuntimeException("Failed to obtain generated id for student")
        return Student(id = id, name = name, email = email, attendance = emptyList(), absence = emptyList(), present = emptyList(), late = emptyList())
    }
}
