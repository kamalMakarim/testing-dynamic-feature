package com.kamal.testingdynamicmodule

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

import androidx.appcompat.app.AppCompatActivity
import com.kamal.testingdynamicmodule.dynamic_module.DynamicDeliveryCallback
import com.kamal.testingdynamicmodule.dynamic_module.DynamicModuleDownloadUtil
import com.kamal.testingdynamicmodule.model.BaseResponse
import com.kamal.testingdynamicmodule.model.User
import com.kamal.testingdynamicmodule.request.BaseApiService
import com.kamal.testingdynamicmodule.request.UtilsApi


class LoginActivity : AppCompatActivity(), DynamicDeliveryCallback {
    private val ADMIN_FEATURE_MODULE = "feature_admin"
    private lateinit var loginButton: Button
    private lateinit var registerNow: TextView
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var mApiService: BaseApiService
    private lateinit var mContext: Context
    private lateinit var dynamicModuleDownloadUtil: DynamicModuleDownloadUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dynamicModuleDownloadUtil = DynamicModuleDownloadUtil(baseContext, this)
        setContentView(R.layout.activity_login)
        this.loginButton = findViewById<Button>(R.id.login_button)
        this.registerNow = findViewById<TextView>(R.id.create_button)
        this.email = findViewById<EditText>(R.id.login_email)
        this.password = findViewById<EditText>(R.id.login_password)
        this.mApiService = UtilsApi.apiService
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
        mApiService.login(emailS, passwordS)
            .enqueue(object : Callback<BaseResponse<User>> {
                override fun onResponse(
                    call: Call<BaseResponse<User>>,
                    response: Response<BaseResponse<User>>
                ) {
                    if (response.isSuccessful) {
                        val res = response.body()
                        if (res != null && res.success) {
                            loggedUser = res.payload as User?
                            if(loggedUser!!.isUserAdmin()){
                                dynamicModuleDownloadUtil.downloadDynamicModule("feature_admin")
                            }
                            downloadDynamicModule()
                            openAdminFeature()
                        } else {
                            Toast.makeText(mContext, res?.message ?: "Login failed", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(mContext, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<BaseResponse<User>>, t: Throwable) {
                    System.out.println(t.message)
                    Toast.makeText(mContext, "Problem with the server: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })

    }

    private fun openAdminFeature(){
        if (dynamicModuleDownloadUtil.isModuleDownloaded(ADMIN_FEATURE_MODULE)) {
            System.out.println("Module is already downloaded.\n")
            startAdminHomActivity()
        }
    }

    private fun startAdminHomActivity() {
        val intent = Intent()
        intent.setClassName(
            "com.kamal.testingdynamicmodule",
            "com.kamal.feature_admin.AdminHomeActivity"
        )
        startActivity(intent)
    }

    private fun downloadDynamicModule() {
        System.out.println("Call for download.\n")
        dynamicModuleDownloadUtil.downloadDynamicModule(ADMIN_FEATURE_MODULE)
    }

    companion object {
        var loggedUser: User? = null
    }

    override fun onDownloadCompleted() {
        System.out.println("Module download completed.")
    }

    override fun onDownloading() {
        System.out.println("Downloading...")
    }

    override fun onInstallSuccess() {
        System.out.println( "Module install Success!\n")
        startAdminHomActivity()
    }

    override fun onFailed(errorMessage: String) {
        System.out.println(errorMessage)
    }
}
