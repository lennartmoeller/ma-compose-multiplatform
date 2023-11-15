package com.lennartmoeller.ma.composemultiplatform.ui.form

class Form {
    private var inputValues = mutableMapOf<String, Any?>()
    private var inputErrors = mutableMapOf<String, Boolean>()

    fun setFormInputFeedback(id: String, value: Any?, hasError: Boolean) {
        inputValues[id] = value
        inputErrors[id] = hasError
    }

    fun hasErrors(): Boolean = inputErrors.values.any { it }

    fun getValues(): MutableMap<String, Any?> = inputValues
}
