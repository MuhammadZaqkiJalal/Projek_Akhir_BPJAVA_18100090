package com.informatika18c.databarang.network.model

import com.google.gson.annotations.SerializedName
import retrofit2.http.Field

data class ResponseActionBarang(

    @field:SerializedName("pesan")
    val pesan: Any? = null,

    @field:SerializedName("data")
    val data: List<Boolean?>? = null,

    @field:SerializedName("status")
    val status: String? = null
)