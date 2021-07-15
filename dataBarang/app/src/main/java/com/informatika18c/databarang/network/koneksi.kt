package com.informatika18c.databarang.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class koneksi {
    companion object{
        val baseUrl = "http:/192.168.43.198/dabar/api/"
        var retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var service: Apiservice = retrofit.create(Apiservice::class.java)
    }
}