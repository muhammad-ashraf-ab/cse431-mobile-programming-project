package com.college.cse431_mobile_programming_project.utils

import android.net.Uri
import androidx.room.TypeConverter

class UserConverters {

    @TypeConverter
    fun fromString(value: String?): Uri? {
        return if (value == null) null else Uri.parse(value)
    }

    @TypeConverter
    fun toString(uri: Uri?): String? {
        return uri?.toString()
    }
}