package uz.xsoft.blog.data.source.local.room

import androidx.room.TypeConverter
import uz.xsoft.blog.data.models.common.State

class Converters {

    @TypeConverter
    fun toState(value: String) = enumValueOf<State>(value)

    @TypeConverter
    fun fromState(value: State) = value.name
}