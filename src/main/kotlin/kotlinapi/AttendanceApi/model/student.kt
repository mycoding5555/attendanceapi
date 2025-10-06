package kotlinapi.AttendanceApi.model

data class Student(
    val id: Int,
    val name: String,
    val email: String,
    val attendance: List<String>,
    val absence: List<String>,
    val present: List<String>,
    val late: List<String>
)