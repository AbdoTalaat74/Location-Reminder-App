package com.udacity.project4.locationreminders.reminderslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udacity.project4.locationreminders.data.FakeDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.locationreminders.MainCoroutineRule
import com.udacity.project4.locationreminders.getOrAwaitValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class RemindersListViewModelTest {

    private lateinit var fakeDataSource: FakeDataSource
    private lateinit var remindersListviewModel: RemindersListViewModel

    private val reminderItem = ReminderDTO(
        title = "Test",
        description = "item for testing",
        location = "test location",
        latitude = 34.434343,
        longitude = 42.33265556
    )

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutinesRule = MainCoroutineRule()


    @Before
    fun init() {
        fakeDataSource = FakeDataSource()
        remindersListviewModel = RemindersListViewModel(
            ApplicationProvider.getApplicationContext(),
            fakeDataSource
        )
    }

    @Test
    fun getReminders_showsSnackBar() {
        mainCoroutinesRule.pauseDispatcher()
        fakeDataSource.setShouldReturnError(true)
        remindersListviewModel.loadReminders()
        mainCoroutinesRule.resumeDispatcher()
        assertThat(remindersListviewModel.showSnackBar.getOrAwaitValue()).isEqualTo("Error getting reminders")
    }

    @Test
    fun addReminder_resultNotEmpty() = runBlockingTest {

        fakeDataSource.saveReminder(reminderItem)
        remindersListviewModel.loadReminders()

        assertThat(remindersListviewModel.showLoading.getOrAwaitValue()).isFalse()
        assertThat(remindersListviewModel.remindersList.getOrAwaitValue()?.isEmpty()).isFalse()
        assertThat(remindersListviewModel.showNoData.getOrAwaitValue()).isFalse()
    }

    @Test
    fun noReminders_resultEmpty() = runBlockingTest {

        fakeDataSource.deleteAllReminders()

        remindersListviewModel.loadReminders()

        assertThat(remindersListviewModel.remindersList.getOrAwaitValue()?.isEmpty()).isTrue()
        assertThat(remindersListviewModel.showNoData.getOrAwaitValue()).isTrue()
    }

    @Test
    fun noReminders_showsLoading() = runBlockingTest {
        mainCoroutinesRule.pauseDispatcher()
        remindersListviewModel.loadReminders()
        assertThat(remindersListviewModel.showLoading.getOrAwaitValue()).isTrue()

        mainCoroutinesRule.resumeDispatcher()
        assertThat(remindersListviewModel.showLoading.getOrAwaitValue()).isFalse()
    }

    @After
    fun tearDown() {
        stopKoin()
    }

}