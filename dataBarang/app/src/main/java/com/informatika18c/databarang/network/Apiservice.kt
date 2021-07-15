package com.informatika18c.databarang.network

import com.informatika18c.databarang.network.model.ResponseActionBarang
import com.informatika18c.databarang.network.model.ResponseBarang
import com.informatika18c.databarang.network.model.ResponseUserItem
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface Apiservice {
    @GET("read.php")
    fun getBarang(): Call<ResponseBarang>

    @FormUrlEncoded
    @POST("create.php")
    fun insertBarang(
        @Field("Nama_barang") namaBarang: String?,
        @Field("Jumlah_barang") jmlBarang: String?
    ): Call<ResponseActionBarang>

    @FormUrlEncoded
    @POST("update.php")
    fun updateBarang(
        @Field("id") id: String?,
        @Field("Nama_barang") namaBarang: String?,
        @Field("Jumlah_barang") jmlBarang: String?
    ): Call<ResponseActionBarang>

    @FormUrlEncoded
    @POST("delete.php")
    fun deleteBarang(
        @Field("id") id: String?
    ): Call<ResponseActionBarang>
}