package com.informatika18c.databarang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.informatika18c.databarang.network.koneksi
import kotlinx.android.synthetic.main.activity_insert_data.*
import kotlinx.android.synthetic.main.activity_insert_data.btn_submit
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Response
import service.SessionPreferences
import javax.security.auth.callback.Callback

class RegisterActivity : AppCompatActivity() {
    private lateinit var sessionPreferences: SessionPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(R.layout.activity_register)
        btn_submit.setOnClickListener {
            val userName = et_username.text.toString()
            val password = et_password.text.toString()
            if(userName.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Form tidak boleh kosong!", Toast.LENGTH_LONG).show()
            }else{
                actionData(userName, password)
            }
        }
        btn_clean.setOnClickListener {
            formClear()
        }
        tv_disini.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
    fun actionData(username : String, password : String){
        koneksi.service.register(username, password).enqueue(object : Callback<ResponseAdmin>) {
            override fun onFailure(call:<ResponseAdmin>, t: Throwable) {
                Log.d("pesan1", t.localizedMessage)
            }

            override fun onResponse(call: Call<ResponseAdmin>, response: Response<ResponseAdmin>) {
                if (response.isSuccessful){
                    val resbody = response.body()
                    val resStatus = resbody?.status
                    val resUserName = resbody?.data?.get(0)?.username
                    Log.d("pesan", resUserName.toString())
                    if(resStatus == true){
                        sessionPreferences = SessionPreferences(this@RegisterActivity)
                        sessionPreferences.actionLogin(resUserName.toString())
                        val i = Intent(this@RegisterActivity, MainActivity)
                        startActivity(i)
                        finish()
                    }else if (resStatus == false){
                        Toast.makeText(this@RegisterActivity
                            "Username atau Password Anda Salah!", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
    fun formClear(){
        et_username.text.clear()
        et_password.text.clear()
    }
}