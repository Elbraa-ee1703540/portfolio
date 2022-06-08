package com.example.sportcommunity.viewmodel

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.util.*


//   DateWithTime
class DateWithTimeViewModel : ViewModel() {
    private val _time = MutableLiveData("")
    var time: LiveData<String> = _time
    private val _date = MutableLiveData("")
    var date: LiveData<String> = _date

    fun deleteDate() {
        date = _date
    }

    fun selectDateTime(context: Context) {
        var time = ""
        val currentDateTime = Calendar.getInstance()
        val startYear = currentDateTime.get(Calendar.YEAR)
        val startMonth = currentDateTime.get(Calendar.MONTH)
        val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
        val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = currentDateTime.get(Calendar.MINUTE)

        DatePickerDialog(context, { _, year, month, day ->
            TimePickerDialog(context, { _, hour, minute ->
                val pickedDateTime = Calendar.getInstance()
                pickedDateTime.set(year, month, day, hour, minute)
                val monthStr: String
                if ((month + 1).toString().length == 1) {
                    monthStr = "0${month + 1}"
                } else {
                    monthStr = month.toString()
                }
                time = "$day - $monthStr - $year $hour:$minute"
                updateDateTime(time)
            }, startHour, startMinute, false).show()
        }, startYear, startMonth, startDay).show()
    }

    fun selectDate(context: Context) {
        var time = ""
        val currentDateTime = Calendar.getInstance()
        val startYear = currentDateTime.get(Calendar.YEAR)
        val startMonth = currentDateTime.get(Calendar.MONTH)
        val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
        val today = LocalDate.of(startYear, startMonth, startDay)
        var chosenDate = LocalDate.of(2016, 12, 25)
        DatePickerDialog(context, { _, year, month, day ->
            val pickedDate = Calendar.getInstance()
            pickedDate.set(year, month, day)
            chosenDate = LocalDate.of(year, month, day)
            val monthStr: String = if ((month + 1).toString().length == 1) {
                "0${month + 1}"
            } else {
                month.toString()
            }
            Log.d("TAG", "selectDate: $today")
            Log.d("TAG", "selectDate: $chosenDate")
            if (chosenDate.isBefore(today)) {
                currentDateTime.add(Calendar.MONTH, 1)
                val modifiedMonth: String = if (currentDateTime.get(Calendar.MONTH).toString().length == 1) {
                    "0${currentDateTime.get(Calendar.MONTH)}"
                } else {
                    currentDateTime.get(Calendar.MONTH).toString()
                }
                time = "$startDay-$modifiedMonth-$startYear"
                updateDate(time)
                Toast.makeText(context, "Cannot choose past date", Toast.LENGTH_LONG).show()
            } else {
                time = "$day-$monthStr-$year"
                updateDate(time)
            }
        }, startYear, startMonth, startDay).show()
    }

    private fun updateDateTime(dateTime: String) {
        _time.value = dateTime
    }

    private fun updateDate(date: String) {
        _date.value = date
    }
}











