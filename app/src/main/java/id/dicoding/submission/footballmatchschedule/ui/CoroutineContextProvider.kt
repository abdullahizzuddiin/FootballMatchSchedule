package id.dicoding.submission.footballmatchschedule.ui

import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext

open class CoroutineContextProvider {
    open val main: CoroutineContext by lazy { UI }
    open val io: CoroutineContext by lazy { Dispatchers.IO }
}