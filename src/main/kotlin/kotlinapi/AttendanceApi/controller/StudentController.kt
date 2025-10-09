package kotlinapi.AttendanceApi.controller

import kotlinapi.AttendanceApi.dataSources.StudentRepository
import kotlinapi.AttendanceApi.model.Student
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus

data class CreateStudentRequest(val name: String, val email: String)

@RestController
@RequestMapping("/students")
class StudentController(private val repository: StudentRepository) {

    @GetMapping
    fun list(): List<Student> = repository.getStudents()

    @GetMapping("/{id}")
    fun get(@PathVariable id: Int): ResponseEntity<Student> {
        val s = repository.getStudentById(id)
        return if (s != null) ResponseEntity.ok(s) else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun create(@RequestBody req: CreateStudentRequest): ResponseEntity<Student> {
        if (req.name.isBlank() || req.email.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
        val created = repository.createStudent(req.name, req.email)
        return ResponseEntity.status(HttpStatus.CREATED).body(created)
    }

}
