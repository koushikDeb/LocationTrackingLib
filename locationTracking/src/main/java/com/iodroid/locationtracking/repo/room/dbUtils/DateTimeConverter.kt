package com.iodroid.locationtracking.repo.room.dbUtils

import androidx.room.TypeConverter
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter


object DateTimeConverter {
  private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    @JvmStatic
    fun toOffsetDateTime(value: String?): OffsetDateTime? {
      return value?.let {
        return formatter.parse(value, OffsetDateTime::from)
      }
    }

    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(date: OffsetDateTime?): String? {
      return date?.format(formatter)
    }

}