package com.example.timetrackerapp.database.convertors

import androidx.room.TypeConverter
import java.sql.Time

class TimeConvertors {


    @TypeConverter
    fun timeToLong(time : Time) : Long{

        return time.time
    }
    @TypeConverter

    fun longToTime(longTime : Long) : Time{
        return  Time(longTime)
    }
}