package dev.tomoya0x00.mutablestatetest

import androidx.compose.runtime.snapshots.Snapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.newSingleThreadContext
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * To be able to use snapshotFlow in test without Compose, We have to take snapshots.
 * So this rule takes snapshots regularly.
 *
 * What's about snapshotFlow:
 * https://developer.android.com/jetpack/compose/side-effects#snapshotFlow
 */
@DelicateCoroutinesApi
class ComposeStateTestRule(
    snapshotIntervalMilliSec: Long = 1L
) : TestWatcher() {

    private val dispatcher = newSingleThreadContext(name = "snapshot")
    private val scope = CoroutineScope(SupervisorJob() + dispatcher)
    private var job: Job? = null

    private val snapshotTaker = flow<Unit> {
        coroutineScope {
            while(isActive) {
                delay(snapshotIntervalMilliSec)
                Snapshot.takeSnapshot { }
            }
        }
    }

    override fun starting(description: Description?) {
        job = snapshotTaker.launchIn(scope)
    }

    override fun finished(description: Description?) {
        job?.cancel()
        job = null
    }
}
