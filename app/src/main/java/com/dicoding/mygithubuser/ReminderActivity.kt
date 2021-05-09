package com.dicoding.mygithubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import com.dicoding.mygithubuser.databinding.ActivityReminderBinding
import com.dicoding.mygithubuser.model.Reminder
import com.dicoding.mygithubuser.receiver.AlarmReceiver
import com.dicoding.mygithubuser.receiver.ReminderPreference

class ReminderActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityReminderBinding
    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var reminder: Reminder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val reminderPreference = ReminderPreference(this)
        binding.tbDaily.isChecked =
            reminderPreference.getReminder().setReminder

        alarmReceiver = AlarmReceiver()

        binding.tbDaily.setOnCheckedChangeListener{buttonView,isChecked ->
            if (isChecked){
                setReminder(true)
                alarmReceiver.setRepeatingAlarm(this,
                    "Repeating Alarm",
                    "09:00",
                    "Please Enter App")
            }else{
                setReminder(false)
                alarmReceiver.cancelAlarm(this)
            }
        }

        binding.btLanguage.setOnClickListener(this)



    }

    private fun setReminder(state: Boolean){
        val reminderPreference = ReminderPreference(this)
        reminder = Reminder()

        reminder.setReminder = state
        reminderPreference.setReminder(reminder.setReminder)
    }

    override fun onClick(v: View?) {
        val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        startActivity(mIntent)
    }


}
