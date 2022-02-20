package dev.tomoya0x00.mutablestatetest

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class MainCoroutineRule(
    private val dispatcherCreator: () -> TestDispatcher = { UnconfinedTestDispatcher() }
) : TestWatcher() {

    override fun starting(description: Description?) {
        Dispatchers.setMain(dispatcherCreator.invoke())
    }

    override fun finished(description: Description?) {
        Dispatchers.resetMain()
    }
}
