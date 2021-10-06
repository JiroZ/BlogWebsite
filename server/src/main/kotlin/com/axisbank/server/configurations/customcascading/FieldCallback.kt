import org.springframework.data.annotation.Id
import org.springframework.util.ReflectionUtils
import java.lang.reflect.Field

class FieldCallback : ReflectionUtils.FieldCallback {
    var isIdFound = false
        private set

    @Throws(IllegalArgumentException::class, IllegalAccessException::class)
    override fun doWith(field: Field) {
        ReflectionUtils.makeAccessible(field)
        if (field.isAnnotationPresent(Id::class.java)) {
            isIdFound = true
        }
    }
}