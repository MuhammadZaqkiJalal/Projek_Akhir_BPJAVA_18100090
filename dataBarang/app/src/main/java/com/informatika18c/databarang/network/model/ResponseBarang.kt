package com.informatika18c.databarang.network.model

import com.google.gson.annotations.SerializedName

data class ResponseBarang(

    @field:SerializedName("pesan")
    val pesan: String? = null,

    @field:SerializedName("data")
    val data: List<DataItem?>? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)

data class DataItem(

    @field:SerializedName("namaBarang")
    val namaBarang: String? = null,

    @field:SerializedName("Id")
    val id: String? = null,

    @field:SerializedName("jmlBarang")
    val jumlahBarang: String? = null
)