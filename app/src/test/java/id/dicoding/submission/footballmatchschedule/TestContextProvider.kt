package id.dicoding.submission.footballmatchschedule

import id.dicoding.submission.footballmatchschedule.ui.CoroutineContextProvider
import kotlinx.coroutines.experimental.Dispatchers
import kotlin.coroutines.experimental.CoroutineContext

class TestContextProvider : CoroutineContextProvider() {
    override val main: CoroutineContext = Dispatchers.Unconfined
    override val io: CoroutineContext = Dispatchers.Unconfined
}