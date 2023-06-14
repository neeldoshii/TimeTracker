package com.example.timetrackerapp.database.convertors

import androidx.room.TypeConverter
import java.sql.Time

class TimeConvertors {


    @TypeConverter
    fun timeToLong(time : Time) : Long{
        println("1************************ "+time.time)
        return time.time
    }
    @TypeConverter

    fun longToTime(longTime : Long) : Time{
        println("2************************ "+Time(longTime))
        return  Time(longTime)
    }
}