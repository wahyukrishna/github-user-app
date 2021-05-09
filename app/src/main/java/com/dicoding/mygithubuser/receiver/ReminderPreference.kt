package com.dicoding.mygithubuser.receiver

import android.content.Context
import com.dicoding.mygithubuser.model.Reminder

class ReminderPreference(context: Context) {
    companion object{
        const val REMINDER_PREF = "reminder_pref"
        private const val REMINDER = "reminder"
    }

    private val preference = context.getSharedPreferences(REMINDER_PREF, Context.MODE_PRIVATE)

    fun setReminder(value: Boolean){
        val editor = preference.edit()
        editor.putBoolean(REMINDER, value)
        editor.apply()
    }

    fun getReminder(): Reminder{
        val model = Reminder()
        model.setReminder = preference.getBoolean(REMINDER, false)
        return model
    }
}