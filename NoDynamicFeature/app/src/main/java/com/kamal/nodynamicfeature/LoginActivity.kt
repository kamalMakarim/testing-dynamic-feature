package com.kamal.nodynamicfeature

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
import com.kamal.nodynamicfeature.model.BaseResponse
import com.kamal.nodynamicfeature.model.User
import com.kamal.nodynamicfeature.request.BaseApiService
import com.kamal.nodynamicfeature.request.UtilsApi


class LoginActivity : AppCompatActivity(){
    private lateinit var loginButton: Button
    private lateinit var registerNow: TextView
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var mApiService: BaseApiService
    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
                                val intent = Intent(mContext, AdminHomeActivity::class.java)
                                startActivity(intent)
                            }
                            else{
                                val intent = Intent(mContext, UserHomeActivity::class.java)
                                startActivity(intent)
                            }
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

    companion object {
        var loggedUser: User? = null
    }
}