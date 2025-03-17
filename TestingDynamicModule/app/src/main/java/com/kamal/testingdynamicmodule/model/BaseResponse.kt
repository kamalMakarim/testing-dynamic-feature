package com.kamal.testingdynamicmodule.model

class BaseResponse<T> {
    var success: Boolean = false
    var message: String? = null
    var payload: T? = null
}