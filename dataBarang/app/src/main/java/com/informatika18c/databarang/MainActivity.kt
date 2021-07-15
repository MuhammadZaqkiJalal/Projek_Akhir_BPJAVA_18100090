package com.informatika18c.databarang

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.informatika18c.databarang.adapter.ListContent
import com.informatika18c.databarang.network.koneksi
import com.informatika18c.databarang.network.model.ResponseBarang
import com.informatika18c.databarang.network.model.ResponseUserItem
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import service.SessionPreferences
import kotlin.math.log


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sessionPreferences = SessionPreferences(this)
        cekSession()
        tv_username.text = sessionPreferences.getUserName()
//          setSupportActionBar(findViewById(R.id.toolbar))
//
        findViewById<FloatingActionButton>(R.id,fab).setOnClickListener { view ->
            val i = Intent(this, InsertDataActivity::class.java)
            startActivity(i)
        }
        getData()
    }
    fun cekSession(){
        sessionPreferences = SessionPreferences(this)
        val userName = sessionPreferences.getUserName()
        if (userName == null){
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
    }
    public fun getData(){
        koneksi.service.getBarang().enqueue(object : Callback<ResponseBarang>{
            override fun onFailure(call: Call<ResponseBarang>, t: Throwable) {
                Log.d("pesan1", t.localizedMessage)
            }

            override fun onResponse(
                call: Call<ResponseBarang>,
                response: Response<ResponseBarang>
            ){
                if (response.isSuccessful){
                    val dataBody = response.body()
                    val datacontent = dataBody!!.data
                    Log.d("pesan", datacontent.toString())

                    val rvAdapter = ListContent(datacontent, this@MainActivity)
                    rv_data_barang.apply {
                        adapter = rvAdapter
                        layoutManager = LinearLayoutManager(this@MainActivity)
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getData()
    }
}