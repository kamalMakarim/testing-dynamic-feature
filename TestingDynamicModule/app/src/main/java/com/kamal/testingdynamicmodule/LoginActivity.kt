package com.kamal.testingdynamicmodule

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kamal.testingdynamicmodule.dynamic_module.DynamicDeliveryCallback
import com.kamal.testingdynamicmodule.dynamic_module.DynamicModuleDownloadUtil

class LoginActivity : AppCompatActivity(), DynamicDeliveryCallback {
    private lateinit var loginButton: Button
    private lateinit var registerNow: TextView
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var mContext: Context
    private lateinit var dynamicModuleDownloadUtil: DynamicModuleDownloadUtil

    private val moduleMap = mapOf(
        "user1" to Pair("feature_user1", "MainActivity1"),
        "user2" to Pair("feature_user2", "MainActivity2"),
        "user3" to Pair("feature_user3", "MainActivity3"),
        "user4" to Pair("feature_user4", "MainActivity4"),
        "user5" to Pair("feature_user5", "MainActivity5"),
        "user6" to Pair("feature_user6", "MainActivity6"),
        "user7" to Pair("feature_user7", "MainActivity7"),
        "user8" to Pair("feature_user8", "MainActivity8"),
        "user9" to Pair("feature_user9", "MainActivity9"),
        "user10" to Pair("feature_user10", "MainActivity10")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dynamicModuleDownloadUtil = DynamicModuleDownloadUtil(baseContext, this)
        setContentView(R.layout.activity_login)

        this.loginButton = findViewById(R.id.login_button)
        this.registerNow = findViewById(R.id.create_button)
        this.email = findViewById(R.id.login_email)
        this.password = findViewById(R.id.login_password)
        this.mContext = this

        loginButton.setOnClickListener { handleLogin() }

        supportActionBar?.hide()
        actionBar?.hide()
    }

    private fun handleLogin() {
        val emailS = email.text.toString().trim()
        val passwordS = password.text.toString().trim()
        if (emailS.isEmpty() || passwordS.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show()
            return
        } else {
            openFeature(emailS)
        }
    }

    private fun openFeature(username: String) {
        val (module, _) = moduleMap[username] ?: run {
            Toast.makeText(mContext, "No feature available for this user", Toast.LENGTH_SHORT).show()
            return
        }

        if (dynamicModuleDownloadUtil.isModuleDownloaded(module)) {
            println("Module is already downloaded.\n")
            startFeatureActivity(username)
        } else {
            println("Downloading module $module.\n")
            dynamicModuleDownloadUtil.downloadDynamicModule(module)
        }
    }

    private fun startFeatureActivity(username: String) {
        val (moduleName, activity) = moduleMap[username] ?: run {
            Toast.makeText(mContext, "No activity available for this user", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent().apply {
            setClassName(
                "com.kamal.testingdynamicmodule",
                "com.kamal.$moduleName.$activity"
            )
        }
        startActivity(intent)
    }

    override fun onDownloadCompleted() {
        println("Module download completed.")
    }

    override fun onDownloading() {
        println("Downloading...")
    }

    override fun onInstallSuccess(moduleName: String) {
        println("Module install Success!\n")

        val username = moduleMap.entries.find { it.value.first == moduleName }?.key ?: run {
            Toast.makeText(mContext, "Could not find user for module $moduleName", Toast.LENGTH_SHORT).show()
            return
        }

        startFeatureActivity(username)
    }

    override fun onFailed(errorMessage: String) {
        println(errorMessage)
    }
}