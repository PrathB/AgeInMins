package com.test.ageinmins

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private var dateselected:TextView?=null
    private var minutes:TextView?=null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//      This will display current date before date is selected
        dateselected=findViewById(R.id.seleacteddate)
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val currdate = LocalDate.now().format(formatter)
        dateselected?.text= currdate

        minutes=findViewById(R.id.minutestxt)

        val datepickbtn:Button = findViewById(R.id.datebtn)

        datepickbtn.setOnClickListener {
            datepickfxn()
        }
    }

    private fun datepickfxn(){
//      calendar instance is created
        val mycalendar = Calendar.getInstance()
        val year = mycalendar.get(Calendar.YEAR)
        val month=mycalendar.get(Calendar.MONTH)
        val day=mycalendar.get(Calendar.DAY_OF_MONTH)

//      DatePickerDialog is called to pop up date selector and to execute some tasks when date is selected
        val dpd = DatePickerDialog(this, { view, selyear, selmonth, selday ->
            val dt = "$selday/${selmonth + 1}/$selyear"
            dateselected?.text = dt

//          After selecting date , we convert the selected date to simple date using the parse method in class SimpleDateFormat
//          Selected date is converted from String to Date? type variable
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            val SimpleSelDt = sdf.parse(dt)

//          converting SimpleSelDt to mins
            SimpleSelDt?.let {
                val SelDtInMin = SimpleSelDt.time / 60000

//              getting current date in mins
                val SimpleCurrDt = sdf.parse(sdf.format(System.currentTimeMillis()))
                SimpleCurrDt?.let {

                    val CurrDtInMin = SimpleCurrDt.time / 60000

    //              calculating difference
                    val diff = CurrDtInMin - SelDtInMin

    //              storing difference in minutes textview
                    minutes?.text = diff.toString()
                }
            }

        }
            ,year,month,day)

        dpd.datePicker.maxDate =System.currentTimeMillis()
        dpd.show()
    }

}