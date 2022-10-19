package com.udacity.project4.locationreminders.data

import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem

 val validReminderItem = ReminderDataItem(
    "Cairo", "Cairo, Egypt’s sprawling capital", "Cairo",
    30.04685422286314, 31.236147738377063
)

 val reminderItemWithTitleNull = ReminderDataItem(
    null, "Cairo, Egypt’s sprawling capital", "Cairo",
    30.04685422286314, 31.236147738377063
)

 val reminderItemWithLocationNull = ReminderDataItem(
    "Cairo", "Cairo, Egypt’s sprawling capital", null,
    30.04685422286314, 31.236147738377063
)

 val reminderItemWithAllNull = ReminderDataItem(
    null, null, null,
    null, null
)

 val reminderItemWithLatLongNull = ReminderDataItem(
    "Cairo", "Cairo, Egypt’s sprawling capital", "Cairo",
    null, null
)