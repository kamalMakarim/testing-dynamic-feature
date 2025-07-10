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
        this.loginButton = findViewById<Button>(R.id.login_button)
        this.registerNow = findViewById<TextView>(R.id.create_button)
        this.email = findViewById<EditText>(R.id.login_email)
        this.password = findViewById<EditText>(R.id.login_password)
        this.mContext = this

        loginButton.setOnClickListener(View.OnClickListener { _: View? ->
            handleLogin()
        })

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        if (actionBar != null) {
            actionBar!!.hide()
        }
    }

    protected fun handleLogin() {
        val emailS = email.text.toString()
        val passwordS = password.text.toString()
        if (emailS.isEmpty() || passwordS.isEmpty()) {
            Toast.makeText(
                mContext, "Field cannot be empty",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        else{
            openFeature(emailS)
        }
    }

    private fun openFeature(username: String) {
        val (module, activity) = moduleMap[username] ?: run {
            Toast.makeText(mContext, "No feature available for this user", Toast.LENGTH_SHORT).show()
            return
        }
        if (dynamicModuleDownloadUtil.isModuleDownloaded(module)) {
            System.out.println("Module is already downloaded.\n")
            startFeatureActivity(module)
        }
        else{
            System.out.println("Downloading module $module.\n")
            dynamicModuleDownloadUtil.downloadDynamicModule(module)
        }
    }

    private fun startFeatureActivity(moduleName: String){
        val activity = moduleMap[moduleName]?.second ?: run {
            Toast.makeText(mContext, "No activity available for this user", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent()
        intent.setClassName(
            "com.kamal.testingdynamicmodule",
            "com.kamal.$moduleName.$activity"
        )
        startActivity(intent)
    }

    override fun onDownloadCompleted() {
        System.out.println("Module download completed.")
    }

    override fun onDownloading() {
        System.out.println("Downloading...")
    }

    override fun onInstallSuccess(moduleName: String) {
        System.out.println( "Module install Success!\n")
        startFeatureActivity(moduleName)
    }

    override fun onFailed(errorMessage: String) {
        System.out.println(errorMessage)
    }
}
