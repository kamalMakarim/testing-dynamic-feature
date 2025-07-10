package com.kamal.testingdynamicmodule.dynamic_module

interface DynamicDeliveryCallback {
    fun onDownloading()
    fun onDownloadCompleted()
    fun onInstallSuccess(moduleName: String)
    fun onFailed(errorMessage: String)
}