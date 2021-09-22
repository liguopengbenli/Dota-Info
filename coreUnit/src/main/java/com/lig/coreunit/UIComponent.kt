package com.lig.coreunit

sealed class UIComponent {
    data class Dialog(
        val titile: String,
        val description: String,
    ): UIComponent()

    data class None(
        val message: String
    ): UIComponent()


}
