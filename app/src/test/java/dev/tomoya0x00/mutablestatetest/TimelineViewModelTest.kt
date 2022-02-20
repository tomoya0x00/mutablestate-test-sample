package dev.tomoya0x00.mutablestatetest

import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeout
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
class TimelineViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val composeStateTestRule = ComposeStateTestRule()

    @Before
    fun setUp() {
        // do nothing
    }

    @After
    fun tearDown() {
        // do nothing
    }

    @Test
    fun testRefreshTimeline() = runTest {
        val viewModel = TimelineViewModel()
        var output: List<TimelineUiState>? = null

        val job = launch {
            output = snapshotFlow { viewModel.uiState }
                .take(2)
                .toList()
        }

        viewModel.refreshTimeline()
        job.join()

        assert(output?.size == 2)
        assert(output?.get(0)?.isLoading == true)
        assert(output?.get(1)?.isLoading == false)
        assert(output?.get(1)?.tweets == listOf("Android 13 Tiramisu", "Android 12L"))
    }
}