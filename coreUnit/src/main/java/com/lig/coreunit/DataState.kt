import com.lig.coreunit.ProgressBarState
import com.lig.coreunit.UIComponent

sealed class DataState<T> {

    data class Response<T>(
        val uiComponent: UIComponent
    ) : DataState<T>()

    data class Data<T>(
        val data: T? = null,
    ) : DataState<T>()

    data class Loading<T>(
        val progressBarState: ProgressBarState
    ): DataState<T>()

}