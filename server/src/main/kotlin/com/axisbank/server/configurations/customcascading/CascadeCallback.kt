import com.axisbank.server.configurations.customcascading.CascadeSave
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.stereotype.Component
import org.springframework.util.ReflectionUtils
import java.lang.reflect.Field

@Component
class CascadeCallback(var source: Any, val mongoOperations: MongoOperations) :
    ReflectionUtils.FieldCallback {

    @Throws(IllegalArgumentException::class, IllegalAccessException::class)
    override fun doWith(field: Field) {
        ReflectionUtils.makeAccessible(field)
        if (field.isAnnotationPresent(DBRef::class.java) && field.isAnnotationPresent(
                CascadeSave::class.java
            )
        ) {
            val fieldValue = field[source]
            if (fieldValue != null) {
                val callback = FieldCallback()
                ReflectionUtils.doWithFields(fieldValue.javaClass, callback)
                mongoOperations.save(fieldValue)
            }
        }
    }
}